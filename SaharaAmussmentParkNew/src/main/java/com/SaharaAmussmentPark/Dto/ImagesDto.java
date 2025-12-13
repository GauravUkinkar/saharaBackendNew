package com.SaharaAmussmentPark.Dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString 
@Accessors(chain = true)

public class ImagesDto {
	private int iId;
	private String images;
	private String imageName;

}
