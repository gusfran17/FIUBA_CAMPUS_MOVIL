package com.fiubapp.fiubapp;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PerfilTabsCompanero extends FragmentActivity {

    private Alumno companero = null;

    public TextView getText() {
        return text;
    }

    public void setText(String msg) {
        this.text = (TextView)findViewById(R.id.profile_name);
        this.text.setText(msg);
    }

    private TextView text = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();

        companero = new Alumno();
        companero.setUsername(i.getStringExtra("userName"));
        companero.setNombre(i.getStringExtra("name"));
        companero.setApellido(i.getStringExtra("lastName"));
        companero.setComentario(i.getStringExtra("comments"));
        companero.setIsMyMate(i.getBooleanExtra("isMyMate",false));

        setContentView(R.layout.perfil_tabs);

        text = (TextView)findViewById(R.id.profile_name);
        text.setText(companero.getNombre()+" "+companero.getApellido());

        ImageView imageBack = (ImageView)findViewById(R.id.back);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager_perfil);
        viewPager.setOffscreenPageLimit(4);

        // Set the ViewPagerAdapter into ViewPager
        PerfilTabsCompaneroAdapter perfilTabsCompaneroAdapter =
                new PerfilTabsCompaneroAdapter(getSupportFragmentManager(), companero);
        viewPager.setAdapter(perfilTabsCompaneroAdapter);

        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator_perfil);
        indicator.setTypeFace(Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/Roboto-Bold.ttf"));
        indicator.setViewPager(viewPager);
    }

    public void borrarCarrera(){
        Fragment fragment = getFragmentManager().findFragmentById(R.id.pager_perfil);
    }
}