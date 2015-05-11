package com.lanhaijiye.WebMarket.entities;

/**
 * Created by CJQ on 2015/5/11.
 */
public class CountryCode {

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
}
