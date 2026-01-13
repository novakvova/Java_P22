package com.example.mytaskmanager;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mytaskmanager.screens.AddTaskActivity;
import com.example.mytaskmanager.screens.LoginActivity;
import com.example.mytaskmanager.screens.MainActivity;
import com.example.mytaskmanager.screens.RegisterActivity;
import com.example.mytaskmanager.utils.CommonUtils;

public class AuthActivity extends AppCompatActivity {

    public AuthActivity() {
        CommonUtils.setContext(this);
    }

    protected void goToMainActivity() {
        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
        startActivity(intent);
    }

    protected void goToAddTaskActivity() {
        Intent intent = new Intent(AuthActivity.this, AddTaskActivity.class);
        startActivity(intent);
    }

    protected void goToRegistration() {
        Intent intent = new Intent(AuthActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    protected void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
