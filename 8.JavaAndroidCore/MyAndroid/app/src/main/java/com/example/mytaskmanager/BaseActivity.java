package com.example.mytaskmanager;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

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


}
