package org.jsp.Banking_system.helper;

import org.jsp.Banking_system.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
@Service
public class MailVerification {
	 @Autowired
	  JavaMailSender mailSender;
	  
	  public void sendMail(Customer customer  )
	  {
	    MimeMessage message=mailSender.createMimeMessage();
	    MimeMessageHelper helper=new MimeMessageHelper(message);
	    
	    try {
	      helper.setFrom("smh29286@gmail.com");
	    } catch (MessagingException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	    try {
	      helper.setTo(customer.getEmail());
	    } catch (MessagingException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	    try {
	      helper.setSubject("OTP Verification");
	    } catch (MessagingException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	    try {
	      helper.setText("Your otp generated"+customer.getOtp());
	    } catch (MessagingException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	    mailSender.send(message);
	  }
	}


