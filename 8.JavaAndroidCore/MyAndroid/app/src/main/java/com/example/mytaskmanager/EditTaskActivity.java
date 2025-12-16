package com.example.mytaskmanager;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mytaskmanager.application.HomeApplication;
import com.example.mytaskmanager.config.Config;
import com.example.mytaskmanager.network.RetrofitClient;
import com.example.mytaskmanager.utils.FileUtil;
import com.example.mytaskmanager.utils.MyLogger;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTaskActivity extends BaseActivity {

    private EditText titleInput;
    private ImageView imagePreview;
    private Uri selectedImageUri;
    private long taskId = -1;
    private String currentImageName;

    private final ActivityResultLauncher<String> imagePicker =
            registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    uri -> {
                        if (uri != null) {
                            selectedImageUri = uri;
                            imagePreview.setImageURI(uri);
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        titleInput = findViewById(R.id.editTaskTitleInput);
        imagePreview = findViewById(R.id.editTaskImagePreview);

        findViewById(R.id.editChooseImageButton)
                .setOnClickListener(v -> imagePicker.launch("image/*"));

        taskId = getIntent().getIntExtra("task_id", -1);
        currentImageName = getIntent().getStringExtra("task_image");
        String taskName = getIntent().getStringExtra("task_name");

        if (taskName != null) {
            titleInput.setText(taskName);
        }

        String url = currentImageName != null && !currentImageName.isEmpty()
                ? Config.IMAGES_URL + "200_" + currentImageName
                : Config.IMAGES_URL + "default.jpg";

        Glide.with(this)
                .load(url)
                .apply(new RequestOptions().override(300))
                .into(imagePreview);
    }

    public void onUpdateClick(View view) {
        String title = titleInput.getText().toString().trim();

        if (title.isEmpty()) {
            MyLogger.toast("Введіть назву задачі");
            return;
        }
        if (taskId == -1) {
            MyLogger.toast("Немає ідентифікатора задачі");
            return;
        }

        updateTask(taskId, title, selectedImageUri);
    }

    private void updateTask(long id, String title, Uri imageUri) {
        String mimeType = imageUri != null ? getContentResolver().getType(imageUri) : null;
        if (mimeType == null) mimeType = "image/jpeg";

        RequestBody titlePart =
                RequestBody.create(title, MultipartBody.FORM);

        MultipartBody.Part imagePart = null;
        if(imageUri != null) {
            String imagePath = FileUtil.getImagePath(this, imageUri);
            if (imagePath != null) {
                File file = new File(imagePath);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                imagePart = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
            }
        }

        RetrofitClient.getInstance()
                .getZadachiApi()
                .update(id, titlePart, imagePart)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        MyLogger.toast("Задача оновлена");
                        goToMainActivity();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("EditTaskActivity", "onFailure type: " + t.getClass().getName());
                        Log.e("EditTaskActivity", "message: " + t.getMessage(), t);
                        MyLogger.toast( "Помилка: " + t.getMessage());
                    }
                });
    }
}