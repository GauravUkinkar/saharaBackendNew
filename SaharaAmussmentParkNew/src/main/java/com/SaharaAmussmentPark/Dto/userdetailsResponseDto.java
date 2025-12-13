package com.SaharaAmussmentPark.Dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class userdetailsResponseDto {
	private int id;
	private String email;
	private String role;
	private String contactNumber;
	private String employee;
}
