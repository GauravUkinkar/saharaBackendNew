package com.SaharaAmussmentPark.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SaharaAmussmentPark.Dto.ContactDto;
import com.SaharaAmussmentPark.Dto.Message;
import com.SaharaAmussmentPark.service.ContactService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/contact")
@RequiredArgsConstructor
@CrossOrigin(origins = { "*" }, allowedHeaders = { "*" })
@Log4j2
public class ContactController {

	private final ContactService contactService;
	
	@PostMapping("/addContact")
	public ResponseEntity<Message<ContactDto>> addContact(@RequestBody ContactDto request) {
		log.info("In UserController login() with request: {}", request);
		Message<ContactDto>message=contactService.addContact(request);
		HttpStatus httpStatus=HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}
	@DeleteMapping("/DeleteContact")
	public ResponseEntity<Message<ContactDto>> DeleteContact(int cId) {
		log.info("In UserController login() with request: {}", cId);
		Message<ContactDto>message=contactService.DeleteContact(cId);
		HttpStatus httpStatus=HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}
	@GetMapping("/getAllContact")
	public ResponseEntity<List<Message<ContactDto>>> getAllContact() {
		log.info("In contact Controller getAllContact()}");
		List<Message<ContactDto>>message=contactService.getAllContact();
		HttpStatus httpStatus=HttpStatus.valueOf(message.get(0).getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}
}
