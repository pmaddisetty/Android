package inclass02.poorna.com.threadinterfacing;

import android.os.AsyncTask;

import java.util.LinkedList;

/**
 * Created by poorn on 2/9/2018.
 */
public class GetTweetAsync extends AsyncTask<String,Void,LinkedList<String>>{

    LinkedList<String> tweets;
    iData idata;
//    MainActivity activity;
//    public GetTweetAsync(MainActivity activity) {
//        this.activity = activity;
//    }

    public GetTweetAsync(iData idata) {
        this.idata = idata;
    }

    @Override
    protected LinkedList<String> doInBackground(String... strings) {
        tweets = new LinkedList<>();
        for(int i=0;i<10;i++)
        {
            tweets.add("Tweet"+i);
        }

        return tweets;
    }

    @Override
    protected void onPostExecute(LinkedList<String> strings) {
        //activity.handledata(tweets);
        idata.handlelistDatainInterfaces(tweets);

    }

    public static interface iData{
        public void handlelistDatainInterfaces(LinkedList<String> strings);

    }
}
