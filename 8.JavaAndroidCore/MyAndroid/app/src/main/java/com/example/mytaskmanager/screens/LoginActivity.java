package com.example.mytaskmanager.screens;

import android.os.Bundle;
import android.view.View;

import com.example.mytaskmanager.AuthActivity;
import com.example.mytaskmanager.BaseActivity;
import com.example.mytaskmanager.R;
import com.example.mytaskmanager.application.HomeApplication;
import com.example.mytaskmanager.dto.auth.AuthResponse;
import com.example.mytaskmanager.dto.auth.LoginRequestDTO;
import com.example.mytaskmanager.network.RetrofitClient;
import com.example.mytaskmanager.utils.CommonUtils;
import com.example.mytaskmanager.utils.MyLogger;
import com.example.mytaskmanager.utils.validation.logic.FieldValidator;
import com.example.mytaskmanager.utils.validation.logic.FormValidator;
import com.example.mytaskmanager.utils.validation.rules.EmailRule;
import com.example.mytaskmanager.utils.validation.rules.MinLengthRule;
import com.example.mytaskmanager.utils.validation.rules.RequiredRule;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AuthActivity {

    private TextInputLayout emailLayout, passwordLayout;
    private TextInputEditText emailInput, passwordInput;
    private FormValidator formValidator;

    private void initViews() {
        emailLayout     = findViewById(R.id.emailLayout);
        passwordLayout  = findViewById(R.id.passwordLayout);

        emailInput      = findViewById(R.id.email);
        passwordInput   = findViewById(R.id.password);

    }

    private void initValidator() {
        formValidator = new FormValidator()
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        HomeApplication.getInstance().deleteToken();

        if(HomeApplication.getInstance().isAuth())
            goToMainActivity();

        initViews();
        initValidator();
    }

    public void onLoginClick(View view) {

        if (!formValidator.validate()) {
            return;
        }

        LoginRequestDTO requestDTO =
                new LoginRequestDTO(emailInput.getText().toString(), passwordInput.getText().toString());

        CommonUtils.showLoading();

        RetrofitClient.getInstance()
                .getAuthApi()
                .login(requestDTO)
                .enqueue(new Callback<AuthResponse>() {

                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                        CommonUtils.hideLoading();
                        if (response.isSuccessful()) {
                            String token = response.body().getToken();
                            HomeApplication.getInstance().saveJwtToken(token);
                            goToMainActivity();
                            finish();
                        } else {
                            MyLogger.toast("Помилка сервера: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {
                        CommonUtils.hideLoading();
                        MyLogger.toast("Помилка: " + t.getMessage());
                    }
                });
    }

    public void onGoToRegisterClick(View view) {
        goToRegistration();
    }
}