package hust.project3.entity.CustomerCare;

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
public class Reply {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String content;
	@Column(nullable = false)
	private String createdBy;
	
	@JsonIgnore
	@ManyToOne 
    @JoinColumn(name = "thread_id") 
	private ThreadMessage thread;

}
