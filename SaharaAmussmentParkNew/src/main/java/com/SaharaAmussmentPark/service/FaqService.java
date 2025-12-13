package com.SaharaAmussmentPark.service;

import java.util.List;

import com.SaharaAmussmentPark.Dto.FaqDto;
import com.SaharaAmussmentPark.Dto.Message;

public interface FaqService {
 public Message<FaqDto> addFaq(FaqDto dto);
 public Message<FaqDto> updateFaq(FaqDto dto);
 public Message<FaqDto> deleteFaq(int id);
 public Message<List<FaqDto>>getBypage(String page);
 public List<Message<FaqDto>> getAllFaq();
 public Message<FaqDto> getFaqById(int id);
 public Message<FaqDto>deleteFaqByPage(String page);
}
