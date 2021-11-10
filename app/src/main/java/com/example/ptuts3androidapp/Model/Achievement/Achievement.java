package com.example.ptuts3androidapp.Model.Achievement;

public class Achievement {

    private boolean isUnlocked;
    private String name;
    private int DrawableId;

    public Achievement(boolean isUnlocked, String name, int drawableId) {
        this.isUnlocked = isUnlocked;
        this.name = name;
        DrawableId = drawableId;
    }


    public int getDrawableId() {
        return DrawableId;
    }


    public String getName() {
        return name;
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }



}
