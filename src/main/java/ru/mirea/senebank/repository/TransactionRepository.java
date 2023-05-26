package ru.mirea.senebank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mirea.senebank.entity.Transaction;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query(value = "select t from Transaction t where t.user.id=:id")
    List<Transaction> findAllByUserId(Integer id);

    @Query(value = "select t from Transaction t where t.accountTo.id=:id or t.accountFrom.id=:id")
    List<Transaction> findAllByAccountId(Integer id);
}
