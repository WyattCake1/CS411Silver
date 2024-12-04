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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import android.util.Log;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.dflib.DataFrame;
import org.dflib.json.Json;

public class Chatroom extends AppCompatActivity {

    String USER_NAME = "TeamSilver";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chatroom);

        // Retrieve the data
        int userId = getIntent().getIntExtra("userId", -1);
        int chatroomId = getIntent().getIntExtra("chatroomId", -1);

        // TODO if the default values are used, show an error page OR if these values not in
        // the database

        Log.d("CHATROOM", "Hello, Logcat!");

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

        // HTTPS request to get the chatroom messages

        Thread HttpThread = new Thread(() -> {
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
                    conn.disconnect();
                    }
                else {
                    Log.d("CHATROOM", "Request failed with status: " + responseCode);
                }
            } catch (Exception e) {
                Log.d("CHATROOM", "Exception thrown:" + e.getMessage());
                e.printStackTrace();
            }
        }); HttpThread.start();

        // END HTTPS request


        // TESTING, remove later
        ArrayList<String> testMessages = new ArrayList<>();
        testMessages.add("Hello...");
        testMessages.add("...world!");
        testMessages.add("A slightly more interesting test message.");

        for (int i = 0; i < 15; i++){
            testMessages.add("SCROLLING TEST " + i);
        }

        ChatroomMessageAdapter adapter = new ChatroomMessageAdapter(testMessages);
        ChatroomMessageStream.setAdapter(adapter);

        adapter.sendMessage("Sent after initialization!");

        // function to print a message to the screen
        SendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.sendMessage(ChatroomInput.getText().toString());
                ChatroomInput.setText("");
            }
        });

    };

    /**
     * Fetches the JSON data from an HTTP connection
     *
     * @param conn the HTTP connection where the JSON data is recieved from.
     * @return the JSON data as a String
     */
    public static String getJson(HttpURLConnection conn){
        Log.d("CHATROOM", "call to getJSON");
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        catch (IOException e) {
            Log.d("CHATROOM", "Exception thrown:" + e.getMessage());
            e.printStackTrace();
        }
        Log.d("CHATROOM", response.toString());
        return response.toString();
    }


}
