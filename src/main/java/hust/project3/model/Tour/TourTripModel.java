package hust.project3.model.Tour;

import hust.project3.Utils.DateUtils;
import hust.project3.entity.Tour.TourTrip;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.text.ParseException;
import java.util.Date;

@Getter
@Setter
public class TourTripModel {
    private Long id;
    private String code;
    private Long priceForChidren;
    private  Long price;
    private String departureDay;
    private String status;
    private String note;

    private  String tourGuide;

    public TourTrip toObject() throws ParseException {
        TourTrip tourTrip = new TourTrip();
        tourTrip.setStatus(status);
        tourTrip.setId(id);
        tourTrip.setNote(note);
        tourTrip.setDepartureDay(DateUtils.String2Date(departureDay));
        tourTrip.setPrice(price);
        tourTrip.setPriceForChidren(priceForChidren);
        tourTrip.setTourGuide(tourGuide);
        return tourTrip;
    }
}
