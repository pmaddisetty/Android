package inclass02.poorna.com.xmldemo;

/**
 * Created by poorn on 2/19/2018.
 */

public class NewsItem {
    String title;
    String description;
    String Date;
    String link;
 Media media;

    public NewsItem() {
    }

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
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    @Override
    public String toString() {
        return "NewsItem{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", Date='" + Date + '\'' +
                ", link='" + link + '\'' +
                ", media=" + media +
                '}';
    }
}
