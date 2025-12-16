package com.example.mytaskmanager.network;

import com.example.mytaskmanager.dto.zadachi.ZadachaItemDTO;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface ZadachiApi {
    @GET("api/zadachi")
    public Call<List<ZadachaItemDTO>> list();
    @Multipart
    @POST("api/zadachi")
    Call<ZadachaItemDTO> create(
            @Part("Name") RequestBody name,
            @Part MultipartBody.Part image
    );

    @Multipart
    @PUT("api/zadachi")
    Call<Void> update(
            @Part("id") long id,
            @Part("Name") RequestBody name,
            @Part MultipartBody.Part image
    );

    @HTTP(method = "DELETE", path = "Zadachi/range", hasBody = true)
    Call<Void> deleteRange(@Body List<Long> ids);
}
