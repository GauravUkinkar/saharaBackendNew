package com.SaharaAmussmentPark.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.SaharaAmussmentPark.Dto.Message;
import com.SaharaAmussmentPark.Dto.ViedoAndAboutSectionDto;
import com.SaharaAmussmentPark.model.ViedoAndAboutSection;
import com.SaharaAmussmentPark.repository.ViedoAndAboutSectionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ViedoAndAboutSectionServiceImpl implements ViedoAndAboutSectionService {
 private final ViedoAndAboutSectionRepository viedoAndAboutSectionRepository;
 
	@Value("${spring.servlet.multipart.location}")
	public String uploadDirectory;
	@Override
	public Message<ViedoAndAboutSectionDto> addViedoSection(ViedoAndAboutSectionDto dto, MultipartFile imageFile,
			MultipartFile desktopVideo, MultipartFile tabVideo, MultipartFile mobVideo) {
		 Message<ViedoAndAboutSectionDto> message = new Message<>();
	        try {
	            ViedoAndAboutSection section = new ViedoAndAboutSection();
	            section.setTitle(dto.getTitle());
	            section.setButtonText(dto.getButtonText());
	            section.setButtonLink(dto.getButtonLink());
	            section.setDescription(dto.getDescription());

	            // Upload image and set path
	            if (imageFile != null && !imageFile.isEmpty()) {
	                section.setImage(uploadFile(imageFile));
	            }

	            // Upload videos and set paths
	            Map<String, String> videoPaths = new HashMap<>();
	            if (desktopVideo != null && !desktopVideo.isEmpty()) {
	                videoPaths.put("desktop", uploadFile(desktopVideo));
	            }
	            if (tabVideo != null && !tabVideo.isEmpty()) {
	                videoPaths.put("tab", uploadFile(tabVideo));
	            }
	            if (mobVideo != null && !mobVideo.isEmpty()) {
	                videoPaths.put("mob", uploadFile(mobVideo));
	            }
	            section.setViedo(videoPaths);

	            // Save to database
	            viedoAndAboutSectionRepository.save(section);
	            dto.setId(section.getId());
	            dto.setImage(section.getImage());
	            dto.setViedo(section.getViedo());

	            message.setData(dto);
	            message.setResponseMessage("ViedoAndAboutSection added successfully");
	            message.setStatus(HttpStatus.CREATED);
	            return message;
	        } catch (IOException e) {
	            throw new RuntimeException("Failed to add ViedoAndAboutSection", e);
	        }
	    }
	@Override
	public Message<ViedoAndAboutSectionDto> updateViedoSection(int id, ViedoAndAboutSectionDto dto,
			MultipartFile imageFile, MultipartFile desktopVideo, MultipartFile tabVideo, MultipartFile mobVideo) {
		Message<ViedoAndAboutSectionDto> message = new Message<>();
	    Optional<ViedoAndAboutSection> sectionOpt = viedoAndAboutSectionRepository.findById(id);

	    if (sectionOpt.isPresent()) {
	        ViedoAndAboutSection section = sectionOpt.get();
	        section.setTitle(dto.getTitle());
	        section.setButtonText(dto.getButtonText());
	        section.setButtonLink(dto.getButtonLink());
	        section.setDescription(dto.getDescription());

	        try {
	            // ✅ Keep the old image if the new one is not provided
	            if (imageFile != null && !imageFile.isEmpty()) {
	                section.setImage(uploadFile(imageFile)); // Upload and replace image
	            }

	            // ✅ Keep the old videos if the new ones are not provided
	            Map<String, String> videoPaths = section.getViedo() != null ? new HashMap<>(section.getViedo()) : new HashMap<>();

	            if (desktopVideo != null && !desktopVideo.isEmpty()) {
	                videoPaths.put("desktop", uploadFile(desktopVideo)); // Upload and replace desktop video
	            }
	            if (tabVideo != null && !tabVideo.isEmpty()) {
	                videoPaths.put("tab", uploadFile(tabVideo)); // Upload and replace tab video
	            }
	            if (mobVideo != null && !mobVideo.isEmpty()) {
	                videoPaths.put("mob", uploadFile(mobVideo)); // Upload and replace mobile video
	            }

	            section.setViedo(videoPaths);

	            viedoAndAboutSectionRepository.save(section);

	            dto.setId(section.getId());
	            dto.setImage(section.getImage());
	            dto.setViedo(section.getViedo());

	            message.setData(dto);
	            message.setResponseMessage("ViedoAndAboutSection updated successfully");
	            message.setStatus(HttpStatus.OK);
	        } catch (IOException e) {
	              throw new RuntimeException("Failed to update ViedoAndAboutSection", e);
	        }
	    } else {
	        message.setResponseMessage("ViedoAndAboutSection not found");
	        message.setStatus(HttpStatus.NOT_FOUND);
	    }
	    return message;
	}
	public Message<ViedoAndAboutSectionDto> getViedoSectionById(int id) {
		 Message<ViedoAndAboutSectionDto> message = new Message<>();
	        Optional<ViedoAndAboutSection> sectionOpt = viedoAndAboutSectionRepository.findById(id);

	        if (sectionOpt.isPresent()) {
	            ViedoAndAboutSection section = sectionOpt.get();
	            ViedoAndAboutSectionDto dto = new ViedoAndAboutSectionDto();
	            dto.setId(section.getId());
	            dto.setTitle(section.getTitle());
	            dto.setImage(section.getImage());
	            dto.setButtonText(section.getButtonText());
	            dto.setButtonLink(section.getButtonLink());
	            dto.setDescription(section.getDescription());
	            dto.setViedo(section.getViedo());

	            message.setData(dto);
	            message.setResponseMessage("ViedoAndAboutSection found successfully");
	            message.setStatus(HttpStatus.OK);
	        } else {
	            message.setResponseMessage("ViedoAndAboutSection not found");
	            message.setStatus(HttpStatus.NOT_FOUND);
	        }
	        return message;
	    }
	

	    private String uploadFile(MultipartFile file) throws IOException {
	        if (file != null && !file.isEmpty()) {
	            String originalFilename = file.getOriginalFilename();

	            Path uploadPath = Paths.get(uploadDirectory);  
	            if (!Files.exists(uploadPath)) {
	                Files.createDirectories(uploadPath);
	            }

	            Path filePath = uploadPath.resolve(originalFilename);
	            Files.write(filePath, file.getBytes());

	            return "https://media.saharaamusement.com/sahara/" + originalFilename;
	        }
	        return null;
	    }

		

}
