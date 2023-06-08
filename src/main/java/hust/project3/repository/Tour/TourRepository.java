package hust.project3.repository.Tour;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hust.project3.entity.Tour.Tour;

@Repository
public interface TourRepository extends JpaRepository<Tour,Long>{
	Boolean existsByCode(String code);
	
	@Query(value = "SELECT * FROM tour where id = :id",nativeQuery = true)
	Tour findTourById(@Param("id") Long id);

}
