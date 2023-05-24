package hust.project3.repository.Tour;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hust.project3.entity.Tour.Tour;

@Repository
public interface TourRepository extends JpaRepository<Tour,Long>{

}
