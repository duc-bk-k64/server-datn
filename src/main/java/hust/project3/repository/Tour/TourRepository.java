package hust.project3.repository.Tour;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hust.project3.entity.Tour.Tour;

import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour,Long>{
	Boolean existsByCode(String code);
	
	@Query(value = "SELECT * FROM tour where id = :id",nativeQuery = true)
	Tour findTourById(@Param("id") Long id);

	@Query(value = "SELECT * FROM tour where status = 'available' order by id desc limit 6",nativeQuery = true)
	List<Tour> findTourAvailable();

	@Query(value = "SELECT * FROM tour  order by id desc ",nativeQuery = true)
	List<Tour> findAllTour();

	@Query(value = "SELECT * FROM tour where departure = :departure and status = 'available' and number_of_day between :minDay and :maxDay", nativeQuery = true)
	List<Tour> findTour(@Param("departure") String departure, @Param("minDay") int minDay, @Param("maxDay") int maxDay);

	@Query (value = "DELETE  FROM tour_destination where tour_id =:id", nativeQuery = true )
	void deleteRelationDes(@Param("id") Long id);

}
