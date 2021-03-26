package com.example.s_martapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdatePassword extends AppCompatActivity {

    ImageView back;
    String num;
    Button save;
    TextView pass,cpass,npass;

    //firebase
    FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    FirebaseDatabase database= FirebaseDatabase.getInstance();
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        Intent intent=getIntent();
        num=intent.getStringExtra("Phone");

        pass=findViewById(R.id.name);
        cpass=findViewById(R.id.phone);
        npass=findViewById(R.id.email);

        ref=database.getReference("User").child(num);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    System.out.println(dataSnapshot1.getKey());
                    if(dataSnapshot1.getKey().equals("password"))
                    {
                        pass.setText(dataSnapshot1.getValue().toString());

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        back=findViewById(R.id.imageView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        save=findViewById(R.id.login);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(cpass.getText().toString().isEmpty() || npass.getText().toString().isEmpty())) {
                    if (pass.getText().toString().equals(cpass.getText().toString())) {

                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    System.out.println(dataSnapshot1.getKey());
                                    if (dataSnapshot1.getKey().equals("password")) {
                                        ref.child(dataSnapshot1.getKey()).setValue(npass.getText().toString());
                                    }
                                }
                                Toast.makeText(UpdatePassword.this, "Password Update Successful!", Toast.LENGTH_LONG).show();
                                onBackPressed();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    } else {
                        Toast.makeText(UpdatePassword.this, "Current password is wrong!!!", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(UpdatePassword.this,"Fill the details",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}