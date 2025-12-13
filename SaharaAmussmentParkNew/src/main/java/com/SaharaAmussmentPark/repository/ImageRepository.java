package com.SaharaAmussmentPark.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SaharaAmussmentPark.model.Images;

@Repository
public interface ImageRepository extends JpaRepository<Images, Integer > {

	List<Images> getByImageName(String imageName);

	

}


