package org.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {
    @Value("${upload.dir}")
    private String uploadDir;

    @ModelAttribute("uploadDir")
    public String getUploadDir() {
        return uploadDir;
    }
}