package com.SaharaAmussmentPark.Dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class ContactDto {
	public int cId;
	private String name;
	private String email;
	private String phoneNumber;
	private String message;
	private String activity;
	private String status;
}
