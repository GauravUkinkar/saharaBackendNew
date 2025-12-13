package com.SaharaAmussmentPark.Dto;

import java.util.Map;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class ActivityDto {
	private int aId;
	private String image;
	private String coverImage;
	private String title;
	private String description; 
	private double price;
	private String activityTitle;
	private Map<String,String> additionalInfo;
}
