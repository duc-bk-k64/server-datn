package hust.project3.repository.Post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hust.project3.entity.Post.Paragraph;

@Repository
public interface ParagraphRepository extends JpaRepository<Paragraph, Long> {
	@Query(value = "SELECT * FROM paragraph where post_id = :post_id order by number_order",nativeQuery = true)
	List<Paragraph> findByPostId(@Param("post_id") Long post_id);
}
