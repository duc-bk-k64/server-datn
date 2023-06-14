package hust.project3.controller;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import hust.project3.Utils.DateUtils;
import hust.project3.common.Constant;
import hust.project3.config.jwtconfig.JwtProvider;
import hust.project3.entity.ManageToken;
import hust.project3.entity.Profile;
import hust.project3.model.ProfileModel;
import hust.project3.model.ResponMessage;
import hust.project3.repository.ManageTokenRepository;
import hust.project3.service.AccountService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(Constant.API.PREFIX)
public class AccountController {
	@Autowired
	ManageTokenRepository manageTokenRepository;
	@Autowired
	private AccountService accountService;

	@PostMapping("/logout")
	@ResponseBody
	public ResponMessage logout() {
		ResponMessage responMessage = new ResponMessage();
		ManageToken manageToken = new ManageToken();
		manageToken.setTime_create(Instant.now());
		String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getHeader("Authorization");
		manageToken.setToken(token);
		try {
			manageTokenRepository.removeTokenExprire(JwtProvider.time_expire / 1000);
			manageTokenRepository.save(manageToken);
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			responMessage.setData(manageToken);
			return responMessage;

		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(Constant.MESSAGE.ERROR);
			responMessage.setData(e.getMessage());
			return responMessage;
		}

	}

	@PutMapping("/updateProfile")
	@ResponseBody
	@ApiOperation("dateOfBirth must format as yyyy-MM-dd")
	public ResponMessage updateProfile(@RequestParam String username, @RequestBody ProfileModel profile) {
		ResponMessage responMessage = new ResponMessage();
		try {
			Date date = DateUtils.String2Date(profile.getDateOfBirth());
			Profile profileModel = new Profile(profile.getId(), profile.getPhoneNumber(), profile.getSex(), date, null);
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(accountService.updateProfile(username, profileModel));

		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;

	}

	@GetMapping("/getProfile")
	@ResponseBody
	public ResponMessage getProfile(@RequestParam String username) {
		ResponMessage responMessage = new ResponMessage();
		try {

			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			Profile profile = accountService.getProfile(username);
			ProfileModel profileModel = new ProfileModel();
			profileModel.setId(profile.getId());
			profileModel.setDateOfBirth(DateUtils.Date2String(profile.getDateOfBirth()));
			profileModel.setPhoneNumber(profile.getPhoneNumber());
			profileModel.setSex(profile.getSex());
			responMessage.setData(profileModel);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
//			System.out.println(profile.getPhoneNumber());
			return responMessage;

		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
			return responMessage;
		}
//		return responMessage;

	}
	@GetMapping("/account/tourguide")
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_STAFF')")
	public  ResponMessage getTourguide() {
		return  accountService.getListTourGuide();
	}

}
