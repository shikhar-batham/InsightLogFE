package com.example.insightlogfe.model;

import android.graphics.Bitmap;

public class UserPostModel {
    private Bitmap imageBitmap;

    public UserPostModel(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }
}
