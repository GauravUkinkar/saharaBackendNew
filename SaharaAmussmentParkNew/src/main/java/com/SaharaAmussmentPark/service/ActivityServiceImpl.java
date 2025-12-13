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
import com.SaharaAmussmentPark.model.Activity;
import com.SaharaAmussmentPark.repository.ActivityRepository;
import com.SaharaAmussmentPark.util.Constants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
	private final ActivityRepository activityRepository;
	@Value("${spring.servlet.multipart.location}")
	public String uploadDirectory;

	@Override
	public Message<ActivityDto> addActivity(ActivityDto dto, MultipartFile images, MultipartFile coverImage) {
		Message<ActivityDto> message = new Message<>();
		try {
			Activity activity = new Activity();
			activity.setImage(uploadImage(images, images.getOriginalFilename()));
			activity.setCoverImage(uploadImage(coverImage, coverImage.getOriginalFilename()));
			activity.setTitle(dto.getTitle());
			activity.setDescription(dto.getDescription());
			activity.setPrice(dto.getPrice());
			activity.setActivityTitle(dto.getActivityTitle());
			activity.setAdditionalInfo(dto.getAdditionalInfo());
			activityRepository.save(activity);
			dto.setAId(activity.getAId());
			dto.setImage(activity.getImage());
			dto.setCoverImage(activity.getCoverImage());
			message.setData(dto);
			message.setResponseMessage("Activity added successfully");
			message.setStatus(HttpStatus.CREATED);
			return message;
		} catch (Exception e) {
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setResponseMessage(Constants.SOMETHING_WENT_WRONG + e.getMessage());
			return message;
		}
	}

	@Override
	public Message<ActivityDto> updateActivity(ActivityDto dto, MultipartFile images, MultipartFile coverImage) {
		Message<ActivityDto> message = new Message<>();
		try {
			Activity activity = activityRepository.findById(dto.getAId()).get();
			if (activity==null) {
				message.setStatus(HttpStatus.NOT_FOUND);
				message.setResponseMessage("Activity not found");
				return message;
			}else {
			activity.setTitle(dto.getTitle());
			activity.setDescription(dto.getDescription());
			activity.setPrice(dto.getPrice());
			activity.setActivityTitle(dto.getActivityTitle());
			activity.setAdditionalInfo(dto.getAdditionalInfo());
			 // ✅ Keep the old image if the new one is not provided
            if (images != null && !images.isEmpty()) {
            	activity.setImage(uploadImage(images, images.getOriginalFilename())); // Upload and replace image
            }
            if (coverImage != null && !coverImage.isEmpty()) {
            	activity.setImage(uploadImage(coverImage, coverImage.getOriginalFilename())); // Upload and replace image
            }
			activityRepository.save(activity);
			dto.setAId(activity.getAId());
			dto.setImage(activity.getImage());
			dto.setCoverImage(activity.getCoverImage());
			message.setData(dto);
			message.setResponseMessage("Activity added successfully");
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
	public Message<ActivityDto> deleteActivity(int aId) {
		Message<ActivityDto> message = new Message<>();
		try {
			Optional<Activity> activity = activityRepository.findById(aId);
			if (activity.isPresent()) {
				activityRepository.delete(activity.get());
				message.setStatus(HttpStatus.OK);
				message.setResponseMessage("Activity deleted successfully");
				return message;
			} else {
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
	public List<Message<ActivityDto>> getAllActivity() {
		List<Message<ActivityDto>> message = new ArrayList<>();
		try {
			List<Activity> activities = activityRepository.findAll();
			if (activities.isEmpty()) {
				message.add(new Message<ActivityDto>(HttpStatus.NOT_FOUND, "Activity not found", null));
				return message;
			}
			for (Activity activity : activities) {
				ActivityDto dto = new ActivityDto();
				dto.setAId(activity.getAId());
				dto.setTitle(activity.getTitle());
				dto.setImage(activity.getImage());
				dto.setCoverImage(activity.getCoverImage());
				dto.setDescription(activity.getDescription());
				dto.setPrice(activity.getPrice());
				dto.setActivityTitle(activity.getActivityTitle());
				dto.setAdditionalInfo(activity.getAdditionalInfo());
				message.add(new Message<ActivityDto>(HttpStatus.OK, "Activity found successfully", dto));
			}
			return message;
		} catch (Exception e) {
			message.add(new Message<ActivityDto>(HttpStatus.INTERNAL_SERVER_ERROR,
					Constants.SOMETHING_WENT_WRONG + e.getMessage(), null));
			return message;
		}
	}

	@Override
	public Message<ActivityDto> getActivityById(int aid) {
		Message<ActivityDto> message = new Message<>();
		try {
			Optional<Activity> activity = activityRepository.findById(aid);
			if (activity.isPresent()) {
				ActivityDto dto = new ActivityDto();
				dto.setAId(activity.get().getAId());
				dto.setTitle(activity.get().getTitle());
				dto.setImage(activity.get().getImage());
				dto.setCoverImage(activity.get().getCoverImage());
				dto.setDescription(activity.get().getDescription());
				dto.setPrice(activity.get().getPrice());
				dto.setActivityTitle(activity.get().getActivityTitle());
				dto.setAdditionalInfo(activity.get().getAdditionalInfo());
				message.setData(dto);
				message.setStatus(HttpStatus.OK);
				message.setResponseMessage("Activity found successfully");
				return message;
			} else {
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
	public Message<ActivityDto> getActivityByTitle(String title) {
		Message<ActivityDto> message = new Message<>();
		try {
			Activity activity = activityRepository.findByTitle(title);
			ActivityDto dto = new ActivityDto();
			dto.setAId(activity.getAId());
			dto.setTitle(activity.getTitle());
			dto.setImage(activity.getImage());
			dto.setCoverImage(activity.getCoverImage());
			dto.setDescription(activity.getDescription());
			dto.setPrice(activity.getPrice());
			dto.setActivityTitle(activity.getActivityTitle());
			dto.setAdditionalInfo(activity.getAdditionalInfo());
			message.setData(dto);
			message.setStatus(HttpStatus.OK);
			message.setResponseMessage("Activity found successfully");
			return message;
		} catch (Exception e) {
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setResponseMessage(Constants.SOMETHING_WENT_WRONG + e.getMessage());
			return message;
		}
	}

	@Override
	public Message<ActivityDto> getActivityByActivityTitle(String activityTitle) {
		Message<ActivityDto> message = new Message<>();
		try {
			Activity activity = activityRepository.findByActivityTitle(activityTitle);
			ActivityDto dto = new ActivityDto();
			dto.setAId(activity.getAId());
			dto.setTitle(activity.getTitle());
			dto.setImage(activity.getImage());
			dto.setCoverImage(activity.getCoverImage());
			dto.setDescription(activity.getDescription());
			dto.setPrice(activity.getPrice());
			dto.setActivityTitle(activity.getActivityTitle());
			dto.setAdditionalInfo(activity.getAdditionalInfo());
			message.setData(dto);
			message.setStatus(HttpStatus.OK);
			message.setResponseMessage("Activity found successfully");
			return message;
		} catch (Exception e) {
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setResponseMessage(Constants.SOMETHING_WENT_WRONG + e.getMessage());
			return message;
		}
	}

	private String uploadImage(MultipartFile image, String imageName) throws IOException {
	    if (image != null && !image.isEmpty()) {
	    	imageName = imageName.replaceAll("\\s+", "");
	        // Extract file extension
	        String extension = "";
	        int dotIndex = imageName.lastIndexOf('.');
	        if (dotIndex > 0 && dotIndex < imageName.length() - 1) {
	            extension = imageName.substring(dotIndex);
	            imageName = imageName.substring(0, dotIndex);
	        }

	        // Add timestamp
	        String timestamp = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
	        String fileName = imageName + "_" + timestamp + extension;

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


