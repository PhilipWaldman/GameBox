package com.gamebox.ui.demos;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.gamebox.R;
import com.gamebox.ui.demos.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class PiEstimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pi_estimation);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.pi_view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.pi_tabs);
        tabs.setupWithViewPager(viewPager);
    }
}