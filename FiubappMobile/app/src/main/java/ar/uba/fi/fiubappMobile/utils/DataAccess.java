package ar.uba.fi.fiubappMobile.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.fiubapp.fiubapp.R;

/**
 * Created by marcelo on 4/19/15.
 */
public class DataAccess{

    private Activity activity;

    public DataAccess(Activity activity) {
        this.activity = activity;
    }

    public String getUserName(){
        final SharedPreferences settings = activity.getSharedPreferences(activity.getResources().getString(R.string.prefs_name), 0);
        return settings.getString("username", null);
    }

    public String getToken(){
        final SharedPreferences settings = activity.getSharedPreferences(activity.getResources().getString(R.string.prefs_name), 0);
        return settings.getString("token",null);
    }

    public String getURLAPI(){
        final String urlAPI = activity.getResources().getString(R.string.urlAPI);
        return urlAPI;
    }
}
