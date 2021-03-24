package com.example.s_martapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    TextView change_prof,chang_pass,chan_col;
    String num;
    TextView name,mob,email,branch,sem,hostel,pass;

    //firebase
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference ref;


    public ProfileFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        return inflater.inflate(R.layout.fargment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HomeActivity activity = (HomeActivity) getActivity();
        num = activity.sendData();

        name=view.findViewById(R.id.textView153);
        mob=view.findViewById(R.id.numb);
        email=view.findViewById(R.id.mail);

        branch=view.findViewById(R.id.branch);
        sem=view.findViewById(R.id.sem);
        hostel=view.findViewById(R.id.hostel);

        pass=view.findViewById(R.id.pass);

        chan_col=view.findViewById(R.id.change_address);
        chang_pass=view.findViewById(R.id.change_pass);
        change_prof=view.findViewById(R.id.change_number);

        ref=database.getReference("User").child(num);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    System.out.println(dataSnapshot1.getKey());
                    if(dataSnapshot1.getKey().equals("Name"))
                    {
                        name.setText(dataSnapshot1.getValue().toString());
                    }
                    if(dataSnapshot1.getKey().equals("Mobile"))
                    {
                        mob.setText(dataSnapshot1.getValue().toString());
                    }
                    if(dataSnapshot1.getKey().equals("Email"))
                    {
                        email.setText(dataSnapshot1.getValue().toString());
                    }
                    if(dataSnapshot1.getKey().equals("Password"))
                    {
                        pass.setText(dataSnapshot1.getValue().toString());
                    }
                    if(dataSnapshot1.getKey().equals("Semester")){
                        sem.setText((dataSnapshot1.getValue().toString()));
                    }
                    if(dataSnapshot1.getKey().equals("Branch")){
                        branch.setText((dataSnapshot1.getValue().toString()));
                    }
                    if(dataSnapshot1.getKey().equals("Hostel")){
                        hostel.setText((dataSnapshot1.getValue().toString()));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        change_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), UpdateProfile.class);
                i.putExtra("Phone",num);
                startActivity(i);
            }
        });

        chang_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), UpdatePassword.class);
                i.putExtra("Phone",num);
                startActivity(i);
            }
        });
        chan_col.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), ChangeCol.class);
                i.putExtra("Phone",num);
                startActivity(i);
            }
        });

    }
}
