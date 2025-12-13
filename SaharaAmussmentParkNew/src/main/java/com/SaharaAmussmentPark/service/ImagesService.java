package com.SaharaAmussmentPark.service;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.SaharaAmussmentPark.Dto.ImagesDto;
import com.SaharaAmussmentPark.Dto.Message;

public interface ImagesService {

	public Message<ImagesDto> uploadImage(MultipartFile file);
    public Message<List<ImagesDto>> getAllImages();
    public Message<List<ImagesDto>> getByImageName(String imageName) ;
    public Message<ImagesDto> deleteImages(int iId);
}
