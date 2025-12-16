package com.example.mytaskmanager.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

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
}