package org.example.data.data_transfer_objects.product;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ProductCreateDTO {
    @NotBlank(message = "Ім'я обов'язкове")
    private String name;

    @NotBlank(message = "Slug обов'язковий")
    private String slug;

    @NotBlank(message = "Slug обов'язковий")
    private double price;

    @NotBlank(message = "Опис обов'язковий")
    private String description;

    private List<MultipartFile> imageFiles;

    @NotBlank(message = "Категорія обов'язкова")
    private Long categoryId;
}
