package com.SaharaAmussmentPark.Dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class TestemonialsDto {

	private int id;
	private String name;
	private String feedBack;
	private int  stars;
}
