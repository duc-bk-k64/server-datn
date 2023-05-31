package hust.project3.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import hust.project3.Utils.DateUtils;
import hust.project3.model.NotificationModel;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String title;
	@Column
	private String content;
	@Column
	private String username;
	@Column
	private String status;
	@Column
	private Instant timeCreated;
	
	public NotificationModel toModel() {
		NotificationModel notificationModel = new NotificationModel();
		notificationModel.setId(id);
		notificationModel.setContent(content);
		notificationModel.setStatus(status);
		notificationModel.setTimeCreated(DateUtils.Instant2String(timeCreated));
		notificationModel.setTitle(title);
		notificationModel.setUsername(username);
		return notificationModel;
	
	}

}
