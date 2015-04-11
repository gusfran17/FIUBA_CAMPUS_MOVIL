package com.fiubapp.fiubapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class Principal extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.icon_page);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        // Set the ViewPagerAdapter into ViewPager
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setTypeFace(Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/Roboto-Bold.ttf"));
        indicator.setViewPager(viewPager);
    }

}