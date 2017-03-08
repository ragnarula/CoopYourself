package com.lostwatchtheatre.coopyourself;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by rag on 11/02/2017.
 */

public class ShareActivity extends AppCompatActivity {

    private File tmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_layout);

        Bitmap bmp = null;
        try {
            bmp = DiskImageBuffer.get(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(bmp == null) return;

        Bitmap banner = BitmapFactory.decodeResource(getResources(),R.drawable.bottom_banner_mobile);

        double newW = bmp.getWidth();
        double oldW = banner.getWidth();
        double ratio = newW / oldW;
        double newH = ratio * banner.getHeight();

        banner = Bitmap.createScaledBitmap(banner, (int) newW, (int) newH, true);
        Canvas canvas = new Canvas(bmp);

        float imageH = bmp.getHeight();

        canvas.drawBitmap(banner, 0, imageH - banner.getHeight(), null);

        ImageView imageView = (ImageView) findViewById(R.id.image);

        imageView.setImageBitmap(bmp);

        ImageButton tickButton = (ImageButton) findViewById(R.id.tickButton);
        File dir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        FileOutputStream fs = null;
        View.OnClickListener listener = null;
        try {
            tmp = File.createTempFile("coop",".jpg",dir);
            fs = new FileOutputStream(tmp);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fs);
            final String path = tmp.toString();
            listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("image/jpeg");
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + path));
                    startActivity(Intent.createChooser(intent, "Share with"));
                }
            };

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(listener != null){
            tickButton.setOnClickListener(listener);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(tmp != null){
            tmp.deleteOnExit();
        }
    }
}
