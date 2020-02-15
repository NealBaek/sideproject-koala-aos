package com.ksdigtalnomad.koala.data.models.interview;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @Builder @ToString
public class Interview {
    private String id;
    private String type;
    private String titleKr;
    private String titleEn;
    private String descKr;
    private String descEn;
    private String createdAt;
    private String updatedAt;
}
