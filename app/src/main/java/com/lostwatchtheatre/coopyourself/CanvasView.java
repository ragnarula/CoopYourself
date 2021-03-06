package com.lostwatchtheatre.coopyourself;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.lostwatchtheatre.coopyourself.lib.CircleModel;
import com.lostwatchtheatre.coopyourself.lib.RotationGestureDetector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Random;

//http://stackoverflow.com/questions/17826358/drag-and-move-a-circle-drawn-on-canvas/17830245#17830245
public class CanvasView extends View {

    private final ScaleGestureDetector scaleGestureDetector;
    private final RotationGestureDetector rotationGestureDetector;
    private String tag = "Canvas View";
    private Bitmap background;
    private Bitmap scaled;
    private Bitmap buffer;
    private CircleModel model;

    private Canvas canvas;

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDrawingCacheEnabled(true);

        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        background = Bitmap.createBitmap(1, 1, conf);
        canvas = new Canvas(background);

        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                model.onScale((int) detector.getPreviousSpan(), (int) detector.getCurrentSpan());
//                Log.i(tag, String.format("onScaleEvent %f", detector.getCurrentSpan()));
                return true;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {

            }
        });

        rotationGestureDetector = new RotationGestureDetector(new RotationGestureDetector.RotationListener() {
            @Override
            public void onRotate(float deltaAngle) {
                model.onRotate(deltaAngle);
//                Log.i(tag, String.format("onRotate %f", deltaAngle));
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH){
        super.onSizeChanged(w, h, oldW, oldH);
        float bgHeight = background.getHeight();
        float bgWidth = background.getWidth();
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types


        if(w < h){
            buffer = Bitmap.createBitmap(w, (int) (bgHeight * (w / bgWidth)), conf);
            scaled = Bitmap.createScaledBitmap(background, w, (int) (bgHeight * (w / bgWidth)), true);
        } else {
            buffer = Bitmap.createBitmap((int) (bgWidth * (h / bgHeight)), h, conf);
            scaled = Bitmap.createScaledBitmap(background, (int) (bgWidth * (h / bgHeight)), h, true);
        }

        canvas = new Canvas(buffer);
    }

    @Override
    protected void onDraw(Canvas c){
        super.onDraw(c);
        canvas.drawBitmap(scaled, 0, 0, null);
        model.onDraw(canvas);
        c.drawBitmap(buffer, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        rotationGestureDetector.onTouch(event);
        int actionIndex = event.getActionIndex();
        int pointerId = event.getPointerId(actionIndex);
        int x = (int) event.getX();
        int y = (int) event.getY();
//        Log.i(tag, String.format("onTouchEvent %d %d %d %d", actionIndex, pointerId, x, y));

        // get touch event coordinates and make transparent circle from it
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                model.onTouchDown(pointerId, x, y);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                model.onTouchDown(pointerId, x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                model.onMove(pointerId, x, y);
                break;
            case MotionEvent.ACTION_UP:
                model.onTouchUp(pointerId, x, y);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                model.onTouchUp(pointerId, x, y);
                break;

            default:
                // do nothing
                break;
        }

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setBackground(String bg, Context context) throws IOException {
        Uri image = Uri.parse(bg);
        InputStream is = context.getContentResolver().openInputStream(image);
        background = BitmapFactory.decodeStream(is);
        if(is != null){
            is.close();
        }
        try {
            String path = getRealPathFromURI(context, image.normalizeScheme());

            ExifInterface exif = new ExifInterface(path);
            Integer orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            Matrix matrix = new Matrix();
            Log.d("ROTATION", orientation.toString());
            if(orientation == 3) matrix.postRotate(180);
            else if(orientation == 6) matrix.postRotate(90);
            else if(orientation == 8) matrix.postRotate(270);
            background = Bitmap.createBitmap(background, 0, 0, background.getWidth(), background.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String getRealPathFromURI(Context c, Uri contentURI) {
        String result;
        Cursor cursor = c.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public void setModel(CircleModel model) {
        this.model = model;
    }

    public Bitmap getScreenBuffer() {
        return buffer;
    }
}
