package com.ksdigtalnomad.koala.data.models.calendar;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder @Setter @Getter @ToString
public class Friend extends BaseData implements Cloneable{
    String name = "";
    int selectedCnt = 0;
    boolean isSelected = false;

    public Friend clone(){
        //내 객체 생성
        Object toReturn;

        try
        {
            toReturn = super.clone();
            return ((Friend)toReturn);
        }
        catch (CloneNotSupportedException e)
        {
            return this;
        }
    }
}
