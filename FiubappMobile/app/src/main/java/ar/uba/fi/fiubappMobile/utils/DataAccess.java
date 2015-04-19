package ar.uba.fi.fiubappMobile.utils;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

import com.fiubapp.fiubapp.R;

/**
 * Created by marcelo on 4/19/15.
 */
public class DataAccess {

    public static String getUserName(Fragment fragment){
        final SharedPreferences settings = fragment.getActivity().getSharedPreferences(fragment.getResources().getString(R.string.prefs_name), 0);
        return settings.getString("username", null);
    }

    public static String getToken(Fragment fragment){
        final SharedPreferences settings = fragment.getActivity().getSharedPreferences(fragment.getResources().getString(R.string.prefs_name), 0);
        return settings.getString("token",null);
    }
}
