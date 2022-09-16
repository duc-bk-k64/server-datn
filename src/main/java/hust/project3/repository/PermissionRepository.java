package hust.project3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hust.project3.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
	Permission findByName(String name);

}
