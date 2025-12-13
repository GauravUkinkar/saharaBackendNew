package com.SaharaAmussmentPark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SaharaAmussmentPark.model.ViedoAndAboutSection;

@Repository
public interface ViedoAndAboutSectionRepository extends JpaRepository<ViedoAndAboutSection, Integer> {

}
