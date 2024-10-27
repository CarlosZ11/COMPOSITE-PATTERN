package com.structural_patterns.composite_pattern.domain;

import java.util.ArrayList;
import java.util.List;

public class Box implements Item{

    private String name;
    private List<Item> items = new ArrayList<>();

    public Box(String name) {
        this.name = name;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    @Override
    public double getPrice() {
        return items.stream().mapToDouble(Item::getPrice).sum();
    }

    @Override
    public String getName() {
        return name;
    }

    public List<Item> getItems() {
        return items;
    }

}
