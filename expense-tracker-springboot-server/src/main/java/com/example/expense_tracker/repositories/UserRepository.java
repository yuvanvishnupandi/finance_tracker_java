package com.example.expense_tracker.repositories;

import com.example.expense_tracker.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByEmail(String email);
    void deleteByEmail(String email);
}
