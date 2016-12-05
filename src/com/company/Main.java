package com.company;

import java.util.prefs.Preferences;

public class Main {

    public static void main(String[] args) {
        //Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        //Preferences prefs = Preferences.userNodeForPackage(this.getClass().getName());
        //Preferences prefs = Preferences.userNodeForPackage(Main.class);

        Preferences prefs = Preferences.userRoot().node("/ch/stageconcept/datatraffic/file");

        String ID = "Hello";
        //prefs.put(ID, "Hello, zapata!");

        System.out.println(prefs.get(ID, "coucou.."));
    }
}
