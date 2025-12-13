package com.SaharaAmussmentPark.service;

import org.springframework.web.multipart.MultipartFile;

import com.SaharaAmussmentPark.Dto.Message;
import com.SaharaAmussmentPark.Dto.ViedoAndAboutSectionDto;

public interface ViedoAndAboutSectionService {
	public Message<ViedoAndAboutSectionDto> addViedoSection(
            ViedoAndAboutSectionDto dto, 
            MultipartFile imageFile,
            MultipartFile desktopVideo,
            MultipartFile tabVideo,
            MultipartFile mobVideo);
	public Message<ViedoAndAboutSectionDto> updateViedoSection(
	        int id,
	        ViedoAndAboutSectionDto dto,
	        MultipartFile imageFile,
	        MultipartFile desktopVideo,
	        MultipartFile tabVideo,
	        MultipartFile mobVideo);
	public Message<ViedoAndAboutSectionDto> getViedoSectionById(int id);
}
