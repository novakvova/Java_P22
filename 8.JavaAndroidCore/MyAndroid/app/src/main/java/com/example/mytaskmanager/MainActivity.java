package com.example.mytaskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytaskmanager.dto.zadachi.ZadachaItemDTO;
import com.example.mytaskmanager.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    RecyclerView taskRecycler;
    TaskAdapter adapter;

    View addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        taskRecycler = findViewById(R.id.taskRecycler);
        taskRecycler.setAdapter(new TaskAdapter(new ArrayList<>()));
        taskRecycler.setLayoutManager(
                new androidx.recyclerview.widget.LinearLayoutManager(this)
        );

        addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(v ->
                {
                    goToAddTaskActivity();
                }
        );
        loadTaskList();
    }

    private void loadTaskList() {
        RetrofitClient.getInstance().getZadachiApi().list()
                .enqueue(new Callback<List<ZadachaItemDTO>>() {
            @Override
            public void onResponse(Call<List<ZadachaItemDTO>> call, Response<List<ZadachaItemDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new TaskAdapter(response.body());
                    taskRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<ZadachaItemDTO>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}