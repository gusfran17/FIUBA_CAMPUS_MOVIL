package ar.uba.fi.fiubappMobile.partners;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fiubapp.fiubapp.R;

/**
 * Created by Gustavo.Franco on 21/04/2015.
 */
public class RegularSearch extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.regular_search, container, false);
        return view;
    }
}
