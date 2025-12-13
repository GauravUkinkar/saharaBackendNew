package com.SaharaAmussmentPark.Dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class PramotionalActivityDto {
	private int paId;
	private String image;
	private String title;
	private String description; 
}
