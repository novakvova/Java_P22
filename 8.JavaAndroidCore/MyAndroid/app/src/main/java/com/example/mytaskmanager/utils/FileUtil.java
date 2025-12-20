package com.example.mytaskmanager.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FileUtil {

    public static String getImagePath(Context context, Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };

        Cursor cursor = context.getContentResolver()
                .query(uri, projection, null, null, null);

        if (cursor != null) {
            int columnIndex =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String imagePath = cursor.getString(columnIndex);
            cursor.close();
            return imagePath;
        }

        return null;
    }
    public static MultipartBody.Part createImagePart(
            Context context,
            Uri uri,
            String partName,
            String fileName
    ) {
        try (InputStream is = context.getContentResolver().openInputStream(uri);
             ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

            byte[] data = new byte[8192];
            int n;
            while ((n = is.read(data)) != -1) {
                buffer.write(data, 0, n);
            }

            RequestBody body = RequestBody.create(
                    MediaType.parse("image/*"),
                    buffer.toByteArray()
            );

            return MultipartBody.Part.createFormData(
                    partName,
                    fileName,
                    body
            );

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}