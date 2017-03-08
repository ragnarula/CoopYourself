package com.lostwatchtheatre.coopyourself.lib;


public class Vec2 {
    private int x;
    private int y;

    public Vec2(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Vec2(final Vec2 v){
        this.x = v.getX();
        this.y = v.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Vec2 add(final Vec2 t){
        this.x += t.getX();
        this.y += t.getY();
        return this;
    }

    public Vec2 add(int x, int y){
        this.x += x;
        this.y += y;
        return this;
    }

    public Vec2 subtract(int x, int y){
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vec2 subtract(final Vec2 t){
        this.x -= t.getX();
        this.y -= t.getY();
        return this;
    }

    public Vec2 rotate(double angle){
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        int newX = (int) ((x * cos) - (y * sin));
        this.y = (int) ((x * sin) + (y * cos));
        this.x = newX;
        return this;
    }

    public Vec2 scale(double factor){
        this.x = (int) (this.x * factor);
        this.y = (int) (this.y * factor);
        return this;
    }

    public double length(){
        return Math.sqrt((x * x) + (y * y));
    }

    @Override
    public String toString(){
        return String.format("x: %d, y: %d", x, y);
    }

    public Vec2 extend(int amount) {
        double l = length();
        this.x = (int) ((this.x / l) * amount);
        this.y = (int) ((this.y / l) * amount);
        return this;

    }
}
