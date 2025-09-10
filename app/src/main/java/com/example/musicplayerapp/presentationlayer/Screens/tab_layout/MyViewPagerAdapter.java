package com.example.musicplayerapp.presentationlayer.Screens.tab_layout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.musicplayerapp.presentationlayer.Screens.Fragments.getAllAudiosFragments;
import com.example.musicplayerapp.presentationlayer.Screens.Fragments.getAllFoldersFragment;

public class MyViewPagerAdapter  extends FragmentStateAdapter {
    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return  new getAllAudiosFragments();
            case 1:
                return new getAllFoldersFragment();
            default:
                return new getAllAudiosFragments();
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
