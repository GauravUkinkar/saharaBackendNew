package com.SaharaAmussmentPark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SaharaAmussmentPark.model.Testemonials;

@Repository
public interface TestemonialsRepository extends JpaRepository<Testemonials, Integer> {

}
