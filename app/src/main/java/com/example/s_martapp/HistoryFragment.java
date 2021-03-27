package com.example.s_martapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class HistoryFragment extends Fragment {

    String num;
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public HistoryFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HomeActivity activity = (HomeActivity) getActivity();
        num = activity.sendData();

        viewPager = view.findViewById(R.id.viewPager);

        viewPager.setOffscreenPageLimit(2);

        tabLayout = (TabLayout)view.findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new CallHisFragment(), "Call History");
        adapter.addFragment(new MyAdHisFragment(), "My Ads History");
        //adapter.addFragment(new Tab3Fragment(), "Tab 3");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    public String sendData() {
        return num;
    }
}
