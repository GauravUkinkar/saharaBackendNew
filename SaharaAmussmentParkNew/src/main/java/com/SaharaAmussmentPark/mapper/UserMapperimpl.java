package com.SaharaAmussmentPark.mapper;

import org.springframework.stereotype.Component;

import com.SaharaAmussmentPark.Dto.UserDto;
import com.SaharaAmussmentPark.model.User;

@Component
public class UserMapperimpl implements UserMapper {

	@Override
	public UserDto userToUserDto(User user) {
		return new UserDto()
				.setId(user.getId())
				.setEmail(user.getEmail())
				.setContactNumber(user.getContactNumber())
				.setPassword(user.getPassword());
	}

}
