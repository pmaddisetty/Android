package inclass02.poorna.com.listdemo;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by poorn on 2/26/2018.
 */

public class NewsParser {
    public static class NewsPullParser{
        static public ArrayList<NewsItem> parseNews(InputStream inputStream) throws XmlPullParserException, IOException, ParseException {
            ArrayList<NewsItem> news=new ArrayList<>();
            NewsItem item=null;
            XmlPullParserFactory factory=  XmlPullParserFactory.newInstance();
            XmlPullParser parser=factory.newPullParser();
            parser.setInput(inputStream,"UTF_8");
            int event=parser.getEventType();
            Log.d("demo","event="+event);
            Boolean flag=false;
            while (event!=XmlPullParser.END_DOCUMENT)
            {
                switch (event){
                    case XmlPullParser.START_TAG:

                        if(parser.getName().equals("item"))
                        {
                            Log.d("demo","item entered");
                            item=new NewsItem();
                            flag=true;

                        }
                        else if(parser.getName().equals("title")&& flag==true)
                        {
                            item.setTitle(parser.nextText().trim());
                        }
                        else if(parser.getName().equals("description")&& flag==true)
                        {
                            item.setDescription(parser.nextText().trim());

                        }
                        else if(parser.getName().equals("pubDate")&& flag==true)
                        {
                            String datetime=parser.nextText().trim();
                            java.util.Date datim;
                            //"Sun, 25 Feb 2018 14:32:29 GMT"
                            SimpleDateFormat sdf= new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
                            datim=sdf.parse(datetime);
                            item.setDate(datetime);
                            item.setDatetime(datim);

                        }
                        else if(parser.getName().equals("media:content")&& flag==true)
                        {
                            item.setImgurl(parser.getAttributeValue(null,"url"));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                         if(parser.getName().equals("item")&& flag==true)
                        {
                            news.add(item);
                        }


                        break;
                    default:
                        break;
                }
                event=parser.next();
            }
            return news;
        }

    }
}
