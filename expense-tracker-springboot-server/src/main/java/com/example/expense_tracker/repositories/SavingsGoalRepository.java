package com.example.expense_tracker.repositories;
import com.example.expense_tracker.entities.SavingsGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SavingsGoalRepository extends JpaRepository<SavingsGoal, Integer> {
List<SavingsGoal> findByUserId(Integer userId);
}
