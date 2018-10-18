package inclass02.poorna.com.xmldemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by poorn on 2/19/2018.
 */

public class GetAsyncImage  extends AsyncTask<String,Integer,Bitmap> {

    ImageView imgv;

    HttpURLConnection connection = null;
    Bitmap bitmap = null;
    iImage intimg;
    int idforn;

    public GetAsyncImage(ImageView imageView,iImage inter,int id) {
        imgv = imageView;
        intimg=inter;
        idforn=id;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Bitmap doInBackground(String... strings) {

        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            // connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                publishProgress();
                bitmap = BitmapFactory.decodeStream(connection.getInputStream());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        intimg.handleImage(bitmap,idforn);

    }

    public static interface iImage{
        public void handleImage(Bitmap bitmap, int idfor);

    }
}
