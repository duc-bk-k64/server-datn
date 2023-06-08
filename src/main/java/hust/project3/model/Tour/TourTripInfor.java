package hust.project3.model.Tour;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourTripInfor {
    private Long id;
    private String code;
    private Long priceForChidren;
    private  Long price;
    private String departureDay;
    private String status;
    private String note;
    private  TourModel tourModel;
}
