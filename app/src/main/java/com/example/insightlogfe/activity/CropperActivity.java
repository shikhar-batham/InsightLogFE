package com.example.insightlogfe.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.insightlogfe.Constants.IntentConstants;
import com.example.insightlogfe.R;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.UUID;

public class CropperActivity extends AppCompatActivity {

    private String resultantImage;
    private Uri fileUri;
    private static final int IMAGE_CROPPER_RESULT_CODE = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cropper);

        readIntent();

        String destinationUri = UUID.randomUUID().toString() + ".jpg";

        UCrop.Options options = new UCrop.Options();

        UCrop.of(fileUri, Uri.fromFile(new File(getCacheDir(), destinationUri)))
                .withOptions(options)
                .withAspectRatio(0, 0)
                .useSourceImageAspectRatio()
                .withMaxResultSize(2000, 2000)
                .start(CropperActivity.this);
    }

    private void readIntent() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            resultantImage = intent.getStringExtra(IntentConstants.IMAGE_DATA);
            fileUri = Uri.parse(resultantImage);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP && data != null) {

            final Uri resultImageUri = UCrop.getOutput(data);
            Intent resultIntent = new Intent();
            resultIntent.putExtra(IntentConstants.RESULT_IMAGE_DATA, resultImageUri + "");
            setResult(IMAGE_CROPPER_RESULT_CODE, resultIntent);
            finish();
        } else if (resultCode == UCrop.RESULT_ERROR && data != null) {

            Error error = (Error) UCrop.getError(data);
            assert error != null;
//            logMessage(error.getMessage());
        }
    }
}