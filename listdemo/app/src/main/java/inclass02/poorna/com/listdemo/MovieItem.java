package inclass02.poorna.com.listdemo;

/**
 * Created by poorn on 3/12/2018.
 */

public class MovieItem {
String title;
String overview;
String date;
double rating,popularity;
String imageurl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    @Override
    public String toString() {
        return "MovieItem{" +
                "title='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", date='" + date + '\'' +
                ", rating=" + rating +
                ", popularity=" + popularity +
                ", imageurl='" + imageurl + '\'' +
                '}';
    }
}
