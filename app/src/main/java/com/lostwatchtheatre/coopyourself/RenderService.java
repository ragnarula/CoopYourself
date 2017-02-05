package com.lostwatchtheatre.coopyourself;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class RenderService {
    private ImageBuffer imageBuffer;
    private Bitmap background;
    private Bitmap scaled;

    RenderService(ImageBuffer imageBuffer){
        this.imageBuffer = imageBuffer;
    }


    public void setBackground(String path){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        background = BitmapFactory.decodeFile(path, options);
    }

    public void setDisplaySize(int w, int h){
        imageBuffer.allocate(w, h);
        scaled = Bitmap.createScaledBitmap(background, w, h, true);
        scaled.copyPixelsToBuffer(imageBuffer.getBuffer());
    }
}
