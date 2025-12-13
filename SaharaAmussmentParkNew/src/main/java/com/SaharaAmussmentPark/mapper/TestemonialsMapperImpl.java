package com.SaharaAmussmentPark.mapper;

import org.springframework.stereotype.Component;

import com.SaharaAmussmentPark.Dto.TestemonialsDto;
import com.SaharaAmussmentPark.model.Testemonials;

@Component
public class TestemonialsMapperImpl implements TestemonialsMapper {

	@Override
	public Testemonials fromDto(TestemonialsDto dto) {
		return new Testemonials()
				.setFeedBack(dto.getFeedBack())
				.setName(dto.getName())
				.setStars(dto.getStars());
	}

	@Override
	public TestemonialsDto fromEntity(Testemonials entity) {
	return new TestemonialsDto().setFeedBack(entity.getFeedBack())
			.setId(entity.getId())
			.setName(entity.getName())
			.setStars(entity.getStars())
			;
	}

}
