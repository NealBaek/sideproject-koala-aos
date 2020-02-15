package com.ksdigtalnomad.koala.data.net.services;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AppCSService {
    @POST("v1/appCS/{type}")
    Call<Boolean> postAppCS(@Path("type") String type);
}
