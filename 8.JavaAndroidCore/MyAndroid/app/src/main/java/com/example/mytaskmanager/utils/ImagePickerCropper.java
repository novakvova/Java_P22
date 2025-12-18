package com.example.mytaskmanager.utils;

import android.content.Intent;
import android.net.Uri;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.mytaskmanager.R;
import com.example.mytaskmanager.callBackInterfaces.ImageResultCallback;
import com.yalantis.ucrop.UCrop;

import java.io.File;

public class ImagePickerCropper {

    private final AppCompatActivity activity;
    private ImageResultCallback callback;

    private final ActivityResultLauncher<String> pickerLauncher;
    private final ActivityResultLauncher<Intent> cropLauncher;

    public ImagePickerCropper(AppCompatActivity activity) {
        this.activity = activity;

        pickerLauncher = activity.registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        startCrop(uri);
                    }
                }
        );

        cropLauncher = activity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == AppCompatActivity.RESULT_OK
                            && result.getData() != null) {

                        Uri cropped = UCrop.getOutput(result.getData());
                        if (cropped != null && callback != null) {
                            callback.onImageReady(cropped);
                        }
                    }
                }
        );
    }

    public void pick(ImageResultCallback callback) {
        this.callback = callback;
        pickerLauncher.launch("image/*");
    }

    private void startCrop(Uri sourceUri) {
        Uri destUri = Uri.fromFile(
                new File(activity.getCacheDir(),
                        "crop_" + System.currentTimeMillis() + ".jpg")
        );

        UCrop.Options options = new UCrop.Options();
        options.setFreeStyleCropEnabled(true);
        options.setHideBottomControls(false);
        options.setToolbarTitle("Обрізати фото");
        options.setActiveControlsWidgetColor(
                ContextCompat.getColor(activity, R.color.primaryColor)
        );

        Intent intent = UCrop.of(sourceUri, destUri)
                .withOptions(options)
                .getIntent(activity);

        cropLauncher.launch(intent);
    }
}
