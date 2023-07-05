package com.example.onlineshopdemo.enumerations;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum   Category {
    CLOTHING("Clothing"),
    ELECTRONICS("Electronics"),
    FOOD("Food");


    private final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Subcategory> getSubcategories() {
        return Arrays.asList(Subcategory.values()).stream()
                .filter(subcategory -> subcategory.getCategory().equals(this))
                .collect(Collectors.toList());
    }
}