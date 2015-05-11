package com.lanhaijiye.WebMarket.activities;

import android.os.Bundle;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.entities.Continent;
import com.lanhaijiye.WebMarket.entities.CountryCode;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJQ on 2015/5/11.
 */
public class CountryListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_list);
        List<Continent> continents = null;
        Continent continent_temp = null;
        CountryCode countryCode_temp = null;
        try {
            XmlPullParserFactory pullParserFactory= XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser=pullParserFactory.newPullParser();
            xmlPullParser.setInput(getResources().openRawResource(R.raw.country_code), "UTF-8");
            int eventType = xmlPullParser.getEventType();

            while (eventType!=XmlPullParser.END_DOCUMENT){
                String nodeName = xmlPullParser.getName();
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        continents = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG:
                        switch (nodeName){
                            case "continent":
                                continent_temp = new Continent();
                                continent_temp.setShortName(xmlPullParser.getAttributeValue(null,"short_name"));
                                continent_temp.setChineseName(xmlPullParser.getAttributeValue(null, "chinese_name"));
                                break;
                            case "country":
                                countryCode_temp = new CountryCode();
                                countryCode_temp.setShortName(xmlPullParser.getAttributeValue(null,"short_name"));
                                countryCode_temp.setChineseName(xmlPullParser.getAttributeValue(null, "chinese_name"));
                                countryCode_temp.setShortName(xmlPullParser.nextText());
                                break;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        switch (nodeName){
                            case "continent":
                                continents.add(continent_temp);
                                break;
                            case "country":
                                continent_temp.add(countryCode_temp);
                                break;
                        }
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;
                }
                eventType = xmlPullParser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
