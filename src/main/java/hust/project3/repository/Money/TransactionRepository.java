package hust.project3.repository.Money;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hust.project3.entity.Money.Transaction;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    @Query(value = "SELECT * FROM transaction order by time_created desc ", nativeQuery = true)
    List<Transaction> findAllTransaction();

    Boolean existsByCode(String code);

    @Query(value = "SELECT  *FROM transaction WHERE type = 'in' and time_created > (NOW() - INTERVAL :day DAY )", nativeQuery = true)
    List<Transaction> findInTransactionByTime(@Param("day") Long day);

    @Query(value = "SELECT  *FROM transaction WHERE  time_created > (NOW() - INTERVAL :day DAY )", nativeQuery = true)
    List<Transaction> findAllTransactionByTime(@Param("day") Long day);

    @Query(value = "SELECT  *FROM transaction WHERE type = 'in' and time_created > (NOW() - INTERVAL :end DAY) and time_created < (NOW() - INTERVAL :start DAY )", nativeQuery = true)
    List<Transaction> findInTransactionByTime(@Param("start") Long start, @Param("end") Long end);

    @Query(value = "SELECT  *FROM transaction WHERE type ='out' and time_created > (NOW() - INTERVAL :day DAY )", nativeQuery = true)
    List<Transaction> findOutTransactionByTime(@Param("day") Long day);

    @Query(value = "SELECT  *FROM transaction WHERE type = 'out' and time_created > (NOW() - INTERVAL :end DAY )  and time_created < (NOW() - INTERVAL :start DAY )", nativeQuery = true)
    List<Transaction> findOutTransactionByTime(@Param("start") Long start, @Param("end") Long end);



}
