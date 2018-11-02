package com.andradecoder.chat.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.andradecoder.chat.R;
import com.andradecoder.chat.adapter.TabsPageAdapter;

//Trabalho desenvolvido por @AndradeCoder na disciplina de Programação para dispositivos móveis
public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.viewPager);
        PagerAdapter page = new TabsPageAdapter(getSupportFragmentManager());

        viewPager.setAdapter(page);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_camera_alt_background_24dp);
        //tabLayout.getTabAt(1).getText().c

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if(i == 0){
                    tabLayout.getTabAt(0).setIcon(R.drawable.ic_camera_alt_background_24dp);
                }else{
                    tabLayout.getTabAt(0).setIcon(R.drawable.ic_camera_alt_black_24dp);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }
}
