package hust.project3.repository.Tour;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hust.project3.entity.Tour.Destination;

import java.util.List;

@Repository
public interface TourDestinationRepository extends JpaRepository<Destination, Long> {
	@Query(value = "SELECT * FROM destination where id = :id",nativeQuery = true)
	Destination findDesById(@Param("id") Long id);

	@Query(value = "SELECT  * FROM destination where  id in (SELECT destination_id FROM tour_destination where tour_id = :tourId)", nativeQuery = true)
	List<Destination> findByTourId(@Param("tourId") Long tourId);

}
