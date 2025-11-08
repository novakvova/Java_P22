package org.example.data.data_transfer_objects;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class RegisterUserDTO {
    @NotBlank(message = "Ім’я користувача не може бути порожнім")
    private String username;

    @NotBlank(message = "Email не може бути порожнім")
    @Email(message = "Некоректний формат email. Приклад: e@e.e")
    private String email;

    @NotBlank(message = "Пароль не може бути порожнім")
    @Size(min = 6, message = "Пароль має містити принаймні 6 символів")
    private String password;

    private MultipartFile imageFile;
}
