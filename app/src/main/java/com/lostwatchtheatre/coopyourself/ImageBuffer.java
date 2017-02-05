package com.lostwatchtheatre.coopyourself;

import android.graphics.Bitmap;
import android.view.View;

import java.nio.ByteBuffer;


public class ImageBuffer {

    View view;
    private Bitmap bitmap;
    private ByteBuffer buffer;

    ImageBuffer(View view){
        this.view = view;
    }


    synchronized boolean allocate(int w, int h) {
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        buffer = ByteBuffer.allocateDirect(w * h * 4);
        return true;
    }

    synchronized ByteBuffer getBuffer() {
        return buffer;
    }

    synchronized void onFrameReady() {
        view.invalidate();
    }

    synchronized Bitmap getRenderedBitmap(){
        bitmap.copyPixelsFromBuffer(buffer);
        return bitmap;
    }

}
