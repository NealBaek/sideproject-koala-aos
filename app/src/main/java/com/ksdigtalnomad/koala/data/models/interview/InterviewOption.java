package com.ksdigtalnomad.koala.data.models.interview;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @Builder @ToString
public class InterviewOption {
    private String id;
    private String interviewId;
    private String nameKr;
    private String nameEn;
    private int sequence;
    private String createdAt;
    private String updatedAt;
}
