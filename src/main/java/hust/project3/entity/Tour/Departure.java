package hust.project3.entity.Tour;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import hust.project3.entity.Post.Post;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Departure {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String name;
	@Column
	private String value;

}
