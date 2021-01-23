package com.example.schema;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;

public class MainActivity2 extends AppCompatActivity {

    private static String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button button = findViewById(R.id.button);
        TextView textView = findViewById(R.id.textView2);
        TextView searchTerm = (TextView) findViewById(R.id.searchTermBox);
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            public void onClick(View v) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            findLink(searchTerm.getText().toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                t.start(); // spawn thread
                try {
                    t.join();  // wait for thread to finish
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                textView.setTextColor(Color.BLUE);
                textView.setText(link);
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.activity_main:
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    break;
                case R.id.thirdFragment:
                    Intent d = new Intent(getApplicationContext(), MainActivity3.class);
                    startActivity(d);
                    break;
            }
            return true;
        });
    }

    private void findLink(String searchTerm) throws IOException {
        Document document = Jsoup.parse(new URL("https://schema.hkr.se/ajax/ajax_sokResurser.jsp?sokord=" + searchTerm + "&startDatum=idag&slutDatum=&intervallTyp=m&intervallAntal=6"), 5000);

        Elements whiteRows = document.select("tbody > tr > td:nth-child(2) > a");
        Elements courseName = document.select("body > ul:nth-child(6) > li > a > table > tbody > tr > td > b");
        System.out.println("nice" + courseName);
        link = String.valueOf(whiteRows);
        link = link.substring(link.indexOf("href=") + 6, link.indexOf(">")).replace('"', ' ');
        link = link.replace("&amp;", "&");
        Singleton.setCourseScheduleLink(link);
        Context context = getApplicationContext();
        writeToFile(link, context);
    }

    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("schemaData.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}