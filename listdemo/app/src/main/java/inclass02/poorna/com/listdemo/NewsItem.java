package inclass02.poorna.com.listdemo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by poorn on 2/26/2018.
 */

public class NewsItem implements Serializable {
    String title;
    String description;
    String date;
    String imgurl;
    Date datetime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "NewsItem{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", datetime=" + datetime +
                '}';
    }

}
