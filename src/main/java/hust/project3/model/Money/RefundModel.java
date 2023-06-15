package hust.project3.model.Money;

import hust.project3.model.AccountDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.Instant;

@Getter
@Setter
public class RefundModel {
    private Long id;
    private String code;
    private Long totalMoney;
    private String content;
    private String status;
    private String timeCreated;
    private AccountDTO account;
}
