package com.robinson.globalserviceproject.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.robinson.globalserviceproject.model.EmailItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreference {

    public static final String PREFS_NAME = "PREFS_NAME";
    public static final String LIST_DATA = "listData";
    public static ArrayList<EmailItem> userList = new ArrayList<>();

    public SharedPreference() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveData(Context context, List<EmailItem> favorites) {
        SharedPreferences settings;
        Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);
        editor.putString(LIST_DATA, jsonFavorites);
        editor.apply();
    }

    public void addData(Context context, EmailItem product) {
        List<EmailItem> favorites = getData(context);
        if (favorites == null)
            favorites = new ArrayList<>();
        favorites.add(product);
        saveData(context, favorites);
    }

    public void editData(Context context, EmailItem product) {
        List<EmailItem> favorites = getData(context);
        if (favorites == null)
            favorites = new ArrayList<>();
        favorites.add(product);
        saveData(context, favorites);
    }

    public void removeData(Context context, EmailItem product) {
        ArrayList<EmailItem> favorites = getData(context);
        if (favorites != null) {
            favorites.remove(product);
            saveData(context, favorites);
        }
    }

    public ArrayList<EmailItem> getData(Context context) {
        SharedPreferences settings;
        List<EmailItem> favorites;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (settings.contains(LIST_DATA)) {
            String jsonFavorites = settings.getString(LIST_DATA, null);
            Gson gson = new Gson();
            EmailItem[] favoriteItems = gson.fromJson(jsonFavorites, EmailItem[].class);
            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<>(favorites);
        } else
            return null;

        return (ArrayList<EmailItem>) favorites;
    }

    public boolean contains(ArrayList<EmailItem> list, String email) {
        for (EmailItem item : list) {
            if (item.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
}