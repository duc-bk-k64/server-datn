package hust.project3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Component
public class EmailService {
	@Autowired
	private JavaMailSender mailSender;

	public String sendEmail(String token, String email) throws Exception {
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setFrom("vuvanduc0501@gmail.com");
			msg.setTo(email);
			msg.setSubject("Forgot Password from Server");
			msg.setText("Token forgot password:" + token);
			mailSender.send(msg);
//			  Twilio.init("AC7696dcbd30efbf615439cc745758d41a", "cee8f936daab9dc94352f30aab5cf7f0");
//
//              Message.creator(new PhoneNumber("84336280763"),
//                              new PhoneNumber("84336280763"), "Token forgot password:"+token).create();
			return "send mail successfully";
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

}
