package hust.project3.model;


import hust.project3.Utils.DateUtils;
import hust.project3.entity.BookTour.BookTour;
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
	private int numberOfAdjust;
	private  int numberOfChildren;
	private String phoneNumber;

	public BookTour toObject() {
		BookTour bookTourModel = new BookTour();
		bookTourModel.setId(this.id);
		bookTourModel.setCode(this.code);
		bookTourModel.setDetail(this.detail);
		bookTourModel.setEmail(this.email);
		bookTourModel.setMoneyToPay(this.moneyToPay);
		bookTourModel.setName(this.name);
		bookTourModel.setStatus(this.status);
		bookTourModel.setTimeCreate(DateUtils.String2Instant(timeCreate));
		bookTourModel.setTourTripCode(this.tourTripCode);
		bookTourModel.setNumberOfAdjust(numberOfAdjust);
		bookTourModel.setNumberOfChildren(numberOfChildren);
		bookTourModel.setPhoneNumber(phoneNumber);
		return bookTourModel;
	}

}
