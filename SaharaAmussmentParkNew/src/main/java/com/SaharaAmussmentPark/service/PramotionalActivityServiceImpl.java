package com.SaharaAmussmentPark.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.SaharaAmussmentPark.Dto.ActivityDto;
import com.SaharaAmussmentPark.Dto.Message;
import com.SaharaAmussmentPark.Dto.PramotionalActivityDto;
import com.SaharaAmussmentPark.model.PramotionalActivity;
import com.SaharaAmussmentPark.repository.PramotionalActivityRepository;
import com.SaharaAmussmentPark.util.Constants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PramotionalActivityServiceImpl implements PramotionalActivityService {

	private final PramotionalActivityRepository pramotionalActivityRepository;
	@Value("${spring.servlet.multipart.location}")
	public String uploadDirectory;

	@Override
	public Message<PramotionalActivityDto> addPramotionalActivity(PramotionalActivityDto dto, MultipartFile file) {
		Message<PramotionalActivityDto> message = new Message<>();
		try {
			PramotionalActivity model = new PramotionalActivity();
			model.setDescription(dto.getDescription());
			model.setTitle(dto.getTitle());
			model.setImage(uploadImage(file, file.getOriginalFilename()));
			pramotionalActivityRepository.save(model);
			dto.setPaId(model.getPaId());
			dto.setImage(model.getImage());
			dto.setDescription(model.getDescription());
			model.setTitle(model.getTitle());
			message.setData(dto);
			message.setResponseMessage("Pramotional Activity Added successfully");
			message.setStatus(HttpStatus.CREATED);
			return message;
		} catch (Exception e) {
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setResponseMessage(Constants.SOMETHING_WENT_WRONG + e.getMessage());
			return message;
		}
	}

	@Override
	public Message<PramotionalActivityDto> updatePramotionalActivity(PramotionalActivityDto dto, MultipartFile file) {
		Message<PramotionalActivityDto> message = new Message<>();
		try {
			PramotionalActivity model = pramotionalActivityRepository.findById(dto.getPaId()).get();
			if (model == null) {
				message.setStatus(HttpStatus.NOT_FOUND);
				message.setResponseMessage("Activity not found");
				return message;
			} else {
				model.setTitle(dto.getTitle());
				model.setDescription(dto.getDescription());
				// ✅ Keep the old image if the new one is not provided
				if (file != null && !file.isEmpty()) {
					model.setImage(uploadImage(file, file.getOriginalFilename())); // Upload and replace image
				}

				pramotionalActivityRepository.save(model);
				dto.setPaId(model.getPaId());
				dto.setImage(model.getImage());
				dto.setTitle(model.getTitle());
				dto.setDescription(model.getDescription());
				message.setData(dto);
				message.setResponseMessage("Pramotional Activity Updated  successfully");
				message.setStatus(HttpStatus.CREATED);
				return message;
			}

		} catch (Exception e) {
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setResponseMessage(Constants.SOMETHING_WENT_WRONG + e.getMessage());
			return message;
		}
	}

	@Override
	public Message<PramotionalActivityDto> deletePramotionalActivity(int paId) {
		Message<PramotionalActivityDto>message=new Message<>();
		try {
			Optional<PramotionalActivity>model=pramotionalActivityRepository.findById(paId);
			if(model.isPresent()) {
				pramotionalActivityRepository.delete(model.get());
				message.setResponseMessage("Pramotional Activity addedd successfully");
				message.setStatus(HttpStatus.OK);
				return message;
			}else {
				message.setStatus(HttpStatus.NOT_FOUND);
				message.setResponseMessage("Activity not found");
				return message;
			}

		} catch (Exception e) {
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setResponseMessage(Constants.SOMETHING_WENT_WRONG + e.getMessage());
			return message;
		}
	}

	@Override
	public Message<PramotionalActivityDto> getPramotionalActivityById(int paId) {
		Message<PramotionalActivityDto>message=new Message<>();
		try {
			Optional<PramotionalActivity> model= pramotionalActivityRepository.findById(paId);
			if(model.isPresent()) {
				PramotionalActivityDto dto=new PramotionalActivityDto();
				dto.setDescription(model.get().getDescription());
				dto.setImage(model.get().getImage());
				dto.setPaId(model.get().getPaId());
				dto.setTitle(model.get().getTitle());
				message.setData(dto);
				message.setResponseMessage("Pramotional Activity Found");
				message.setStatus(HttpStatus.OK);
				return message;
				
			}else {
				message.setStatus(HttpStatus.NOT_FOUND);
				message.setResponseMessage("Activity not found");
				return message;
			}
		} catch (Exception e) {
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setResponseMessage(Constants.SOMETHING_WENT_WRONG + e.getMessage());
			return message;
		}
	}

	@Override
	public List<Message<PramotionalActivityDto>> getAllPramotionalActivity() {
		List<Message<PramotionalActivityDto>>message=new ArrayList<>();
		try {
			List<PramotionalActivity>model=pramotionalActivityRepository.findAll();
			if(model.isEmpty()) {
				message.add(new Message<PramotionalActivityDto>(HttpStatus.NOT_FOUND, "Activity not found", null));
				return message;
			}
			for (PramotionalActivity activity: model) {
				PramotionalActivityDto dto= new PramotionalActivityDto();
				dto.setPaId(activity.getPaId());
				dto.setImage(activity.getImage());
	            dto.setTitle(activity.getTitle());
	            dto.setDescription(activity.getDescription());
				message.add(new Message<PramotionalActivityDto>(HttpStatus.OK, "Activity found successfully", dto));
			}
			return message;
		} catch (Exception e) {
			message.add(new Message<PramotionalActivityDto>(HttpStatus.INTERNAL_SERVER_ERROR,
					Constants.SOMETHING_WENT_WRONG + e.getMessage(), null));
			return message;
		}
	}

	private String uploadImage(MultipartFile image, String imageName) throws IOException {  
		if (image != null && !image.isEmpty()) {
			String fileName = imageName;
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
