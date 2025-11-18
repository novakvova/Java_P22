package org.example.data.dto.country;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CountryCreateDTO {
    private String name;
    private String code;
    private String slug;
    private MultipartFile image;
}
