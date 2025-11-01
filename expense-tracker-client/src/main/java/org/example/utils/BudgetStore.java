// File: expense-tracker-client/src/main/java/org/example/utils/BudgetStore.java
package org.example.utils;

import org.example.models.Budget;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class BudgetStore {
    private static final Map<Integer, List<Budget>> STORE = new ConcurrentHashMap<>();
    private static final Map<Integer, Long> SEQ = new ConcurrentHashMap<>();

    private BudgetStore(){}

    private static synchronized long nextId(int userId){
        long v = SEQ.getOrDefault(userId, 0L) + 1;
        SEQ.put(userId, v);
        return v;
    }

    public static synchronized List<Budget> getBudgets(int userId){
        return new ArrayList<>(STORE.getOrDefault(userId, Collections.emptyList()));
    }

    public static synchronized Budget getById(int userId, Long id){
        if (id == null) return null;
        for (Budget b : STORE.getOrDefault(userId, Collections.emptyList())) {
            if (id.equals(b.getId())) return b;
        }
        return null;
    }

    public static synchronized void add(int userId, Budget b){
        if (b.getId() == null) b.setId(nextId(userId));
        List<Budget> list = STORE.computeIfAbsent(userId, k -> new ArrayList<>());
        int idx = -1;
        for (int i = 0; i < list.size(); i++) {
            Budget old = list.get(i);
            if (Objects.equals(old.getId(), b.getId()) || old.equals(b)) { idx = i; break; }
        }
        if (idx >= 0) list.set(idx, b); else list.add(b);
    }

    public static synchronized void update(int userId, Budget b){
        add(userId, b);
    }

    public static synchronized void removeById(int userId, Long id){
        if (id == null) return;
        List<Budget> list = STORE.getOrDefault(userId, Collections.emptyList());
        list.removeIf(b -> id.equals(b.getId()));
    }
}