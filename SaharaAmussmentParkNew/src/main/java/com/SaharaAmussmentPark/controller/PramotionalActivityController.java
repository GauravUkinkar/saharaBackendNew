package com.SaharaAmussmentPark.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SaharaAmussmentPark.Dto.ActivityDto;
import com.SaharaAmussmentPark.Dto.Message;
import com.SaharaAmussmentPark.Dto.PramotionalActivityDto;
import com.SaharaAmussmentPark.service.PramotionalActivityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/PramotionalActivity")
@RequiredArgsConstructor
@CrossOrigin(origins = { "*" }, allowedHeaders = { "*" })
public class PramotionalActivityController {
	private final PramotionalActivityService service;

	@PostMapping("/addPramotionalActivity")
	public ResponseEntity<Message<PramotionalActivityDto>> addPramotionalActivity(
			@RequestPart("data") String activityJson,
			@RequestPart("image") MultipartFile imageFile) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		PramotionalActivityDto dto = objectMapper.readValue(activityJson, PramotionalActivityDto.class);
		Message<PramotionalActivityDto> message = service.addPramotionalActivity(dto, imageFile);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}
	@PutMapping("/update")
	 public ResponseEntity<Message<PramotionalActivityDto>> updateActivity(
	         @RequestPart("data") String activityJson,
	         @RequestPart(value = "image", required = false) MultipartFile imageFile)
	         throws JsonMappingException, JsonProcessingException {
		 ObjectMapper objectMapper = new ObjectMapper();
		 PramotionalActivityDto dto = objectMapper.readValue(activityJson, PramotionalActivityDto.class);
		  Message<PramotionalActivityDto>message=service.updatePramotionalActivity(dto, imageFile);
		  HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
	     return ResponseEntity.status(httpStatus).body(message);
	 }
	@GetMapping("/getById/{paId}")
	 public ResponseEntity<Message<PramotionalActivityDto>> getActivity(@PathVariable int paId) {
	     Message<PramotionalActivityDto> message = service.getPramotionalActivityById(paId);
	     HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
	     return ResponseEntity.status(httpStatus).body(message);
	 }
	 @GetMapping("/GetAll")
	 public ResponseEntity<List<Message<PramotionalActivityDto>>> getAllActivity() {
			List<Message<PramotionalActivityDto>> message = service.getAllPramotionalActivity();
			return ResponseEntity.status(HttpStatus.OK).body(message);
		}
	 @DeleteMapping("/delete/{paId}")
	 public ResponseEntity<Message<PramotionalActivityDto>> deleteActivity(@PathVariable int paId) {
		 Message<PramotionalActivityDto> message = service.deletePramotionalActivity(paId);
		 HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		 return ResponseEntity.status(httpStatus).body(message);
	 }
}
