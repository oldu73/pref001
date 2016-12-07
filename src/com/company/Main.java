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

        hashMap.put("File1", "C:\\Users\\oldu7\\Desktop\\test\\testprop1.txt");
        hashMap.put("File2", "C:\\Users\\oldu7\\Desktop\\test\\folder\\testprop2.txt");
        hashMap.put("File3", "C:\\Users\\oldu7\\Desktop\\test\\folder\\subfolder\\testprop3.txt");

        Preferences preferences = Preferences.userRoot().node("/ch/stageconcept/datatraffic/file");

        //test1(preferences, hashMap);

        //writePrefs(preferences, hashMap);

        readPrefs(preferences);

        /*
        writePropFileFromPrefPath(preferences, "File1", 15, 19);
        writePropFileFromPrefPath(preferences, "File2", 4, 6);
        writePropFileFromPrefPath(preferences, "File3", 1, 10);
        */

        readPropFileFromPrefPath(preferences, "File1");
        readPropFileFromPrefPath(preferences, "File2");
        readPropFileFromPrefPath(preferences, "File3");

    }

    private static void readPropFileFromPrefPath(Preferences preferences, String key) {
        String filePath = preferences.get(key, null);

        if (filePath != null) {
            try (InputStream input = new FileInputStream(filePath)) {

                Properties properties = new Properties();
                properties.load(input);

                Enumeration enumeration = properties.propertyNames();

                System.out.println("### Reading property file from key: " + key + ", with path: " + filePath);

                while (enumeration.hasMoreElements()) {
                    String propertyKey = (String) enumeration.nextElement();
                    System.out.println(propertyKey + " -- " + properties.getProperty(propertyKey));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(key + " filePath == null");
        }

        System.out.println("###");
        System.out.println();
    }

    private static void writePropFileFromPrefPath(Preferences preferences, String key, int propStart, int propEnd) {
        String filePath = preferences.get(key, null);

        if (filePath != null) {
            try (OutputStream output = new FileOutputStream(filePath)) {

                Properties properties = new Properties();

                for (int i=propStart; i<=propEnd; ++i) {
                    properties.setProperty("prop" + i, "properties" + i);
                }

                properties.store(output, null);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(key + " filePath == null");
        }
    }

    private static void test1(Preferences preferences, HashMap<String, String> hashMap) {
        writePrefs(preferences, hashMap);

        readPrefs(preferences);
        System.out.println("###");

        removePrefs(preferences, "File2");

        readPrefs(preferences);
        System.out.println("###");

        removePrefs(preferences, null);

        readPrefs(preferences);
        System.out.println("###");
    }

    private static void writePrefs(Preferences preferences, HashMap<String, String> hashMap) {
        for (HashMap.Entry<String, String> entry : hashMap.entrySet())
        {
            preferences.put(entry.getKey(), entry.getValue());
        }
    }

    private static void readPrefs(Preferences preferences) {

        System.out.println("### Reading preferences");

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

        System.out.println("###");
        System.out.println();
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
