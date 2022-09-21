package hust.project3.controller;

import static org.springframework.util.StringUtils.hasText;

import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hust.project3.common.Constant;
import hust.project3.config.jwtconfig.JwtProvider;
import hust.project3.entity.Account;
import hust.project3.entity.ManageToken;
import hust.project3.model.ResponMessage;
import hust.project3.repository.ManageTokenRepository;

@RestController
@RequestMapping(Constant.API.PREFIX)
public class AccountController {
	@Autowired
	ManageTokenRepository manageTokenRepository;

	@PostMapping("/logout")
	@ResponseBody
	public ResponMessage logout(@RequestParam String token) {
		ResponMessage responMessage = new ResponMessage();
		ManageToken manageToken = new ManageToken();
		manageToken.setTime_create(Instant.now());
		manageToken.setToken(token);
		try {
			manageTokenRepository.removeTokenExprire(JwtProvider.time_expire/1000);
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
}
