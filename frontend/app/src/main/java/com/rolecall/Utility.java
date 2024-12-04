package com.rolecall;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.net.HttpURLConnection;

public class Utility {

    /**
     * Outputs the HTTP connection log.
     */
    public static void outputConnectionLog(HttpURLConnection conn)  {
        // Get the response code and message

        try {
            int responseCode = conn.getResponseCode();
            String responseMessage = conn.getResponseMessage();
            Log.d("CHATROOM", "Response: " + responseCode + " " + responseMessage);
        } catch (IOException e) {
            Log.d("CHATROOM", "Error reading server response: " + e.getMessage());
        }

    }

    /**
     * Outputs the exception trace with a specified tag, to be looked up via Logcat.
     * @param tag the searchable tag in Logcat that can be looked up via filtering tag:[tag]
     * @param e the exception that was raised.
     */
    public static void outputExceptionTrace(String tag, Exception e){

        Log.d(tag, "An exception was thrown...");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stackTrace = sw.toString();
        Log.d("CHATROOM", stackTrace);

    }


}
