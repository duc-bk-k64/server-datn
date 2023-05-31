package hust.project3.entity.Money;

import java.time.Instant;

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
public class Bill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String code;
	@Column
	private Long totalMoney;
	@Column
	private String content;
	@Column
	private String detail;
	@Column
	private String status;
	
	@Column
	private Instant timeCreated;
	
	@ManyToOne 
    @JoinColumn(name = "account_id") 
	@JsonIgnore
	private Account account;

}
