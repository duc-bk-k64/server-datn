package hust.project3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hust.project3.entity.cache;
import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface cacheRepository extends JpaRepository<cache, Long> {
	@Query(value = "SELECT * FROM cache where id = :id", nativeQuery = true)
	cache findCacheById(@Param("id") Long id);

}
