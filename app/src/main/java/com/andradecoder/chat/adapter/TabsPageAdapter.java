package com.andradecoder.chat.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.andradecoder.chat.MapaFragment;
import com.andradecoder.chat.fragment.CameraFragment;
import com.andradecoder.chat.fragment.ChatFragment;

public class TabsPageAdapter extends FragmentPagerAdapter {

    int x = 1;
    boolean primeiraVez = true;

    public TabsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

//        if(primeiraVez)
//            primeiraVez = false;
//        else
//            x = i;

        switch (i){
            case 0:
                return new CameraFragment();

            case 1:
                return new ChatFragment();

            case 2:
                return new MapaFragment();
                default:
                    return null;

        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return "";

            case 1:
                return "Chat";

            case 2:
                return "Mapa";
            default:
                return null;

        }
        //return super.getPageTitle(position);
    }
}
