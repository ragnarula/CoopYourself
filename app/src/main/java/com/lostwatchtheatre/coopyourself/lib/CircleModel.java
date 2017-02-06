package com.lostwatchtheatre.coopyourself.lib;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import java.util.Stack;


public class CircleModel {

    private String tag = "Circle Model";
    private Stack<DoubleCircleShape> circles = new Stack<>();
    private Stack<DoubleCircleShape> selectHistory = new Stack<>();
    private DoubleCircleShape selected;
    private Paint circlePaint;
    private int pointer;
    private int prevX;
    private int prevY;

    private View view;

    public CircleModel(final View view){
        this.view = view;
        circlePaint = new Paint();
        circlePaint.setColor(Color.BLUE);
        circlePaint.setStrokeWidth(40);
        circlePaint.setStyle(Paint.Style.FILL);
        view.post(new Runnable() {
            @Override
            public void run() {
                addCircle();
            }
        });

    }

    public void onTouchDown(int pointer, int x, int y) {
        Log.i(tag, String.format("onTouchDown %d %d %d", pointer, x, y));
        this.pointer = pointer;
        selected = getTouchedCircle(x, y);
        prevX = x;
        prevY = y;
    }

    public void onTouchUp(int pointer, int x, int y){
        Log.i(tag, String.format("onTouchUp %d %d %d", pointer, x, y));
        if(selected != null && selected.isTouched(x, y)){
            selected = null;
        }
        this.pointer = -1;
    }

    public void onMove(int pointer, int x, int y){

        if(this.pointer != pointer) return;

        if(selected != null){
            int dx = x - prevX;
            int dy = y - prevY;
            Log.i(tag, String.format("onMove %d %d %d %d %d", pointer, x, y, dx, dy));
            prevX = x;
            prevY = y;
            selected.move(dx, dy);
            if(selected.isOutside(view.getWidth(), view.getHeight())){
                circles.remove(selected);
                selected = null;
            }
            view.invalidate();
        }
    }

    public void addCircle(){
        DoubleCircleShape c = new DoubleCircleShape((int) view.getWidth() / 2, (int) view.getHeight() / 2);
        circles.push(c);
        view.invalidate();
    }

    public void reColour(){
        for(DoubleCircleShape c: circles){
            c.reColour();
        }
        view.invalidate();
    }

    public void removeLast(){
        if(!circles.empty()){
            circles.pop();
            view.invalidate();
        }
    }

    public void onDraw(Canvas canvas){
        for(DoubleCircleShape c: circles){
            circlePaint.setColor(Color.parseColor(c.getColour()));
            canvas.drawCircle(c.getC1().getX(), c.getC1().getY(), c.getC1().getR(), circlePaint);
            canvas.drawCircle(c.getC2().getX(), c.getC2().getY(), c.getC2().getR(), circlePaint);
        }
    }

    private DoubleCircleShape getTouchedCircle(int x, int y) {
        DoubleCircleShape touched = null;
        for(DoubleCircleShape c: circles){
            if(c.isTouched(x, y)) touched = c;
        }
        if(touched != null){
            circles.remove(touched);
            circles.push(touched);
        }
        return touched;
    }

    public void onScale(int prevSpan, int currentSpan) {
        if(selected != null){
            selected.scale(currentSpan - prevSpan);
            view.invalidate();
        }
    }
}
