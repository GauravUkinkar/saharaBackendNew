package com.SaharaAmussmentPark.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.SaharaAmussmentPark.Dto.Message;
import com.SaharaAmussmentPark.Dto.PramotionalActivityDto;

public interface PramotionalActivityService {
public Message<PramotionalActivityDto> addPramotionalActivity(PramotionalActivityDto dto,MultipartFile file);
public Message<PramotionalActivityDto> updatePramotionalActivity(PramotionalActivityDto dto,MultipartFile file);
public Message<PramotionalActivityDto> deletePramotionalActivity(int paId);
public Message<PramotionalActivityDto> getPramotionalActivityById(int paId);
public List<Message<PramotionalActivityDto>> getAllPramotionalActivity();
}
