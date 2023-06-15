package hust.project3.model.Money;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BillModel {
    private Long id;
    private String code;
    private Long totalMoney;
    private String content;
    private String detail;
    private String status;
    private String timeCreated;
}
