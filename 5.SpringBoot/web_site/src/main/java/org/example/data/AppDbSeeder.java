package org.example.data;

import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import com.ibm.icu.text.Transliterator;
import lombok.RequiredArgsConstructor;
import org.example.data.constants.RolesConstants;
import org.example.data.seed.CategorySeed;
import org.example.data.seed.ProductSeed;
import org.example.entities.CategoryEntity;
import org.example.entities.ImageEntity;
import org.example.entities.ProductEntity;
import org.example.entities.RoleEntity;
import org.example.mappers.CategoryMapper;
import org.example.mappers.ProductMapper;
import org.example.repositories.ICategoryRepository;
import org.example.repositories.IProductRepository;
import org.example.repositories.IRoleRepository;
import org.example.services.FileService;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class AppDbSeeder {

    private final IRoleRepository roleRepository;
    private final ICategoryRepository categoryRepository;
    private final IProductRepository productRepository;
    private final CategoryMapper categoryMapper;
    private final ProductMapper productMapper;
    private final Faker faker = new Faker(new Locale("uk"));
    private final Slugify slugify = Slugify.builder().build();
    private final FileService fileService;

    @PostConstruct
    public void seedData() {
        seedRoles();
        seedCategories();
        seedProducts();
    }

    private void seedRoles() {
        List<String> roles = RolesConstants.Roles;

        for (String roleName : roles) {
            boolean exists = roleRepository.findByName(roleName).isPresent();
            if (!exists) {
                RoleEntity role = new RoleEntity();
                role.setName(roleName);
                roleRepository.save(role);
                System.out.println("Додано роль: " + roleName);
            } else {
                System.out.println("Роль уже існує: " + roleName);
            }
        }
    }

    private void seedCategories(){
        if(categoryRepository.count() == 0){
            System.out.println("----------------Start category seeder ---------------");
            for(int i=0;i<5;i++){
                CategorySeed seed = new CategorySeed();
                seed.setName(faker.commerce().department());
                Transliterator transliterator = Transliterator.getInstance("Cyrillic-Latin");
                String latinText = transliterator.transliterate(seed.getName());
                String slug = slugify.slugify(latinText);

                seed.setSlug(slug);
                seed.setImageUrl("https://loremflickr.com/640/480/");
                CategoryEntity category = categoryMapper.toEntity(seed);
                category.setImage(fileService.load(seed.getImageUrl()));
                categoryRepository.save(category);
            }
            System.out.println("--------- Finish category seeder -----------");
        }else
            System.out.println("------------ Categories already exist ------------");
    }

    private void seedProducts(){
        if(productRepository.count() == 0) {
            var categories = categoryRepository.findAll();
            System.out.println("----------------Start products seeder ---------------");
            if(!categories.isEmpty()) {
                for (int i = 0; i < 12; i++) {
                    ProductSeed seed = new ProductSeed();
                    seed.setName(faker.commerce().productName());
                    Transliterator transliterator = Transliterator.getInstance("Cyrillic-Latin");
                    String latinText = transliterator.transliterate(seed.getName());
                    String slug = slugify.slugify(latinText);

                    seed.setSlug(slug);
                    seed.setPrice(faker.random().nextInt(1000, 10000));

                    seed.setDescription(faker.lorem().paragraph());

                    var randomCategory = categories.get(faker.random().nextInt(categories.size()));
                    seed.setCategoryId(randomCategory.getId());

                    ProductEntity product = productMapper.toEntity(seed);
                    product.setCategory(randomCategory);
                    int imagesCount = faker.random().nextInt(1,3);
                    String image;
                    List<ImageEntity> imageEntities = new ArrayList<>();
                    for (short j=0;j<imagesCount;j++){
                        ImageEntity img = new ImageEntity();
                        img.setName(fileService.load("https://loremflickr.com/640/480/"));
                        img.setPriority(j);
                        img.setProduct(product);
                        imageEntities.add(img);
                    }
                    product.setImages(imageEntities);
                    productRepository.save(product);
                }
                System.out.println("----------------Finish products seeder ---------------");
            }
        }
    }
}
