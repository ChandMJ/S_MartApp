package com.example.s_martapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    String num;

    ImageView next,notification,settings;

    LinearLayout search;

    CardView book,electronic,sport,misc,music,vehicles,snacks;

    public HomeFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HomeActivity activity = (HomeActivity) getActivity();
        num = activity.sendData();

        book=view.findViewById(R.id.books);
        sport=view.findViewById(R.id.sports);
        electronic=view.findViewById(R.id.electronics);
        vehicles=view.findViewById(R.id.vehicles);
        snacks=view.findViewById(R.id.snacks);
        misc=view.findViewById(R.id.misc);
        music=view.findViewById(R.id.music);

        notification=view.findViewById(R.id.notification);
        notification.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getActivity(), NotificationActivity.class);
                startActivity(intent);
            }

        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),BrowsedCategoryActivity.class);
                i.putExtra("Phone",num);
                i.putExtra("category","Books");
                startActivity(i);
            }
        });

        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),BrowsedCategoryActivity.class);
                i.putExtra("Phone",num);
                i.putExtra("category","Sports Equipment");
                startActivity(i);
            }
        });
        electronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),BrowsedCategoryActivity.class);
                i.putExtra("Phone",num);
                i.putExtra("category","Electronics");
                startActivity(i);
            }
        });
        vehicles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),BrowsedCategoryActivity.class);
                i.putExtra("Phone",num);
                i.putExtra("category","Vehicles");
                startActivity(i);
            }
        });
        snacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getActivity(),BrowsedCategoryActivity.class);
                i.putExtra("Phone",num);
                i.putExtra("category","Snacks");
                startActivity(i);
            }
        });
        misc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getActivity(),BrowsedCategoryActivity.class);
                i.putExtra("Phone",num);
                i.putExtra("category","Miscellaneous");
                startActivity(i);
            }
        });
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),BrowsedCategoryActivity.class);
                i.putExtra("Phone",num);
                i.putExtra("category","Music Instruments");
                startActivity(i);

            }
        });

        search=view.findViewById(R.id.search_bar);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),SearchhomeActivity.class);
                i.putExtra("Phone",num);
                startActivity(i);
            }
        });

        next=view.findViewById(R.id.imageView);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),Browse_cat.class);
                i.putExtra("Phone",num);
                startActivity(i);
            }
        });

        settings=view.findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),SettingsActivity.class);
                i.putExtra("Phone",num);
                startActivity(i);
            }
        });


    }
}