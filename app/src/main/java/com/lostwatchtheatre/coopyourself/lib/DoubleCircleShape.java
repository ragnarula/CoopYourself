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
    private static int MIN_RADIUS = 80;


    private String colour;


    private Vec2 position;
    private Vec2 circle1;
    private Vec2 circle2;
    private int radius;

    DoubleCircleShape (int x, int y){
        this.position = new Vec2(x, y);
        Vec2 offset = new Vec2((int)(MIN_RADIUS * 1.2), 0);
        this.circle1 = new Vec2(this.position).add(offset);
        this.circle2 = new Vec2(this.position).subtract(offset);
        this.radius = MIN_RADIUS;
        this.colour = colours[rand.nextInt(colours.length)];
    }

     void reColour(){
        this.colour = colours[rand.nextInt(colours.length)];
    }


    boolean isTouched(int x, int y){
        double d1 = new Vec2(circle1).subtract(x, y).length();
        double d2 = new Vec2(circle2).subtract(x, y).length();
        return d1 < radius || d2 < radius;
    }


    void move(int dx, int dy){
        position.add(dx, dy);
        circle1.add(dx, dy);
        circle2.add(dx, dy);
    }

    public void scale(int dSpan) {

        int factor = (int) (dSpan * 1.2);
        Vec2 c1Diff = new Vec2(circle1).subtract(position).extend(factor);
        Vec2 c2Diff = new Vec2(circle2).subtract(position).extend(factor);
        circle1.add(c1Diff);
        circle2.add(c2Diff);

        radius += dSpan;
    }


    void rotate(float angle){
//        if(angle > 0){
            circle1 = new Vec2(position).subtract(circle1).rotate(angle).add(position);
            Vec2 c1offSet = new Vec2(circle1).subtract(position);
            c1offSet.extend((int) (radius * 1.2));
            circle1 = new Vec2(position).add(c1offSet);

            circle2 = new Vec2(position).subtract(circle2).rotate(angle).add(position);
            Vec2 c2offSet = new Vec2(circle2).subtract(position);
            c2offSet.extend((int) (radius * 1.2));
            circle2 = new Vec2(position).add(c2offSet);
//        }


    }

    boolean isOutside(int x, int y) {
        return false;
    }

    String getColour() {
        return colour;
    }

    public Vec2 getC1() {
        return circle1;
    }

    public Vec2 getC2() {
        return circle2;
    }

    public int getRadius(){
        return radius;
    }
}