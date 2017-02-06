package com.lostwatchtheatre.coopyourself.lib;

/**
 * Created by rag on 06/02/2017.
 */

public class Vec2 {
    int x;
    int y;

    public Vec2(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void add(final Vec2 t){
        this.x += t.getX();
        this.y += t.getY();
    }

    public void subtract(final Vec2 t){
        this.x -= t.getX();
        this.y -= t.getY();
    }

    public void rotate(float angle){
        this.x = (int)(Math.cos(x) - Math.sin(y));
        this.y = (int)(Math.sin(x) - Math.cos(y));
    }
}
