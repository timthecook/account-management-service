package com.tim.accountservice.repository;

import com.tim.accountservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findById(Long id);

    @Query("SELECT a FROM Account a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :keyword, '%'))" + "OR LOWER(a.email) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Account> searchByKeyword(@Param("keyword") String keyword);
}
