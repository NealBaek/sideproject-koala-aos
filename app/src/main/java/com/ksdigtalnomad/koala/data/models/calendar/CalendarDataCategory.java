package com.ksdigtalnomad.koala.data.models.calendar;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @Builder @ToString
public class CalendarDataCategory {
    private String id;
    private String code;
    private String parentId;
    private String type;
    private int depth;
    private String nameKr;
    private String nameEn;
    private int sequence;
    private int version;
    private String createdAt;
    private String updatedAt;
}
