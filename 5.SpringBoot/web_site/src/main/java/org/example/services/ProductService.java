package org.example.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.data.data_transfer_objects.common.PageDTO;
import org.example.data.data_transfer_objects.common.PageResponseDTO;
import org.example.data.data_transfer_objects.product.ProductCreateDTO;
import org.example.data.data_transfer_objects.product.ProductItemDTO;
import org.example.data.data_transfer_objects.product.ProductListItemDTO;
import org.example.data.data_transfer_objects.search.ProductSearchDTO;
import org.example.mappers.ProductMapper;
import org.example.entities.CategoryEntity;
import org.example.entities.ImageEntity;
import org.example.entities.ProductEntity;
import org.example.repositories.ICategoryRepository;
import org.example.repositories.IProductRepository;
import org.example.specifications.ProductSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final FileService fileService;

    @Transactional
    public ProductItemDTO create(ProductCreateDTO dto) {
        CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Категорію не знайдено"));

        ProductEntity entity = productMapper.fromCreateDTO(dto, category);

        List<ImageEntity> imageEntities = new ArrayList<>();

        if (dto.getImageFiles() != null && !dto.getImageFiles().isEmpty()) {
            short priority = 0;
            for (MultipartFile file : dto.getImageFiles()) {
                String fileName = fileService.load(file);

                ImageEntity image = new ImageEntity();
                image.setName(fileName);
                image.setPriority(priority++);
                image.setProduct(entity);

                imageEntities.add(image);
            }
        } else {
            String fallbackName = fileService.load("https://loremflickr.com/800/600");
            ImageEntity image = new ImageEntity();
            image.setName(fallbackName);
            image.setPriority((short) 0);
            image.setProduct(entity);
            imageEntities.add(image);
        }

        entity.setImages(imageEntities);
        productRepository.save(entity);

        return productMapper.toDTO(entity);
    }

    @Transactional
    public List<ProductListItemDTO> getAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toListItemDTO)
                .toList();
    }

    @Transactional()
    public PageResponseDTO<ProductListItemDTO> getAllPaginated(int page, int size,
                                                               ProductSearchDTO searchDTO) {
        Pageable pageable = PageRequest.of(page-1, size);

        Specification<ProductEntity> spec = ProductSpecifications.search(searchDTO);

        Page<ProductEntity> productPage = productRepository.findAll(spec, pageable);

        List<ProductListItemDTO> content = productPage.getContent()
                .stream()
                .map(productMapper::toListItemDTO)
                .toList();

        PageDTO pageDTO = PageDTO.builder()
                .currentPage(page-1)
                .totalPages(productPage.getTotalPages())
                .totalElements(productPage.getTotalElements())
                .pageSize(productPage.getSize())
                .hasNext(productPage.hasNext())
                .hasPrevious(productPage.hasPrevious())
                .isFirst(productPage.isFirst())
                .isLast(productPage.isLast())
                .build();

        return PageResponseDTO.<ProductListItemDTO>builder()
                .content(content)
                .page(pageDTO)
                .build();
    }

}