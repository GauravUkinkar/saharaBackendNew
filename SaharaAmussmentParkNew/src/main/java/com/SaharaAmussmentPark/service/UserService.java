package com.SaharaAmussmentPark.service;

import com.SaharaAmussmentPark.Dto.LoginDto;
import com.SaharaAmussmentPark.Dto.Message;
import com.SaharaAmussmentPark.Dto.UserDto;

public interface UserService {
 public Message<UserDto> login(LoginDto loginDto);
 public Message<UserDto>AddUser(UserDto request);
 public Message<UserDto> getUserById(int id);
 
}
