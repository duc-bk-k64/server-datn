package hust.project3.repository.Money;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hust.project3.entity.Money.Refund;

@Repository
public interface RefundRepository extends JpaRepository<Refund,Long> {

}
