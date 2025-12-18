package com.example.mytaskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytaskmanager.dto.zadachi.ZadachaItemDTO;
import com.example.mytaskmanager.network.RetrofitClient;
import com.example.mytaskmanager.utils.CommonUtils;
import com.example.mytaskmanager.utils.MyLogger;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    RecyclerView taskRecycler;
    TaskAdapter adapter;
    View accountButton;
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
        taskRecycler.setAdapter(new TaskAdapter(new ArrayList<>(),
                MainActivity.this::onClickEditZadatch));
        taskRecycler.setLayoutManager(
                new androidx.recyclerview.widget.LinearLayoutManager(this)
        );

        addButton = findViewById(R.id.addButton);

        accountButton = findViewById(R.id.accountButton);
        accountButton.setOnClickListener(v -> goToRegistration());

        addButton.setOnClickListener(v ->
                {
                    goToAddTaskActivity();
                }
        );
        CommonUtils.showLoading();
        loadTaskList();

    }

    private void loadTaskList() {
        RetrofitClient.getInstance().getZadachiApi().list()
                .enqueue(new Callback<List<ZadachaItemDTO>>() {
            @Override
            public void onResponse(Call<List<ZadachaItemDTO>> call, Response<List<ZadachaItemDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new TaskAdapter(response.body(),
                            MainActivity.this::onClickEditZadatch);
                    taskRecycler.setAdapter(adapter);
                    CommonUtils.hideLoading();
                }
            }

            @Override
            public void onFailure(Call<List<ZadachaItemDTO>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void onClickEditZadatch(ZadachaItemDTO item) {
        //MyLogger.toast(MainActivity.this, "Зміна задачі");
        Intent intent = new Intent(this, EditTaskActivity.class);
        intent.putExtra("task_id", item.getId());
        intent.putExtra("task_name", item.getName());
        intent.putExtra("task_image", item.getImage());
        this.startActivity(intent);
    }

}