package com.SaharaAmussmentPark.Dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class FaqDto {
	private int id;
	private String question;
	private String answer;
	private String page;
}
