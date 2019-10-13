package com.ksdigtalnomad.koala.data.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder @Setter @Getter @ToString
public class Friend extends BaseData{
    String name;
    int selectedCnt;
    boolean isSelected;
}
