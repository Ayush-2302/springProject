package com.practice.practice.serviceimple;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.practice.practice.service.FileService;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.UUID;
import java.nio.file.Files;

@Service
public class FileImple implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("The file is empty.");
        }

        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new IllegalArgumentException("The file name is invalid.");
        }

        // Generate a unique filename

        String randomId = UUID.randomUUID().toString();

        String filename1 = randomId.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));

        String fullPath = Paths.get(path, filename1).toString();

        // String filePath = path + File.separator + originalFilename;

        // create directory if not exist
        File folder = new File(path);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        // coping file
        Files.copy(file.getInputStream(), Paths.get(fullPath));

        return originalFilename;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        // String fullPath = path + File.separator + fileName;
        String fullPath = Paths.get(path, fileName).toString();
        InputStream is = new FileInputStream(fullPath);
        return is;
    }

}
