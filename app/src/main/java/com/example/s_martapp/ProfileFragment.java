package com.example.s_martapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {

    TextView change_prof, chang_pass, chan_col;
    String num;
    TextView name, mob, email, branch, sem, hostel, pass;

    //firebase
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref;

    SharedPreferences sharedPreferences;

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
        assert activity != null;
        num = activity.sendData();

        sharedPreferences = getActivity().getSharedPreferences("login", MODE_PRIVATE);

        name = view.findViewById(R.id.textView153);
        mob = view.findViewById(R.id.numb);
        email = view.findViewById(R.id.mail);


        branch = view.findViewById(R.id.branch);
        sem = view.findViewById(R.id.sem);
        hostel = view.findViewById(R.id.hostel);

        pass = view.findViewById(R.id.pass);

        chan_col = view.findViewById(R.id.change_address);
        chang_pass = view.findViewById(R.id.change_pass);
        change_prof = view.findViewById(R.id.change_number);

        ref = database.getReference("User").child(num);

        ref.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    System.out.println(dataSnapshot1.getKey());
                    if (Objects.equals(dataSnapshot1.getKey(), "name")) {
                        name.setText(Objects.requireNonNull(dataSnapshot1.getValue()).toString());
                    }
                    if (Objects.equals(dataSnapshot1.getKey(), "mobile")) {
                        mob.setText(Objects.requireNonNull(dataSnapshot1.getValue()).toString());
                    }
                    if (dataSnapshot1.getKey().equals("email")) {
                        email.setText(Objects.requireNonNull(dataSnapshot1.getValue()).toString());
                    }
                    if (dataSnapshot1.getKey().equals("password")) {
                        pass.setText(Objects.requireNonNull(dataSnapshot1.getValue()).toString());
                    }
                    if (dataSnapshot1.getKey().equals("semester")) {
                        sem.setText("Semester: " + (Objects.requireNonNull(dataSnapshot1.getValue()).toString()));
                    }
                    if (dataSnapshot1.getKey().equals("branch")) {
                        branch.setText("Branch: " + (Objects.requireNonNull(dataSnapshot1.getValue()).toString()));
                    }
                    if (dataSnapshot1.getKey().equals("hostel")) {
                        hostel.setText("Hostel: " + (Objects.requireNonNull(dataSnapshot1.getValue()).toString()));
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
                Intent i = new Intent(getActivity(), UpdateProfile.class);
                i.putExtra("Phone", num);
                startActivity(i);
            }
        });

        chang_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), UpdatePassword.class);
                i.putExtra("Phone", num);
                startActivity(i);
            }
        });
        chan_col.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ChangeCol.class);
                i.putExtra("Phone", num);
                startActivity(i);
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.settings_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            SharedPreferences.Editor editor = requireActivity().getApplication().getSharedPreferences("login", MODE_PRIVATE).edit();
            editor.putBoolean("Flag", false);
            editor.apply();
            Intent intent3 = new Intent(getActivity(), MainActivity.class);
            startActivity(intent3);
            getActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
