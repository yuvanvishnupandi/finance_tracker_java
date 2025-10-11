package org.example.models;

public class TransactionCategory {
    private int id;
    private String categoryName;
    private String categoryColor;

    public TransactionCategory(int id, String categoryName, String categoryColor) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
    }

    public int getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryColor() {
        return categoryColor;
    }

    public void setCategoryColor(String categoryColor) {
        this.categoryColor = categoryColor;
    }
}
