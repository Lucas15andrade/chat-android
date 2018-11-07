package com.andradecoder.chat.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.andradecoder.chat.R;
import com.andradecoder.chat.adapter.TabsPageAdapter;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

//Trabalho desenvolvido por @AndradeCoder na disciplina de Programação para dispositivos móveis
public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private FirebaseDatabase mFirebase;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbarChat);
        setSupportActionBar(toolbar);

        ViewPager viewPager = findViewById(R.id.viewPager);
        PagerAdapter page = new TabsPageAdapter(getSupportFragmentManager());

        viewPager.setAdapter(page);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_camera_alt_background_24dp);
        //tabLayout.getTabAt(1).getText().c


        //Autenticação com o Firebase
        mFirebase = FirebaseDatabase.getInstance();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.sair:
                Log.i("TESTE","Clicou em sair!");
                AuthUI.getInstance().signOut(this);
                return true;
            default:{
                return super.onOptionsItemSelected(item);
            }
        }
    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if(mAuthStateListener != null)
//            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
//    }
}
