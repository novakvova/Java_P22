package com.example.mytaskmanager.utils;

import android.content.Context;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import okio.Okio;

public class UriRequestBody extends RequestBody {

    private final Context context;
    private final Uri uri;
    private final String contentType;

    public UriRequestBody(Context context, Uri uri, String contentType) {
        this.context = context;
        this.uri = uri;
        this.contentType = contentType;
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse(contentType);
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        try (InputStream is =
                     context.getContentResolver().openInputStream(uri)) {
            Objects.requireNonNull(is);
            sink.writeAll(Okio.source(is));
        }
    }
}
