package com.SaharaAmussmentPark.service;

import java.util.List;

import com.SaharaAmussmentPark.Dto.ContactDto;
import com.SaharaAmussmentPark.Dto.Message;

public interface ContactService {
	public Message<ContactDto>addContact(ContactDto request);
	public Message<ContactDto>DeleteContact(int cId);
	public List<Message<ContactDto>>getAllContact(); }
