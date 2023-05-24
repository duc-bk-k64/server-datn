package hust.project3.entity.BookTour;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import hust.project3.entity.Account;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BookTour {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String name;
	@Column
	private String email;
	@Column
	private String  code;
	@Column
	private String tourTripCode;
	@Column
	private Date timeCreate;
	@Column
	private String status;
	@Column
	private Long moneyToPay;
	@Column
	private String detail;
	
	@JsonIgnore
	@ManyToOne 
    @JoinColumn(name = "account_id") 
	private Account account;

}