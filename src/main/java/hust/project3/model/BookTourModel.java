package hust.project3.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookTourModel {
	private Long id;
	private String name;
	private String email;
	private String  code;
	private String tourTripCode;
	private String timeCreate;
	private String status;
	private Long moneyToPay;
	private String detail;

}
