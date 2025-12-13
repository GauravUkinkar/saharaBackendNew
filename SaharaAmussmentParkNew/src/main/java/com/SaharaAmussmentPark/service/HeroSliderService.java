package com.SaharaAmussmentPark.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.SaharaAmussmentPark.Dto.HeroSliderDto;
import com.SaharaAmussmentPark.Dto.Message;


public interface HeroSliderService {
	public Message<HeroSliderDto> addHeroSlider(HeroSliderDto heroSliderDto,MultipartFile images);
	public Message<HeroSliderDto> deleteSlider(int id);
	public Message<HeroSliderDto> updateSlider(HeroSliderDto heroSliderDto,MultipartFile images);
	public List<Message<HeroSliderDto>> getAllSlider();

}
