package com.lostwatchtheatre.coopyourself;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.lostwatchtheatre.coopyourself.lib.CircleModel;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by rag on 23/01/2017.
 */

public class CoopActivity extends AppCompatActivity{
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coop_layout);

        String bg = getIntent().getStringExtra("IMAGE_PATH");


        CanvasView cv = (CanvasView) findViewById(R.id.coopImageView);
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

        ImageButton removeButton = (ImageButton) findViewById(R.id.moveButton);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.removeLast();
            }
        });
    }
}
