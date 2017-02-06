package com.lostwatchtheatre.coopyourself.lib;

import java.util.Random;

class DoubleCircleShape {

    private String[] colours = {
            "#58C2AD",
            "#FFCD69",
            "#003166",
            "#DE163C",
            "#41B8EA",
            "#231F20",
            "#F38091",
            "#ED1A6E",
            "#FAA42F",
            "#89B837",
            "#FFFFFF",
            "#9F228D"
    };

    private static Random rand = new Random(System.currentTimeMillis());
    private static int MIN_RADIUS = 50;
    private static int DEFAULT_SPACING = 100;

    private CircleArea c1;
    private CircleArea c2;
    private String colour;
    private int spacing;
    private int x;
    private int y;

    DoubleCircleShape (int x, int y){
        this.x = x;
        this.y = y;
        this.spacing = DEFAULT_SPACING;
        int radius = getRadiusForSpacing(spacing);
        this.c1 = new CircleArea(x - spacing, y, radius);
        this.c2 = new CircleArea(x + spacing, y, radius);
        this.colour = colours[rand.nextInt(colours.length)];
    }

     void reColour(){
        this.colour = colours[rand.nextInt(colours.length)];
    }

    private int getRadiusForSpacing(int spacing){
        return (int) (spacing * 0.8);
    }

    boolean isTouched(int x, int y){
        //TODO add code to detech touch between circles
        return c1.isTouched(x, y) || c2.isTouched(x, y);
    }



    CircleArea getC1() {
        return c1;
    }

    CircleArea getC2() {
        return c2;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    String getColour() {
        return colour;
    }

    boolean isOutside(int x, int y) {
        return this.x < 0 || this.x > x || this.y < 0 || this.y > y;
    }

    public void scale(int dSpan) {
        int newSpacing = spacing + dSpan;
        int newR = getRadiusForSpacing(spacing);

        if(newR < MIN_RADIUS && dSpan < 0) return;

        spacing = newSpacing;
        c1.setR(newR);
        c1.setX(x - spacing);
        c2.setR(newR);
        c2.setX(x + spacing);

    }

    void move(int dx, int dy){
        x += dx;
        y += dy;
        c1.move(dx, dy);
        c2.move(dx, dy);
    }

    void rotate(float angle){

    }
}