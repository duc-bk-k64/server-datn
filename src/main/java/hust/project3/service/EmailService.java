package hust.project3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
	@Autowired
	private JavaMailSender mailSender;

	public String sendEmail(String token, String email) throws Exception {
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setFrom("vuvanduc0501@gmail.com");
			msg.setTo(email);
			msg.setSubject("Email forgot password");
			msg.setText("Thân gửi bạn!\n"
					+ "Bạn đã yêu cầu thiết lập lại mật khẩu. Đây là mã code để đặt lại mật khẩu. Vui lòng không tiết lộ mã code với bất kỳ ai, nếu tiết lộ, bạn có nguy cơ bị mất tài khoản. \n"
					+ "Code : " + token + "\n" + "Nếu không phải bạn, vui lòng bỏ qua email này.\n \n \n"
					+ "Trân trọng cảm ơn!\n" + "Vũ Văn Đức.\n" + "SĐT: 0336280763");
			mailSender.send(msg);
			return "send mail successfully";
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}



	public String sendMailRegister(String code, String email) throws Exception {
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setFrom("vuvanduc0501@gmail.com");
			msg.setTo(email);
			msg.setSubject("Email active account");
			msg.setText("Thân gửi bạn!\n"
					+ "Hệ thống đã nhận được yêu cầu đăng kí tài khoản của bạn. Đây là mã code để kích hoạt tài khoản. Vui lòng không tiết lộ mã code với bất kỳ ai, nếu tiết lộ, bạn có nguy cơ bị mất tài khoản.   Truy cập đường link https://vuvanduc.id.vn/#/auth/active để kích hoạt tài khoản. \n"
					+ "Code : " + code + "\n" + "Nếu không phải bạn, vui lòng bỏ qua email này.\n \n \n"
					+ "Trân trọng cảm ơn!\n" + "Vũ Văn Đức.\n" + "SĐT: 0336280763");
			mailSender.send(msg);
			return "send mail successfully";
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

}
