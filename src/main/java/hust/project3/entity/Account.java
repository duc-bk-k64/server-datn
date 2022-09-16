package hust.project3.entity;

import java.time.Instant;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
