package hust.project3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hust.project3.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	Account findUserByUsername(String username);

//	Boolean existByUsername(String username);
	Boolean existsByUsername(String username);
	Account findByTokenForgotPassword(String token);

}
