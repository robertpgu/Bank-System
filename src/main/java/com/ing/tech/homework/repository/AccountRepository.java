package com.ing.tech.homework.repository;

import com.ing.tech.homework.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
}
