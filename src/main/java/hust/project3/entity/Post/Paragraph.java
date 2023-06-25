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

import java.io.Serializable;

@Entity
@Getter
@Setter
public class Paragraph implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String title;
	@Column(length = 10000)
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
