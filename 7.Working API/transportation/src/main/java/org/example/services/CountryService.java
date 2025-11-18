package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.data.dto.country.CountryCreateDTO;
import org.example.data.dto.country.CountryItemDTO;
import org.example.data.mappers.CountryMapper;
import org.example.entities.location.CountryEntity;
import org.example.repository.ICountryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CountryService {

    private final ICountryRepository countryRepository;
    private final CountryMapper countryMapper;
    private final FileService fileService;

    @Transactional
    public CountryItemDTO create(CountryCreateDTO dto) {
        if (countryRepository.existsBySlug(dto.getSlug())) {
            throw new IllegalArgumentException("Категорія зі slug '" + dto.getSlug() + "' вже існує");
        }

        CountryEntity entity = countryMapper.fromCreateDTO(dto);

        if(dto.getImage()!=null) {
            String fileName = fileService.load(dto.getImage());
            entity.setImage(fileName);
        }

        CountryEntity saved = countryRepository.save(entity);
        return countryMapper.toDto(saved);
    }

    public List<CountryItemDTO> getAll() {
        return countryRepository.findAll()
                .stream()
                .map(countryMapper::toDto)
                .collect(Collectors.toList());
    }

    public CountryItemDTO getBySlug(String slug) {
        CountryEntity entity = countryRepository.findBySlug(slug)
                .orElseThrow(() -> new IllegalArgumentException("Категорію не знайдено: " + slug));
        return countryMapper.toDto(entity);
    }
}
