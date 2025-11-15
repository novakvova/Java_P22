package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.data.data_transfer_objects.product.CategoryCreateDTO;
import org.example.data.data_transfer_objects.product.CategoryItemDTO;

import org.example.entities.CategoryEntity;
import org.example.mappers.CategoryMapper;
import org.example.repositories.ICategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final ICategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryItemDTO create(CategoryCreateDTO dto) {
        if (categoryRepository.existsBySlug(dto.getSlug())) {
            throw new IllegalArgumentException("Категорія зі slug '" + dto.getSlug() + "' вже існує");
        }

        CategoryEntity entity = categoryMapper.fromCreateDTO(dto);

        CategoryEntity saved = categoryRepository.save(entity);
        return categoryMapper.toDto(saved);
    }

    public List<CategoryItemDTO> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public CategoryItemDTO getBySlug(String slug) {
        CategoryEntity entity = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new IllegalArgumentException("Категорію не знайдено: " + slug));
        return categoryMapper.toDto(entity);
    }
}
