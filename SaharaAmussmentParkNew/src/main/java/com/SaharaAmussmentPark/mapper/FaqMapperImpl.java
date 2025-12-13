package com.SaharaAmussmentPark.mapper;

import org.springframework.stereotype.Component;

import com.SaharaAmussmentPark.Dto.FaqDto;
import com.SaharaAmussmentPark.model.Faq;

@Component
public class FaqMapperImpl implements FaqMapper {

	@Override
	public FaqDto fromEntity(Faq entity) {
		return new FaqDto()
				.setId(entity.getId())
				.setQuestion(entity.getQuestion())
				.setAnswer(entity.getAnswer())
				.setPage(entity.getPage());
	}

	@Override
	public Faq fromDto(FaqDto dto) {
		return new Faq()
				.setAnswer(dto.getAnswer())
				.setPage(dto.getPage())
				.setQuestion(dto.getQuestion());
	}

}
