package com.ksdigtalnomad.koala.data.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder @Setter @Getter @ToString
public class User {
    String _id;
    String email;
    String password;
    String firstName;
    String lastName;
    String phone;
    String birthday;
    String gender;
    String platform;    // android
    String role;
    String state;
//    List<Social> social;
    String pushToken;
    String createdAt;
    String updatedAt;

    String accessToken;
    String authNum;     // phone auth

    // social
    String socialType;
    String socialId;
}
