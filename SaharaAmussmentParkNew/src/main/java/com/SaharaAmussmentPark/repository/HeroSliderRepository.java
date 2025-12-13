package com.SaharaAmussmentPark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SaharaAmussmentPark.model.HeroSlider;

@Repository
public interface HeroSliderRepository extends JpaRepository<HeroSlider, Integer> {

}
