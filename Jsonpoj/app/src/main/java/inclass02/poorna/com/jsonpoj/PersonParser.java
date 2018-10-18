package inclass02.poorna.com.jsonpoj;

import android.content.Intent;
import android.util.Log;
import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by poorn on 2/18/2018.
 */

public class PersonParser {
    public static class PersonSAXParser extends DefaultHandler
    {
        StringBuilder innerXml;
        ArrayList<Person> persons;
        Person per;
        Address address;

        static public ArrayList<Person> parsePersons(InputStream inputStream) throws IOException, SAXException {
            PersonSAXParser parser= new PersonSAXParser();
            Xml.parse(inputStream,Xml.Encoding.UTF_8,parser);
            return parser.persons;
        }
        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            this.persons=new ArrayList<Person>();
            this.innerXml=new StringBuilder();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if(localName.equals("person"))
            {
                per=new Person();
                per.id= Long.valueOf(attributes.getValue("id"));
                Log.d("demo","Pers started");
            }
            else if(localName.equals("address"))
            {
                address=new Address();
                Log.d("demo","address started");
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            String text="";
            if(innerXml.toString()!=null)
            {
                text=innerXml.toString().trim();
            }
             if(localName.equals("name"))
            {
                per.name=text;
            }
            else if(localName.equals("age"))
            {
                per.age=Integer.valueOf(text);
            }
            else if(localName.equals("line1"))
            {
                address.line1=text;
            }
            else if(localName.equals("city"))
            {
                address.city=text;
            }
            else if(localName.equals("state"))
            {
                address.state=text;
            }
            else if(localName.equals("zip"))
            {
                address.zip=text;
            }
           else  if(localName.equals("address"))
            {
                per.address=address;
            }
           else if(localName.equals("person"))
            {
                persons.add(per);
                Log.d("demo","person list="+persons.toString());
            }
            innerXml.setLength(0);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            innerXml.append(ch,start,length);
         }
    }
    public static class PersonPullParser{
        static public ArrayList<Person> parsePersons(InputStream inputStream) throws XmlPullParserException, IOException {
            ArrayList<Person> persons=new ArrayList<>();
            Person per=null;
            Address address=null;
            XmlPullParserFactory factory=  XmlPullParserFactory.newInstance();
            XmlPullParser parser=factory.newPullParser();
            parser.setInput(inputStream,"UTF_8");
            int event=parser.getEventType();
            Log.d("demo","event="+event);
            while (event!=XmlPullParser.END_DOCUMENT)
            {
                switch (event){
                    case XmlPullParser.START_TAG:
                        if(parser.getName().equals("person"))
                        {
                            per=new Person();
                            per.id=Long.valueOf(parser.getAttributeValue(null,"id"));
                            Log.d("demo","entered");
                        }
                        else if(parser.getName().equals("name"))
                        {
                            per.name=parser.nextText().trim();
                        }
                        else if(parser.getName().equals("age"))
                        {
                            per.age=Integer.valueOf(parser.nextText().trim());
                        }
                        else if(parser.getName().equals("address"))
                        {
                           address=new Address();
                        }
                        else  if(parser.getName().equals("line1"))
                        {
                            address.line1=parser.nextText().trim();
                        }
                        else if(parser.getName().equals("city"))
                        {
                            address.city=parser.nextText().trim();
                        }
                        else if(parser.getName().equals("state"))
                        {
                            address.state=parser.nextText().trim();
                        }
                        else if(parser.getName().equals("zip"))
                        {
                            address.zip=parser.nextText().trim();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("address"))
                        {
                            per.address=address;
                        }
                        else if(parser.getName().equals("person"))
                        {
                            persons.add(per);
                        }
                        break;
                    default:
                        break;
                }
                event=parser.next();
            }
            return persons;
        }

    }
}
