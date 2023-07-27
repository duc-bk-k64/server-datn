package hust.project3.repository.Tour;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hust.project3.entity.Tour.Departure;

@Repository
public interface DepartureRepository extends JpaRepository<Departure,Long> {
	Boolean existsByName(String name);

}
