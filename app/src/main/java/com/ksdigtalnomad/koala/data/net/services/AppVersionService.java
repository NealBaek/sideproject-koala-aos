package com.ksdigtalnomad.koala.data.net.services;

import com.ksdigtalnomad.koala.data.models.AppVersion;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AppVersionService {
    @GET("v1/appVersion/{platform}")
    Observable<AppVersion> getAppVersion(@Path("platform") String platform);
}
