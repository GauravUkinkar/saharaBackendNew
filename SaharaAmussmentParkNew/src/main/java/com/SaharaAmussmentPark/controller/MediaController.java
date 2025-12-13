package com.SaharaAmussmentPark.controller;

import java.io.IOException;
import org.springframework.http.MediaType;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/images")
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
public class MediaController {

    @Autowired
    private ImagesService imagesService;
    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Message<List<ImagesDto>>> uploadImages(
            @RequestPart("data") ImagesDto request,
            @RequestPart("files") MultipartFile[] files) {

        Message<List<ImagesDto>> response = imagesService.uploadImages(files, request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    
    @GetMapping("/getAllImages")
    public ResponseEntity<Message<List<ImagesDto>>> getAllImages() {
        Message<List<ImagesDto>> response = imagesService.getAllImages();
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @DeleteMapping("/deleteImage")
    public ResponseEntity<Message<ImagesDto>> DeleteImage(@RequestParam int iId){
    	log.info("In usercontroller login() with request:{}", iId);
    	Message<ImagesDto> message = imagesService.deleteImages(iId);
    	HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
    	return ResponseEntity.status(httpStatus).body(message);
    	
    }
    
    @GetMapping("/getImageByImageName")
    public ResponseEntity<Message<List<ImagesDto>>> getImagesByImageName(
            @RequestParam String imageName) {

        log.info("In ImagesController getImagesByImageName() with request: {}", imageName);

        Message<List<ImagesDto>> message = imagesService.getByImageName(imageName);

        return ResponseEntity
                .status(message.getStatus())
                .body(message);
    }


}

