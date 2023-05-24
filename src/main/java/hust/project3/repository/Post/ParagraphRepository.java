package hust.project3.repository.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hust.project3.entity.Post.Paragraph;

@Repository
public interface ParagraphRepository extends JpaRepository<Paragraph, Long> {

}
