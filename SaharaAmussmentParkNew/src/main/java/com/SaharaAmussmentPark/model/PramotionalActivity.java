package com.SaharaAmussmentPark.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
@Entity
@RequiredArgsConstructor
public class PramotionalActivity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int paId;
	private String image;
	private String title;
	private String description; 
}
