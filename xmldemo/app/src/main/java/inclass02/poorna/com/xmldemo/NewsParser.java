package inclass02.poorna.com.xmldemo;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by poorn on 2/19/2018.
 */

public class NewsParser {
    public static class NewsPullParser{
        static public ArrayList<NewsItem> parseNews(InputStream inputStream) throws XmlPullParserException, IOException {
            ArrayList<NewsItem> news=new ArrayList<>();
            NewsItem item=null;
            Media media=null;
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
                        else if(parser.getName().equals("link")&& flag==true)
                        {
                            item.setLink(parser.nextText().trim());
                        }
                        else  if(parser.getName().equals("media:group")&& flag==true)
                        {
                            media= new Media();
                        }
                        else if(parser.getName().equals("media:content")&& flag==true)
                        {
                            media.setMedium(parser.getAttributeValue(null,"medium"));
                            media.setUrl(parser.getAttributeValue(null,"url"));
                            media.setHeight(Integer.valueOf(parser.getAttributeValue(null,"height")));
                            media.setWidth(Integer.valueOf(parser.getAttributeValue(null,"width")));

                        }
                         else if(parser.getName().equals("feedburner:origLink")&& flag==true)
                         {
                             item.setDate("Tue, 15 Aug 2017 16:57:18 GMT");
                         }

                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("media:group")&& flag==true)
                        {
                           item.media=media;
                        }
                        else if(parser.getName().equals("item")&& flag==true)
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
