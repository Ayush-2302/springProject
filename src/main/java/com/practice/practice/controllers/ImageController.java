package com.practice.practice.controllers;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.practice.practice.payloads.FileResponse;
import com.practice.practice.serviceimple.FileImple;

import java.io.IOException;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/image")
public class ImageController {

    @Autowired
    private FileImple fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/uploadImage")
    public ResponseEntity<FileResponse> uploadImage(@RequestParam("image") MultipartFile image)
            throws java.io.IOException {
        String uploadImage;
        try {
            uploadImage = fileService.uploadImage(path, image);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new FileResponse(null, "Image is not Uploaded"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new FileResponse(uploadImage, "Image is successfully Uploaded"), HttpStatus.OK);

    }

    @GetMapping(value = "/{url}", produces = MediaType.IMAGE_JPEG_VALUE)
    public String downloadImage(@PathVariable String url, HttpServletResponse resopnse) throws IOException {
        InputStream resource = fileService.getResource(path, url);
        resopnse.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, resopnse.getOutputStream());
        return new String();
    }

}
