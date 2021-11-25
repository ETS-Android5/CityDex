package com.tlbail.ptuts3androidapp.Model.Achievement;

public class Achievement {

    private boolean isUnlocked;
    private String name;
    private String description;
    private int DrawableId;

    public Achievement(boolean isUnlocked, String name, int drawableId, String description) {
        this.isUnlocked = isUnlocked;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }
}
