package com.mcal.androlib;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Contract;

public class Version {
    @NonNull
    @Contract(pure = true)
    public static String getVersion() {
        return "2.7.0";
    }
}