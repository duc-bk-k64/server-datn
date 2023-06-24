package hust.project3.repository.Tour;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hust.project3.entity.Tour.TourTrip;

import java.util.List;

@Repository
public interface TourTripRepository extends JpaRepository<TourTrip,Long> {
	Boolean existsByCode(String code);

	TourTrip findByCode(String code);

	@Query(value = "SELECT * FROM tour_trip where tour_id = :id", nativeQuery = true)
	List<TourTrip> findByTourId(@Param("id") Long id);

	@Query(value = "SELECT * FROM tour_trip where tour_id = :id and status = 'available'", nativeQuery = true)
	List<TourTrip> findTripAvailableByTourId(@Param("id") Long id);

	@Query(value = "SELECT * FROM tour_trip where id = :id", nativeQuery = true)
	TourTrip findTripById(@Param("id") Long id);

	@Query(value = "SELECT * FROM tour_trip where tour_guide = :username order by departure_day desc", nativeQuery = true)
	List<TourTrip> findTripByTourGuide(@Param("username") String username);

	@Query(value = "SELECT * FROM tour_trip where status = 'available' and departure_day <  (NOW() + INTERVAL 5 DAY )", nativeQuery = true)
	List<TourTrip> findTripNeedAlert();

}
