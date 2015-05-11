package com.lanhaijiye.WebMarket.entities;

import java.util.ArrayList;

/**
 * Created by CJQ on 2015/5/11.
 */
public class Continent extends ArrayList<CountryCode>{

    private String shortName;
    private String chineseName;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }
}
