package com.SaharaAmussmentPark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SaharaAmussmentPark.model.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

	Activity findByTitle(String title);

	Activity findByActivityTitle(String activityTitle);

}
	