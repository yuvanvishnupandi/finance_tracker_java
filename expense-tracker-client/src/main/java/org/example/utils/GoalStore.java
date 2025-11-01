package org.example.utils;

import org.example.models.SavingsGoal;
import java.util.*;

public class GoalStore {
private static final Map<Integer, List<SavingsGoal>> STORE = new HashMap<>();


public static List<SavingsGoal> getGoals(int userId) {
    return STORE.computeIfAbsent(userId, k -> new ArrayList<>());
}

public static int nextId(int userId) {
    return getGoals(userId).stream().mapToInt(SavingsGoal::getId).max().orElse(0) + 1;
}

public static void add(int userId, SavingsGoal goal) {
    getGoals(userId).add(goal);
}

public static void update(int userId, SavingsGoal goal) {
    List<SavingsGoal> list = getGoals(userId);
    for (int i = 0; i < list.size(); i++) {
        if (list.get(i).getId() == goal.getId()) {
            list.set(i, goal);
            return;
        }
    }
    list.add(goal);
}

public static void delete(int userId, int goalId) {
    getGoals(userId).removeIf(g -> g.getId() == goalId);
}
}