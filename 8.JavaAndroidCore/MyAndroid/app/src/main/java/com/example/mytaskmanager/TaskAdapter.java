package com.example.mytaskmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mytaskmanager.config.Config;
import com.example.mytaskmanager.dto.zadachi.ZadachaItemDTO;
import com.example.mytaskmanager.network.RetrofitClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    ArrayList<ZadachaItemDTO> taskList = new ArrayList<>();

    public TaskAdapter() {
        RetrofitClient.getInstance().getZadachiApi().list()
                .enqueue(new Callback<List<ZadachaItemDTO>>() {
            @Override
            public void onResponse(Call<List<ZadachaItemDTO>> call, Response<List<ZadachaItemDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    taskList.addAll(response.body());
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<ZadachaItemDTO>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        ZadachaItemDTO item = taskList.get(position);
        holder.taskText.setText(item.getName());

        Glide.with(holder.itemView.getContext())
                .load(Config.IMAGES_URL + "400_" + item.getImage())
                .into(holder.taskImage);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void swap(int from, int to) {
        Collections.swap(taskList, from, to);
        notifyItemMoved(from, to);
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView taskText;
        CheckBox taskCheckBox;
        ImageView taskImage;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskText = itemView.findViewById(R.id.taskText);
            taskCheckBox = itemView.findViewById(R.id.taskCheckBox);
            taskImage = itemView.findViewById(R.id.taskImage);
        }
    }

}