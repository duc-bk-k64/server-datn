package hust.project3.service;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

import hust.project3.Utils.GenerateCode;
import hust.project3.entity.*;
import hust.project3.entity.BookTour.BookTour;
import hust.project3.entity.Tour.TourTrip;
import hust.project3.model.AccountDTO;
import hust.project3.model.AccountModel;
import hust.project3.model.ResponMessage;
import hust.project3.repository.BookTour.BookTourRepository;
import hust.project3.repository.RoleRepository;
import hust.project3.repository.Tour.TourTripRepository;
import hust.project3.service.Tour.TourTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hust.project3.common.Constant;
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

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BookTourRepository bookTourRepository;
	@Autowired
	private TourTripRepository tourTripRepository;

	@Autowired
	private  NotificationService notificationService;
//	@Scheduled(cron = "0 0/1 * * * *") //per 1 minutes
    @Scheduled(cron = "0 0 8 * * MON-FRI")
	public void sendAlert() throws Exception {
		List<BookTour> bookTours = bookTourRepository.findAllBooktourNeedAlert();
		List<TourTrip> tourTrips = tourTripRepository.findTripNeedAlert();
		System.out.println("Size booktour"+bookTours.size());
		if(bookTours.size() != 0 ) {
			Notification notification = new Notification();
			notification.setStatus(Constant.STATUS.UNREAD);
			notification.setTitle("Đơn đặt tour chưa được xác nhận");
			notification.setUsername("SYSTEM");
			StringBuilder content = new StringBuilder("Các đơn đặt tour:  ");
			bookTours.forEach(e -> {
				content.append(e.getCode()+", ");
			});
			content.append("chưa được xác nhận. Nhân viên vui lòng kiểm tra.");
			notification.setContent(content.toString());
			notificationService.sendToStaff(notification);
		}
		if(tourTrips.size() != 0 ) {
			Notification notification = new Notification();
			notification.setStatus(Constant.STATUS.UNREAD);
			notification.setTitle("Chuyến đi chưa được chốt");
			notification.setUsername("SYSTEM");
			StringBuilder content = new StringBuilder("Các chuyến đi:  ");
			tourTrips.forEach(e -> {
				content.append(e.getCode()+", ");
			});
			content.append("chưa được chốt. Nhân viên vui lòng kiểm tra");
			notification.setContent(content.toString());
			notificationService.sendToStaff(notification);
		}
	}

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
	public ResponMessage createAccount(AccountDTO signUp) {
		ResponMessage responMessage = new ResponMessage();
		try {
			if (accountRepository.existsByUsername(signUp.getUsername())) {
				responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
				responMessage.setMessage(Constant.MESSAGE.USERNAME_EXIST);
				return responMessage;

			} else if (accountRepository.existsByEmail(signUp.getEmail())) {
				responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
				responMessage.setMessage(Constant.MESSAGE.EMAIL_EXIST);
				return responMessage;
			} else {
				Account account = new Account();

				account.setStatus(Constant.STATUS.DE_ACTIVE);
				account.setUsername(signUp.getUsername());
				account.setPassword(passwordEncoder.encode(signUp.getPassword()));
				account.setProvider(Provider.LOCAL);
				Set<Role> roles = new HashSet<>();
				if(signUp.getRole() == null) {
					responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
					responMessage.setMessage("Role is null");
					return  responMessage;
				}
				Role role = roleRepository.findByName(signUp.getRole());
				roles.add(role);
				account.setRoles(roles);
				account.setEmail(signUp.getEmail());
				String token = generateToken();
				while (accountRepository.existsByCode(token)) {
					token =generateToken();
				}
				account.setName(signUp.getName());
				account.setCode(token);
				accountRepository.save(account);
				sendMailActiveAccount(token, signUp.getEmail());
				responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
				responMessage.setMessage(Constant.MESSAGE.SUCCESS);
				responMessage.setData(account);
				return responMessage;
			}

		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}

	public ResponMessage changeRole(String username,String role) {
		ResponMessage responMessage = new ResponMessage();
		try {
			Account account = accountRepository.findUserByUsername(username);
			if(account == null) {
				responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
				responMessage.setMessage(Constant.MESSAGE.NOT_FOUND_USER);
			} else {
				Role roles = roleRepository.findByName(role);
				Set<Role> set = new HashSet<>();
				set.add(roles);
				account.setRoles(set);
				accountRepository.save(account);
				responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
				responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			}

		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}

	public ResponMessage activeStatus(String username) {
		ResponMessage responMessage = new ResponMessage();
		try {
			Account account = accountRepository.findUserByUsername(username);
			if(account == null) {
				responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
				responMessage.setMessage(Constant.MESSAGE.NOT_FOUND_USER);
			} else {
				account.setStatus(Constant.STATUS.ACTIVE);
				responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
				responMessage.setMessage(Constant.MESSAGE.SUCCESS);
				responMessage.setData(accountRepository.save(account).toDTO());
			}

		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}
	public ResponMessage deactiveStatus(String username) {
		ResponMessage responMessage = new ResponMessage();
		try {
			Account account = accountRepository.findUserByUsername(username);
			if(account == null) {
				responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
				responMessage.setMessage(Constant.MESSAGE.NOT_FOUND_USER);
			} else {
				account.setStatus(Constant.STATUS.DE_ACTIVE);
				responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
				responMessage.setMessage(Constant.MESSAGE.SUCCESS);
				responMessage.setData(accountRepository.save(account).toDTO());
			}

		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}
	public ResponMessage deleteAccount(String username) {
		ResponMessage responMessage = new ResponMessage();
		try {
			    Account account = accountRepository.findUserByUsername(username);
				account.setRoles(null);
				account = accountRepository.save(account);
				Thread.sleep(1000);
				accountRepository.delete(account);
				responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
				responMessage.setMessage(Constant.MESSAGE.SUCCESS);
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}

	public ResponMessage findAll() {
		ResponMessage responMessage = new ResponMessage();
		try {
			List<AccountDTO> list = new ArrayList<>();
			accountRepository.findAll().forEach(e -> {
				AccountDTO accountDTO  = e.toDTO();
				if(e.getRoles().size()!=0)
				   accountDTO.setRole(e.getRoles().iterator().next().getName());
				list.add(accountDTO);
			});
			responMessage.setData(list);
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
			System.out.println(e.getMessage());
		}
		return responMessage;
	}

	public ResponMessage findAllRole() {
		ResponMessage responMessage = new ResponMessage();
		try {

			List<Role> roles = new ArrayList<>();
			roleRepository.findAll().forEach(e -> {
				e.setAccount(null);
				roles.add(e);
			});
			responMessage.setData(roles);
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
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

	public ResponMessage getListTourGuide() {
		ResponMessage responMessage = new ResponMessage();
		try  {
			List<AccountModel> accounts = new ArrayList<>();
			Role role = roleRepository.findByName("ROLE_TOURGUIDE");
			accountRepository.findAll().forEach(e -> {
				if(e.getRoles().contains(role)) {
					accounts.add(e.toModel());
				}
			});
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			responMessage.setData(accounts);
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}

}
