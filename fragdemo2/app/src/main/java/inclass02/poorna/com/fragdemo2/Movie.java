package inclass02.poorna.com.fragdemo2;

import java.io.Serializable;

/**
 * Created by poorn on 3/19/2018.
 */

public class Movie implements Serializable {
    String title, description, genre, year,imdb;
    int rating;

    public Movie(String title, String description, String genre, String year, String imdb, int rating) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.year = year;
        this.imdb = imdb;
        this.rating = rating;
    }

    public Movie() {
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", genre='" + genre + '\'' +
                ", year='" + year + '\'' +
                ", imdb='" + imdb + '\'' +
                ", rating=" + rating +
                '}';
    }
}
