package inclass02.poorna.com.xmldemo;

/**
 * Created by poorn on 2/19/2018.
 */

public class Media {
    int height;
    int width;
    String medium;
    String url;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Media{" +
                "height=" + height +
                ", width=" + width +
                ", medium='" + medium + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
