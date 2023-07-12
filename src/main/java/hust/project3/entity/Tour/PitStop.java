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

import hust.project3.model.Tour.PitStopModel;
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
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String content;
	@Column(nullable = false)
	private String imageUrl;
	@Column(name = "orderNumber") 
	private int order;
	
	@ManyToOne 
    @JoinColumn(name = "tour_id") 
	@JsonIgnore
	private Tour tour;

	public PitStopModel toModel() {
		PitStopModel pitStop = new PitStopModel();
		pitStop.setId(id);
		pitStop.setContent(content);
		pitStop.setName(name);
		pitStop.setOrder(order);
		pitStop.setImageUrl(imageUrl);
		return pitStop;
	}

	

}
