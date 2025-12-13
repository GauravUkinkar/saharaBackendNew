package com.SaharaAmussmentPark.service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.SaharaAmussmentPark.Dto.FaqDto;
import com.SaharaAmussmentPark.Dto.HeroSliderDto;
import com.SaharaAmussmentPark.Dto.Message;
import com.SaharaAmussmentPark.model.HeroSlider;
import com.SaharaAmussmentPark.repository.HeroSliderRepository;
import com.SaharaAmussmentPark.util.Constants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HeroSliderServiceImpl implements HeroSliderService {
	private final HeroSliderRepository heroSliderRepository;
	@Value("${spring.servlet.multipart.location}")
	public String uploadDirectory;

	@Override
	public Message<HeroSliderDto> addHeroSlider(HeroSliderDto heroSliderDto, MultipartFile images) {
		Message<HeroSliderDto> message = new Message<>();
		try {
            // Create new HeroSlider instance
            HeroSlider heroSlider = new HeroSlider();
            heroSlider.setDiscription(heroSliderDto.getDiscription());
            heroSlider.setTitle(heroSliderDto.getTitle());

            // Upload and get image URL
            String imageUrl = uploadImage(images, images.getOriginalFilename());
            heroSlider.setImage(imageUrl);

            // Save entity to DB
            heroSlider = heroSliderRepository.save(heroSlider);

            // Convert entity to DTO for response
            HeroSliderDto responseDto = new HeroSliderDto();
            responseDto.setTitle(heroSlider.getTitle());
            responseDto.setDiscription(heroSlider.getDiscription());
            responseDto.setImage(heroSlider.getImage());

            message.setData(responseDto);
            message.setResponseMessage("HeroSlider added successfully");
            message.setStatus(HttpStatus.CREATED);
            // Return Message<HeroSliderDto>
            return message;

        } catch (Exception e) {
            throw new RuntimeException("Failed to add HeroSlider", e);
            
        }
    }

	@Override
	public Message<HeroSliderDto> updateSlider(HeroSliderDto heroSliderDto, MultipartFile images) {
         Message<HeroSliderDto> message = new Message<>();
         try {
        	 HeroSlider slider= heroSliderRepository.findById(heroSliderDto.getHId()).orElse(null);
        	 if(slider!=null) {
        		 
        		 slider.setDiscription(heroSliderDto.getDiscription());
        		 slider.setTitle(heroSliderDto.getTitle());

                 // Upload and get image URL
                 String imageUrl = uploadImage(images, images.getOriginalFilename());
                 slider.setImage(imageUrl);

                 // Save entity to DB
                 heroSliderRepository.save(slider);

                 // Convert entity to DTO for response
                 HeroSliderDto responseDto = new HeroSliderDto();
                 responseDto.setTitle(slider.getTitle());
                 responseDto.setDiscription(slider.getDiscription());
                 responseDto.setImage(slider.getImage());

                 message.setData(responseDto);
                 message.setResponseMessage("HeroSlider added successfully");
                 message.setStatus(HttpStatus.CREATED);
                 // Return Message<HeroSliderDto>
                 return message;

        	 }
        	 else{
        		 message.setStatus(HttpStatus.NOT_FOUND);
				 message.setResponseMessage(Constants.SOMETHING_WENT_WRONG);
				 return message;
				 
			 
        		 } 
			 }	catch (Exception e) {
                 throw new RuntimeException("Failed to add HeroSlider", e);
                 
             } 
        	 
         }


	@Override
	public Message<HeroSliderDto> deleteSlider(int id) {
		Message<HeroSliderDto> message = new Message<>();
		try {
			HeroSlider heroSlider = heroSliderRepository.findById(id).orElse(null);
			if (heroSlider != null) {
				heroSliderRepository.delete(heroSlider);
				message.setStatus(HttpStatus.OK);
				message.setResponseMessage("HeroSlider deleted successfully");
				return message;
			} else {
				message.setStatus(HttpStatus.NOT_FOUND);
				message.setResponseMessage("HeroSlider not found");
				return message;
			}
		}catch (Exception e) {
	        // General error handling
	        message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
	        message.setResponseMessage(Constants.SOMETHING_WENT_WRONG);
	        return message;
		}
	}

	

	@Override
	public List<Message<HeroSliderDto>> getAllSlider() {
        List<Message<HeroSliderDto>> messages = new ArrayList<>();  // List to hold the Message objects
        List<HeroSlider> heroSliders = heroSliderRepository.findAll(); 
        if (heroSliders.isEmpty()) {
        	
            Message<HeroSliderDto> message = new Message<>();
            message.setStatus(HttpStatus.NOT_FOUND);
            message.setResponseMessage("Faq not found");
            messages.add(message);
            return messages;
        }

        for (HeroSlider heroSlider : heroSliders) {
            // Convert Entity to DTO
            HeroSliderDto dto = new HeroSliderDto();
            dto.setHId(heroSlider.getHId());
            dto.setTitle(heroSlider.getTitle());
            dto.setDiscription(heroSlider.getDiscription());
            dto.setImage(heroSlider.getImage());

            // Create Message object
            Message<HeroSliderDto> message = new Message<>();
            message.setStatus(HttpStatus.OK);
            message.setResponseMessage("Hero Slider found successfully");
            message.setData(dto);

            // Add the Message object to the list
            messages.add(message);
        }

        return messages;  // Return the list of messages
    }

   
	
	private String uploadImage(MultipartFile image, String imageName) throws IOException {
		if (image != null && !image.isEmpty()) {
			String fileName = imageName ;
			Path uploadPath = Paths.get(uploadDirectory);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			Path filePath = uploadPath.resolve(fileName);
			Files.write(filePath, image.getBytes());
			return "https://media.saharaamusement.com/sahara/" + fileName;
		}
		return null;
	}
	
	
	


}
