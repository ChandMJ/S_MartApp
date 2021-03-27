package com.example.s_martapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    Button logout;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);

        logout=findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                SharedPreferences.Editor editor= getApplication().getSharedPreferences("login",MODE_PRIVATE).edit();
                editor.putBoolean("Flag",false);
                editor.commit();
                Intent intent3=new Intent(SettingsActivity.this,MainActivity.class);
                startActivity(intent3);
               finish();
            }
        });
    }
}