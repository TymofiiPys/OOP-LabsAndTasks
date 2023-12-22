package com.example.mazegame;

public class Wall {
    public final float pointX;
    public final float pointY;
    public final float width;
    public final boolean isHorizontal;
    public Wall(float pointX, float pointY, float width, boolean isHorizontal){
        this.pointX = pointX;
        this.pointY = pointY;
        this.width = width;
        this.isHorizontal = isHorizontal;
    }
}
