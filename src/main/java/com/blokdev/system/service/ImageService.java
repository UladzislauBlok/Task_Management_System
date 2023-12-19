package com.blokdev.system.service;

import com.blokdev.system.util.PropertiesUtil;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ImageService {
    private static final String IMAGE_FOLDER = "users";
    private static final String UNIQUE_IMAGE_NAME_PATTERN = "%s/%s/%d_%s";
    private static final ImageService INSTANCE = new ImageService();
    private final String baseImagePath = PropertiesUtil.get("image.base.url");

    @SneakyThrows
    public void upload(Long id, String submittedFileName, InputStream imageContent) {
        var imageFullPath = getFullPath(id, submittedFileName);
        try (imageContent){
            Files.createDirectories(imageFullPath.getParent());
            Files.write(imageFullPath, imageContent.readAllBytes(), CREATE, TRUNCATE_EXISTING);
        }
    }

    @SneakyThrows
    public Optional<InputStream> getImage(Long id, String imageName) {
        var imageFullPath = getFullPath(id, imageName);

        return Files.exists(imageFullPath)
                ? Optional.of(Files.newInputStream(imageFullPath))
                : Optional.empty();
    }

    private Path getFullPath(Long id, String submittedFileName) {
        return Path.of(
                UNIQUE_IMAGE_NAME_PATTERN.formatted(baseImagePath, IMAGE_FOLDER, id, submittedFileName)
        );
    }

    public static ImageService getInstance() {
        return INSTANCE;
    }
}
