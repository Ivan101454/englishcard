package com.ivan101454.englishcard.service.card;

import com.ivan101454.englishcard.config.StorageProperties;
import com.ivan101454.englishcard.exception.StorageException;
import com.ivan101454.englishcard.exception.StorageFileNotFoundException;
import com.ivan101454.englishcard.service.api.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;
@Service
public class FileSystemStorageService implements StorageService {
    private final Path rootLocation;
    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        if (properties.getLocation().trim().length() == 0) {
            throw new StorageException("Загружаемый файл не может быть пустым");
        }
        this.rootLocation = Paths.get(properties.getLocation());
    }

    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            Path destinationalFile = this.rootLocation.resolve(
                    Paths.get(file.getOriginalFilename()))
                            .normalize().toAbsolutePath();
            if (!destinationalFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException(
                        "Невозможно сохранить файл вне текущего каталога");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationalFile, StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new StorageException("Ошибка хранилища файлов");
        }
    }
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Ошибка чтения хранимого файла", e);
        }
    }
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return  resource;
            }
        else {
            throw new StorageFileNotFoundException(
                    "Не могу прочесть файл: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Не могу прочитать файл: " + filename, e);
        }
    }
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Не возможно инициализировать хранилище", e);
        }
    }

}
