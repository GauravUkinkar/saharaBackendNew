package com.SaharaAmussmentPark.mapper;

import com.SaharaAmussmentPark.Dto.TestemonialsDto;
import com.SaharaAmussmentPark.model.Testemonials;

public interface TestemonialsMapper {
public Testemonials fromDto(TestemonialsDto dto);
public TestemonialsDto fromEntity(Testemonials entity);
}
