package com.lostwatchtheatre.coopyourself.lib;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by rag on 05/02/2017.
 */

public class CircleModel {

    private List<DoubleCircleShape> circles = new LinkedList<>();
    private DoubleCircleShape selected;
    private Paint circlePaint;

    private View view;

    CircleModel(View view){
        this.view = view;
        circlePaint = new Paint();
        circlePaint.setColor(Color.BLUE);
        circlePaint.setStrokeWidth(40);
        circlePaint.setStyle(Paint.Style.FILL);
    }

    public void onTouchDown(int pointer, int x, int y) {
        if (pointer != 0) return;
        selected = getTouchedCircle(x, y);
    }

    public void onTouchUp(int pointer, int x, int y){
        if(pointer != 0) return;
    }

    public void onMove(int pointer, int x, int y){
        if(pointer != 0) return;
        if(null != selected){
            selected.move(x, y);
            view.invalidate();
        }
    }

    public void addCircle(){
        DoubleCircleShape c = new DoubleCircleShape((int) view.getX(), (int) view.getY());
        circles.add(c);
        view.invalidate();
    }

    public void onDraw(Canvas canvas){
        for(DoubleCircleShape c: circles){
            canvas.drawCircle(c.getC1().getX(), c.getC1().getY(), c.getC1().getR(), circlePaint);
            canvas.drawCircle(c.getC2().getX(), c.getC2().getY(), c.getC2().getR(), circlePaint);
        }
    }

    private DoubleCircleShape getTouchedCircle(int x, int y) {
        for(DoubleCircleShape c: circles){
            if(c.isTouched(x, y)) return c;
        }
        return null;
    }
}
