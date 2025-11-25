package org.example.data.dto.account;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class RegisterUserDTO {
    private String lastName;
    private String name;
    private String email;
    private String password;
    private String phone;
    private MultipartFile imageFile;
}
