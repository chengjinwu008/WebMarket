package com.lanhaijiye.WebMarket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.adapter.ContinentAdapter;
import com.lanhaijiye.WebMarket.entities.Continent;
import com.lanhaijiye.WebMarket.entities.CountryCode;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJQ on 2015/5/11.
 */
public class CountryListActivity extends BaseActivity implements View.OnClickListener {

    private Handler mHandler=new Handler();
    private ListView list;
    public static final String ITENT_EXTRA_COUNTRY_CODE="code";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_list);
        list= (ListView) findViewById(R.id.country_list);

        findViewById(R.id.sign_up_back).setOnClickListener(this);

        new Thread(){
            @Override
            public void run() {
                try {
                    List<Continent> continents = new ArrayList<Continent>();
                    SAXParserFactory parserFactory =SAXParserFactory .newInstance();
                    SAXParser parser = parserFactory.newSAXParser();
                    parser.parse(getResources().openRawResource(R.raw.country_code), new DefaultHandler() {
                        Continent continent_temp;
                        CountryCode countryCode_temp;
                        String preTag;

                        @Override
                        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                            if ("continent".equals(localName)) {
                                continent_temp = new Continent();
                                continent_temp.setShortName(attributes.getValue("short_name"));
                                continent_temp.setChineseName(attributes.getValue("chinese_name"));
                            } else if ("country".equals(localName)) {
                                countryCode_temp = new CountryCode();
                                countryCode_temp.setShortName(attributes.getValue("short_name"));
                                countryCode_temp.setChineseName(attributes.getValue("chinese_name"));
                                preTag = "country";
                            }
                        }

                        @Override
                        public void endElement(String uri, String localName, String qName) throws SAXException {
                            if ("continent".equals(localName)) {
                                continents.add(continent_temp);
                            } else if ("country".equals(localName)) {
                                continent_temp.add(countryCode_temp);
                                preTag = null;
                            }
                        }

                        @Override
                        public void characters(char[] ch, int start, int length) throws SAXException {
                            if (countryCode_temp != null && preTag != null)
                                countryCode_temp.setCode(new String(ch, start, length));
                        }
                    });

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            //²éÑ¯Íê±Ï·µ»Ø
                            list.setAdapter(new ContinentAdapter(continents, CountryListActivity.this, new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent res =getIntent();

                                    res.putExtra(ITENT_EXTRA_COUNTRY_CODE, (Parcelable) parent.getAdapter().getItem(position));

                                    setResult(RESULT_OK,res);

                                    finish();
                                }
                            }));
                        }
                    });
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
