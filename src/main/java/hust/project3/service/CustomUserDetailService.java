package hust.project3.service;

import hust.project3.common.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hust.project3.config.jwtconfig.CustomUserDetail;
import hust.project3.entity.Account;
import hust.project3.repository.AccountRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Account account = accountRepository.findUserByUsername(username);
		return CustomUserDetail.createCustomUserDetails(account);
	}

}