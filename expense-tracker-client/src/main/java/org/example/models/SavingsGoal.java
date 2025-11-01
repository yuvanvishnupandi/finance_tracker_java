package org.example.models;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Front-end POJO for a Savings Goal.
 * We keep userId only (back-end receives a nested user object
 * created inside SavingsGoalUtil when sending JSON).
 */
public class SavingsGoal {

    private int id;
    private int userId;                 // owner
    private String name;

    private BigDecimal targetAmount;
    private BigDecimal currentAmount;
    private LocalDate deadline;

    private boolean completed;

    /* ----------  Constructors  ---------- */

    public SavingsGoal() { /* default */ }

    public SavingsGoal(String name,
                       BigDecimal targetAmount,
                       BigDecimal currentAmount,
                       LocalDate deadline) {
        this.name = name;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.deadline = deadline;
        this.completed = false;
    }

    /* ----------  Getters / Setters  ---------- */

    public int getId()               { return id; }
    public void setId(int id)        { this.id = id; }

    public int getUserId()           { return userId; }
    public void setUserId(int userId){ this.userId = userId; }

    public String getName()          { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getTargetAmount()              { return targetAmount; }
    public void setTargetAmount(BigDecimal targetAmount) { this.targetAmount = targetAmount; }

    public BigDecimal getCurrentAmount()             { return currentAmount; }
    public void setCurrentAmount(BigDecimal currentAmount) { this.currentAmount = currentAmount; }

    public LocalDate getDeadline()   { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    public boolean isCompleted()     { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    /* ----------  Convenience  ---------- */

    @Override
    public String toString() {
        return "SavingsGoal{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", target=" + targetAmount +
                ", current=" + currentAmount +
                ", deadline=" + deadline +
                ", completed=" + completed +
                '}';
    }
}
