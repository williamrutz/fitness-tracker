package com.william.fitnesstracker;

public class MainItem {

    private int id;
    private int drawableId;
    private int color;
    private int description;

    public MainItem(int id, int drawableId, int color, int description) {
        this.id = id;
        this.drawableId = drawableId;
        this.color = color;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }
}
