package com.lostwatchtheatre.coopyourself.lib;

import java.util.Random;

/**
 * Created by rag on 05/02/2017.
 */

class OffsetCircleArea {


    final private Vec2 origin;
    final private Vec2 offset;
    private int radius;

    OffsetCircleArea(final Vec2 origin, int radius, Vec2 offset){
        this.origin = origin;
        this.offset = offset;
        this.radius = radius;

    }

    boolean isTouched(int xp, int yp){
        Vec2 distance = new Vec2(origin);
        distance.add(offset);
        distance.subtract(xp, yp);
        return distance.length() < radius;
    }


    public Vec2 getPositon(){
        Vec2 pos = new Vec2(origin);
        pos.add(offset);
        return pos;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int r) {
        this.radius = r;
    }

//    public void rotateAround(final Vec2 origin, double angle){
//        position.subtract(origin);
//        position.rotate(angle);
//        position.add(origin);
//    }

}