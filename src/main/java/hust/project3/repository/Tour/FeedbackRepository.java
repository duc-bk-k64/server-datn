package hust.project3.repository.Tour;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hust.project3.entity.Tour.FeedBack;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedBack, Long>{

}
