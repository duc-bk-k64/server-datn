package hust.project3.service;

import java.time.Instant;

import hust.project3.model.Tour.FeedbackModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
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

//	@Autowired
//	private KafkaTemplate<String, Object> kafkaTemplate;
	
	public void sendNotifcationToUser(String username, Notification message) throws Exception {
		try {
			message.setStatus(Constant.STATUS.UNREAD);
			message.setTimeCreated(Instant.now());
			notificationRepository.save(message);
//			kafkaTemplate.send("notificationDATN",message);
			simpMessagingTemplate.convertAndSendToUser(username, "/queue/reply",message);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public void sendToStaff(Notification message) throws Exception {
		try {
			message.setStatus(Constant.STATUS.UNREAD);
			message.setTimeCreated(Instant.now());
			notificationRepository.save(message);
//			kafkaTemplate.send("notificationStaffDATN",message);
			simpMessagingTemplate.convertAndSend( "/topic/staff",message.getTitle());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

//	@KafkaListener(id = "sendUser", topics = "notificationDATN")
//	public void sendUser(Notification message) {
////		System.out.println(message.getTitle());
//		simpMessagingTemplate.convertAndSendToUser(message.getUsername(), "/queue/reply",message.getTitle());
//	}
//
//	@KafkaListener(id = "sendStaff", topics = "notificationStaffDATN")
//	public void sendStaff(Notification message) {
//		System.out.println(message.getTitle());
////		simpMessagingTemplate.convertAndSend( "/topic/staff",message.getTitle());
//	}

	public void test() {
		this.simpMessagingTemplate.convertAndSend("/topic/test", "test abcd");
	}

}
