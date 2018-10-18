package inclass02.poorna.com.group4_inclass11;

import java.util.ArrayList;

/**
 * Created by poorn on 4/9/2018.
 */

public class GalleryResponse {
    public ArrayList<Galleryimage> images;

    public static class Galleryimage
    {
        String title;
        public ArrayList<Displaysize> display_sizes;


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public ArrayList<Displaysize> getDisplay_sizes() {
            return display_sizes;
        }

        public void setDisplay_sizes(ArrayList<Displaysize> display_sizes) {
            this.display_sizes = display_sizes;
        }

        @Override
        public String toString() {
            return "Galleryimage{" +
                    "title='" + title + '\'' +
                    ", display_sizes=" + display_sizes +
                    '}';
        }
    }
    public static class Displaysize{
        String uri;

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        @Override
        public String toString() {
            return "Displaysize{" +
                    "uri='" + uri + '\'' +
                    '}';
        }
    }

    public ArrayList<Galleryimage> getImages() {
        return images;
    }

    public void setImages(ArrayList<Galleryimage> images) {
        this.images = images;

    }

    @Override
    public String toString() {
        return "GalleryResponse{" +
                "images=" + images +
                '}';
    }
}
