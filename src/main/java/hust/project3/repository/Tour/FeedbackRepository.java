package hust.project3.repository.Tour;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hust.project3.entity.Tour.FeedBack;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedBack, Long>{
    @Query(value = "SELECT * FROM feed_back where  tour_id = :id order by id desc ",nativeQuery = true)
    List<FeedBack> findByTourId(@Param("id") Long id);

}
