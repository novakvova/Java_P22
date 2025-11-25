package org.example.validators;

import lombok.RequiredArgsConstructor;
import org.example.data.dto.account.RegisterUserDTO;
import org.example.data.dto.validation.FieldError;
import org.example.interfaces.ValidationRule;
import org.example.interfaces.Validator;
import org.example.repository.IUserRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RegisterUserValidator implements Validator<RegisterUserDTO> {

    private final IUserRepository userRepository;

    @Override
    public List<FieldError> validate(RegisterUserDTO dto) {
        List<FieldError> errors = new ArrayList<>();

        List<ValidationRule<RegisterUserDTO>> rules = List.of(
                o -> !StringUtils.hasText(o.getName()) ? new FieldError("name", "Ім'я є обов'язковим") : null,
                o -> o.getName() != null && o.getName().length() > 100 ? new FieldError("name", "Ім'я не може бути довше 100 символів") : null,
                o -> !StringUtils.hasText(o.getLastName()) ? new FieldError("lastName", "Прізвище є обов'язковим") : null,
                o -> o.getLastName() != null && o.getLastName().length() > 100 ? new FieldError("lastName", "Прізвище не може бути довше 100 символів") : null,
                o -> !StringUtils.hasText(o.getEmail()) ? new FieldError("email", "Електронна пошта є обов’язковою") : null,
                o -> o.getEmail() != null && !o.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$") ? new FieldError("email", "Некоректний формат електронної пошти") : null,
                o -> {
                    if (o.getEmail() != null && userRepository.findByEmail(o.getEmail()).isPresent()) {
                        return new FieldError("email", "Користувач з такою поштою вже зареєстрований");
                    }
                    return null;
                },
                o -> !StringUtils.hasText(o.getPhone()) ? new FieldError("phone", "Телефон є обов'язковим") : null,
                o -> o.getPhone() != null && !o.getPhone().matches("^\\+?[0-9]{10,15}$")
                        ? new FieldError("phone", "Некоректний формат телефону") : null,
                o -> {
                    if (o.getPhone() != null && userRepository.findByPhone(o.getPhone()).isPresent()) {
                        return new FieldError("phone", "Користувач з таким телефоном вже зареєстрований");
                    }
                    return null;
                },
                o -> !StringUtils.hasText(o.getPassword()) ? new FieldError("password", "Пароль є обов'язковим") : null,
                o -> {
                    if (o.getPassword() == null) return null;
                    String pwd = o.getPassword();
                    if (pwd.length() < 6) return new FieldError("password", "Пароль повинен містити щонайменше 6 символів");
                    if (!pwd.matches(".*[A-Z].*")) return new FieldError("password", "Пароль повинен містити хоча б одну латинську велику літеру");
                    if (!pwd.matches(".*[a-z].*")) return new FieldError("password", "Пароль повинен містити хоча б одну латинську малу літеру");
                    if (!pwd.matches(".*\\d.*")) return new FieldError("password", "Пароль повинен містити хоча б одну цифру");
                    if (!pwd.matches(".*[^a-zA-Z0-9].*")) return new FieldError("password", "Пароль повинен містити хоча б один спеціальний символ");
                    return null;
                }
        );

        for (ValidationRule<RegisterUserDTO> rule : rules) {
            FieldError error = rule.validate(dto);
            if (error != null) errors.add(error);
        }

        return errors;
    }
}