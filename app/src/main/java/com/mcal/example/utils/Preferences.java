package com.mcal.example.utils;

import android.content.Context;
import android.content.SharedPreferences;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class Preferences {
    @NotNull
    private static SharedPreferences preferences(@NotNull Context context) {
        return context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
    }

    @NotNull
    private static SharedPreferences.Editor editor(@NotNull Context context) {
        return preferences(context).edit();
    }

    @NotNull
    public static String getAaptPath(@NotNull Context context) {
        final SharedPreferences preferences = preferences(context);
        return preferences.getString("aapt", new File(FileHelper.getToolsDir(context), "aapt").getPath());
    }

    public static void setAaptPath(@NotNull Context context, @NotNull String path) {
        final SharedPreferences.Editor editor = editor(context);
        editor.putString("aapt", path);
        editor.apply();
    }

    @NotNull
    public static String getAapt2Path(@NotNull Context context) {
        final SharedPreferences preferences = preferences(context);
        return preferences.getString("aapt2", new File(FileHelper.getToolsDir(context), "aapt2").getPath());
    }

    public static void setAapt2Path(@NotNull Context context, @NotNull String path) {
        final SharedPreferences.Editor editor = editor(context);
        editor.putString("aapt2", path);
        editor.apply();
    }

    @NotNull
    public static String getFrameworkPath(@NotNull Context context) {
        final SharedPreferences preferences = preferences(context);
        return preferences.getString("framework", new File(FileHelper.getToolsDir(context), "android.jar").getPath());
    }

    public static void setFrameworkPath(@NotNull Context context, @NotNull String path) {
        final SharedPreferences.Editor editor = editor(context);
        editor.putString("framework", path);
        editor.apply();
    }

    public static boolean isUseAapt2(@NotNull Context context) {
        final SharedPreferences preferences = preferences(context);
        return preferences.getBoolean("use_aapt2", true);
    }

    public static void setUseAapt2(@NotNull Context context, boolean mode) {
        final SharedPreferences.Editor editor = editor(context);
        editor.putBoolean("use_aapt2", mode);
        editor.apply();
    }
}
