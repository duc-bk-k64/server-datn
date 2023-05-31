package hust.project3.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class loginResponse {
	private String token;
	private String username;
	private String role;

}
