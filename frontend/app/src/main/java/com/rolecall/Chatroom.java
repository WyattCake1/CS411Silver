package com.rolecall;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.rolecall.R;
import com.google.android.material.textfield.TextInputEditText;

import org.dflib.Series;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import android.util.Log;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.dflib.DataFrame;
import org.dflib.json.Json;
import java.util.concurrent.TimeUnit;
import java.net.URLEncoder;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Callable;



public class Chatroom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chatroom);

        // Retrieve the data
        int userId = getIntent().getIntExtra("userId", -1);
        int campaignId = getIntent().getIntExtra("chatroomId", -1);
        int chatroomId = findChatroomId(campaignId);
        Log.d("CHATROOM", campaignId + " " + chatroomId);


        // TODO if the default values are used, show an error page OR if these values not in the database
            // Probably should occur from the previous activity...

        // Future Tasks

        FutureTask<ArrayList<String>> loadChatroomMessagesTask = new FutureTask<>(() -> loadChatroomMessages(chatroomId));
        Thread loadChatMessagesThread = new Thread(loadChatroomMessagesTask); loadChatMessagesThread.start();


        // Initialize the UI elements

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        final Button SendButton = findViewById(R.id.chatroom_send);
        final TextInputEditText ChatroomInput = findViewById(R.id.chatroom_input);

        RecyclerView ChatroomMessageStream = findViewById(R.id.chatroom_message_stream);
        ChatroomMessageStream.setLayoutManager(
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        // Initialize the message log

        ArrayList<String> messageLog = new ArrayList<>();
        try {
            messageLog = loadChatroomMessagesTask.get(5, TimeUnit.SECONDS);
        }
        catch (Exception e) {
            Log.d("CHATROOM", "An exception was thrown...");
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString();
            Log.d("CHATROOM", stackTrace);
        }

        // Initialize the adapter with the chatroom message log.

        ChatroomMessageAdapter adapter = new ChatroomMessageAdapter(messageLog);
        ChatroomMessageStream.setAdapter(adapter);

        // function to print a message to the screen
        SendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = ChatroomInput.getText().toString();
                Thread HttpThread = new Thread(() -> {
                    postMessage(chatroomId, userId, message);
                }); HttpThread.start();

                // TODO in the future, update the message feed at this time instead
                // TODO retrieve the user's name from their ID
                adapter.sendMessage("you: " + ChatroomInput.getText().toString());
                ChatroomInput.setText("");
            }
        });
    };

    /**
     * Loads all messages from a chatroom, returning them as a list of Strings.
     *
     * @pre chatroomId is in chatroom database && this method is not invoked via the main thread.
     * @param chatroomId
     * @return a list of all messages from the chatroom
     */
    private static ArrayList<String> loadChatroomMessages(Integer chatroomId){

        ArrayList<String> messageLog = new ArrayList<>();
        try {
            Log.d("CHATROOM", "Attempting HTTP connection");

            String urlString = "http://10.0.2.2:5000/chatroom/" + chatroomId;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // TODO, add username and password args so random users cannot view chatroom

            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();

            if (responseCode == 200) {
                String jsonResponse = getJson(conn);
                DataFrame df = Json.loader().load(jsonResponse);

                Log.d("CHATROOM", String.valueOf(df));

                df.forEach(row -> {
                    String name = row.get("name").toString();
                    String message = row.get("message").toString();
                    Log.d("CHATROOM", name + ": " + message);
                    messageLog.add(name + ": " + message);
                });
            }
            else {
                Log.d("CHATROOM", "Request failed with status: " + responseCode);
            }
            conn.disconnect();
        } catch (Exception e) {
            //Log.d("CHATROOM", "Exception thrown:" + e.getMessage());
            Log.d("CHATROOM", "An exception was thrown...");
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString();
            Log.d("CHATROOM", stackTrace);
        }
        return messageLog;
    }

    /**
     * Inserts a new message into the chat_messages database.
     *
     * @param chatroomId the ID of the chatroom the message is to sent to
     * @param userId the ID of the user who sent the message
     * @param message the content
     */
    private static void postMessage(Integer chatroomId, Integer userId, String message){

        try {
            String urlString = "http://10.0.2.2:5000/send_chatroom_message";

            String encodedParams =
                    "chatroom_id=" + URLEncoder.encode(chatroomId.toString(), "UTF-8") +
                    "&user_id=" + URLEncoder.encode(userId.toString(), "UTF-8") +
                    "&message=" + URLEncoder.encode(message, "UTF-8");

            Log.d("CHATROOM", urlString);
            urlString += "?" + encodedParams;

            URL url = new URL(urlString);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = encodedParams.getBytes("UTF-8");
                os.write(input, 0, input.length);
            }

            // OUTPUT

            // Get the response code and message
            int responseCode = conn.getResponseCode();
            String responseMessage = conn.getResponseMessage();
            Log.d("CHATROOM", "Response: " + responseCode + " " + responseMessage);

            // Read the server's response
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                Log.d("CHATROOM", "Server response: " + response.toString());

            } catch (IOException e) {
                Log.d("CHATROOM", "Error reading server response: " + e.getMessage());
            }

            // END OUTPUT
            conn.disconnect();

        } catch (Exception e) {
            Log.d("CHATROOM", "An exception was thrown...");
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString();
            Log.d("CHATROOM", stackTrace);
        }
    }

    /**
     * Fetches the JSON data from an HTTP connection
     *
     * @param conn the HTTP connection where the JSON data is recieved from.
     * @return the JSON data as a String
     */
    private static String getJson(HttpURLConnection conn){
        Log.d("CHATROOM", "call to getJSON");
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        } catch (IOException e) {
            Log.d("CHATROOM", "Exception thrown:" + e.getMessage());
            e.printStackTrace();
        }
        Log.d("CHATROOM", response.toString());
        return response.toString();
    }


    /**
     * Gets a ChatroomId associated with a campaignListingId from the database, creating
     * a chatroom if it does not already exist.
     * @param campaignListingId
     * perhaps this should all be private aside from findChatroomId?
     */
    public static int createOrGetChatroom(Integer campaignListingId){

        if (!hasChatroom(campaignListingId))
            createChatroom(campaignListingId);

        return findChatroomId(campaignListingId);
    }

    /**
     * Creates a Chatroom for a Campaign Listing
     *
     * @param campaignListingId
     * Turn to private later
     */
    private static void createChatroom(Integer campaignListingId){
        try {
            String urlString = "http://10.0.2.2:5000/create_chatroom";
            String encodedParams = "campaign_id="
                    + URLEncoder.encode(campaignListingId.toString(), "UTF-8");

            Log.d("CHATROOM", urlString);
            urlString += "?" + encodedParams;

            URL url = new URL(urlString);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = encodedParams.getBytes("UTF-8");
                os.write(input, 0, input.length);
            }

            // OUTPUT

            // Get the response code and message
            int responseCode = conn.getResponseCode();
            String responseMessage = conn.getResponseMessage();
            Log.d("CHATROOM", "Response: " + responseCode + " " + responseMessage);

            // Read the server's response
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                Log.d("CHATROOM", "Server response: " + response.toString());

            } catch (IOException e) {
                Log.d("CHATROOM", "Error reading server response: " + e.getMessage());
            }

            // END OUTPUT
            conn.disconnect();

        } catch (Exception e) {
            Log.d("CHATROOM", "An exception was thrown...");
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString();
            Log.d("CHATROOM", stackTrace);
        }
    }

    /**
     * Evaluates whether a chatroom exists for a campaign listing.
     *
     * @param campaignListingID
     * @return true if a chatroom exists for the campaign listing, false otherwise
     */
    public static Boolean hasChatroom(Integer campaignListingID){
        return findChatroomId(campaignListingID) != -1;
    }

    /**
     *
     * TODO WIP DO NOT USE YET
     * Finds the chatroomId associated with a CampaignListing, or -1 if it does not exist.
     *
     * @param campaignListingId
     * @return a valid chatroomId if there is a chatroom associated with a campaign listing,
     *         or -1 if there is no such chatroom.
     *
     */
    public static int findChatroomId(Integer campaignListingId){

        FutureTask<Integer> httpTask = new FutureTask<>(() -> {
            try {
                String urlString = "http://10.0.2.2:5000/get_campaign_chatroom/" + campaignListingId.toString();
                Log.d("CHATROOM", urlString);
                URL url = new URL(urlString);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setDoOutput(true);

                // OUTPUT

                // Get the response code and message
                int responseCode = conn.getResponseCode();
                String responseMessage = conn.getResponseMessage();
                Log.d("CHATROOM", "Response: " + responseCode + " " + responseMessage);

                // Read the server's response
                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    Log.d("CHATROOM", "Server response: " + response.toString());

                } catch (IOException e) {
                    Log.d("CHATROOM", "Error reading server response: " + e.getMessage());
                }

                // END OUTPUT
                conn.disconnect();

                String jsonResponse = getJson(conn);
                DataFrame df = Json.loader().load(jsonResponse);


                if (df.height() == 0) {
                    return -1;
                }
                Series<Integer> chatroom_ids = df.getColumn("chatroom_id");
                return chatroom_ids.get(0);

            } catch (Exception e) {
                Log.d("CHATROOM", "An exception was thrown...");
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String stackTrace = sw.toString();
                Log.d("CHATROOM", stackTrace);
                return -2;
                }
            }
        );

        Thread httpThread = new Thread(httpTask); httpThread.start();

        try {
            return httpTask.get(2, TimeUnit.SECONDS);
        }
        catch (Exception e) {
            throw(new RuntimeException(e));
        }
    }
}


