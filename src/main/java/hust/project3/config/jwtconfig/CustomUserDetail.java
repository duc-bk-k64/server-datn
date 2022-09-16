package hust.project3.config.jwtconfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import hust.project3.entity.Account;
import hust.project3.entity.Permission;
import hust.project3.entity.Role;

public class CustomUserDetail implements UserDetails {
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> roles;

	public static CustomUserDetail createCustomUserDetails(Account account) {
		if (account != null) {
//			List<GrantedAuthority> roless = account.getRoles().stream().filter(Objects::nonNull)
//					.map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
			List<GrantedAuthority> roleAuthorities = new ArrayList<>();
			for (Role role : account.getRoles()) {
				roleAuthorities.add(new SimpleGrantedAuthority(role.getName()));
			}
			for (Permission permission : account.getPermissions()) {
				roleAuthorities.add(new SimpleGrantedAuthority(permission.getName()));
			}
			CustomUserDetail customUserDetails = new CustomUserDetail();
			customUserDetails.setUsername(account.getUsername());
			customUserDetails.setPassword(account.getPassword());
			customUserDetails.setRoles(roleAuthorities);
//			System.out.println(roleAuthorities.toString());
			return customUserDetails;
		}
		return null;
	}

	public Collection<? extends GrantedAuthority> getRoles() {
		return roles;
	}

	public void setRoles(Collection<? extends GrantedAuthority> roles) {
		this.roles = roles;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return roles;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
