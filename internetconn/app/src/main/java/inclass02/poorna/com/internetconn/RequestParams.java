package inclass02.poorna.com.internetconn;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by poorn on 2/9/2018.
 */

public class RequestParams {
    private HashMap<String,String> params;
    private StringBuilder stringBuilder;

    public RequestParams() {
        params=new HashMap<>();
        stringBuilder=new StringBuilder();
    }
    public RequestParams addParams(String key,String value) {
        try {
            params.put(key, URLEncoder.encode(value, "UTF-8"));
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return this;
    }
    public String getEncodedParams(){
        for (String key: params.keySet()) {
            if(stringBuilder.length()>0)
            {
                stringBuilder.append("&");
            }
            stringBuilder.append(key+"="+params.get(key));
        }
        return stringBuilder.toString();
    }
    public String getEncodedUrl(String url)
    {
        return url+"?"+getEncodedParams();
    }
    public void encodebody(HttpURLConnection connection)
    {
        try {
            OutputStreamWriter outstream=new OutputStreamWriter(connection.getOutputStream());
            outstream.write(getEncodedParams());
            outstream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
