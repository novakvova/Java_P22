package org.example.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.data.dto.account.LoginDto;
import org.example.data.dto.account.RegisterUserDTO;
import org.example.data.dto.account.UserItemDTO;
import org.example.services.AccountService;
import org.example.validators.helpers.ValidatedDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
@Tag(name = "Account")
public class AccountController {

    private final AccountService accountService;

    @PostMapping(value = "/register", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserItemDTO> register(@ValidatedDto @ModelAttribute RegisterUserDTO dto) {
        return ResponseEntity.ok(accountService.registerUser(dto));
    }

    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody LoginDto dto) {
        try {
            var auth = accountService.login(dto);
            return ResponseEntity.ok(auth);
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body("Невірно введені дані! Спробуйте ще раз!");
        }
    }
}