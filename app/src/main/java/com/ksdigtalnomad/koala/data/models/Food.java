package com.ksdigtalnomad.koala.data.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder @Setter @Getter @ToString
public class Food extends BaseData implements Cloneable {
    String name;
    int selectedCnt;
    boolean isSelected;

    public Food clone(){
        //내 객체 생성
        Object toReturn;

        try
        {
            toReturn = super.clone();
            return ((Food)toReturn);
        }
        catch (CloneNotSupportedException e)
        {
            return this;
        }
    }
}
