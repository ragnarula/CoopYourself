package com.lostwatchtheatre.coopyourself;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BeginActivity extends AppCompatActivity {
    String mCurrentPhotoPath;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_PICK = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.begin_layout);

        final ImageButton takePhotoButton = (ImageButton) findViewById(R.id.takePhotoButton);

        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        final ImageButton pickButton = (ImageButton) findViewById(R.id.cameraRollButton);

        pickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , REQUEST_IMAGE_PICK);//one can be replaced with any action code
            }
        });

    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.lostwatchtheatre.coopyourself.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }


    private File createImageFile() throws IOException {

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = DiskImageBuffer.createNew(getApplicationContext());
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.toString();
        return image;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE) {
            Log.i("IMAGE", mCurrentPhotoPath);
            Intent coopActivityIntent = new Intent(getApplicationContext(), CoopActivity.class);
            coopActivityIntent.putExtra("IMAGE_PATH", "file://" + mCurrentPhotoPath);
            startActivityForResult(coopActivityIntent, 0);
        } else if(resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_PICK){
            Uri selectedImage = data.getData();
            Log.i("IMAGE", selectedImage.toString());
            Intent coopActivityIntent = new Intent(getApplicationContext(), CoopActivity.class);
            coopActivityIntent.putExtra("IMAGE_PATH", selectedImage.toString());
            startActivityForResult(coopActivityIntent, 0);
        }
    }
}
