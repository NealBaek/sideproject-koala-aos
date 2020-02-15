package com.ksdigtalnomad.koala.data.net.services;

import android.database.Observable;

import com.ksdigtalnomad.koala.data.models.vo.InterviewSelectVo;
import com.ksdigtalnomad.koala.data.models.vo.InterviewVo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InterviewService {

    @GET("v1/interview/{type}")
    Observable<InterviewVo> getInterview(@Path("type") String type);

    @GET("v1/interview/{type}/select")
    Call<Boolean> postInterviewSelect(@Body InterviewSelectVo vo);
}
