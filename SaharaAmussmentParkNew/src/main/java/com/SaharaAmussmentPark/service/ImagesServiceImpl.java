package com.SaharaAmussmentPark.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.SaharaAmussmentPark.Dto.ImagesDto;
import com.SaharaAmussmentPark.Dto.Message;
import com.SaharaAmussmentPark.model.Images;
import com.SaharaAmussmentPark.repository.ImageRepository;
import com.SaharaAmussmentPark.util.Constants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImagesServiceImpl implements ImagesService {

    @Value("${spring.servlet.multipart.location}")
    private String uploadDirectory;
    private final ImageRepository imageRepository;
    @Override
    public Message<List<ImagesDto>> uploadImages(MultipartFile[] files) throws IOException {

        Message<List<ImagesDto>> response = new Message<>();

        if (files == null || files.length == 0) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setResponseMessage("No files received");
            response.setData(null);
            return response;
        }

        List<ImagesDto> imagesList = new ArrayList<>();

        for (MultipartFile file : files) {

            if (file == null || file.isEmpty()) {
                continue;
            }

            String imageUrl = uploadFile(file);

            ImagesDto dto = new ImagesDto()
                    .setImageName(file.getOriginalFilename())
                    .setImages(imageUrl);

            imagesList.add(dto);
        }

        if (imagesList.isEmpty()) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setResponseMessage("No valid images uploaded");
            response.setData(null);
            return response;
        }

        response.setStatus(HttpStatus.OK);
        response.setResponseMessage("Images uploaded successfully");
        response.setData(imagesList);

        return response;
    }



    private String uploadFile(MultipartFile file) throws IOException {

        String originalFilename = file.getOriginalFilename();
        String fileName = System.currentTimeMillis() + "_" + originalFilename;

        Path uploadPath = Paths.get(uploadDirectory);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return "https://media.saharaamusement.com/sahara/" + fileName;
    }


    @Override
    public Message<List<ImagesDto>> getAllImages() {
        Message<List<ImagesDto>> response = new Message<>();

        try {
            List<Images> imagesList = imageRepository.findAll();

            if (imagesList == null || imagesList.isEmpty()) {
                response.setStatus(HttpStatus.NOT_FOUND);
                response.setResponseMessage(Constants.IMAGE_NOT_FOUND);
                response.setData(Collections.emptyList());
                return response;
            }

            List<ImagesDto> dtoList = new ArrayList<>();

            for (Images images : imagesList) {
                ImagesDto dto = new ImagesDto();
                dto.setIId(images.getIId());
                dto.setImageName(images.getImageName());
                dto.setImages(images.getImages());
                dtoList.add(dto);
            }

            response.setStatus(HttpStatus.OK);
            response.setResponseMessage("Images found successfully");
            response.setData(dtoList);
            return response;

        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setResponseMessage(Constants.SOMETHING_WENT_WRONG + " : " + e.getMessage());
            response.setData(null);
            return response;
        }
    }


	@Override
	public Message<List<ImagesDto>> getByImageName(String imageName) {
	    Message<List<ImagesDto>> response = new Message<>();
	    try {
	        List<Images> imagesList = imageRepository.getByImageName(imageName);

	        if (imagesList == null || imagesList.isEmpty()) {
	            response.setStatus(HttpStatus.NOT_FOUND);
	            response.setResponseMessage(Constants.IMAGE_NOT_FOUND);
	            return response;
	        }

	        List<ImagesDto> dtoList = new ArrayList<>();

	        for (Images images : imagesList) {
	            ImagesDto dto = new ImagesDto();
	            dto.setIId(images.getIId());
	            dto.setImageName(images.getImageName());
	            dto.setImages(images.getImages());
	            dtoList.add(dto);
	        }

	        response.setStatus(HttpStatus.OK);
	        response.setResponseMessage(Constants.IMAGES_FOUND);
	        response.setData(dtoList);
	        return response;

	    } catch (Exception e) {
	        System.err.println("Error fetching Images: " + e.getMessage());
	        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
	        response.setResponseMessage(Constants.SOMETHING_WENT_WRONG);
	        return response;
	    }
	}




	@Override
	public Message<ImagesDto> deleteImages(int iId) {
		Message<ImagesDto> message = new Message<>();
		try {
			Images images = new Images();
			images = imageRepository.getById(iId);
			
			if (images == null) {
				message.setStatus(HttpStatus.BAD_REQUEST);
				message.setResponseMessage(Constants.IMAGE_NOT_FOUND);
				return message;
			}
			ImagesDto dto = new ImagesDto();
			dto.setIId(images.getIId());
			dto.setImageName(images.getImageName());
			dto.setImages(images.getImages());
			imageRepository.deleteById(iId);
			
			message.setStatus(HttpStatus.OK);
			message.setResponseMessage(Constants.IMAGE_DELETED_SUCCESSFULLY);
			return message;
		} catch (Exception e) {
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setResponseMessage(Constants.SOMETHING_WENT_WRONG);
			return message;
		}
		
	}
}

