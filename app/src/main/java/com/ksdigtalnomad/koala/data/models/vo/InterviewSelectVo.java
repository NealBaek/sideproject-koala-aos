package com.ksdigtalnomad.koala.data.models.vo;

import com.ksdigtalnomad.koala.data.models.interview.InterviewOption;

import java.util.ArrayList;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @Builder @ToString
public class InterviewSelectVo {
    private ArrayList<InterviewOption> interviewOptionList;
}
