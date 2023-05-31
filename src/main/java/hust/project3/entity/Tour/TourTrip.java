package hust.project3.entity.Tour;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import hust.project3.entity.Account;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class TourTrip {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String code;
	@Column
	private Long priceForChidren;
	@Column
	private  Long price;
	@Column
	private Date departureDay;
	@Column
	private String status;
	
	@Column(length = 5000)
	private String note;
	
	@ManyToMany(mappedBy = "tourTrips")
	private Set<Account> account;
	
	@ManyToOne 
    @JoinColumn(name = "tour_id") 
	@JsonIgnore
	private Tour tour;
}
