package com.SaharaAmussmentPark.model;

import java.util.Map;

import org.hibernate.annotations.Type;

import com.vladmihalcea.hibernate.type.json.JsonType;

import jakarta.persistence.Column;
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
public class Activity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int aId;
	private String image;
	private String coverImage;
	private String title;
	private String description; 
	private double price;
	private String activityTitle;
	@Type(JsonType.class)
    @Column(columnDefinition = "json") 
    private Map<String,String> additionalInfo;
	

}
