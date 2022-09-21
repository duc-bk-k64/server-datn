package hust.project3.repository;

import java.time.Instant;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hust.project3.entity.ManageToken;

@Repository
public interface ManageTokenRepository extends JpaRepository<ManageToken, Long> {
	Boolean existsByToken(String token);

	@Query(value = "DELETE FROM manage_token WHERE time_create < (NOW() - INTERVAL :timeExpire SECOND)", nativeQuery = true)
	@Modifying
    @Transactional
	void removeTokenExprire(@Param("timeExpire") Long timeExpire);
}
