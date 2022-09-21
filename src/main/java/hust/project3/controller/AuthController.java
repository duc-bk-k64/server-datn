package hust.project3.controller;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import hust.project3.common.*;
import hust.project3.config.jwtconfig.JwtProvider;
import hust.project3.entity.Account;
import hust.project3.entity.Permission;
import hust.project3.entity.Role;
import hust.project3.model.ResponMessage;
import hust.project3.model.SignInData;
import hust.project3.model.fotgotPW;
import hust.project3.repository.AccountRepository;
import hust.project3.repository.PermissionRepository;
import hust.project3.repository.RoleRepository;
import hust.project3.service.AccountService;

@RestController
@RequestMapping(Constant.API.PREFIX_AUTH)
public class AuthController {
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private PermissionRepository permissionRepository;
	@Autowired
	private AccountService accountService;

	@PostMapping("/signin")
	@ResponseBody
	public ResponMessage signIn(@RequestBody SignInData data) {

		Account account = accountRepository.findUserByUsername(data.getUserName());
		ResponMessage responMessage = new ResponMessage();

		if (account == null) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(Constant.MESSAGE.NOT_FOUND_USER);
			return responMessage;
		} else if (!passwordEncoder.matches(data.getPassWord(), account.getPassword())) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(Constant.MESSAGE.PASSWORD_INCORRECT);
			return responMessage;

		} else {
			String token = jwtProvider.generateToken(data.getUserName());
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			responMessage.setData(token);
			return responMessage;
		}
	}
	@PostMapping("/signup")
	@ResponseBody
	public ResponMessage signup(@RequestBody SignInData signUp) {
		ResponMessage responMessage = new ResponMessage();
		if (accountRepository.existsByUsername(signUp.getUserName())) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(Constant.MESSAGE.USERNAME_EXIST);
			return responMessage;

		} else {
			try {
				Account account = new Account();
				account.setStatus(Constant.STATUS.ACTIVE);
				account.setUsername(signUp.getUserName());
				account.setPassword(passwordEncoder.encode(signUp.getPassWord()));
				Set<Role> roles = new HashSet<>();
				Role role = roleRepository.findByName(Constant.ROLE.EMPLOYEE);
				roles.add(role);
				Set<Permission> permissions=new HashSet<>();
				Permission  permission=permissionRepository.findByName(Constant.PERMISSION.READ);
				permissions.add(permission);
				account.setRoles(roles);
				account.setPermissions(permissions);
				account.setEmail(signUp.getEmail());
				accountRepository.save(account);
				responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
				responMessage.setMessage(Constant.MESSAGE.SUCCESS);
				responMessage.setData(account);
				return responMessage;
			} catch (Exception e) {
				responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
				responMessage.setMessage(Constant.MESSAGE.ERROR);
				responMessage.setData(e.getMessage());
				return responMessage;
			}
		}

	}
	@PostMapping("/forgotpw")
	@ResponseBody
	public ResponMessage forgotPW(@RequestBody fotgotPW forgot) throws Exception {
		
		ResponMessage responMessage = new ResponMessage();
		try {
			String message = accountService.forgotPassword(forgot.getUserName());
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			responMessage.setData(message);
			return responMessage;
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(Constant.MESSAGE.ERROR);
			responMessage.setData(e.getMessage());
			return responMessage;
		}

	}

	@PostMapping("/resetpw")
	@ResponseBody
	public ResponMessage resetPW(@RequestBody hust.project3.model.resetPW reset) {
		ResponMessage responMessage= new ResponMessage();
		try {
			String message = accountService.resetPassword(reset.getToken(), reset.getPassword());
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			responMessage.setData(message);
			return responMessage;
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(Constant.MESSAGE.ERROR);
			responMessage.setData(e.getMessage());
			return responMessage;		}
	}

}
