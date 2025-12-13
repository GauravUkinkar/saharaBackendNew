package com.SaharaAmussmentPark.mapper;

import com.SaharaAmussmentPark.Dto.ContactDto;
import com.SaharaAmussmentPark.model.Contact;

public interface ContactMapper {
 public ContactDto tocontact(Contact contact);
 public Contact toContactDto(ContactDto contact);
}
