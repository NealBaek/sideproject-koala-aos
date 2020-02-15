package com.ksdigtalnomad.koala.data.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @Builder @ToString
public class AppVersion {
    private String text;
    private String versionChar;
    private int major;
    private int minor;
    private int patch;
    private String createdAt;
    private String updatedAt;
}
