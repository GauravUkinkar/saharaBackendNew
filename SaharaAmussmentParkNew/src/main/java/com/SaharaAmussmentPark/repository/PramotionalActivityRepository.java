package com.SaharaAmussmentPark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SaharaAmussmentPark.model.PramotionalActivity;

@Repository
public interface PramotionalActivityRepository extends JpaRepository<PramotionalActivity, Integer> {

}
