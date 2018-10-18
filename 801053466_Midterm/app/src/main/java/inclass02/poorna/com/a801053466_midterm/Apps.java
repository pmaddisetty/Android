package inclass02.poorna.com.a801053466_midterm;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by poorn on 3/12/2018.
 */

public class Apps implements Serializable {
    String artist,appname,releasedate;
    ArrayList<String> genlist= new ArrayList<>();
    String imgurl;
    String copyright;
    ArrayList<String> forlist= new ArrayList<>();

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public ArrayList<String> getGenlist() {
        return genlist;
    }

    public void setGenlist(ArrayList<String> genlist) {
        this.genlist = genlist;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public ArrayList<String> getForlist() {
        return forlist;
    }

    public void setForlist(ArrayList<String> forlist) {
        this.forlist = forlist;
    }

    @Override
    public String toString() {
        return "Apps{" +
                "artist='" + artist + '\'' +
                ", appname='" + appname + '\'' +
                ", releasedate='" + releasedate + '\'' +
                ", genlist=" + genlist +
                ", imgurl='" + imgurl + '\'' +
                ", copyright='" + copyright + '\'' +
                ", forlist=" + forlist +
                '}';
    }
}
