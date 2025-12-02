package org.example.data.dto.file;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SaveImageFileDTO {
    @NotBlank
    private MultipartFile imageFile;
}
