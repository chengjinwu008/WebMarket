package com.lanhaijiye.WebMarket.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CJQ on 2015/5/11.
 */
public class CountryCode implements Parcelable{

    private String shortName;
    private String chineseName;
    private String code;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(chineseName);
        dest.writeString(shortName);
        dest.writeString(code);
    }

    public static final Parcelable.Creator<CountryCode> CREATOR = new Creator<CountryCode>() {
        @Override
        public CountryCode createFromParcel(Parcel source) {
            return new CountryCode(source);
        }

        @Override
        public CountryCode[] newArray(int size) {
            return new CountryCode[size];
        }
    };

    public CountryCode(Parcel parcel) {
        chineseName = parcel.readString();
        shortName = parcel.readString();
        code = parcel.readString();
    }

    public CountryCode() {
    }
}
