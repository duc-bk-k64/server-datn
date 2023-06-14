package hust.project3.model.Tour;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindTourModel {
    private String departure;
    private Long destination;
    private String time;
    private String price;
}
