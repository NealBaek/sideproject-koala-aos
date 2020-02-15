package com.ksdigtalnomad.koala.data.net.services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AlarmService {
    @POST("v1/alarm")
    Call<Boolean> postAlarm(@Body String type, @Body String time, @Body Boolean isAgree);
}
