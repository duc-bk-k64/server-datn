package hust.project3.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import hust.project3.common.Constant;
import hust.project3.entity.Notification;
import hust.project3.repository.NotificationRepository;

@Service
public class NotificationService {
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	@Autowired
	private NotificationRepository notificationRepository;
	
	public void sendNotifcationToUser(String username, Notification message) throws Exception {
		try {
			message.setStatus(Constant.STATUS.UNREAD);
			message.setTimeCreated(Instant.now());
			notificationRepository.save(message);
			simpMessagingTemplate.convertAndSendToUser(username, "/queue/reply",message.getTitle());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public void sendToStaff(Notification message) throws Exception {
		try {
			message.setStatus(Constant.STATUS.UNREAD);
			message.setTimeCreated(Instant.now());
			notificationRepository.save(message);
			simpMessagingTemplate.convertAndSend( "/topic/staff",message.getTitle());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public void test() {
		this.simpMessagingTemplate.convertAndSend("/topic/test", "test abcd");
	}

}
