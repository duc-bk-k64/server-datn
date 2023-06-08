package hust.project3.entity.Tour;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import hust.project3.entity.Post.Post;
import hust.project3.model.Tour.DestinationModel;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class Destination {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String name;
	
//	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@ManyToMany(mappedBy = "destinations")
	private Set<Tour> tours;
	
	@OneToMany(mappedBy = "destination", cascade = CascadeType.ALL)
	private Set<Post> posts;

	public DestinationModel toModel() {
		DestinationModel destinationModel = new DestinationModel();
		destinationModel.setId(id);
		destinationModel.setName(name);
		return  destinationModel;
	}

}
