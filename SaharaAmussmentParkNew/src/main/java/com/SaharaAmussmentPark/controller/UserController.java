package com.SaharaAmussmentPark.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SaharaAmussmentPark.Dto.LoginDto;
import com.SaharaAmussmentPark.Dto.Message;
import com.SaharaAmussmentPark.Dto.UserDto;
import com.SaharaAmussmentPark.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins = { "*" }, allowedHeaders = { "*" })

@Log4j2

public class UserController {
 
	private final UserService userService;
	
	@PostMapping("/Login")
	public ResponseEntity<Message<UserDto>> loginUser(@RequestBody LoginDto request) {
		log.info("In UserController login() with request: {}", request);
		Message<UserDto> message = userService.login(request);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}
	@PostMapping("/addUser")
	public ResponseEntity <Message<UserDto>> addUser(@RequestBody UserDto request){
		log.info("In usercontroller login() with request:{}", request);
		Message<UserDto> message=userService.AddUser(request);
		HttpStatus httpStatus=HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
		
	}
	@GetMapping("/getUserById/{id}")
	public ResponseEntity<Message<UserDto>> getUser(@PathVariable int id){
		 log.info("In usercontroller login() with request:{}",id);
		 Message<UserDto> message=userService.getUserById(id);
		 HttpStatus httpStatus=HttpStatus.valueOf(message.getStatus().value());
		 return ResponseEntity.status(httpStatus).body(message);
	}
}
