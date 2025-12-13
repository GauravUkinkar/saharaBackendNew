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
@Accessors(chain = true)
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int cId;
	private String name;
	private String email;
	private String phoneNumber;
	private String message;
	private String activity;
	private String status;
}
