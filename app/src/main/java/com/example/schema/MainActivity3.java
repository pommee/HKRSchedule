package com.example.schema;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.activity_main:
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                    break;
                case R.id.secondFragment:
                    Intent d = new Intent(getApplicationContext(),MainActivity2.class);
                    startActivity(d);
                    break;
            }
            return true;
        });
    }
}