package org.example;


import org.example.entities.UserEntity;
import org.example.repository.IUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.NoSuchElementException;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        //System.out.println("Привіт козаки і козачки :)");
    }

    @Bean
    CommandLineRunner runner(IUserRepository repository) {
        return args -> {
//            UserEntity user = new UserEntity();
//            user.setUsername("admin");
//            user.setPassword("");
//            repository.save(user);
//            UserEntity saved = repository.findById(user.getId()).orElseThrow(NoSuchElementException::new);
        };
    }
}