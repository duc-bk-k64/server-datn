package hust.project3.entity.Post;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import hust.project3.entity.Tour.Destination;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String title;
	@Column
	private String imageUrl;
	@Column
	private Date timeCreated;
	@Column
	private String createdBy;
	@Column
	private String status;
	
	@ManyToOne 
    @JoinColumn(name = "destination_id") 
	@JsonIgnore
	private Destination destination;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private Set<Paragraph> paragraphs;

}
