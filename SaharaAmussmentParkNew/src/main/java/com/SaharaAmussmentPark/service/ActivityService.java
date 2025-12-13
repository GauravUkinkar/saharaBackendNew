package com.SaharaAmussmentPark.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.SaharaAmussmentPark.Dto.ActivityDto;
import com.SaharaAmussmentPark.Dto.Message;

public interface ActivityService {
public Message<ActivityDto> addActivity(ActivityDto dto, MultipartFile images,MultipartFile coverImage);
public Message<ActivityDto> updateActivity(ActivityDto dto, MultipartFile images,MultipartFile coverImage);
public Message<ActivityDto> deleteActivity(int aId);
public List<Message<ActivityDto>> getAllActivity();
public Message<ActivityDto> getActivityById(int aId);
public Message<ActivityDto> getActivityByTitle(String title);
public Message<ActivityDto> getActivityByActivityTitle(String activityTitle);
}
