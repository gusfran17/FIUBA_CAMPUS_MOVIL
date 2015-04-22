package ar.uba.fi.fiubappMobile.partners;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.fiubapp.fiubapp.R;
import com.fiubapp.fiubapp.TabPageIndicator;


public class StudentSearchTabs extends FragmentActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.student_search_tabs);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager_student_search);
        viewPager.setOffscreenPageLimit(4);

        // Set the ViewPagerAdapter into ViewPager
        StudentSearchTabsAdapter studentSearchTabsAdapter = new StudentSearchTabsAdapter(getSupportFragmentManager());
        viewPager.setAdapter(studentSearchTabsAdapter);

        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator_student_search);
        indicator.setTypeFace(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Roboto-Bold.ttf"));
        indicator.setViewPager(viewPager);
    }


}
