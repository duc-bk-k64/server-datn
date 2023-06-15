package hust.project3.repository.Money;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hust.project3.entity.Money.Refund;

import java.util.List;

@Repository
public interface RefundRepository extends JpaRepository<Refund,Long> {
    @Query(value = "SELECT * FROM  refund where code = :code", nativeQuery = true)
    Refund findRefundByCode(@Param("code") String code);

    @Query(value = "SELECT * FROM  refund order by time_created desc", nativeQuery = true)
    List<Refund> findAllRefund();

    Boolean existsByCode(String code);

}
