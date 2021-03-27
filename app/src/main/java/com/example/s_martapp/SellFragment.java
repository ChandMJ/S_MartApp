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

public class SellFragment extends Fragment {

    String num;

    CardView book, electronic, sport, misc, music, vehicles, snacks;

    public SellFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sell, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HomeActivity activity = (HomeActivity) getActivity();
        num = activity.sendData();

        book = view.findViewById(R.id.books);
        sport = view.findViewById(R.id.sport);
        electronic = view.findViewById(R.id.electronics);
        vehicles = view.findViewById(R.id.vehicle);
        snacks = view.findViewById(R.id.snacks);
        misc = view.findViewById(R.id.misc);
        music = view.findViewById(R.id.music);

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ItemdetailsActivity.class);
                i.putExtra("Phone", num);
                i.putExtra("category", "Books");
                startActivity(i);
            }
        });

        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ItemdetailsActivity.class);
                i.putExtra("Phone", num);
                i.putExtra("category", "Sports Equipment");
                startActivity(i);
            }
        });
        electronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ItemdetailsActivity.class);
                i.putExtra("Phone", num);
                i.putExtra("category", "Electronics");
                startActivity(i);
            }
        });
        vehicles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ItemdetailsActivity.class);
                i.putExtra("Phone", num);
                i.putExtra("category", "Vehicles");
                startActivity(i);
            }
        });
        snacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), ItemdetailsActivity.class);
                i.putExtra("Phone", num);
                i.putExtra("category", "Snacks");
                startActivity(i);
            }
        });
        misc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), ItemdetailsActivity.class);
                i.putExtra("Phone", num);
                i.putExtra("category", "Miscellaneous");
                startActivity(i);
            }
        });
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ItemdetailsActivity.class);
                i.putExtra("Phone", num);
                i.putExtra("category", "Music Instruments");
                startActivity(i);

            }
        });


    }
}
