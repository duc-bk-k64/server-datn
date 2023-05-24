package hust.project3.repository.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hust.project3.entity.Post.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

}
