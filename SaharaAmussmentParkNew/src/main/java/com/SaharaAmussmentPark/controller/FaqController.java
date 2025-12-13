package com.SaharaAmussmentPark.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.SaharaAmussmentPark.Dto.FaqDto;
import com.SaharaAmussmentPark.Dto.Message;
import com.SaharaAmussmentPark.service.FaqService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/faq")
@CrossOrigin(origins = { "*" }, allowedHeaders = { "*" })
@RequiredArgsConstructor
public class FaqController {

	private final FaqService service;

	@PostMapping("/addFaq")
	public ResponseEntity<Message<FaqDto>> addTestemonials(@RequestBody FaqDto dto) {

		Message<FaqDto> message = service.addFaq(dto);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);

	}

	@PutMapping("/updateFaq")
	public ResponseEntity<Message<FaqDto>> updateFaq(@RequestBody FaqDto dto) {
		Message<FaqDto> message = service.updateFaq(dto);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}

	@GetMapping("/getByPage/{page}")
	public ResponseEntity<Message<List<FaqDto>>> getByPage(@PathVariable String page) {
		Message<List<FaqDto>> message = service.getBypage(page);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}

	@GetMapping("/GetAll")
	public ResponseEntity<List<Message<FaqDto>>> getAllFaq() {
		List<Message<FaqDto>> message = service.getAllFaq();
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@DeleteMapping("/DeleteFaq/{id}")
	public ResponseEntity<Message<FaqDto>> deleteFaq(@PathVariable int id) {
		Message<FaqDto> message = service.deleteFaq(id);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}
	@GetMapping("/getById/{id}")
	public ResponseEntity<Message<FaqDto>>getById(@PathVariable int id){
		Message<FaqDto> message = service.getFaqById(id);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}
	@DeleteMapping("/DeleteByPage/{page}")
	public ResponseEntity<Message<FaqDto>> deleteFaqByPage(@PathVariable String page) {
		Message<FaqDto> message = service.deleteFaqByPage(page);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
		}
}
