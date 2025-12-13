package com.SaharaAmussmentPark.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.SaharaAmussmentPark.Dto.LoginDto;
import com.SaharaAmussmentPark.Dto.Message;
import com.SaharaAmussmentPark.Dto.UserDto;
import com.SaharaAmussmentPark.mapper.UserMapper;
import com.SaharaAmussmentPark.model.User;
import com.SaharaAmussmentPark.repository.UserRepository;
import com.SaharaAmussmentPark.util.Constants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
private final UserMapper userMapperimpl;
private final UserRepository userRepository;
	@Override
	public Message<UserDto> login(LoginDto loginDto) {
		Message<UserDto>message=new Message<>();
		try {
			User user=userRepository.findByEmail(loginDto.getEmail());
			if(user!=null) {
				if(user.getPassword().equals(loginDto.getPassword())) {
					message.setData(userMapperimpl.userToUserDto(user));
					message.setStatus(HttpStatus.OK);
					message.setResponseMessage(Constants.SUCCESS);
					return message;
				}
				else {
					message.setStatus(HttpStatus.BAD_REQUEST);
					message.setResponseMessage(Constants.INVALID_PASSWORD);
					return message;
				}
			}
			else {
				message.setStatus(HttpStatus.BAD_REQUEST);
				message.setResponseMessage(Constants.USER_NOT_FOUND);
				return message;
			}
		} catch (Exception e) {
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setResponseMessage(Constants.SOMETHING_WENT_WRONG);
			return message;
		}
	}
	@Override
	public Message<UserDto> AddUser(UserDto request) {
		Message<UserDto> response = new Message<>();
		try {
			if(request == null) {
				response.setStatus(HttpStatus.NOT_FOUND);
				response.setResponseMessage(Constants.INVALID_USER_DATA);
				return response;
			}
			User user = new User();
			user.setContactNumber(request.getContactNumber());
			user.setEmail(request.getEmail());
			user.setPassword(request.getPassword());
			
			userRepository.save(user);
			UserDto userDto=userMapperimpl.userToUserDto(user);
			
			response.setStatus(HttpStatus.OK);
			response.setResponseMessage(Constants.USER_ADDED);
			response.setData(userDto);
			return response;
			
		}catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setResponseMessage(e.getMessage());
			return response;
		}
		
	}
	@Override
	public Message<UserDto> getUserById(int id) {
		Message<UserDto> response = new Message<>();
		
		try {
			User user = new User();
			
			
			user=userRepository.getById(id);
			if(user == null) {
				response.setStatus(HttpStatus.NOT_FOUND);
				response.setResponseMessage(Constants.USER_NOT_FOUND);
				return response;
			}
			UserDto dto = userMapperimpl.userToUserDto(user);
			
//			dto.setContactNumber(user.getContactNumber());
//			dto.setEmail(user.getEmail());
//			dto.setPassword(user.getPassword());
			
			response.setStatus(HttpStatus.OK);
			response.setResponseMessage(Constants.USER_FOUND);
			response.setData(dto);
			return response;
		
		}catch (Exception e) {
			System.err.println("Error fetching User:" +e.getMessage());
			
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setResponseMessage(e.getMessage());
			return response;
		}
		
	}
}
