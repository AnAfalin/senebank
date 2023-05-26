package ru.mirea.senebank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mirea.senebank.entity.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query(value = "select a from Account a left join a.user where a.id=:id")
    Optional<Account> findAccountWithUserById(Integer id);
}
