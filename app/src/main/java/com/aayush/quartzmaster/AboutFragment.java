package com.aayush.quartzmaster;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class AboutFragment extends Fragment {
    Context context;

    public AboutFragment() {}

    public static AboutFragment getInstance(Context context) {
        AboutFragment aboutFragment = new AboutFragment();
        aboutFragment.context = context;
        return aboutFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
