package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.data.dto.location.CityCreateDTO;
import org.example.data.dto.location.CityItemDTO;
import org.example.services.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
@Tag(name = "Cities", description = "Міста")
public class CityController {
    private final CityService cityService;

    @Operation(summary = "Створити нове місто")
    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CityItemDTO> create(@ModelAttribute CityCreateDTO dto) {
        CityItemDTO res = cityService.create(dto);

        return ResponseEntity.ok(res);
    }

    @Operation(summary = "Отримати список всіх міст")
    @GetMapping
    public ResponseEntity<List<CityItemDTO>> getAll() {
        return ResponseEntity.ok(cityService.getAll());
    }

    @Operation(summary = "Отримати місто за слагом")
    @GetMapping("/{slug}")
    public CityItemDTO getCityBySlug(@PathVariable("slug") String slug) {
        return cityService.getBySlug(slug);
    }


}
