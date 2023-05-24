package hust.project3.entity.Post;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Paragraph {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String title;
	@Column
	private String  content;
	@Column
	private String imageUrl;
	@Column(name = "numberOrder")
	private int order;
	
	@ManyToOne 
    @JoinColumn(name = "post_id") 
	@JsonIgnore
	private Post post;

}
