package hust.project3.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hust.project3.common.Constant;
import hust.project3.entity.Notification;
import hust.project3.model.NotificationModel;
import hust.project3.model.ResponMessage;
import hust.project3.repository.NotificationRepository;

@RestController
@RequestMapping(Constant.API.PREFIX)
public class NotificationController {
	@Autowired
	private NotificationRepository notificationRepository;
	
	@GetMapping("/notifi/findByUsername")
	@ResponseBody
	public ResponMessage findById(@RequestParam String username) {
		ResponMessage responMessage = new ResponMessage();
		try {
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			ArrayList<NotificationModel> notificationModels = new ArrayList<>();
			notificationRepository.findNotifiByUsername(username).forEach(e -> {
				notificationModels.add(e.toModel());
			});
			responMessage.setData(notificationModels);
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}
	
	@PutMapping("/notifi/read")
	@ResponseBody
	public ResponMessage readNotification(@RequestParam Long id) {
		ResponMessage responMessage = new ResponMessage();
		try {
			Notification notification = notificationRepository.findNotifiById(id);
			notification.setStatus(Constant.STATUS.READED);
			notificationRepository.save(notification);
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			responMessage.setData(notification.toModel());
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}
	
	
	

}
