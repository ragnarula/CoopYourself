package com.lostwatchtheatre.coopyourself;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by rag on 23/01/2017.
 */

public class CoopActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coop_layout);
        String bg = getIntent().getStringExtra("IMAGE_PATH");
        CanvasView cv = (CanvasView) findViewById(R.id.coopImageView);
        cv.setBackground(bg);
    }
}
