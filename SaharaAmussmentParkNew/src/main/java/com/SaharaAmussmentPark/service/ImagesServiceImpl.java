package com.SaharaAmussmentPark.service;

import java.io.IOException;
import org.springframework.util.StringUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
	public Message<List<ImagesDto>> uploadImages(MultipartFile[] files, ImagesDto request) {

	    Message<List<ImagesDto>> response = new Message<>();

	    if (files == null || files.length == 0) {
	        response.setStatus(HttpStatus.BAD_REQUEST);
	        response.setResponseMessage("No files received");
	        return response;
	    }

	    List<ImagesDto> imagesList = new ArrayList<>();

	    try {
	        for (MultipartFile file : files) {

	            if (file == null || file.isEmpty()) {
	                continue;
	            }
	            if (!isImage(file)) {
	                continue;
	            }

	            String imageUrl = uploadFile(file);

	            Images image = new Images();
	            image.setImageName(file.getOriginalFilename());
	            image.setImageName(imageUrl);
	          

	            imageRepository.save(image);

	            // Prepare DTO
	            ImagesDto dto = new ImagesDto();
	            dto.setImageName(image.getImageName());
	            dto.setImages(imageUrl);

	            imagesList.add(dto);
	        }

	        if (imagesList.isEmpty()) {
	            response.setStatus(HttpStatus.BAD_REQUEST);
	            response.setResponseMessage("No valid images uploaded");
	            return response;
	        }

	        response.setStatus(HttpStatus.OK);
	        response.setResponseMessage("Images uploaded successfully");
	        response.setData(imagesList);
	        return response;

	    } catch (IOException ex) {
	        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
	        response.setResponseMessage("Image upload failed");
	        return response;
	    }
	}


	private String uploadFile(MultipartFile file) throws IOException {

	    String originalFilename = StringUtils.cleanPath(
	            Objects.requireNonNull(file.getOriginalFilename())
	    );

	    String fileName = System.currentTimeMillis() + "_" + originalFilename;

	    Path uploadPath = Paths.get(uploadDirectory).toAbsolutePath().normalize();
	    Files.createDirectories(uploadPath);

	    Path filePath = uploadPath.resolve(fileName);
	    if (!filePath.startsWith(uploadPath)) {
	        throw new IOException("Invalid file path");
	    }

	    Files.copy(
	            file.getInputStream(),
	            filePath,
	            StandardCopyOption.REPLACE_EXISTING
	    );

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
	private boolean isImage(MultipartFile file) {

	    if (file == null || file.isEmpty()) {
	        return false;
	    }

	    String contentType = file.getContentType();

	    return contentType != null &&
	           (contentType.equalsIgnoreCase("image/jpeg") ||
	            contentType.equalsIgnoreCase("image/png") ||
	            contentType.equalsIgnoreCase("image/jpg") ||
	            contentType.equalsIgnoreCase("image/webp"));
	}

}

