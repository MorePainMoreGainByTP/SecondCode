package com.example.swjtu.secondcode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.swjtu.secondcode.entity.Person;
import com.example.swjtu.secondcode.util.MyXMLHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by tangpeng on 2017/8/4.
 */

public class ParserXMLActivity extends AppCompatActivity {
    private static final String TAG = "ParserXMLActivity";

    String xmlData;
    StringBuilder stringBuilder = new StringBuilder();
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parser_xml);
        textView = (TextView) findViewById(R.id.textView);
        xmlData = "<apps>" +
                "<app><id>1</id> <name>Google Maps</name> <version>1.0</version></app>" +
                "<app><id>2</id> <name>Chrome</name> <version>2.1</version></app>" +
                "<app><id>3</id> <name>Google play</name> <version>1.5</version></app>" +
                "</apps>";
    }

    public void onPull(View v) {
        textView.setText("");
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xmlData));
            int eventType = parser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = parser.getName();
                switch (eventType) {
                    //开始解析某个节点
                    case XmlPullParser.START_TAG:
                        if ("id".equals(nodeName)) {
                            id = parser.nextText();
                        } else if ("name".equals(nodeName)) {
                            name = parser.nextText();
                        } else if ("version".equals(nodeName)) {
                            version = parser.nextText();
                        }
                        break;
                    //解析某个节点完成
                    case XmlPullParser.END_TAG:
                        if ("app".equals(nodeName)) {
                            String strId = "id is " + id;
                            String strName = "name is " + name;
                            String strVersion = "version is " + version;
                            Log.i(TAG, strId);
                            Log.i(TAG, strName);
                            Log.i(TAG, strVersion);
                            stringBuilder.append(strId);
                            stringBuilder.append(strName);
                            stringBuilder.append(strVersion);
                        }
                        break;
                }
                eventType = parser.next(); //下一个解析事件
            }
            textView.setText(stringBuilder.toString());
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onSax(View v) {
        textView.setText("");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            XMLReader reader = factory.newSAXParser().getXMLReader();
            MyXMLHandler myXMLHandler = new MyXMLHandler();
            myXMLHandler.setTextView(textView);
            reader.setContentHandler(myXMLHandler);
            reader.parse(new InputSource(new StringReader(xmlData)));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String jsonArray = "[{\"id\":\"5\",\"version\":\"5.5\",\"name\":\"clash of clans\"},{\"id\":\"6\",\"version\":\"3.5\",\"name\":\"Boom of Beach\"},{\"id\":\"7\",\"version\":\"9.5\",\"name\":\"clash of Royale\"}]";

    //使用GSON解析JSON数组
    public void onJSONs(View v) {
        Gson gson = new Gson();
        List<Person> personList = gson.fromJson(jsonArray, new TypeToken<List<Person>>() {
        }.getType());
        for (Person person : personList) {
            Log.i(TAG, "onJSONs: " + person);
            textView.append(person.toString() + "\n");
        }
    }

}
