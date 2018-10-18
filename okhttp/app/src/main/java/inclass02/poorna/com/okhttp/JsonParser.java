package inclass02.poorna.com.okhttp;

import java.util.ArrayList;

/**
 * Created by poorn on 4/2/2018.
 */

public class JsonParser {
    ArrayList<item> itemobj;

    public static class item
    {
        ArrayList<urlencoded> urlobj;

        public ArrayList<urlencoded> getUrlobj() {
            return urlobj;
        }

        public void setUrlobj(ArrayList<urlencoded> urlobj) {
            this.urlobj = urlobj;
        }
    }
    public static class urlencoded
    {
        String key, value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public ArrayList<item> getItemobj() {
        return itemobj;
    }

    public void setItemobj(ArrayList<item> itemobj) {
        this.itemobj = itemobj;
    }
}
