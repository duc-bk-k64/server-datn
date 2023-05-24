package hust.project3.repository.BookTour;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hust.project3.entity.BookTour.BookTour;

@Repository
public interface BookTourRepository extends JpaRepository<BookTour,Long> {

}
