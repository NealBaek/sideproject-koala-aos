package com.ksdigtalnomad.koala.data.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder @Setter @Getter @ToString
public class User {
    private String id;
    private String versionId;
    private String adId;
    private String socialId;
    private String socialType;
    private String email;
    private String password;
    private String name;
    private String gender;
    private String birthday;
    private String platform;    // android
    private String pushToken;
    private String accessToken;
    private String state;
    private String role;
    private String createdAt;
    private String updatedAt;

}
