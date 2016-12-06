package com.company;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Main {

    public static void main(String[] args) {

        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("File1", "File number: 1");
        hashMap.put("File2", "File number: 2");
        hashMap.put("File3", "File number: 3");

        Preferences preferences = Preferences.userRoot().node("/ch/stageconcept/datatraffic/file");

        writePrefs(preferences, hashMap);

        readPrefs(preferences);
        System.out.println("###");

        removePrefs(preferences, "File2");

        readPrefs(preferences);
        System.out.println("###");

        removePrefs(preferences, null);

        readPrefs(preferences);
        System.out.println("###");


        // Properties file ###

        try (OutputStream output = new FileOutputStream("C:\\Users\\oldu7\\Desktop\\test\\testprop.txt")) {

            Properties properties = new Properties();

            properties.setProperty("prop1", "properties1");
            properties.setProperty("prop2", "properties2");
            properties.setProperty("prop3", "properties3");

            properties.store(output, null);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (InputStream input = new FileInputStream("C:\\Users\\oldu7\\Desktop\\test\\testprop.txt")) {

            Properties properties = new Properties();
            properties.load(input);

            Enumeration enumeration = properties.propertyNames();
            while (enumeration.hasMoreElements()) {
                String key = (String) enumeration.nextElement();
                System.out.println(key + " -- " + properties.getProperty(key));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void writePrefs(Preferences preferences, HashMap<String, String> hashMap) {
        for (HashMap.Entry<String, String> entry : hashMap.entrySet())
        {
            preferences.put(entry.getKey(), entry.getValue());
        }
    }

    private static void readPrefs(Preferences preferences) {

        String methodName = "readPrefs";

        String[] keys = new String[0];
        try {
            keys = preferences.keys();
        } catch (BackingStoreException e) {
            System.err.println(methodName + ", unable to read backing store: " + e);
            //e.printStackTrace();
        } catch (IllegalStateException e) {
            //System.err.println(methodName + ", " + e);
            System.out.println(methodName + ", node has been removed!");
        }
        for (String key : keys) {
            System.out.println(key + " = " + preferences.get(key, null));
        }
    }

    private static void removePrefs(Preferences preferences, String key) {
        if (key != null) {
            preferences.remove(key);
        } else {
            try {
                preferences.removeNode();
            } catch (BackingStoreException e) {
                e.printStackTrace();
            }
        }
    }
}
