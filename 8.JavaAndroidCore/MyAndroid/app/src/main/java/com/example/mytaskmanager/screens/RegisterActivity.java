package com.example.mytaskmanager.screens;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


import com.example.mytaskmanager.BaseActivity;
import com.example.mytaskmanager.R;
import com.example.mytaskmanager.network.RetrofitClient;
import com.example.mytaskmanager.utils.CommonUtils;
import com.example.mytaskmanager.utils.ImagePickerCropper;
import com.example.mytaskmanager.utils.MyLogger;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity {

    private EditText firstNameInput, lastNameInput, emailInput, passwordInput;
    private ImageView imagePreview;
    private Uri selectedImageUri;

    private ImagePickerCropper imageCropper;

    private MultipartBody.Part createImagePart(Uri uri) {
        try {
            InputStream is = getContentResolver().openInputStream(uri);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[8192];
            int n;
            while ((n = is.read(data)) != -1) {
                buffer.write(data, 0, n);
            }

            RequestBody body = RequestBody.create(
                    MediaType.parse("image/jpeg"),
                    buffer.toByteArray()
            );

            return MultipartBody.Part.createFormData(
                    "ImageFile",
                    "avatar.jpg",
                    body
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstNameInput = findViewById(R.id.firstName);
        lastNameInput  = findViewById(R.id.lastName);
        emailInput     = findViewById(R.id.email);
        passwordInput  = findViewById(R.id.password);
        imagePreview   = findViewById(R.id.imagePreview);

        imageCropper = new ImagePickerCropper(this);

        findViewById(R.id.selectImage).setOnClickListener(v ->
                imageCropper.pick(uri -> {
                    selectedImageUri = uri;
                    imagePreview.setImageURI(uri);
                })
        );
    }

    public void onRegisterClick(View view) {
        String fn = firstNameInput.getText().toString().trim();
        String ln = lastNameInput.getText().toString().trim();
        String em = emailInput.getText().toString().trim();
        String pw = passwordInput.getText().toString().trim();

        if (fn.isEmpty() || ln.isEmpty() || em.isEmpty() || pw.isEmpty()) {
            MyLogger.toast("Заповніть усі поля");
            return;
        }

        if (selectedImageUri == null) {
            MyLogger.toast("Додайте зображення");
            return;
        }

        uploadRegister(fn, ln, em, pw, selectedImageUri);
    }

    private void uploadRegister(String fn, String ln, String em, String pw, Uri uri) {
        RequestBody fnPart = RequestBody.create(fn, MultipartBody.FORM);
        RequestBody lnPart = RequestBody.create(ln, MultipartBody.FORM);
        RequestBody emPart = RequestBody.create(em, MultipartBody.FORM);
        RequestBody pwPart = RequestBody.create(pw, MultipartBody.FORM);

        MultipartBody.Part imagePart = createImagePart(uri);

        CommonUtils.showLoading();

        RetrofitClient.getInstance()
                .getAuthApi()
                .register(fnPart, lnPart, emPart, pwPart, imagePart)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            MyLogger.toast("Реєстрація успішна");
                            finish();
                        } else {
                            MyLogger.toast("Помилка сервера: " + response.code());
                        }

                        CommonUtils.hideLoading();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        MyLogger.toast("Помилка: " + t.getMessage());
                        CommonUtils.hideLoading();
                    }
                });
    }
}