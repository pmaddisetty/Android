package com.example.akhilajana.inclass12;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static GoogleMap googleMap;
    ArrayList<LatLng> latLngArrayList;
    GoogleApiClient mGoogleApiClient;
    Location location;
    Marker mCurrentLocationMark;
    LocationRequest locationRequest;
    LocationManager locationManager;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 909;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkPermissions();
        }

        latLngArrayList = new ArrayList<>();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
            builder.setTitle("Enable GPS!");
            builder.setMessage("Do you want to enable GPS?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int k)
                {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);

                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    dialogInterface.cancel();
                    finish();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else
        {

        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        this.googleMap = googleMap;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                buildGoogleApiClient();
                this.googleMap.setMyLocationEnabled(true);
            }
        }
        else
        {
            buildGoogleApiClient();
            this.googleMap.setMyLocationEnabled(true);
        }

        this.googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng point) {

                if (latLngArrayList.size() > 1)
                {
                    latLngArrayList.clear();
                    MapsActivity.this.googleMap.clear();
                }

                latLngArrayList.add(point);

                MarkerOptions onMapClick = new MarkerOptions();
                onMapClick.position(point);


                if (latLngArrayList.size() == 1)
                {
                    onMapClick.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    Toast.makeText(MapsActivity.this,"Tracking has began",Toast.LENGTH_SHORT).show();
                }
                else if (latLngArrayList.size() == 2)
                {
                    onMapClick.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    Toast.makeText(MapsActivity.this,"Tracking ended",Toast.LENGTH_SHORT).show();
                }

                MapsActivity.this.googleMap.addMarker(onMapClick);

                if (latLngArrayList.size() >= 2)
                {
                    LatLng mStart = latLngArrayList.get(0);
                    LatLng mEnd = latLngArrayList.get(1);

                    String mapClickedDet = getLatLngDetailsFromMap(mStart, mEnd);

                    GetDownloadedData data = new GetDownloadedData();

                    data.execute(mapClickedDet);
                    MapsActivity.this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(mStart));
                    MapsActivity.this.googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                }

            }
        });

    }

    private String getLatLngDetailsFromMap(LatLng start, LatLng end)
    {

        String begin = "origin=" + start.latitude + "," + start.longitude;
        String finalDest = "destination=" + end.latitude + "," + end.longitude;

        String parameters = begin + "&" + finalDest + "&" + "sensor=false";
        String latLngUrl = "https://maps.googleapis.com/maps/api/directions/json?" + parameters;

        return latLngUrl;
    }


    static class ParseGoogleLocations extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData)
        {

            JSONObject jsonObject;
            List<List<HashMap<String, String>>> routesList = null;

            try {
                jsonObject = new JSONObject(jsonData[0]);
                RouteParser routeParser = new RouteParser();

                routesList = routeParser.parse(jsonObject);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return routesList;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> routesList)
        {
            ArrayList<LatLng> latlngPointsList;
            PolylineOptions polylineOptions = null;

            for (int i = 0; i < routesList.size(); i++)
            {
                latlngPointsList = new ArrayList<>();
                polylineOptions = new PolylineOptions();

                List<HashMap<String, String>> currentRoute = routesList.get(i);

                for (int j = 0; j < currentRoute.size(); j++)
                {
                    HashMap<String, String> currentPoint = currentRoute.get(j);

                    double lat = Double.parseDouble(currentPoint.get("lat"));
                    double lng = Double.parseDouble(currentPoint.get("lng"));

                    LatLng positionSelected = new LatLng(lat, lng);
                    latlngPointsList.add(positionSelected);
                }

                polylineOptions.addAll(latlngPointsList);
                polylineOptions.width(12);
                polylineOptions.color(Color.BLUE);
            }

            if(polylineOptions != null)
            {
                googleMap.addPolyline(polylineOptions);
            }
            else
            {
                Log.d("onPostExecute","No Polylines exist");
            }
        }
    }

    protected synchronized void buildGoogleApiClient()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) 
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);


    }

    @Override
    public void onConnectionSuspended(int i)
    {
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {
    }

    @Override
    public void onLocationChanged(Location location)
    {

        this.location = location;
        if (mCurrentLocationMark != null)
        {
            mCurrentLocationMark.remove();
        }

        LatLng currentlatLngMark = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions options = new MarkerOptions();
        options.position(currentlatLngMark);
        options.title("My current location");
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
        mCurrentLocationMark = googleMap.addMarker(options);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentlatLngMark));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        if (mGoogleApiClient != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    public boolean checkPermissions()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_LOCATION:
                {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null)
                        {
                            buildGoogleApiClient();
                        }
                        googleMap.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_LONG).show();
                }
                return;
            }

        }
    }

}