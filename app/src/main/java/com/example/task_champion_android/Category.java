package com.example.task_champion_android;

import java.util.ArrayList;

public class Category {
    private String name;
    private int taskCounter;
    private int itemCounter;
    private ArrayList<Item> items;

    public Category(String name, int taskCounter, int itemCounter, ArrayList<Item> items) {
        this.name = name;
        this.taskCounter = taskCounter;
        this.itemCounter = itemCounter;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTaskCounter() {
        return taskCounter;
    }

    public void setTaskCounter(int taskCounter) {
        this.taskCounter = taskCounter;
    }

    public int getItemCounter() {
        return itemCounter;
    }

    public void setItemCounter(int itemCounter) {
        this.itemCounter = itemCounter;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
