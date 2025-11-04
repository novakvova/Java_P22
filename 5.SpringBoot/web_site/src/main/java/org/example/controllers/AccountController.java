package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.services.AccountService;
import org.example.services.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

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
                               Model model) {

        String fileName = fileService.load(imageFile);

        boolean success = accountService.registerUser(username, password, fileName);
        if (success) {
            model.addAttribute("message", "Реєстрація успішна!");

            return "redirect:/users";

        } else {
            model.addAttribute("message", "Користувач із таким іменем вже існує.");
        }

        return "account/register";
    }

}
