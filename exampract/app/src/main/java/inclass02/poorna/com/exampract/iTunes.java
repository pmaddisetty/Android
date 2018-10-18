package inclass02.poorna.com.exampract;

import java.io.Serializable;

/**
 * Created by poorn on 3/12/2018.
 */

public class iTunes implements Serializable{
    String appname;
    double price;
    String imageurl;
    int thumbnail;

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public int getThumbnail() {
        Double priceval=this.getPrice();

        if(priceval>=0&&priceval<=1.99)
        {
           setThumbnail(1);
        }
        else if (priceval>=2&&priceval<=5.99)
        {
           setThumbnail(2);
        }
        else if (priceval>=6)
        {
            setThumbnail(3);
        }
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "iTunes{" +
                "appname='" + appname + '\'' +
                ", price=" + price +
                ", imageurl='" + imageurl + '\'' +
                ", thumbnail=" + thumbnail +
                '}';
    }
}
