package hust.project3.entity.Money;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class Transaction {
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
	private String createdBy;
	@Column
	private String status;

}
