package org.example.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.data.data_transfer_objects.ForgotPasswordDTO;
import org.example.data.data_transfer_objects.RegisterUserDTO;
import org.example.data.data_transfer_objects.ResetPasswordDTO;
import org.example.services.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registerUserDTO", new RegisterUserDTO());
        return "account/register";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", accountService.GetAllUsers());
        return "account/users";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute RegisterUserDTO form,
                               BindingResult bindingResult,
                               HttpServletRequest request,
                               Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "Перевірте правильність введених даних");
            return "account/register";
        }

        boolean success = accountService.registerUser(form, request);

        if (success) {
            model.addAttribute("message", "Реєстрація успішна!");
            return "redirect:/users";
        } else {
            model.addAttribute("message", "Користувач із таким іменем або email вже існує.");
            return "account/register";
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        return "account/login";
    }


    @GetMapping("/forgot-password")
    public String forgotPasswordForm(Model model) {
        model.addAttribute("forgotPasswordDTO", new ForgotPasswordDTO());
        return "account/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPasswordSubmit(
            @Valid @ModelAttribute ForgotPasswordDTO forgotPasswordDTO,
            BindingResult result,
            Model model,
            HttpServletRequest request) {

        if (result.hasErrors()) {
            return "account/forgot-password";
        }

        boolean sent = accountService.forgotPassword(forgotPasswordDTO, request);
        if (sent) {
            return "account/forgot-password-success";
        } else {
            model.addAttribute("error", "Користувача з таким email не знайдено.");
            return "account/forgot-password";
        }
    }

    @GetMapping("/reset-password")
    public String resetPasswordForm(@RequestParam("token") String token, Model model) {
        ResetPasswordDTO dto = new ResetPasswordDTO();
        dto.setToken(token);
        model.addAttribute("resetPasswordDTO", dto);
        return "account/reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPasswordSubmit(
            @Valid @ModelAttribute ResetPasswordDTO resetPasswordDTO,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "account/reset-password";
        }

        boolean success = accountService.resetPassword(resetPasswordDTO);
        if (success) {
            model.addAttribute("message", "Пароль успішно змінено! Тепер ви можете увійти.");
            return "account/login";
        } else {
            model.addAttribute("error", "Токен недійсний або паролі не співпадають.");
            return "account/reset-password";
        }
    }
}