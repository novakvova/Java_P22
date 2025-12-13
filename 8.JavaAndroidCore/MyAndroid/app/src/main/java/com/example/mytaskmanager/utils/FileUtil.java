package com.example.mytaskmanager.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

public class FileUtil {

    public static String getFileName(Context context, Uri uri) {
        String name = null;

        if ("content".equals(uri.getScheme())) {
            try (Cursor cursor = context.getContentResolver()
                    .query(uri, null, null, null, null)) {

                if (cursor != null && cursor.moveToFirst()) {
                    int index =
                            cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (index >= 0) {
                        name = cursor.getString(index);
                    }
                }
            }
        }

        if (name == null) {
            name = "image_" + System.currentTimeMillis() + ".jpg";
        }

        return name;
    }
}