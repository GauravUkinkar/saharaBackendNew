package com.SaharaAmussmentPark.Dto;

import java.util.Map;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString 
@Accessors(chain = true)
public class ViedoAndAboutSectionDto {
	  private int id;
	    private String title;
	    private String image;
	    private String buttonText;
	    private String buttonLink;
	    private String description;
	    private Map<String, String> viedo;
}
