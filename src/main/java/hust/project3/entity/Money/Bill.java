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

import hust.project3.Utils.DateUtils;
import hust.project3.entity.Account;
import hust.project3.model.Money.BillModel;
import hust.project3.model.Money.RefundModel;
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

	public BillModel toModel() {
		BillModel refundModel = new BillModel();
		refundModel.setCode(code);
		refundModel.setContent(content);
		refundModel.setId(id);
		refundModel.setTotalMoney(totalMoney);
		refundModel.setStatus(status);
		refundModel.setTimeCreated(DateUtils.Instant2String(timeCreated));
		return  refundModel;
	}

}
