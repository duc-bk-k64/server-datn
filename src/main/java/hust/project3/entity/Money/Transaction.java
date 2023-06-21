package hust.project3.entity.Money;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import hust.project3.Utils.DateUtils;
import hust.project3.model.Money.TransactionModel;
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

	@Column
	private Instant timeCreated;
	@Column
	private String type;

	public TransactionModel toModel() {
		TransactionModel transaction = new TransactionModel();
		transaction.setTotalMoney(totalMoney);
		transaction.setType(type);
		transaction.setStatus(status);
		transaction.setContent(content);
		transaction.setTimeCreated(DateUtils.Instant2String(timeCreated));
		transaction.setCode(code);
		transaction.setCreatedBy(createdBy);
		transaction.setId(id);
		return  transaction;
	}

}
