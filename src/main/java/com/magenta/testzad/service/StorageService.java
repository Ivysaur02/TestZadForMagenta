package com.magenta.testzad.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageService {

    public void store(MultipartFile file, String path) {
        try {
            String fileName = file.getOriginalFilename();
            InputStream is = file.getInputStream();

            Files.copy(is, Paths.get(path + fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
