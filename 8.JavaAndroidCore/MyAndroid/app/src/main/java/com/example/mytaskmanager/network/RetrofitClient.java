package com.example.mytaskmanager.network;

import com.example.mytaskmanager.config.Config;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance;
    private Retrofit retrofit;


    public RetrofitClient() {

        // Створюємо логер
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY); // показує все — headers + body

        OkHttpClient.Builder client =  new  OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(logging); // <<< додано логування
        retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }
    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance=new RetrofitClient();
        }
        return instance;
    }

    public ZadachiApi getZadachiApi() {
        return retrofit.create(ZadachiApi.class);
    }
}
