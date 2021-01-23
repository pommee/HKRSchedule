package com.example.schema;

import java.util.ArrayList;

public class Singleton {
    // Singleton Related Code
    private static Singleton instance = null;

    Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    private static ArrayList<Lecture> allLectures = new ArrayList();
    private static String courseScheduleLink;

    public static ArrayList<Lecture> getAllLectures() {
        return allLectures;
    }

    public static void setAllLectures(ArrayList<Lecture> allLectures) {
        Singleton.allLectures = allLectures;
    }

    public static String getCourseScheduleLink() {
        return courseScheduleLink;
    }

    public static void setCourseScheduleLink(String courseScheduleLink) {
        Singleton.courseScheduleLink = courseScheduleLink;
    }
}
