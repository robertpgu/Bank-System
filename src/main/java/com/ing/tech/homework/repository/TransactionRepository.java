package com.ing.tech.homework.repository;

import com.ing.tech.homework.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
