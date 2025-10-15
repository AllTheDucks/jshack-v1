package org.oscelot.jshack.jsp;

import com.google.gson.Gson;

public class JsonFunctions {
    private static final Gson GSON = new Gson();

    public static String toJson(Object o) {
        return GSON.toJson(o);
    }
}