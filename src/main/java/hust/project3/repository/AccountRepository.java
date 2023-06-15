package hust.project3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hust.project3.entity.Account;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	Account findUserByUsername(String username);

	Boolean existsByEmail(String email);

	Boolean existsByUsername(String username);

	Account findByTokenForgotPassword(String token);

	Boolean existsByTokenForgotPassword(String token);

	Boolean existsByCode(String code);

	Account findByCode(String code);

	@Query( value = "SELECT  * FROM account ", nativeQuery = true)
	List<Account> findAllAccount();

}
