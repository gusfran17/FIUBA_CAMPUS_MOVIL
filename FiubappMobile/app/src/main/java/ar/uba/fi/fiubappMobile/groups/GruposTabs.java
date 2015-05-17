package ar.uba.fi.fiubappMobile.groups;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.fiubapp.fiubapp.R;
import com.fiubapp.fiubapp.TabPageIndicator;

public class GruposTabs extends FragmentActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.grupo_tabs);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager_grupos);
        viewPager.setOffscreenPageLimit(4);

        ImageView imageBack = (ImageView)findViewById(R.id.back_search);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Set the ViewPagerAdapter into ViewPager
        GruposTabsAdapter gruposTabsAdapter = new GruposTabsAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(gruposTabsAdapter);

        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator_grupos);
        indicator.setTypeFace(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Roboto-Bold.ttf"));
        indicator.setViewPager(viewPager);
    }
}
