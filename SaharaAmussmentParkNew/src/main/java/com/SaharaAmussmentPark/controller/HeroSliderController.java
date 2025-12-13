package com.SaharaAmussmentPark.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SaharaAmussmentPark.Dto.HeroSliderDto;
import com.SaharaAmussmentPark.Dto.Message;
import com.SaharaAmussmentPark.service.HeroSliderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/HeroSlider")
@RequiredArgsConstructor
@Log4j2
public class HeroSliderController {
 private final HeroSliderService heroSliderService;
 
 @PostMapping("/addHeroSlider")
 public ResponseEntity<Message<HeroSliderDto>> addHeroSlider(@RequestPart("HeroSlider")String herosliderjson,@RequestPart("image")MultipartFile image) throws JsonMappingException, JsonProcessingException {
	 ObjectMapper objectMapper = new ObjectMapper();
	 HeroSliderDto productDTO = objectMapper.readValue(herosliderjson, HeroSliderDto.class);
	 Message<HeroSliderDto> message=heroSliderService.addHeroSlider(productDTO, image);
	 HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
	 return ResponseEntity.status(httpStatus).body(message);
 }
 @PutMapping("/updateSlider")
 public ResponseEntity<Message<HeroSliderDto>> updateHeroSlider(@RequestPart("HeroSlider")String herosliderjson,@RequestPart("image")MultipartFile image) throws JsonMappingException, JsonProcessingException {
	 ObjectMapper objectMapper = new ObjectMapper();
	 HeroSliderDto productDTO = objectMapper.readValue(herosliderjson, HeroSliderDto.class);
	 Message<HeroSliderDto> message=heroSliderService.updateSlider(productDTO, image);
	 HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
	 return ResponseEntity.status(httpStatus).body(message);
 }
 @GetMapping("/getHeroSlider")
 public ResponseEntity<List<Message<HeroSliderDto>>> getHeroSlider(){
	 List<Message<HeroSliderDto>> message=heroSliderService.getAllSlider();
	 return ResponseEntity.status(HttpStatus.OK).body(message);
 }
 @DeleteMapping("/deleteHeroSlider")
 public ResponseEntity<Message<HeroSliderDto>> deleteHeroSlider(@RequestParam("SliderId")int id){
	 Message<HeroSliderDto> message=heroSliderService.deleteSlider(id);
	 HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
	 return ResponseEntity.status(httpStatus).body(message);
 }
}
