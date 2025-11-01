package com.example.expense_tracker.controllers;
import com.example.expense_tracker.entities.SavingsGoal;
import com.example.expense_tracker.services.SavingsGoalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/savings-goals")
@CrossOrigin(origins = "*")
public class SavingsGoalController {



private final SavingsGoalService service;

public SavingsGoalController(SavingsGoalService service) {
    this.service = service;
}

@GetMapping("/user/{userId}")
public List<SavingsGoal> byUser(@PathVariable Integer userId) {
    return service.listByUser(userId);
}

@PostMapping
public SavingsGoal create(@RequestBody SavingsGoal g) {
    Integer userId = (g.getUser() != null ? g.getUser().getId() : null);
    if (userId == null) throw new IllegalArgumentException("user.id is required in payload");
    return service.create(g, userId);
}

@PutMapping
public SavingsGoal update(@RequestBody SavingsGoal g) {
    Integer userId = (g.getUser() != null ? g.getUser().getId() : null);
    return service.upsert(g, userId);
}

@DeleteMapping("/{id}")
public void delete(@PathVariable Integer id) {
    service.delete(id);
}
}
