package com.example.schema;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;

public class Lecture {

    private final String moment;
    private final String lecture;
    private final String signature;
    private final String room;
    private final String time;
    private String day;
    private String date;

    public Lecture(String moment, String lecture, String signature, String room, String time, String day, String date) {
        this.moment = moment;
        this.lecture = lecture;
        this.signature = signature;
        this.room = room;
        this.time = time;
        this.day = day;
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMoment() {
        return moment;
    }

    public String getLecture() {
        return lecture;
    }

    public String getSignature() {
        return signature;
    }

    public String getRoom() {
        return room;
    }

    public String getTime() {
        return time;
    }

    @NotNull
    @Override
    public String toString() {
        return "Lecture{" +
                "moment='" + moment + '\'' +
                ", lecture='" + lecture + '\'' +
                ", signature='" + signature + '\'' +
                ", room='" + room + '\'' +
                ", time='" + time + '\'' +
                ", day='" + day + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
