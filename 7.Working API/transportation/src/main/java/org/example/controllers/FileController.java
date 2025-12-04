package org.example.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.data.dto.file.SaveImageFileDTO;
import org.example.data.dto.file.SavedImageDTO;
import org.example.services.FileService;
import org.example.services.ImageDbService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
@Tag(name = "File")
public class FileController {

    private final FileService fileService;
    private final ImageDbService imageDbService;

    @PostMapping(value = "/saveImage", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SavedImageDTO> saveImage(@ModelAttribute SaveImageFileDTO dto) {
        String res = fileService.load(dto.getImageFile());

        SavedImageDTO response = imageDbService.save(res);

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/saveImageFromUrl", consumes = "text/plain")
    public ResponseEntity<SavedImageDTO> saveImageFromUrl(@RequestBody String url) {

        String res = fileService.load(url);
        SavedImageDTO response = imageDbService.save(res);

        return ResponseEntity.ok(response);
    }
}