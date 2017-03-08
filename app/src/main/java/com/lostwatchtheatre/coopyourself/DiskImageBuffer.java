package com.lostwatchtheatre.coopyourself;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class DiskImageBuffer {

    private static String FILENAME = "CYS_TMP_IMAGE.jpg";

    public static File createNew(Context context) throws IOException {
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir, FILENAME);
        if(image.exists()){
            image.delete();
            image.createNewFile();
        }
        return image;
    }

    public static File getFile(Context context) {
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(storageDir, FILENAME);
    }

    public static boolean put(Context context, Bitmap bitmap){
        FileOutputStream fs = null;
        try {
            File photoFile = createNew(context);
            fs = new FileOutputStream(photoFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(fs != null){
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fs);
            return true;
        }
        return false;
    }

    public static Bitmap get(Context context) throws IOException {
        File imageFile = getFile(context);

        FileInputStream is = new FileInputStream(imageFile);

        Bitmap bitmap = BitmapFactory.decodeStream(is);
        bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        if(is != null){
            is.close();
        }
        try {
            Uri uri = Uri.parse(imageFile.toString());
            String path = getRealPathFromURI(context, uri);
            ExifInterface exif = new ExifInterface(path);
            Integer orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            Matrix matrix = new Matrix();
            Log.i("ROTATION", orientation.toString());
            if(orientation == 3) matrix.postRotate(180);
            else if(orientation == 6) matrix.postRotate(90);
            else if(orientation == 8) matrix.postRotate(270);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private static String getRealPathFromURI(Context c, Uri contentURI) {
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
}
