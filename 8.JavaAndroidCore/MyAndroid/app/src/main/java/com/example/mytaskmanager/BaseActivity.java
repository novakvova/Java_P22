package com.example.mytaskmanager;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mytaskmanager.screens.AddTaskActivity;
import com.example.mytaskmanager.screens.MainActivity;
import com.example.mytaskmanager.screens.RegisterActivity;
import com.example.mytaskmanager.utils.CommonUtils;

public class BaseActivity extends AppCompatActivity {

    public BaseActivity() {
        CommonUtils.setContext(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int select = item.getItemId();

        if(select == R.id.m_create) {
            goToAddTaskActivity();
            return true;
        }

        if(select == R.id.m_zadachi) {
            goToMainActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void goToMainActivity() {
        Intent intent = new Intent(BaseActivity.this, MainActivity.class);
        startActivity(intent);
    }

    protected void goToAddTaskActivity() {
        Intent intent = new Intent(BaseActivity.this, AddTaskActivity.class);
        startActivity(intent);
    }

    protected void goToRegistration() {
        Intent intent = new Intent(BaseActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
