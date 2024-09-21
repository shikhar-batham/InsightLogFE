package com.example.insightlogfe.model;

import android.graphics.Bitmap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedModel {

    private Bitmap userImageBitmap;
    private Bitmap feedImageBitmap;
    private String userName;
    private String content;
}
