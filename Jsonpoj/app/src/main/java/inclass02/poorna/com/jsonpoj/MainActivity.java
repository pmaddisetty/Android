package inclass02.poorna.com.jsonpoj;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

//import org.apache.commons.io.IOUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //new GetAsyncImgList().execute("http://api.theappsdr.com/json/");
                new GetAsyncImgList().execute("http://api.theappsdr.com/xml/");

            }
        });
    }

    public class GetAsyncImgList extends AsyncTask<String, Integer, ArrayList<Person>> {
        HttpURLConnection connection;
        String result;
        BufferedReader br;
        StringBuilder sb;
        ArrayList<Person> alist;


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected ArrayList<Person> doInBackground(String... strings) {
            try {

                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                Log.d("demo","value url="+url);
                alist = new ArrayList<>();
                   // br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                  //  sb = new StringBuilder();
                   // String line = "";
                 /*   while ((line = br.readLine()) != null) {
                        sb.append(line);
                        //alist.add(line);

                }*/
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                   // alist= PersonParser.PersonSAXParser.parsePersons(connection.getInputStream());
                    alist= PersonParser.PersonPullParser.parsePersons(connection.getInputStream());

                    Log.d("demo","list val="+alist.toString());

               /*    result = sb.toString();
                    //result= IOUtils.toString(connection.getInputStream(),"UTF-8");
                   // result="hello";
                    JSONObject root=new JSONObject(result);
                    JSONArray persons=root.getJSONArray("persons");
                    for(int i=0;i<persons.length();i++)
                    {
                                JSONObject personjson= persons.getJSONObject(i);
                                Person per=new Person();
                                per.name=personjson.getString("name");
                                per.id=personjson.getInt("id");
                                per.age=personjson.getInt("age");
                                Address adr=new Address();
                                JSONObject adressjson= personjson.getJSONObject("address");
                                adr.line1=adressjson.getString("line1");
                                adr.city=adressjson.getString("line1");
                                adr.state=adressjson.getString("line1");
                                adr.zip=adressjson.getString("line1");
                                per.address=adr;
                            alist.add(per);
                    }
                    */


                    Log.d("demo", "list size=" + alist.size());
                    Log.d("demo", "final list is =" + alist);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            catch (SAXException e) {
//                e.printStackTrace();
//            }
            catch (XmlPullParserException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return alist;
        }

        @Override
        protected void onPostExecute(ArrayList<Person> people) {
            if(people.size()>0)
            {
                Log.d("demo","result="+alist.toString());
            }
            else
            {
                Log.d("demo","result=0");
            }
        }
    }

}
