package com.SaharaAmussmentPark.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data 
@Entity
@ToString
@Accessors(chain=true)

public class Images {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int iId;
	private String images;
	private String imageName;

}
