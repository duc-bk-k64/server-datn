package hust.project3.repository.Tour;

import hust.project3.entity.Tour.TripPitstop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripPitstopRepository extends JpaRepository<TripPitstop, Long> {
    @Query(value = "SELECT  * FROM  trip_pitstop where trip_code = :tripCode " ,nativeQuery = true)
    List<TripPitstop> findByTripCode(@Param("tripCode") String tripCode);

    @Query(value = "SELECT  * FROM  trip_pitstop where id = :id" ,nativeQuery = true)
    TripPitstop findTripPitstopById(@Param("id") Long id);
}
