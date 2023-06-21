package hust.project3.repository.CustomerCare;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hust.project3.entity.CustomerCare.ThreadMessage;

import java.util.List;

@Repository
public interface ThreadRepository extends JpaRepository<ThreadMessage, Long> {
    @Query(value = "SELECT  * FROM  thread_message where id = :id",nativeQuery = true)
    ThreadMessage findThreadMessageById(@Param("id") Long id);

    @Query(value = "SELECT  * FROM  thread_message where account_id = :id order by id desc ",nativeQuery = true)
    List<ThreadMessage> findThreadMessageByAccountId(@Param("id") Long id);

    @Query(value = "SELECT  * FROM  thread_message  order by status desc ,id desc ",nativeQuery = true)
    List<ThreadMessage> findAllThreadMessage();

}
