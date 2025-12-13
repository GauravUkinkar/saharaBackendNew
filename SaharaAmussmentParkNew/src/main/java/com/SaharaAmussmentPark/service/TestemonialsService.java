package com.SaharaAmussmentPark.service;

import java.util.List;

import com.SaharaAmussmentPark.Dto.Message;
import com.SaharaAmussmentPark.Dto.TestemonialsDto;

public interface TestemonialsService {
	public Message<TestemonialsDto> addTestemonials(TestemonialsDto dto);

	public Message<TestemonialsDto> updateTestemonials(TestemonialsDto dto);

	public Message<TestemonialsDto> deleteTestemonials(int id);

	public List<Message<TestemonialsDto>> getAllTestemonials();
}
