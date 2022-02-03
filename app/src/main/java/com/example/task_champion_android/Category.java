package com.example.task_champion_android;

public class Category {
    private String name;
    private int taskCounter;
    private int itemCounter;

    public Category(String name, int taskCounter, int itemCounter) {
        this.name = name;
        this.taskCounter = taskCounter;
        this.itemCounter = itemCounter;
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
}
