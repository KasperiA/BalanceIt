package com.kasperistudios.android.balanceit;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Tämä luokka tallentaa oliot puhelimen muistiin.
 */

public class StorageManager {

    public void save(Context context, MoneyStorage moneyStorage) {
        try {
            File file = new File(context.getFilesDir(), moneyStorage.getYear() + moneyStorage.getMonth());
            FileOutputStream fileOutputStream = context.openFileOutput(moneyStorage.getYear() + moneyStorage.getMonth(), Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(moneyStorage);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MoneyStorage read(Context context, String filename) {
        MoneyStorage moneyStorage = null;

        try {
            FileInputStream fileInputStream = context.openFileInput(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            moneyStorage = (MoneyStorage) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return moneyStorage;
    }

    public void save(Context context, ChosenDate chosenDate) {
        try {
            File file = new File(context.getFilesDir(), "chosenDate");
            FileOutputStream fileOutputStream = context.openFileOutput("chosenDate", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(chosenDate);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ChosenDate read(Context context, String filename, boolean info) {
        ChosenDate chosenDate = null;

        try {
            FileInputStream fileInputStream = context.openFileInput(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            chosenDate = (ChosenDate) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return chosenDate;
    }
}
