package com.SaharaAmussmentPark.mapper;

import com.SaharaAmussmentPark.Dto.UserDto;
import com.SaharaAmussmentPark.model.User;

public interface UserMapper {
 public UserDto userToUserDto(User user);
}
