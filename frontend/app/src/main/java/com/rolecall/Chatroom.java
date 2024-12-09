package com.rolecall;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Chatroom extends AppCompatActivity {

    String USER_NAME = "TeamSilver";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chatroom);
        Intent intent = getIntent();
        String deciders=intent.getStringExtra("d");


        if(Objects.equals(deciders, "dog")){
            runOnUiThread(() -> Toast.makeText(getApplicationContext(), deciders, Toast.LENGTH_SHORT).show());


            Intent Mover= new Intent(getApplicationContext(),MainActivity.class);
            startActivity(Mover);

        }

        // code here

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



}
