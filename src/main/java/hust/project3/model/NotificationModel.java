package hust.project3.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationModel {
	private Long id;
	private String title;
	private String content;
	private String status;
	private String timeCreated;
	private String username;

}
