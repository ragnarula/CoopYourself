package com.lostwatchtheatre.coopyourself.lib;

/**
 * Created by rag on 05/02/2017.
 */

class CircleArea {
    private int x;
    private int y;
    private int r;

    CircleArea(int x, int y, int r){
        this.x = x;
        this.y = y;
        this.r = r;
    }

    boolean isTouched(int xp, int yp){
        double d = (xp - x) * (xp - x) + (yp -y) * (yp -y);
        return d <= r * r;
    }

    void move(int dx, int dy){
        x += dx;
        y += dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getR() {
        return r;
    }
}