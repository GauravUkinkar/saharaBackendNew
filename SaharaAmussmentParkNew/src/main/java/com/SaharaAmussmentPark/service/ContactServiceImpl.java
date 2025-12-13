package com.SaharaAmussmentPark.service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.SaharaAmussmentPark.Dto.ContactDto;
import com.SaharaAmussmentPark.Dto.Message;
import com.SaharaAmussmentPark.mapper.ContactMapper;
import com.SaharaAmussmentPark.model.Contact;
import com.SaharaAmussmentPark.repository.ContactRepository;
import com.SaharaAmussmentPark.util.Constants;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ContactServiceImpl implements ContactService {
	private final ContactRepository contactRepository;
	private final ContactMapper contactMapper;
	private final Configuration config;
	private final EmailService emailService;
	@Value("${spring.mail.username}")
	private String sender;
	@Value("${spring.mail.password}")
	private String password;

	@Override
	public Message<ContactDto> addContact(ContactDto request) {
		Message<ContactDto> response = new Message<>();
		try {
			contactRepository.save(contactMapper.toContactDto(request));
			Properties props = new Properties();
			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(sender, password);
				}
			});

			MimeMessage mailMessage = new MimeMessage(session);

			MimeMessageHelper helper = new MimeMessageHelper(mailMessage,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
			Map<String, Object> model = new HashMap<>();
			model.put("request1", request);
//			model.put("user", user);
			Template t = config.getTemplate("contact.html");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
//			 SimpleMailMessage mailMessage = new SimpleMailMessage();
			helper.setFrom(sender);
			helper.setTo("info@saharaamusement.com");
			helper.setSubject("Contact Details");
			helper.setText(html, true);

			emailService.sendEmail(mailMessage);
			response.setStatus(HttpStatus.OK);
			response.setResponseMessage(Constants.SUCCESS);
			response.setData(request);
			return response;
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setResponseMessage(Constants.SOMETHING_WENT_WRONG);
			return response;
		}
	}

	@Override
	public Message<ContactDto> DeleteContact(int cId) {
		Message<ContactDto> response = new Message<>();
		try {
			Contact contact=contactRepository.findById(cId).orElse(null);
			if(contact==null) {
				response.setStatus(HttpStatus.BAD_REQUEST);
				response.setResponseMessage(Constants.CONTACT_NOT_FOUND);
				return response;
			}
			contactRepository.deleteById(cId);
			response.setStatus(HttpStatus.OK);
			response.setResponseMessage(Constants.CONTACT_DELETED);
			return response;
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setResponseMessage(Constants.SOMETHING_WENT_WRONG);
			return response;
		}
	}

	@Override
	public List<Message<ContactDto>> getAllContact() {
		List<Message<ContactDto>> message = new ArrayList<>();
		try {
			List<Contact> activities = contactRepository.findAll();
			if (activities.isEmpty()) {
				message.add(new Message<ContactDto>(HttpStatus.BAD_REQUEST, Constants.CONTACT_NOT_FOUND, null));
				return message;
			}
			for (Contact activity : activities) {
				ContactDto dto = new ContactDto();
				dto.setCId(activity.getCId());
				dto.setActivity(activity.getActivity());
				dto.setEmail(activity.getEmail());
				dto.setName(activity.getName());
				dto.setMessage(activity.getMessage());
				dto.setPhoneNumber(activity.getPhoneNumber());
				log.info("In ContactController getAllContact() with response: {}", dto);
				message.add(new Message<ContactDto>(HttpStatus.OK, "Contact found successfully", dto));
			}
			return message;
		} catch (Exception e) {
			message.add(new Message<ContactDto>(HttpStatus.INTERNAL_SERVER_ERROR,
					Constants.SOMETHING_WENT_WRONG + e.getMessage(), null));
			return message;
		}
	}

}
