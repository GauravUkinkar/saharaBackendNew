package com.SaharaAmussmentPark.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SaharaAmussmentPark.model.Faq;

@Repository
public interface FaqRepository extends JpaRepository<Faq, Integer> {

	List<Faq> findByPage(String page);
	 List<Faq> getByPage(String page);

}
