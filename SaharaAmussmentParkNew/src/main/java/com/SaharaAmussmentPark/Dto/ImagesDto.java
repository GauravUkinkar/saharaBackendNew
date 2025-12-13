package com.SaharaAmussmentPark.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString 
@Accessors(chain = true)

@NoArgsConstructor
@AllArgsConstructor
public class ImagesDto {
	private int iId;
	private String images;
	private String imageName;

}
