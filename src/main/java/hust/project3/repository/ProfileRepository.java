package hust.project3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hust.project3.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
