package hust.project3.entity.CustomerCare;

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

import hust.project3.entity.Account;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ThreadMessage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String title;

	@Column
	private  String status;

	@OneToMany(mappedBy = "thread", cascade = CascadeType.ALL)
	private Set<Question> questions;

	@OneToMany(mappedBy = "thread", cascade = CascadeType.ALL)
	private Set<Reply> replies;
	
	@JsonIgnore
	@ManyToOne 
    @JoinColumn(name = "account_id") 
	private Account account;

}
