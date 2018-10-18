package inclass02.poorna.com.group4_hw03;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by poorn on 2/16/2018.
 */

public class GetAyncQue extends AsyncTask<String,Integer,ArrayList<Question>> {
    static int j=0;
    static int countforindex;
    BufferedReader br;
    HttpURLConnection connection;
    ArrayList<Question> qlist;


    StringBuilder sb;
    String[] qsplit;
    int indexin;
    iUrl iurl;
    GetAyncQue( iUrl iurl)
    {
        this.iurl=iurl;
    }

    @Override
    protected ArrayList<Question> doInBackground(String... strings) {
        qlist=new ArrayList<Question>();

        sb=new StringBuilder();


        try {
            URL url=new URL(strings[0]);
             connection= (HttpURLConnection) url.openConnection();
             br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
             String line="";
            String s2=null;

            while ((line=br.readLine())!=null)
            {
                Question queobj=new Question();
                ArrayList<String>  oplist=new ArrayList<String>();
                sb.append(line+"\n");
                qsplit=line.split(";");
                queobj.setIndex(Integer.parseInt(qsplit[0]));

                queobj.setQuestion(qsplit[1]);
                if(qsplit[2].startsWith("http"))
                {
                    queobj.setUrl(qsplit[2]);
                }
                else
                {
                    queobj.setUrl(null);
                }
                queobj.setAnswer(Integer.parseInt(qsplit[(qsplit.length)-1]));
                oplist.clear();
                for(int i=3;i<qsplit.length-1;i++)
                {
                    oplist.add(qsplit[i]);
                }
                queobj.setOptlist(oplist);
                Log.d("demo", "Que Object is : " + queobj.toString());
                qlist.add(queobj);
                Log.d("demo", "Array List of objects is : " + qlist.toString());
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return qlist;
    }

    @Override
    protected void onPreExecute() {
        iurl.initprog();
    }

    @Override
    protected void onPostExecute(ArrayList<Question> questions) {
        for (Question q : questions) {
            Log.d("demo", "Post Final QList is : " + q.toString());
        }
        Log.d("demo", "Final QList size is : " + questions.size());
        iurl.handlelist(questions);

    }

    @Override
    protected void onProgressUpdate(Integer... values) {

    }
    public interface iUrl
    {
        public void initprog();
        public void displprog();
        public void handlelist(ArrayList<Question> qstlist);

    }
}
