package hust.project3.entity.BookTour;

import java.time.Instant;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import hust.project3.Utils.DateUtils;
import hust.project3.entity.Account;
import hust.project3.model.BookTourModel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BookTour {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String email;
	@Column
	private String  code;
	@Column(nullable = false)
	private String tourTripCode;
	@Column
	private Instant timeCreate;
	@Column
	private String status;
	@Column
	private Long moneyToPay;
	@Column
	private String detail;
	@Column(nullable = false)
	private  int numberOfAdjust;
	@Column(nullable = false)
	private  int numberOfChildren;
	@Column(nullable = false)
	private String phoneNumber;
	
	@JsonIgnore
	@ManyToOne 
    @JoinColumn(name = "account_id",nullable = false) 
	private Account account;
	
	public BookTourModel toModel() {
		BookTourModel bookTourModel = new BookTourModel();
		bookTourModel.setId(this.id);
		bookTourModel.setCode(this.code);
		bookTourModel.setDetail(this.detail);
		bookTourModel.setEmail(this.email);
		bookTourModel.setMoneyToPay(this.moneyToPay);
		bookTourModel.setName(this.name);
		bookTourModel.setStatus(this.status);
		bookTourModel.setTimeCreate(DateUtils.Instant2String(this.timeCreate));
		bookTourModel.setTourTripCode(this.tourTripCode);
		bookTourModel.setNumberOfAdjust(numberOfAdjust);
		bookTourModel.setNumberOfChildren(numberOfChildren);
		bookTourModel.setPhoneNumber(phoneNumber);
		return bookTourModel;
	}

}
