package com.fiubapp.fiubapp;

import com.viewpagerindicator.TabPageIndicator;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText padron = null;
    private TextView tvPadron = null;
    private EditText clave = null;
    private CheckBox soyDeIntercambio = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from activity_main.xml
        setContentView(R.layout.login);

        padron = (EditText)findViewById(R.id.editTextPadron);
        clave = (EditText)findViewById(R.id.editTextClave);
        soyDeIntercambio = (CheckBox)findViewById(R.id.checkBoxIntercambio);
        tvPadron = (TextView)findViewById(R.id.textViewNumeroPadron);
        /*
        // Locate the viewpager in activity_main.xml
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        // Set the ViewPagerAdapter into ViewPager
        ViewPagerAdapter vAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(vAdapter);

        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
        */
    }

    public void login(View view) {
        if (padron.getText().toString().equals("admin") &&
            clave.getText().toString().equals("admin")) {
            Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
        }
    }

    public void soyDeIntercambio(View view) {

        String text = getString(R.string.numeroPadron);

        if(soyDeIntercambio.isChecked())
            text = getString(R.string.pasaporte);

        tvPadron.setText(text);
    }


}