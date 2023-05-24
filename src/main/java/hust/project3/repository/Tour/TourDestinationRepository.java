package hust.project3.repository.Tour;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hust.project3.entity.Tour.Destination;

@Repository
public interface TourDestinationRepository extends JpaRepository<Destination, Long> {

}
