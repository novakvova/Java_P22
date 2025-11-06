package org.example.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.services.AccountService;
import org.example.services.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final FileService fileService;

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", accountService.GetAllUsers());
        return "account/users";
    }

    @GetMapping("/register")
    public String register() {
        return "account/register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam(required = false) MultipartFile imageFile,
                               HttpServletRequest request,
                               Model model) {

        String fileName = fileService.load(imageFile);

        boolean success = accountService.registerUser(username, password, fileName, request);
        if (success) {
            model.addAttribute("message", "Реєстрація успішна!");

            return "redirect:/users";
        } else {
            model.addAttribute("message", "Користувач із таким іменем вже існує.");
        }

        return "account/register";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "account/login";
    }

}
