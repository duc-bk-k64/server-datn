package hust.project3.entity;

import java.time.Instant;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import hust.project3.entity.BookTour.BookTour;
import hust.project3.entity.CustomerCare.ThreadMessage;
import hust.project3.entity.Money.Bill;
import hust.project3.entity.Money.Refund;
import hust.project3.entity.Tour.TourTrip;
import hust.project3.model.AccountDTO;
import hust.project3.model.AccountModel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String username;
	@Column(nullable = false)
	private String password;
	@Column
	private Long status;
	@Column
	private String tokenForgotPassword;
	@Column
	private Instant timeCreatioToken;
	@Column
	private String email;
	@Column
	private String code;
	@Column
	private String name;
	
	
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "account_role", joinColumns = {
			@JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false) })
	private Set<Role> roles;
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "account_permission", joinColumns = {
			@JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "permission_id", referencedColumnName = "id", nullable = false) })
	private Set<Permission> permissions;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
	private Profile profile;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private Set<ThreadMessage> threadMessages;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private Set<BookTour> bookTours;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "account_tourtrip", joinColumns = {
			@JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "tourtrip_id", referencedColumnName = "id", nullable = false) })
	private Set<TourTrip> tourTrips ;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private Set<Refund> refunds;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private Set<Bill> bills;

	// oauth2
	@Enumerated(EnumType.STRING)
	private Provider provider;

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public AccountModel toModel() {
		AccountModel accountModel = new AccountModel();
		accountModel.setUsername(username);
		accountModel.setEmail(email);
		return accountModel;
	}

	public AccountDTO toDTO() {
		AccountDTO account = new AccountDTO();
		account.setId(id);
		account.setUsername(username);
//		account.setPassword(password);
		account.setStatus(status);
		account.setTokenForgotPassword(tokenForgotPassword);
		account.setEmail(email);
		account.setCode(code);
		account.setName(name);
		account.setProvider(String.valueOf(provider));
		return  account;
	}


}
