package com.SaharaAmussmentPark.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SaharaAmussmentPark.Dto.ImagesDto;
import com.SaharaAmussmentPark.Dto.Message;
import com.SaharaAmussmentPark.service.ImagesService;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/MediaController")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Log4j2
public class MediaController {

    private final ImagesService imagesService;

    @PostMapping(value = "/uploadImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Message<ImagesDto>> uploadSingleImage(
            @RequestPart("file") MultipartFile file) {

        log.info("Uploading single image: {}", file.getOriginalFilename());

        Message<ImagesDto> response = imagesService.uploadImage(file);

        return ResponseEntity.status(response.getStatus() != null ? response.getStatus() : HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(response);
    }


    @GetMapping("/getAllImage")
    public ResponseEntity<Message<List<ImagesDto>>> getAllImages() {

        Message<List<ImagesDto>> response = imagesService.getAllImages();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/deleteImage")
    public ResponseEntity<Message<ImagesDto>> deleteImage(
            @RequestParam int iId) {

        log.info("Deleting image with id: {}", iId);

        Message<ImagesDto> response = imagesService.deleteImages(iId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/getImageByImageName")
    public ResponseEntity<Message<List<ImagesDto>>> getImagesByImageName(
            @RequestParam String imageName) {

        log.info("Fetching images by imageName: {}", imageName);

        Message<List<ImagesDto>> response =
                imagesService.getByImageName(imageName);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
