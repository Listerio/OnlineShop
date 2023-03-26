package com.example.onlineshopdemo.enumerations;

public enum Subcategory {
    SHIRTS(Category.CLOTHING, "Shirts"),
    PANTS(Category.CLOTHING, "Pants"),
    SHOES(Category.CLOTHING, "Shoes"),
    PHONES(Category.ELECTRONICS, "Phones"),
    LAPTOPS(Category.ELECTRONICS, "Laptops"),
    TABLETS(Category.ELECTRONICS, "Tablets"),
    SNACKS(Category.FOOD, "Snacks"),
    BEVERAGES(Category.FOOD, "Beverages"),
    CANNED_FOOD(Category.FOOD, "Canned Food");

    private final Category category;
    private final String name;

    Subcategory(Category category, String name) {
        this.category = category;
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }
}