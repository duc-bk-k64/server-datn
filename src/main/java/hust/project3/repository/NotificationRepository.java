package hust.project3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hust.project3.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
	
	List<Notification> findByUsername(String username);
	
	@Query(value = "SELECT * from notification where id = :id", nativeQuery = true)
	Notification findNotifiById(@Param("id") Long id);
	
	@Query(value = "SELECT * from notification where username = :username order by time_created desc", nativeQuery = true)
	List<Notification> findNotifiByUsername(@Param("username") String username);

}
