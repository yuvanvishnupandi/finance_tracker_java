package com.example.expense_tracker.services;
import com.example.expense_tracker.entities.SavingsGoal;
import com.example.expense_tracker.entities.User;
import com.example.expense_tracker.repositories.SavingsGoalRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SavingsGoalService {


private final SavingsGoalRepository goals;

@PersistenceContext
private EntityManager em;

public SavingsGoalService(SavingsGoalRepository goals) {
    this.goals = goals;
}

public List<SavingsGoal> listByUser(Integer userId) {
    return goals.findByUserId(userId);
}

public SavingsGoal create(SavingsGoal g, Integer userId) {
    // attach user reference without needing a UserRepository
    User u = em.getReference(User.class, userId);
    g.setUser(u);
    return goals.save(g);
}

public SavingsGoal upsert(SavingsGoal g, Integer userId) {
    if (g.getUser() == null && userId != null) {
        User u = em.getReference(User.class, userId);
        g.setUser(u);
    }
    return goals.save(g);
}

public void delete(Integer id) {
    goals.deleteById(id);
}
}


