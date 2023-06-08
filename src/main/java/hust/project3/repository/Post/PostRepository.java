package hust.project3.repository.Post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hust.project3.entity.Post.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
	@Query(value = "SELECT * FROM post where id = :id", nativeQuery = true)
	Post findPostById(@Param("id") Long id);
	
	@Query(value = "SELECT * FROM post where destination_id = :id and status = 'available'", nativeQuery = true)
	List<Post> findPostByDestinationId(@Param("id") Long id);
	
//	@Query(value = "SELECT * FROM post where status = :status", nativeQuery = true)
//	List<Post> findPostByStatus(@Param("status") String status);

}
