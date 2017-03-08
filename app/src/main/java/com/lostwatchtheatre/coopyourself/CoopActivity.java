package com.lostwatchtheatre.coopyourself;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.lostwatchtheatre.coopyourself.lib.CircleModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



public class CoopActivity extends AppCompatActivity{
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coop_layout);

        String bg = getIntent().getStringExtra("IMAGE_PATH");


        final CanvasView cv = (CanvasView) findViewById(R.id.coopImageView);
        try {
            cv.setBackground(bg, getApplicationContext());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final CircleModel model = new CircleModel(cv);
        cv.setModel(model);

        ImageButton addButton = (ImageButton) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.addCircle();
            }
        });

        ImageButton colourButton = (ImageButton) findViewById(R.id.colourButton);
        colourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.reColour();
            }
        });

        ImageButton removeButton = (ImageButton) findViewById(R.id.removeButton);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.removeLast();
            }
        });

        ImageButton okButton = (ImageButton) findViewById(R.id.tickButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap b = cv.getScreenBuffer();
                FileOutputStream fs = null;
                String path = "";
                try {
                    File photoFile = DiskImageBuffer.createNew(getApplicationContext());
                    path = "file://" + photoFile.toString();
                    fs = new FileOutputStream(photoFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(fs != null){
                    b.compress(Bitmap.CompressFormat.JPEG, 100, fs);
                }

                Intent intent = new Intent(v.getContext(), ShareActivity.class);
                intent.putExtra("IMAGE_PATH", path);
                startActivity(intent);
            }
        });
    }
}
