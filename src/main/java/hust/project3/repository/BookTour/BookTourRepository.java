package hust.project3.repository.BookTour;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hust.project3.entity.BookTour.BookTour;

@Repository
public interface BookTourRepository extends JpaRepository<BookTour,Long> {
	@Query(value = "SELECT * FROM book_tour  WHERE id = :id", nativeQuery = true)
	BookTour findBookTourById(@Param("id") Long id);

	@Query(value = "SELECT  *FROM book_tour WHERE  time_create > (NOW() - INTERVAL 1 DAY )", nativeQuery = true)
	List<BookTour> findAllInOneDay();

	@Query(value = "SELECT * FROM book_tour  WHERE status = :status", nativeQuery = true)
	List<BookTour> findBookTourByStatus(@Param("status") String status);

	@Query(value = "SELECT * FROM book_tour  order by  time_create desc ", nativeQuery = true)
	List<BookTour> findAllBooktour();
	
	List<BookTour> findByTourTripCode(String tourTripCode);

	Boolean existsByCode(String code);
	
//	@Query(value= "SELECT * FROM book_tour where account_id = :account_id")
//	List<BookTour> findByAccountID(@Param("account_id") Long account_id);

}
