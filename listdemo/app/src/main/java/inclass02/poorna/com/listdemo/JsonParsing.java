package inclass02.poorna.com.listdemo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by poorn on 3/12/2018.
 */

public class JsonParsing {
    static public class JsonParser{
        static ArrayList<MovieItem> items(String res) throws JSONException {
            ArrayList<MovieItem> alist = new ArrayList<>();
            String posterpath;
            JSONObject root = new JSONObject(res);
            JSONArray result = root.getJSONArray("results");
            for (int i = 0; i < result.length(); i++) {
                JSONObject moviejson = result.getJSONObject(i);
                MovieItem item = new MovieItem();
                item.setTitle(moviejson.getString("title"));
                item.setDate(moviejson.getString("release_date"));
                item.setOverview(moviejson.getString("overview"));
                item.setPopularity(Double.parseDouble(moviejson.getString("popularity")));
                item.setRating(Double.parseDouble(moviejson.getString("vote_average")));
                posterpath=moviejson.getString("poster_path");
                item.setImageurl("http://image.tmdb.org/t/p/w154/"+posterpath);
                alist.add(item);

            }
            return alist;
        }
    }
}
