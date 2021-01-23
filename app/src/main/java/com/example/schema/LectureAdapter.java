package com.example.schema;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LectureAdapter extends RecyclerView.Adapter<LectureAdapter.Viewholder> {

    private Context context;
    private ArrayList<Lecture> courseModelArrayList;

    // Constructor
    public LectureAdapter(Context context, ArrayList<Lecture> courseModelArrayList) {
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
    }

    @NonNull
    @Override
    public LectureAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LectureAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        Lecture model = courseModelArrayList.get(position);
        holder.moment.setText(model.getMoment() + ", " + model.getLecture());
        holder.signature.setText(model.getSignature());
        holder.room.setText(model.getRoom());
        holder.time.setText(model.getTime());
        holder.day.setText(model.getDay());
        holder.mon.setBackgroundColor(currentDay(holder.day.getText().toString(), "Mon"));
        holder.tue.setBackgroundColor(currentDay(holder.day.getText().toString(), "Tue"));
        holder.wed.setBackgroundColor(currentDay(holder.day.getText().toString(), "Wed"));
        holder.thu.setBackgroundColor(currentDay(holder.day.getText().toString(), "Thu"));
        holder.fri.setBackgroundColor(currentDay(holder.day.getText().toString(), "Fri"));
        holder.sat.setBackgroundColor(currentDay(holder.day.getText().toString(), "Sat"));
        holder.sun.setBackgroundColor(currentDay(holder.day.getText().toString(), "Sun"));
        holder.mon.setTextColor(currentDaySecond(holder.day.getText().toString(), "Mon"));
        holder.tue.setTextColor(currentDaySecond(holder.day.getText().toString(), "Tue"));
        holder.wed.setTextColor(currentDaySecond(holder.day.getText().toString(), "Wed"));
        holder.thu.setTextColor(currentDaySecond(holder.day.getText().toString(), "Thu"));
        holder.fri.setTextColor(currentDaySecond(holder.day.getText().toString(), "Fri"));
        holder.sat.setTextColor(currentDaySecond(holder.day.getText().toString(), "Sat"));
        holder.sun.setTextColor(currentDaySecond(holder.day.getText().toString(), "Sun"));
    }

    private static int currentDay(String day, String givenDay) {
        System.out.println(day);
        if (day.equals("Mån") && givenDay.equals("Mon"))
            return Color.GRAY;
        else if (day.equals("Tis") && givenDay.equals("Tue"))
            return Color.GRAY;
        else if (day.equals("Ons") && givenDay.equals("Wed"))
            return Color.GRAY;
        else if (day.equals("Tor") && givenDay.equals("Thu"))
            return Color.GRAY;
        else if (day.equals("Fre") && givenDay.equals("Fri"))
            return Color.GRAY;
        else if (day.equals("Lör") && givenDay.equals("Sat"))
            return Color.GRAY;
        else if (day.equals("Sön") && givenDay.equals("Sun"))
            return Color.GRAY;
        return Color.WHITE;
    }

    private static int currentDaySecond(String day, String givenDay) {
        System.out.println(day);
        if (day.equals("Mån") && givenDay.equals("Mon"))
            return Color.BLACK;
        else if (day.equals("Tis") && givenDay.equals("Tue"))
            return Color.BLACK;
        else if (day.equals("Ons") && givenDay.equals("Wed"))
            return Color.BLACK;
        else if (day.equals("Tor") && givenDay.equals("Thu"))
            return Color.BLACK;
        else if (day.equals("Fre") && givenDay.equals("Fri"))
            return Color.BLACK;
        else if (day.equals("Lör") && givenDay.equals("Sat"))
            return Color.BLACK;
        else if (day.equals("Sön") && givenDay.equals("Sun"))
            return Color.BLACK;
        return Color.GRAY;
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return courseModelArrayList.size();
    }

    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView courseImage;
        private TextView moment, lecture, signature, room, time, day, date, mon, tue, wed, thu, fri, sat, sun;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            moment = itemView.findViewById(R.id.momentAndLecture);
            signature = itemView.findViewById(R.id.signature);
            room = itemView.findViewById(R.id.room);
            time = itemView.findViewById(R.id.time);
            day = itemView.findViewById(R.id.day);
            mon = itemView.findViewById(R.id.mon);
            tue = itemView.findViewById(R.id.tue);
            wed = itemView.findViewById(R.id.wed);
            thu = itemView.findViewById(R.id.thu);
            fri = itemView.findViewById(R.id.fri);
            sat = itemView.findViewById(R.id.sat);
            sun = itemView.findViewById(R.id.sun);
        }
    }
}