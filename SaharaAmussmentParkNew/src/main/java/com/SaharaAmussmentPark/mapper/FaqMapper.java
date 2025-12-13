package com.SaharaAmussmentPark.mapper;

import com.SaharaAmussmentPark.Dto.FaqDto;
import com.SaharaAmussmentPark.model.Faq;

public interface FaqMapper {
 public FaqDto fromEntity(Faq entity);
 public Faq fromDto(FaqDto dto);
}
