package hust.project3.entity.Tour;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@Table(name = "pitstop")
public class PitStop {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String name;
	@Column
	private String content;
	@Column
	private String imageUrl;
	@Column(name = "orderNumber") 
	private int order;
	
	@ManyToOne 
    @JoinColumn(name = "tour_id") 
	@JsonIgnore
	private Tour tour;
	

}
