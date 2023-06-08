package hust.project3.repository.Tour;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hust.project3.entity.Tour.PitStop;

import java.util.List;

@Repository
public interface PitStopRepository extends JpaRepository<PitStop, Long> {
    @Query(value = "SELECT * FROM pitstop where tour_id = :tourId order by order_number", nativeQuery = true)
    List<PitStop> findPitStopById(@Param("tourId") Long tourId);

}
