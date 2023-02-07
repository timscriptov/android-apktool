package com.mcal.androlib.utils;

import java.util.logging.Level;

public interface Logger {

    public void error(String log);

    public void log(Level warring, String format, Throwable ex);

    public void fine(String log);

    public void warning(String log);

    public void info(String log);
}