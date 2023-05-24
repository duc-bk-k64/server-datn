package hust.project3.entity.Money;

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
public class Refund {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String code;
	@Column
	private Long totalMoney;
	@Column
	private String content;
	@Column
	private String status;
	
	@ManyToOne 
    @JoinColumn(name = "account_id") 
	@JsonIgnore
	private Account account;

}
