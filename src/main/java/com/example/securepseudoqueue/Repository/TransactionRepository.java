package com.example.securepseudoqueue.Repository;

import com.example.securepseudoqueue.DAO.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
