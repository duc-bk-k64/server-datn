package hust.project3.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hust.project3.common.Constant;
import hust.project3.entity.Account;
import hust.project3.entity.Profile;
import hust.project3.model.ProfileModel;
import hust.project3.repository.AccountRepository;
import hust.project3.repository.ProfileRepository;

@Service
public class AccountService {
	private static final int EXPIRE_TOKEN = 10;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ProfileRepository profileRepository;

	public String forgotPassword(String username) throws Exception {
		Account account = accountRepository.findUserByUsername(username);
		if (account == null)
			return "not found in system";
		String token = generateToken();
		while (accountRepository.existsByTokenForgotPassword(token)) {
			token = generateToken();
		}
		account.setTokenForgotPassword(token);
		account.setTimeCreatioToken(Instant.now());
		accountRepository.save(account);
//		System.out.println(account.getEmail());
		try {
			String message = emailService.sendEmail(token, account.getEmail());
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

	public String generateToken() {
		Random random = new Random();
		StringBuilder token = new StringBuilder();
		for (int i = 0; i < 8; i++) {
			token.append(random.nextInt(10));
		}
		return token.toString();
	}

	public String sendMailActiveAccount(String code, String email) throws Exception {
		try {
			return emailService.sendMailRegister(code, email);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public String updateProfile(String username, Profile profile) {
		Account account = accountRepository.findUserByUsername(username);
		if (account == null)
			return Constant.MESSAGE.NOT_FOUND_USER;
		else {
			if (account.getProfile() == null) {
//				profileRepository.save(profile);
				account.setProfile(profile);
			} else {
				Profile profileOld = account.getProfile();
				profileOld.setPhoneNumber(profile.getPhoneNumber());
				profileOld.setDateOfBirth(profile.getDateOfBirth());
				profileOld.setSex(profile.getSex());
				profileRepository.save(profileOld);
//				account.setProfile(profileOld);
			}
			accountRepository.save(account);
			return "Update profile successfully";
		}

	}

	public Profile getProfile(String username) {
		Account account = accountRepository.findUserByUsername(username);
		if (account == null)
			return null;
		else {
			Profile profile = account.getProfile();
			return profile;
		}

	}

}
