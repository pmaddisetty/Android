package inclass02.poorna.com.inclass09_group4;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static final int LOCATION_SET = 300;
    String res;
    ArrayList latlist;
    ArrayList<AppResponse.Points> points;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        InputStream inputStream= getResources().openRawResource(R.raw.trip);
        InputStreamReader inputStreamReader=new InputStreamReader(inputStream);

        BufferedReader br=new BufferedReader(inputStreamReader);
        StringBuffer sb=new StringBuffer();
        String line="";
        try {
            while ((line=br.readLine())!=null)
            {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        res=sb.toString();
        Gson gson=new Gson();
        AppResponse appResponse=gson.fromJson(res,AppResponse.class);
        points=appResponse.getPoints();
        title=appResponse.getTitle();
        Log.d("demo","points  list="+points);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_SET);
//            return;
//        }
     //   mMap.setMyLocationEnabled(true);

        MarkerOptions markerOptions=new MarkerOptions();

//        if(points!=null)
//        {
//            LatLng latLng=new LatLng(points.get(0).getLatitude(),points.get(1).getLongitude());
//            markerOptions.position(latLng);
//            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//            mMap.addMarker(markerOptions);
//            LatLng latLng2=new LatLng(points.get(points.size()-1).getLatitude(),points.get(points.size()-1).getLongitude());
//            markerOptions.position(latLng2);
//            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//            mMap.addMarker(markerOptions);
//
//        }
        if(points!=null)
        {
             latlist=new ArrayList();
            MarkerOptions markerOptions2=new MarkerOptions();
            PolylineOptions polylineOptions=new PolylineOptions();
            for(int i=0;i<points.size();i++)
            {
                LatLng latLng=new LatLng(points.get(i).getLatitude(),points.get(i).getLongitude());
                Log.d("demo","latlang cocord="+latLng);
                latlist.add(new LatLng(points.get(i).getLatitude(),points.get(i).getLongitude()));
                markerOptions2.position(latLng);
                if(i==0)
                {
                    mMap.addMarker(markerOptions2.title("Start Location"));
                      }
                if(i==points.size()-1)
                {
                    mMap.addMarker(markerOptions2.title("End Location"));
                }

               // mMap.addMarker(markerOptions2);
                Log.d("demo","latlist=="+latlist);
            }
          // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(points.get(0).getLatitude(), points.get(0).getLongitude()), 12.0f));



            polylineOptions.addAll(latlist);
            polylineOptions.color(Color.BLUE);
            polylineOptions.width(15);
            polylineOptions.geodesic(true);
            mMap.addPolyline(polylineOptions);



            mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for(int j=0;j<latlist.size();j++){
                        builder.include(new LatLng(points.get(j).getLatitude(),points.get(j).getLongitude()));
                    }
                    LatLngBounds bounds = builder.build();
                    Log.d("demo", "onMapReady: "+bounds);
                    CameraUpdate cu= CameraUpdateFactory.newLatLngBounds(bounds,10);
                    mMap.animateCamera(cu);
                }
            });
        }


        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
