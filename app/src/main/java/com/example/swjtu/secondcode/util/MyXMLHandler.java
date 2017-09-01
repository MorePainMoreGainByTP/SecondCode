package com.example.swjtu.secondcode.util;


import android.widget.TextView;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by tangpeng on 2017/8/4.
 */

public class MyXMLHandler extends DefaultHandler {
    String nodeName;
    StringBuilder id;
    StringBuilder name;
    StringBuilder version;
    TextView textView;

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        id = new StringBuilder();
        name = new StringBuilder();
        version = new StringBuilder();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        nodeName = localName;   //当前节点名
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if(localName.equals("app")){
            textView.append("id is "+id.toString().trim()+"\n");    //调用trim()是为了，将tag间的回车或换行符去掉
            textView.append("name is "+name.toString().trim()+"\n");
            textView.append("version is "+version.toString().trim()+"\n");
            id.setLength(0);
            name.setLength(0);
            version.setLength(0);
        }
    }

    //取节点内容时被调用
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        switch (nodeName){
            case "id":
                id.append(ch,start,length);
                break;
            case "name":
                name.append(ch,start,length);
                break;
            case "version":
                version.append(ch,start,length);
                break;
        }
    }
}
