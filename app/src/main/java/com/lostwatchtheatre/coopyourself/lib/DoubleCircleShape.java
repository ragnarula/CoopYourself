package com.lostwatchtheatre.coopyourself.lib;

/**
 * Created by rag on 05/02/2017.
 */

class DoubleCircleShape {
    private static int DEFAULT_RADIUS = 50;
    private static int DEFAULT_SPACING = 25;

    private CircleArea c1;
    private CircleArea c2;
    private int spacing;

    DoubleCircleShape (int x, int y){
        this.spacing = DEFAULT_SPACING;
        this.c1 = new CircleArea(x - spacing, y, DEFAULT_RADIUS);
        this.c2 = new CircleArea(x + spacing, y, DEFAULT_RADIUS);
    }

    public boolean isTouched(int x, int y){
        //TODO add code to detech touch between circles
        return c1.isTouched(x, y) || c2.isTouched(x, y);
    }

    public void move(int dx, int dy){
        c1.move(dx, dy);
        c2.move(dx, dy);
    }

    public CircleArea getC1() {
        return c1;
    }

    public CircleArea getC2() {
        return c2;
    }
}