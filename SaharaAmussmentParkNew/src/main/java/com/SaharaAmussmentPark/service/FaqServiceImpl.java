package com.SaharaAmussmentPark.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.SaharaAmussmentPark.Dto.FaqDto;
import com.SaharaAmussmentPark.Dto.Message;
import com.SaharaAmussmentPark.mapper.FaqMapper;
import com.SaharaAmussmentPark.model.Faq;
import com.SaharaAmussmentPark.repository.FaqRepository;
import com.SaharaAmussmentPark.util.Constants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService {
private final FaqMapper mapper;
private final FaqRepository repo;
	@Override
	public Message<FaqDto> addFaq(FaqDto dto) {
		Message<FaqDto> message = new Message<>();
		try {
			Faq faq=mapper.fromDto(dto);
			repo.save(faq);
			message.setData(mapper.fromEntity(faq));
			message.setResponseMessage("Faq added successfully");
			message.setStatus(HttpStatus.CREATED);
			return message;
		} catch (Exception e) {
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setResponseMessage(Constants.SOMETHING_WENT_WRONG + e.getMessage());
			return message;
		}
	}

	@Override
	public Message<FaqDto> updateFaq(FaqDto dto) {
		Message<FaqDto> message = new Message<>();
		try {
			Faq faq=repo.findById(dto.getId()).get();
			faq.setAnswer(dto.getAnswer());
			faq.setPage(dto.getPage());
			faq.setQuestion(dto.getQuestion());
			repo.save(faq);
			message.setData(mapper.fromEntity(faq));
			message.setResponseMessage("Faq updated successfully");
			message.setStatus(HttpStatus.CREATED);
			return message;
		} catch (Exception e) {
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setResponseMessage(Constants.SOMETHING_WENT_WRONG + e.getMessage());
			return message;
		}
	}

	@Override
	public Message<FaqDto> deleteFaq(int id) {
		Message<FaqDto> message = new Message<>();
		try {
			Faq faq=repo.findById(id).get();
			repo.delete(faq);
			message.setData(mapper.fromEntity(faq));
			message.setResponseMessage("Faq deleted successfully");
			message.setStatus(HttpStatus.OK);
			return message;
		} catch (Exception e) {
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setResponseMessage(Constants.SOMETHING_WENT_WRONG + e.getMessage());
			return message;
		}
	}

	@Override
	public Message<List<FaqDto>> getBypage(String page) {
Message<List<FaqDto>> message = new Message<>();
        
        List<Faq> faqs = repo.findByPage(page);
        
        if (faqs.isEmpty()) {
            message.setStatus(HttpStatus.NOT_FOUND);
            message.setResponseMessage("No FAQs found for page: " + page);
            return message;
        }

        // Manually map Faq to FaqDto
        List<FaqDto> faqDtos = new ArrayList<>();
        for (Faq faq : faqs) {
            FaqDto dto = new FaqDto();
            dto.setId(faq.getId());
            dto.setQuestion(faq.getQuestion());
            dto.setAnswer(faq.getAnswer());
            dto.setPage(faq.getPage());
            faqDtos.add(dto);
        }

        message.setStatus(HttpStatus.OK);
        message.setResponseMessage("FAQs retrieved successfully");
        message.setData(faqDtos);
        
        return message;
    
	}

	@Override
	public List<Message<FaqDto>> getAllFaq() {
		List<Message<FaqDto>> messages = new ArrayList<>();

        try {
            List<Faq> faqs = repo.findAll();

            if (faqs.isEmpty()) {
                Message<FaqDto> message = new Message<>();
                message.setStatus(HttpStatus.NOT_FOUND);
                message.setResponseMessage("Faq not found");
                messages.add(message);
                return messages;
            }

            for (Faq faq : faqs) {
                Message<FaqDto> message = new Message<>();
                message.setData(mapper.fromEntity(faq)); // Convert entity to DTO
                message.setResponseMessage("Faq found successfully");
                message.setStatus(HttpStatus.OK);
                messages.add(message);
            }

        } catch (Exception e) {
            Message<FaqDto> errorMessage = new Message<>();
            errorMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            errorMessage.setResponseMessage("Something went wrong: " + e.getMessage());
            messages.add(errorMessage);
        }

        return messages;
    }

	@Override
	public Message<FaqDto> getFaqById(int id) {
		Message<FaqDto> message = new Message<>();
		try {
			Faq faq=repo.findById(id).get();
			message.setData(mapper.fromEntity(faq));
			message.setResponseMessage("Faq found successfully");
			message.setStatus(HttpStatus.OK);
			return message;	
		}catch (Exception e) {
            Message<FaqDto> errorMessage = new Message<>();
            errorMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            errorMessage.setResponseMessage("Something went wrong: " + e.getMessage());
			return errorMessage;
		}
	}

	@Override
	public Message<FaqDto> deleteFaqByPage(String page) {
		Message<FaqDto> message = new Message<>();
		try {
		List<Faq> faqs = repo.getByPage(page);
		if(faqs!=null) {
			for(Faq faq:faqs) {
				repo.delete(faq);
			}
			message.setResponseMessage("Faq deleted successfully");
			message.setStatus(HttpStatus.OK);
			return message;
		}
		else {
			message.setStatus(HttpStatus.NOT_FOUND);
			message.setResponseMessage("Faq not found");
			return message;
		}
		} catch (Exception e) {
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setResponseMessage(Constants.SOMETHING_WENT_WRONG + e.getMessage());
			return message;
		}
	}

}
