package hust.project3.repository.Money;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hust.project3.entity.Money.Bill;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill,Long> {
    @Query(value = "SELECT * FROM  bill where account_id = (SELECT id FROM  account where username = :username)",nativeQuery = true)
    List<Bill> findBillsByAccount(@Param("username") String username);

    boolean existsByCode(String code);

}
