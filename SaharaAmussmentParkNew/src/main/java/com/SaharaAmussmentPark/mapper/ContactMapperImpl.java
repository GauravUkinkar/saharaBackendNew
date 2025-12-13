package com.SaharaAmussmentPark.mapper;

import org.springframework.stereotype.Component;

import com.SaharaAmussmentPark.Dto.ContactDto;
import com.SaharaAmussmentPark.model.Contact;

@Component
public class ContactMapperImpl implements ContactMapper {

	@Override
	public ContactDto tocontact(Contact contact) {
		return new ContactDto()
				.setActivity(contact.getActivity())
				.setCId(contact.getCId())
				.setEmail(contact.getEmail())
				.setMessage(contact.getMessage())
				.setName(contact.getName())
				.setPhoneNumber(contact.getPhoneNumber())
				.setStatus(contact.getStatus());
	}

	@Override
	public Contact toContactDto(ContactDto contact) {
		return new Contact()
				.setActivity(contact.getActivity())
				.setEmail(contact.getEmail())
				.setMessage(contact.getMessage())
				.setName(contact.getName())
				.setPhoneNumber(contact.getPhoneNumber())
				.setStatus(contact.getStatus());
	}

}
