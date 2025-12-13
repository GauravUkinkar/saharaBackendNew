package com.SaharaAmussmentPark.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SaharaAmussmentPark.Dto.ActivityDto;
import com.SaharaAmussmentPark.Dto.Message;
import com.SaharaAmussmentPark.service.ActivityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/activity")
@RequiredArgsConstructor
@CrossOrigin(origins = { "*" }, allowedHeaders = { "*" })
public class ActivityController {
 private final ActivityService service;	
 
 @PostMapping("/Add")
 public ResponseEntity<Message<ActivityDto>> addActivity(
         @RequestPart("data") String activityJson,
         @RequestPart("image") MultipartFile imageFile,@RequestPart( "coverImage") MultipartFile coverImage) throws JsonMappingException, JsonProcessingException {
	  ObjectMapper objectMapper = new ObjectMapper();
	  ActivityDto dto = objectMapper.readValue(activityJson, ActivityDto.class);
	  Message<ActivityDto>message=service.addActivity(dto, imageFile, coverImage);
	  HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
      return ResponseEntity.status(httpStatus).body(message);
 }
 @PutMapping("/update")
 public ResponseEntity<Message<ActivityDto>> updateActivity(
         @RequestPart("data") String activityJson,
         @RequestPart(value = "image", required = false) MultipartFile imageFile,@RequestPart( value = "coverImage", required = false) MultipartFile coverImage)
         throws JsonMappingException, JsonProcessingException {
	 ObjectMapper objectMapper = new ObjectMapper();
	  ActivityDto dto = objectMapper.readValue(activityJson, ActivityDto.class);
	  Message<ActivityDto>message=service.updateActivity(dto, imageFile, coverImage);
	  HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
     return ResponseEntity.status(httpStatus).body(message);
 }
 @GetMapping("/getById")
 public ResponseEntity<Message<ActivityDto>> getActivity(@RequestParam("activityId") int aid) {
     Message<ActivityDto> message = service.getActivityById(aid);
     HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
     return ResponseEntity.status(httpStatus).body(message);
 }
 @GetMapping("/GetAll")
 public ResponseEntity<List<Message<ActivityDto>>> getAllActivity() {
		List<Message<ActivityDto>> message = service.getAllActivity();
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}
 @GetMapping("/getBytitle")
 public ResponseEntity<Message<ActivityDto>> getActivityByTitle(@RequestParam("Title") String title) {
     Message<ActivityDto> message = service.getActivityByTitle(title);
     HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
     return ResponseEntity.status(httpStatus).body(message);
 }
 @GetMapping("/getByActivityTitletitle")
 public ResponseEntity<Message<ActivityDto>> getActivity(@RequestParam("Title") String activityTitle) {
     Message<ActivityDto> message = service.getActivityByActivityTitle(activityTitle);
     HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
     return ResponseEntity.status(httpStatus).body(message);
 } 
 @DeleteMapping("/delete")
 public ResponseEntity<Message<ActivityDto>> deleteActivity(@RequestParam("activityId") int aid) {
	 Message<ActivityDto> message = service.deleteActivity(aid);
	 HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
	 return ResponseEntity.status(httpStatus).body(message);
 }
}
