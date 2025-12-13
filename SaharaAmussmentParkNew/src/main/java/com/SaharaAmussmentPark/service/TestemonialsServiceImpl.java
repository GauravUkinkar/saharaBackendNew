package com.SaharaAmussmentPark.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.SaharaAmussmentPark.Dto.Message;
import com.SaharaAmussmentPark.Dto.TestemonialsDto;
import com.SaharaAmussmentPark.mapper.TestemonialsMapper;
import com.SaharaAmussmentPark.model.Testemonials;
import com.SaharaAmussmentPark.repository.TestemonialsRepository;
import com.SaharaAmussmentPark.util.Constants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestemonialsServiceImpl implements TestemonialsService {
	private final TestemonialsMapper mapper;
	private final TestemonialsRepository repo;

	@Override
	public Message<TestemonialsDto> addTestemonials(TestemonialsDto dto) {
		Message<TestemonialsDto> message = new Message<TestemonialsDto>();
		try {
			Testemonials testemonials=mapper.fromDto(dto);
			repo.save(testemonials);
			message.setData(mapper.fromEntity(testemonials));
			message.setResponseMessage("Testemonials added successfully");
			message.setStatus(HttpStatus.CREATED);
			return message;
		} catch (Exception e) {
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setResponseMessage(Constants.SOMETHING_WENT_WRONG + e.getMessage());
			return message;
		}
	}

	@Override
	public Message<TestemonialsDto> updateTestemonials(TestemonialsDto dto) {
		Message<TestemonialsDto> message = new Message<TestemonialsDto>();
		try {
			Optional<Testemonials> testemonials=repo.findById(dto.getId());
			if(testemonials.isPresent()) {
				Testemonials entity=testemonials.get();
				entity.setFeedBack(dto.getFeedBack());
				entity.setName(dto.getName());
				entity.setStars(dto.getStars());
				repo.save(entity);
				message.setData(mapper.fromEntity(entity));
				message.setResponseMessage("Testemonials updated successfully");
				message.setStatus(HttpStatus.CREATED);
				return message;
			}else {
				message.setStatus(HttpStatus.NOT_FOUND);
				message.setResponseMessage("Testemonials not found");
				return message;
			}
		} catch (Exception e) {
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setResponseMessage(Constants.SOMETHING_WENT_WRONG + e.getMessage());
			return message;
			
		}
	}

	@Override
	public Message<TestemonialsDto> deleteTestemonials(int id) {
		Message<TestemonialsDto> message = new Message<TestemonialsDto>();
		try {
			Optional<Testemonials> testemonials=repo.findById(id);
			if(testemonials.isPresent()) {
				repo.delete(testemonials.get());
				message.setStatus(HttpStatus.OK);
				message.setResponseMessage("Testemonials deleted successfully");
				return message;
			}else {
				message.setStatus(HttpStatus.NOT_FOUND);
				message.setResponseMessage("Testemonials not found");
				return message;
			}
		} catch (Exception e) {
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setResponseMessage(Constants.SOMETHING_WENT_WRONG + e.getMessage());
			return message;
		}
	}

	@Override
	public List<Message<TestemonialsDto>> getAllTestemonials() {
		List<Message<TestemonialsDto>> messages = new ArrayList<>();

        try {
            List<Testemonials> testemonials = repo.findAll();

            if (testemonials.isEmpty()) {
                Message<TestemonialsDto> message = new Message<>();
                message.setStatus(HttpStatus.NOT_FOUND);
                message.setResponseMessage("Testemonials not found");
                messages.add(message);
                return messages;
            }

            for (Testemonials testemonial : testemonials) {
                Message<TestemonialsDto> message = new Message<>();
                message.setStatus(HttpStatus.OK);
                message.setResponseMessage("Testemonials found successfully");
                message.setData(mapper.fromEntity(testemonial)); // Convert entity to DTO
                messages.add(message);
            }
            
        } catch (Exception e) {
            Message<TestemonialsDto> errorMessage = new Message<>();
            errorMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            errorMessage.setResponseMessage("Something went wrong: " + e.getMessage());
            messages.add(errorMessage);
        }

        return messages;
    }

}
