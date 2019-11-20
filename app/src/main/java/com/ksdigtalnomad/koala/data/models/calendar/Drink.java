package com.ksdigtalnomad.koala.data.models.calendar;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder @Setter @Getter @ToString
public class Drink extends BaseData implements Cloneable{
    String name = "";
    int selectedCnt = 0;
    boolean isSelected = false;

    public Drink clone(){
        //내 객체 생성
        Object toReturn;

        try
        {
            toReturn = super.clone();
            return ((Drink)toReturn);
        }
        catch (CloneNotSupportedException e)
        {
            return this;
        }
    }
}
