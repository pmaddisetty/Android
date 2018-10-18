package inclass02.poorna.com.group4_inclass10;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by poorn on 4/10/2018.
 */

public class SharedClass extends AppCompatActivity {

    public void saveToken(String key,String val)
    {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.pref), MODE_PRIVATE);
        SharedPreferences.Editor e=sharedPreferences.edit();
        e.putString(key,val);
        e.commit();
    }

    public String getToken(String key)
    {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.pref), MODE_PRIVATE);
        String val=sharedPreferences.getString(key,"");
        Log.d("demo","retunred val"+val);
        return val;
    }

    public void deleteToken(String tokenval)
    {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.pref), MODE_PRIVATE);
        SharedPreferences.Editor e=sharedPreferences.edit();
        e.clear();
        e.commit();
    }
}
