package hust.project3.service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hust.project3.entity.Account;
import hust.project3.repository.AccountRepository;

@Service
public class AccountService {
	private static final int EXPIRE_TOKEN = 10;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public String forgotPassword(String username) throws Exception {
		Account account = accountRepository.findUserByUsername(username);
		if (account == null)
			return "not found in system";
		String token = generateToken();
		account.setTokenForgotPassword(token);
		account.setTimeCreatioToken(Instant.now());
		accountRepository.save(account);
		System.out.println(account.getEmail());
		try {
			String message=emailService.sendEmail(token, account.getEmail());
			return message;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public String resetPassword(String token, String password) {
		if (token == null)
			return "token is null";
		if (password == null)
			return "new password is null";
		Account account = accountRepository.findByTokenForgotPassword(token);
		if (account == null)
			return "not found in system";
		if (isexpire(account.getTimeCreatioToken()))
			return "expire token";
		String newpasswordString = passwordEncoder.encode(password);
		account.setPassword(newpasswordString);
		accountRepository.save(account);
		return "reset password successfully";

	}
	
	private Boolean isexpire(Instant timeCreation) {
		Instant now = Instant.now();
		Duration diff = Duration.between(timeCreation, now);

		return diff.toMinutes() >= EXPIRE_TOKEN;

	}

	private String generateToken() {
		StringBuilder token = new StringBuilder();
		token.append(UUID.randomUUID().toString()).append(UUID.randomUUID().toString());
		return token.toString();
	}

}
