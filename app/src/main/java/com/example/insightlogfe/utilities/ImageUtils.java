package com.example.insightlogfe.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class ImageUtils {


    public static Bitmap getImageFromResponse(Response<ResponseBody> response) {

        byte[] byteImage;
        try {
            byteImage = response.body().bytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
        return bitmap;
    }

    public static String getStringImageFromResponse(Response<ResponseBody> response) {

        byte[] byteImage = null;
        try {
            if (response.body() != null)
                byteImage = response.body().bytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Bitmap bitmap = null;
        if (byteImage != null)
            bitmap = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);

        return bitMapToString(bitmap);
    }

    public static String bitMapToString(Bitmap bitmap) {
        String res = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            res = Base64.encodeToString(b, Base64.DEFAULT);
        } catch (Exception ignored) {

        }
        return res;
    }

    public static Bitmap stringToBitMap(String encodedString) {

        Bitmap bitmap;
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] getImageFromDrawable(Drawable drawable) {

        Bitmap bitmap = drawableToBitmap(drawable);
        return bitmapToByteArray(bitmap);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

}

