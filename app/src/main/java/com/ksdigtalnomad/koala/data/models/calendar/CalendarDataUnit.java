package com.ksdigtalnomad.koala.data.models.calendar;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @Builder @ToString
public class CalendarDataUnit {
    private String id;
    private String code;
    private String calendarDataCategoryCode;
    private String type;
    private String nameKr;
    private String nameEn;
    private double quantity;
    private double ml;
    private double calorie;
    private int sequence;
    private int version;
    private String createdAt;
    private String updatedAt;
}