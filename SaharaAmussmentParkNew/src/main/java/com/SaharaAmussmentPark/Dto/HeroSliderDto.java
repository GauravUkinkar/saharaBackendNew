package com.SaharaAmussmentPark.Dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class HeroSliderDto {
	private int hId;
	private String title;
	private String discription;
	private String image;

}
