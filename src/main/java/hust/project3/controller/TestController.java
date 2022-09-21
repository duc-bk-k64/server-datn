package hust.project3.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hust.project3.common.Constant;
import hust.project3.model.ResponMessage;

@RestController
@RequestMapping(Constant.API.PREFIX)
public class TestController {
	@GetMapping("/")
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_EMPLOYEE') and  hasAuthority('READ')")
	public ResponMessage test() {
		ResponMessage responMessage= new ResponMessage();
		responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
		responMessage.setMessage(Constant.MESSAGE.SUCCESS);
		responMessage.setData("Logout successfully");
		return responMessage;
	}

}
