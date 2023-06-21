package hust.project3.model.Money;

import hust.project3.Utils.DateUtils;
import hust.project3.entity.Money.Transaction;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.Instant;

@Getter
@Setter
public class TransactionModel {
    private Long id;
    private String code;
    private Long totalMoney;
    private String content;
    private String createdBy;
    private String status;
    private String timeCreated;
    private String type;

    public Transaction toObject() {
        Transaction transaction = new Transaction();
        transaction.setTotalMoney(totalMoney);
        transaction.setType(type);
        transaction.setStatus(status);
        transaction.setContent(content);
        transaction.setTimeCreated(null);
        transaction.setCode(code);
        transaction.setCreatedBy(createdBy);
        transaction.setId(id);
        return  transaction;
    }
}
