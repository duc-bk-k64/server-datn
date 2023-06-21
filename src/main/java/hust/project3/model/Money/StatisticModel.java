package hust.project3.model.Money;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class StatisticModel {
    private String day;
    private Long value;
}
