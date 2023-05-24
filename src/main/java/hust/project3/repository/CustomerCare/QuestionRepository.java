package hust.project3.repository.CustomerCare;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hust.project3.entity.CustomerCare.Question;

@Repository
public interface QuestionRepository  extends JpaRepository<Question, Long>{

}
