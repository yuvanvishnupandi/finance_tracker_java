package com.example.expense_tracker.repositories;

import com.example.expense_tracker.entities.Transaction;
// IMPORTANT: Use the correct import for @Modifying and @Query
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.websocket.server.PathParam; // Keep this import for your existing method
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    
    // ----------------------------------------------------------------------------------
    // THE FIX: Method to unlink transactions from a category before the category is deleted
    // ----------------------------------------------------------------------------------
    @Modifying // Indicates this query will change the database state (UPDATE, DELETE, INSERT)
    @Query("UPDATE Transaction t SET t.transactionCategory = NULL WHERE t.transactionCategory.id = :categoryId")
    int unlinkCategory(@Param("categoryId") int categoryId);
    // Note: The @Param annotation is needed here for Spring Data JPA to bind the parameter.
    // ----------------------------------------------------------------------------------

    List<Transaction> findAllByUserIdOrderByTransactionDateDesc(int userId, Pageable pageable);
    
    List<Transaction> findAllByUserIdAndTransactionDateBetweenOrderByTransactionDateDesc(
            int userId,
            LocalDate startDate,
            LocalDate endDate
    );

    @Query("SELECT DISTINCT YEAR(t.transactionDate) FROM Transaction t WHERE t.user.id = :userId")
    List<Integer> findDistinctYears(@PathParam("userId") int userId);
}