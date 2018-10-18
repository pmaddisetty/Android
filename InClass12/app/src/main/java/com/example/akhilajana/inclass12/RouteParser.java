package com.example.akhilajana.inclass12;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by akhilajana on 11/27/17.
 */

class RouteParser {

    /** Receives a JSONObject and returns a list of lists containing latitude and longitude */
    public List<List<HashMap<String,String>>> parse(JSONObject jObject){

        List<List<HashMap<String, String>>> routes = new ArrayList<>() ;
        JSONArray jsonRoutesArray;
        JSONArray jLegs;
        JSONArray jsonStepsArray;

        try {

            jsonRoutesArray = jObject.getJSONArray("routes");

            for(int i=0;i<jsonRoutesArray.length();i++)
            {
                jLegs = ( (JSONObject)jsonRoutesArray.get(i)).getJSONArray("legs");
                List pathList = new ArrayList<>();

                for(int j=0;j<jLegs.length();j++)
                {
                    jsonStepsArray = ( (JSONObject)jLegs.get(j)).getJSONArray("steps");

                    for(int k=0;k<jsonStepsArray.length();k++)
                    {
                        String polyline = "";
                        polyline = (String)((JSONObject)((JSONObject)jsonStepsArray.get(k)).get("polyline")).get("points");
                        List<LatLng> latLngList = decodePolyline(polyline);

                        for(int l=0;l<latLngList.size();l++)
                        {
                            HashMap<String, String> latlngMap = new HashMap<>();
                            latlngMap.put("lat", Double.toString((latLngList.get(l)).latitude) );
                            latlngMap.put("lng", Double.toString((latLngList.get(l)).longitude) );
                            pathList.add(latlngMap);
                        }
                    }
                    routes.add(pathList);
                }
            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
        }
        return routes;
    }

    private List<LatLng> decodePolyline(String encodedPolyline) {

        List<LatLng> decodedPolylineList = new ArrayList<>();
        int index = 0, encodedPolyLength = encodedPolyline.length();
        int lat = 0, lng = 0;

        while (index < encodedPolyLength)
        {
            int val, change = 0, res = 0;
            do
            {
                val = encodedPolyline.charAt(index++) - 63;
                res |= (val & 0x1f) << change;
                change += 5;
            } while (val >= 0x20);

            int dlat = ((res & 1) != 0 ? ~(res >> 1) : (res >> 1));
            lat += dlat;

            change = 0;
            res = 0;
            do {
                val = encodedPolyline.charAt(index++) - 63;
                res |= (val & 0x1f) << change;
                change += 5;
            } while (val >= 0x20);

            int dlng = ((res & 1) != 0 ? ~(res >> 1) : (res >> 1));
            lng += dlng;

            LatLng poly = new LatLng((((double) lat / 1E5)), (((double) lng / 1E5)));
            decodedPolylineList.add(poly);
        }

        return decodedPolylineList;
    }
}
