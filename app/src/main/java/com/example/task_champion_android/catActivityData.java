package com.example.task_champion_android;

public class catActivityData {
    private String cat_name;
    private Integer  catImage;

    public catActivityData(String cat_name) {
        this.cat_name = cat_name;
    }

    public catActivityData(String cat_name, Integer catImage) {
        this.cat_name = cat_name;
        this.catImage = catImage;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public Integer getCatImage() {
        return catImage;
    }

    public void setCatImage(Integer catImage) {
        this.catImage = catImage;
    }
}
