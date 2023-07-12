package hust.project3.entity.Tour;

import java.time.Instant;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import hust.project3.Utils.DateUtils;
import hust.project3.model.Tour.TourModel;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class Tour {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String code;
	@Column 
	private String createdBy;
	@Column (nullable = false)
	private int numberOfDay;
	@Column(nullable = false)
	private int numberOfNight;
	@Column
	private String departure;
	@Column(nullable = false)
	private String imageUrl;
	@Column
	private String status;
//	@Column
//	private Instant timeCreated;
	
	@OneToMany(mappedBy = "tour", cascade = CascadeType.ALL)
	private Set<TourTrip> tourTrips;
	
	@OneToMany(mappedBy = "tour", cascade = CascadeType.ALL)
	private Set<FeedBack> feedBacks ;
	
	@OneToMany(mappedBy = "tour", cascade = CascadeType.ALL)
	private Set<PitStop> pitStops;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "tour_destination", joinColumns = {
			@JoinColumn(name = "tour_id", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "destination_id", referencedColumnName = "id", nullable = false) })
	private Set<Destination> destinations;

	public TourModel toModel() {
		TourModel tour = new TourModel();
		tour.setCode(code);
		tour.setCreatedBy(createdBy);
		tour.setDeparture(departure);
		tour.setId(id);
		tour.setImageUrl(imageUrl);
		tour.setName(name);
		tour.setNumberOfDay(numberOfDay);
		tour.setNumberOfNight(numberOfNight);
		tour.setStatus(status);
//		tour.setTimeCreated(DateUtils.Instant2String(timeCreated));
		return tour;

	}
	

}
