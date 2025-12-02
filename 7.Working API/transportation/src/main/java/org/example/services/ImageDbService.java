package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.data.dto.file.SavedImageDTO;
import org.example.data.mappers.ImageMapper;
import org.example.entities.common.ImageEntity;
import org.example.repository.IImageRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageDbService {
    private final IImageRepository imageRepository;
    private final ImageMapper imageMapper;
    private final FileService fileService;

    public SavedImageDTO save(String fileName) {
        ImageEntity img = new ImageEntity();
        img.setImageName(fileName);

        img = imageRepository.save(img);

        return imageMapper.toDto(img);
    }

    //@Scheduled(fixedRate = 60_000) // раз на хвилину
    @Scheduled(cron = "0 0 0 * * ?") // 12 ночі
    public void cleanOrphanImages() {
        List<ImageEntity> orphanImages = imageRepository.findByCityIsNull();

        for (ImageEntity img : orphanImages) {
            fileService.remove(img.getImageName());
            imageRepository.delete(img);
            System.out.println("Видалено зображення: " + img.getImageName());
        }

        if (!orphanImages.isEmpty()) {
            System.out.println("Очистка завершена. Видалено " + orphanImages.size() + " зображень.");
        }
    }
}
