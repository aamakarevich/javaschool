package com.tsystems.ecare.app.utils;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Andrei Makarevich
 */
public class JsonUtil {

    public static <T> T getObjectFromJson(HttpServletRequest request, Class<T> clazz) throws IOException {
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = reader.readLine()) != null) {
            sb.append(s);
        }
        return gson.fromJson(sb.toString(), clazz);
    }

    public static <T> void writeObjectToJson(HttpServletResponse resp, T object) throws IOException {
        String json = new Gson().toJson(object);
        resp.setContentType("application/json");
        resp.getWriter().write(json);
    }
}
