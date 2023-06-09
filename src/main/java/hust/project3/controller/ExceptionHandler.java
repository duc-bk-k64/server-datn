package hust.project3.controller;

import org.apache.log4j.lf5.AppenderFinalizer;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import hust.project3.common.Constant;
import hust.project3.model.ResponMessage;

@ControllerAdvice
public class ExceptionHandler {
	
	@org.springframework.web.bind.annotation.ExceptionHandler(NoHandlerFoundException.class)
	@ResponseBody
	public ResponMessage handle404(final NoHandlerFoundException e) {
		ResponMessage responMessage=new ResponMessage();
		responMessage.setMessage(Constant.MESSAGE.NOT_FOUND_HANDLE);
		responMessage.setResultCode(Constant.RESULT_CODE.NOT_FOUND);
		responMessage.setData(e.getMessage());
		return responMessage;
	}
	@org.springframework.web.bind.annotation.ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseBody
	public ResponMessage handle405(final HttpRequestMethodNotSupportedException e) {
		ResponMessage responMessage=new ResponMessage();
		responMessage.setMessage(Constant.MESSAGE.NOT_ALLOWED);
		responMessage.setResultCode(Constant.RESULT_CODE.NOT_ALLOWED);
		responMessage.setData(e.getMessage());
		return responMessage;
	}
//	@org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class)
//	@ResponseBody
//	public ResponMessage handle403(final AccessDeniedException e) {
//		ResponMessage responMessage = new ResponMessage();
//		responMessage.setResultCode(Constant.RESULT_CODE.UNAUTHORIZED);
//		responMessage.setMessage(Constant.MESSAGE.ERROR);
//		responMessage.setData("Access denied");
//		return responMessage;
//	}
//	@org.springframework.web.bind.annotation.ExceptionHandler(AuthenticationException.class)
//	@ResponseBody
//	public ResponMessage handle400(final AuthenticationException e) {
//		ResponMessage responMessage = new ResponMessage();
//		responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
//		responMessage.setMessage(Constant.MESSAGE.ERROR);
//		responMessage.setData("Authentication failure");
//		return responMessage;
//	}
//	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
//	@ResponseBody
//	public ResponMessage handle500(Exception e) {
//		ResponMessage responMessage=new ResponMessage();
//		responMessage.setMessage(Constant.MESSAGE.INTERNAL_ERROR);
//		responMessage.setResultCode(Constant.RESULT_CODE.INTERNAL_ERROR);
//		responMessage.setData(e.getMessage());
//		return responMessage;
//	}


}
