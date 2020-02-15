package com.ksdigtalnomad.koala.data.models.vo;

import com.ksdigtalnomad.koala.data.models.interview.Interview;
import com.ksdigtalnomad.koala.data.models.interview.InterviewOption;
import com.ksdigtalnomad.koala.ui.customView.calendarView.day.DayModel;

import java.util.ArrayList;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @Builder @ToString
public class InterviewVo {
    private Interview interview;
    private ArrayList<InterviewOption> interviewOptionList;
}
