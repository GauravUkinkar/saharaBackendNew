package com.SaharaAmussmentPark.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SaharaAmussmentPark.Dto.Message;
import com.SaharaAmussmentPark.Dto.TestemonialsDto;
import com.SaharaAmussmentPark.service.TestemonialsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/testemonials")
@RequiredArgsConstructor
@CrossOrigin(origins = { "*" }, allowedHeaders = { "*" })
public class TestemonialsController {
	private final TestemonialsService testemonialsService;

	@PostMapping("/addTestemonials")
	public ResponseEntity<Message<TestemonialsDto>> addTestemonials(@RequestBody TestemonialsDto dto) {

		Message<TestemonialsDto> message = testemonialsService.addTestemonials(dto);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);

	}
	@PutMapping("/updateTestemonials")
	public ResponseEntity<Message<TestemonialsDto>> updateTestemonials(@RequestBody TestemonialsDto dto) {

		Message<TestemonialsDto> message = testemonialsService.updateTestemonials(dto);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}
	@GetMapping("/GetAll")
	public ResponseEntity <List<Message<TestemonialsDto>>>getAllTestemonials() {
		List<Message<TestemonialsDto>>message=testemonialsService.getAllTestemonials();
		return ResponseEntity.status(HttpStatus.OK).body(message);
		
	}
	@DeleteMapping("/delete")
	public ResponseEntity <Message<TestemonialsDto>> deleteTestemonials(@RequestParam("id") int id) {
		Message<TestemonialsDto> message = testemonialsService.deleteTestemonials(id);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}
}
