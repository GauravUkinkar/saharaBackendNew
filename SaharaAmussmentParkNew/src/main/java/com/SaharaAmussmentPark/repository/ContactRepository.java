package com.SaharaAmussmentPark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SaharaAmussmentPark.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

}
