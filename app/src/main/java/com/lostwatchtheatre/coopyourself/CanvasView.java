package com.lostwatchtheatre.coopyourself;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.ExifInterface;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Random;

//http://stackoverflow.com/questions/17826358/drag-and-move-a-circle-drawn-on-canvas/17830245#17830245
public class CanvasView extends View {


    private static final String TAG = "CirclesDrawingView";

    private static class CircleArea {
        private int radius;
        private int centerX;
        private int centerY;
        private int spacing;

        CircleArea(int centerX, int centerY, int radius) {
            this.radius = radius;
            this.centerX = centerX;
            this.centerY = centerY;
            this.spacing = (int) Math.floor(radius * 1.5);
        }

        @Override
        public String toString() {
            return "Circle[" + centerX + ", " + centerY + ", " + radius + "]";
        }

        int getX1(){
            return centerX - spacing;
        }

        int getY1(){
            return centerY;
        }

        int getX2(){
            return centerX + spacing;
        }

        int getY2(){
            return centerY;
        }

        int getSpacing(){
            return spacing;
        }

        public void move(int x, int y) {
            centerX += x;
            centerY += y;
        }


    }

    private static class TouchEvent {
        int x;
        int y;

        TouchEvent(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    private Bitmap background;
    private Bitmap scaled;
    private Paint mCirclePaint;

    private final static int RADIUS_LIMIT = 100;
    private static final int CIRCLES_LIMIT = 3;


    private HashSet<CircleArea> mCircles = new HashSet<>(CIRCLES_LIMIT);
    private SparseArray<CircleArea> mCirclePointer = new SparseArray<>(CIRCLES_LIMIT);
    private SparseArray<TouchEvent> mTouchEvent = new SparseArray<>();

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCirclePaint = new Paint();

        mCirclePaint.setColor(Color.BLUE);
        mCirclePaint.setStrokeWidth(40);
        mCirclePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH){
        super.onSizeChanged(w, h, oldW, oldH);
        float bgHeight = background.getHeight();
        float bgWidth = background.getWidth();

        if(w < h){
            scaled = Bitmap.createScaledBitmap(background, w, (int) (bgHeight * (w / bgWidth)), true);
        } else {
            scaled = Bitmap.createScaledBitmap(background, (int) (bgWidth * (h / bgHeight)), w, true);
        }
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawBitmap(scaled, 0, 0, null);
        for (CircleArea circle : mCircles) {
            canvas.drawCircle(circle.getX1(), circle.getY1(), circle.radius, mCirclePaint);
            canvas.drawCircle(circle.getX2(), circle.getY2(), circle.radius, mCirclePaint);
        }
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        boolean handled = false;

        CircleArea touchedCircle;
        int xTouch;
        int yTouch;
        int pointerId;
        int actionIndex = event.getActionIndex();

        // get touch event coordinates and make transparent circle from it
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
//                // it's the first pointer, so clear all existing pointers data
//                clearCirclePointer();
//
//                xTouch = (int) event.getX(0);
//                yTouch = (int) event.getY(0);
//
//                // check if we've touched inside some circle
//                touchedCircle = obtainTouchedCircle(xTouch, yTouch);
//                mCirclePointer.put(event.getPointerId(0), touchedCircle);
//                mTouchEvent.put(event.getPointerId(0), new TouchEvent(xTouch, yTouch));
//
//                invalidate();
//                handled = true;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
//                Log.w(TAG, "Pointer down");
//                // It secondary pointers, so obtain their ids and check circles
//                pointerId = event.getPointerId(actionIndex);
//
//                xTouch = (int) event.getX(actionIndex);
//                yTouch = (int) event.getY(actionIndex);
//
//                // check if we've touched inside some circle
//                touchedCircle = obtainTouchedCircle(xTouch, yTouch);
//
//                mCirclePointer.put(pointerId, touchedCircle);
//                mTouchEvent.put(pointerId, new TouchEvent(xTouch, yTouch));
//                invalidate();
//                handled = true;
                pointerId = event.getPointerId(event.getActionIndex());

                break;

            case MotionEvent.ACTION_MOVE:
//                final int pointerCount = event.getPointerCount();
//
//                Log.w(TAG, "Move");
//
//                for (actionIndex = 0; actionIndex < pointerCount; actionIndex++) {
//                    // Some pointer has moved, search it by pointer id
//                    pointerId = event.getPointerId(actionIndex);
//                    TouchEvent e = mTouchEvent.get(pointerId);
//
//                    xTouch = (int) event.getX(actionIndex);
//                    yTouch = (int) event.getY(actionIndex);
//
//                    touchedCircle = mCirclePointer.get(pointerId);
//
//                    if (null != touchedCircle) {
//                        touchedCircle.move(xTouch - e.x, yTouch - e.y);
//                    }
//
//                    mTouchEvent.put(pointerId, new TouchEvent(xTouch, yTouch));
//                }
//                invalidate();
//                handled = true;
                break;

            case MotionEvent.ACTION_UP:
//                clearCirclePointer();
//                invalidate();
//                handled = true;
                break;

            case MotionEvent.ACTION_POINTER_UP:
//                // not general pointer was up
//                pointerId = event.getPointerId(actionIndex);
//
//                mCirclePointer.remove(pointerId);
//                invalidate();
//                handled = true;
                break;

            case MotionEvent.ACTION_CANCEL:
                handled = true;
                break;

            default:
                // do nothing
                break;
        }

        return super.onTouchEvent(event) || handled;
    }

    private CircleArea obtainTouchedCircle(final int xTouch, final int yTouch) {
        CircleArea touchedCircle = getTouchedCircle(xTouch, yTouch);

        if (null == touchedCircle) {
            touchedCircle = new CircleArea(xTouch, yTouch, 50);

            if (mCircles.size() == CIRCLES_LIMIT) {
                Log.w(TAG, "Clear all circles, size is " + mCircles.size());
                // remove first circle
                mCircles.clear();
            }

            Log.w(TAG, "Added circle " + touchedCircle);
            mCircles.add(touchedCircle);
        }

        return touchedCircle;
    }

    private CircleArea getTouchedCircle(final int xTouch, final int yTouch) {
        CircleArea touched = null;

        for (CircleArea circle : mCircles) {

            if ((circle.getX1() - xTouch) * (circle.getX1() - xTouch) + (circle.getY1() - yTouch) * (circle.getY1() - yTouch) <= circle.radius * circle.radius) {
                touched = circle;
                break;
            }
            if ((circle.getX2() - xTouch) * (circle.getX2() - xTouch) + (circle.getY2() - yTouch) * (circle.getY2() - yTouch) <= circle.radius * circle.radius) {
                touched = circle;
                break;
            }
        }

        return touched;
    }

    private void clearCirclePointer() {
        Log.w(TAG, "clearCirclePointer");

        mCirclePointer.clear();
    }

    public void setBackground(String bg) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        background = BitmapFactory.decodeFile(bg, options);
        try {
            ExifInterface exif = new ExifInterface(bg);
            Integer orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            Matrix matrix = new Matrix();
            Log.i("ROTATION", orientation.toString());
            if(orientation == 3) matrix.postRotate(180);
            else if(orientation == 6) matrix.postRotate(90);
            else if(orientation == 8) matrix.postRotate(270);
            background = Bitmap.createBitmap(background, 0, 0, background.getWidth(), background.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
