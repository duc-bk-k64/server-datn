package hust.project3.model.Tour;

import hust.project3.entity.Tour.Tour;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourModel {
	private Long id;
	private String name;
	private String code;
	private String createdBy;
	private int numberOfDay;
	private int numberOfNight;
	private String departure;
	private String imageUrl;
	private String status;
	
	public Tour toObject() {
		Tour tour = new Tour();
		tour.setCode(code);
		tour.setCreatedBy(createdBy);
		tour.setDeparture(departure);
		tour.setId(id);
		tour.setImageUrl(imageUrl);
		tour.setName(name);
		tour.setNumberOfDay(numberOfDay);
		tour.setNumberOfNight(numberOfNight);
		tour.setStatus(status);
		return tour;
	}

}
