package com.example.mytaskmanager.screens;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import com.example.mytaskmanager.BaseActivity;
import com.example.mytaskmanager.R;
import com.example.mytaskmanager.network.RetrofitClient;
import com.example.mytaskmanager.utils.CommonUtils;
import com.example.mytaskmanager.utils.FileUtil;
import com.example.mytaskmanager.utils.ImagePickerCropper;
import com.example.mytaskmanager.utils.MyLogger;
import com.example.mytaskmanager.utils.validation.logic.FieldValidator;
import com.example.mytaskmanager.utils.validation.logic.FormValidator;
import com.example.mytaskmanager.utils.validation.rules.EmailRule;
import com.example.mytaskmanager.utils.validation.rules.MinLengthRule;
import com.example.mytaskmanager.utils.validation.rules.RequiredRule;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity {

    private TextInputLayout firstNameLayout, lastNameLayout, emailLayout, passwordLayout;
    private TextInputEditText firstNameInput, lastNameInput, emailInput, passwordInput;

    private ImageView imagePreview;
    private Uri selectedImageUri;

    private ImagePickerCropper imageCropper;
    private FormValidator formValidator;

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

    private void initViews() {
        firstNameLayout = findViewById(R.id.firstNameLayout);
        lastNameLayout  = findViewById(R.id.lastNameLayout);
        emailLayout     = findViewById(R.id.emailLayout);
        passwordLayout  = findViewById(R.id.passwordLayout);

        firstNameInput  = findViewById(R.id.firstName);
        lastNameInput   = findViewById(R.id.lastName);
        emailInput      = findViewById(R.id.email);
        passwordInput   = findViewById(R.id.password);

        imagePreview = findViewById(R.id.imagePreview);
    }

    private void initValidator() {
        formValidator = new FormValidator()
                .addField(
                        new FieldValidator(firstNameLayout, firstNameInput)
                                .addRule(new RequiredRule("Введіть імʼя"))
                )
                .addField(
                        new FieldValidator(lastNameLayout, lastNameInput)
                                .addRule(new RequiredRule("Введіть прізвище"))
                )
                .addField(
                        new FieldValidator(emailLayout, emailInput)
                                .addRule(new RequiredRule("Введіть email"))
                                .addRule(new EmailRule("Некоректний email"))
                )
                .addField(
                        new FieldValidator(passwordLayout, passwordInput)
                                .addRule(new RequiredRule("Введіть пароль"))
                                .addRule(new MinLengthRule(6, "Мінімум 6 символів"))
                );
    }

    private void initImagePicker() {
        imageCropper = new ImagePickerCropper(this);

        findViewById(R.id.selectImage).setOnClickListener(v ->
                imageCropper.pick(uri -> {
                    Log.d("---URL image----", uri.getPath());
                    selectedImageUri = uri;
                    imagePreview.setImageURI(uri);
                })
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        initValidator();
        initImagePicker();

    }

    public void onRegisterClick(View view) {

        if (!formValidator.validate()) {
            return;
        }

        if (selectedImageUri == null) {
            MyLogger.toast("Додайте зображення");
            return;
        }

        uploadRegister(
                firstNameInput.getText().toString().trim(),
                lastNameInput.getText().toString().trim(),
                emailInput.getText().toString().trim(),
                passwordInput.getText().toString().trim(),
                selectedImageUri
        );
    }

    private void uploadRegister(String fn, String ln, String em, String pw, Uri uri) {

        RequestBody fnPart = RequestBody.create(fn, MultipartBody.FORM);
        RequestBody lnPart = RequestBody.create(ln, MultipartBody.FORM);
        RequestBody emPart = RequestBody.create(em, MultipartBody.FORM);
        RequestBody pwPart = RequestBody.create(pw, MultipartBody.FORM);

        MultipartBody.Part imagePart =
                FileUtil.createImagePart(
                        this,
                        selectedImageUri,
                        "ImageFile",
                        "avatar.jpg"
                );

        CommonUtils.showLoading();

        RetrofitClient.getInstance()
                .getAuthApi()
                .register(fnPart, lnPart, emPart, pwPart, imagePart)
                .enqueue(new Callback<Void>() {

                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        CommonUtils.hideLoading();

                        if (response.isSuccessful()) {
                            MyLogger.toast("Реєстрація успішна");
                            finish();
                        } else {
                            MyLogger.toast("Помилка сервера: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        CommonUtils.hideLoading();
                        MyLogger.toast("Помилка: " + t.getMessage());
                    }
                });
    }
}