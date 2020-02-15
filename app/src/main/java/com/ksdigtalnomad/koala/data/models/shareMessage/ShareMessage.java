package com.ksdigtalnomad.koala.data.models.shareMessage;

import com.ksdigtalnomad.koala.data.models.calendar.BaseData;

import java.util.ArrayList;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder @Setter @Getter @ToString
public class ShareMessage extends BaseData implements Cloneable{
    String langCode;
    String content;

    public ShareMessage clone(){
        //내 객체 생성
        Object toReturn;

        try
        {
            toReturn = super.clone();
            return ((ShareMessage)toReturn);
        }
        catch (CloneNotSupportedException e)
        {
            return this;
        }
    }
}