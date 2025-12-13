package com.SaharaAmussmentPark.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SaharaAmussmentPark.Dto.Message;
import com.SaharaAmussmentPark.Dto.ViedoAndAboutSectionDto;
import com.SaharaAmussmentPark.service.ViedoAndAboutSectionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/viedoAndAboutSection")
@RequiredArgsConstructor
@CrossOrigin(origins = { "*" }, allowedHeaders = { "*" })

public class ViedoAndAboutSectionController {
	private final ViedoAndAboutSectionService viedoAndAboutSectionService;
	
	
	  @PostMapping("/add")
	    public ResponseEntity<Message<ViedoAndAboutSectionDto>> addViedoSection(
	            @RequestPart("data") String viedoSectionJson,
	            @RequestPart("image") MultipartFile imageFile,
	            @RequestPart(value = "desktopVideo", required = false) MultipartFile desktopVideo,
	            @RequestPart(value = "tabVideo", required = false) MultipartFile tabVideo,
	            @RequestPart(value = "mobVideo", required = false) MultipartFile mobVideo) 
	            throws JsonMappingException, JsonProcessingException {

	        ObjectMapper objectMapper = new ObjectMapper();
	        ViedoAndAboutSectionDto dto = objectMapper.readValue(viedoSectionJson, ViedoAndAboutSectionDto.class);

	        Message<ViedoAndAboutSectionDto> message = viedoAndAboutSectionService.addViedoSection(dto, imageFile, desktopVideo, tabVideo, mobVideo);
	        HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
	        return ResponseEntity.status(httpStatus).body(message);
	    }
	  @PutMapping("/update/{id}")
	    public ResponseEntity<Message<ViedoAndAboutSectionDto>> updateViedoSection(
	            @PathVariable int id,
	            @RequestPart("data") String viedoSectionJson,
//	            @RequestPart("image") MultipartFile imageFile,
	          @RequestPart(value = "image", required = false) MultipartFile imageFile,
	            @RequestPart(value = "desktopVideo", required = false) MultipartFile desktopVideo,
	            @RequestPart(value = "tabVideo", required = false) MultipartFile tabVideo,
	            @RequestPart(value = "mobVideo", required = false) MultipartFile mobVideo) 
	            throws JsonMappingException, JsonProcessingException {

	        ObjectMapper objectMapper = new ObjectMapper();
	        ViedoAndAboutSectionDto dto = objectMapper.readValue(viedoSectionJson, ViedoAndAboutSectionDto.class);

	        Message<ViedoAndAboutSectionDto> message = viedoAndAboutSectionService.updateViedoSection(id, dto, imageFile, desktopVideo, tabVideo, mobVideo);
	        HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
	        return ResponseEntity.status(httpStatus).body(message);
	    }
	  @GetMapping("/get/{id}")
	    public ResponseEntity<Message<ViedoAndAboutSectionDto>> getViedoSectionById(@PathVariable int id) {
	        Message<ViedoAndAboutSectionDto> message = viedoAndAboutSectionService.getViedoSectionById(id);
	        HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
	        return ResponseEntity.status(httpStatus).body(message);
	    }
}
