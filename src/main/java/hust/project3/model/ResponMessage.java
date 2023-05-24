package hust.project3.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponMessage implements Serializable {
	
	private Long resultCode;
	private String message;
	private Object data;
}
