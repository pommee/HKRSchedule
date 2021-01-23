package com.example.schema;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.Signature;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView courseRV;
    private static final ArrayList<Lecture> whiteLectures = new ArrayList();
    private static final ArrayList<Lecture> greyLectures = new ArrayList();
    private static ArrayList<Lecture> sortedLectures = new ArrayList<>();
    private static boolean firstStart = true;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        courseRV = findViewById(R.id.idRVCourse);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Context context = getApplicationContext();
                    Singleton.setCourseScheduleLink(readFromFile(context));
                    sortedLectures = gather();
                } catch (
                        IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start(); // spawn thread
        try {
            t.join();  // wait for thread to finish
        } catch (
                InterruptedException e) {
            e.printStackTrace();
        }

        // we are initializing our adapter class and passing our arraylist to it.
        LectureAdapter courseAdapter = new LectureAdapter(this, Singleton.getAllLectures());

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        courseRV.setLayoutManager(linearLayoutManager);
        courseRV.setAdapter(courseAdapter);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item ->

        {
            switch (item.getItemId()) {
                case R.id.secondFragment:
                    // TODO Auto-generated method stub
                    Intent i = new Intent(getApplicationContext(), MainActivity2.class);
                    startActivity(i);
                    break;
                case R.id.thirdFragment:
                    // TODO Auto-generated method stub
                    Intent d = new Intent(getApplicationContext(), MainActivity3.class);
                    startActivity(d);
                    break;
            }
            return true;
        });
    }


    private ArrayList<Lecture> gather() throws IOException {
        // Parse website with 5 seconds timeout
        Document document = Jsoup.parse(new URL(Singleton.getCourseScheduleLink()), 5000);

        // All versions as table rows
        Elements whiteRows = document.select("table.schemaTabell > tbody > tr.data-white");
        for (Element tr : whiteRows) { // For grabbing all white lined lectures
            String lecture = tr.select("td.commonCell").get(3).text();
            String moment = tr.select("td.commonCell").get(7).text();
            String signature = tr.select("td.commonCell").get(4).text();
            String room = tr.select("td.commonCell").get(5).text();
            String day = tr.select("td.data").get(1).text();
            String date = tr.select("td.data").get(2).text();
            String time = tr.select("nobr").text();

            Lecture lectureAdd = new Lecture(moment, lecture, signature, room, time, day, date);
            whiteLectures.add(lectureAdd);
        }

        Elements greyRows = document.select("table.schemaTabell > tbody > tr.data-grey");
        for (Element tr : greyRows) { // For grabbing all gray lined lectures
            String lecture = tr.select("td.commonCell").get(3).text();
            String moment = tr.select("td.commonCell").get(7).text();
            String signature = tr.select("td.commonCell").get(4).text();
            String room = tr.select("td.commonCell").get(5).text();
            String day = tr.select("td.data").get(1).text();
            String date = tr.select("td.data").get(2).text();
            String time = tr.select("nobr").text();
            Lecture lectureAdd = new Lecture(moment, lecture, signature, room, time, day, date);
            greyLectures.add(lectureAdd);
        }

        //For adding the lectures in correct order...
        int size = whiteLectures.size() + greyLectures.size();
        for (int i = 0; i < size; i++) {
            try {
                sortedLectures.add(whiteLectures.get(i));
            } catch (IndexOutOfBoundsException ignored) {
            }
            try {
                sortedLectures.add(greyLectures.get(i));
            } catch (IndexOutOfBoundsException ignored) {
            }
        }

        for (int i = 0; i < sortedLectures.size(); i++) { //If the lecture does not have a day then revert to the last one
            if (sortedLectures.get(i).getDay().length() == 0) {
                while (true) {
                    int a = 1;
                    if (sortedLectures.get(i - a).getDay().length() != 0) {
                        sortedLectures.get(i).setDay(sortedLectures.get(i - a).getDay());
                        break;
                    }
                    a++;
                }
            }
            if (sortedLectures.get(i).getDate().length() == 0) {//If the lecture does not have a date then revert to the last one
                while (true) {
                    int a = 1;
                    if (sortedLectures.get(i - a).getDate().length() != 0) {
                        sortedLectures.get(i).setDate(sortedLectures.get(i - a).getDate());
                        break;
                    }
                    a++;
                }
            }
        }
        Singleton.setAllLectures(sortedLectures);
        return sortedLectures;
    }

    private String readFromFile(Context context) {
        String ret = "";
        try {
            InputStream inputStream = context.openFileInput("schemaData.txt");
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append("\n").append(receiveString);
                }
                System.out.println(stringBuilder);
                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }
}
