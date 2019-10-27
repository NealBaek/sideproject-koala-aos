package com.ksdigtalnomad.koala.data.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder @Setter @Getter @ToString
public class Drink extends BaseData implements Cloneable{
    String name;
    int selectedCnt;
    boolean isSelected;

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
