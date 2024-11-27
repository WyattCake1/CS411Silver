package com.rolecall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rolecall.R;

public class ViewListing extends AppCompatActivity {
    private Listing display;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_listing);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        fillFields(new TextView(this));
        Intent intent = getIntent();
        Listing listing = (Listing) intent.getSerializableExtra("Listing");
        userId = (String) intent.getExtras().get("userId");
    }

    public void backToListings(View v){
        Intent intent = new Intent(ViewListing.this, UserListingsPage.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void fillFields(View v){
        //Dummy Listing for proof of concept
        display = new Listing();
        display.setGameName("Dungeons & Dragons");
        display.setEnvironment("Foundry Virtual Tabletop");
        display.setStartTime("Mon 12:30 PM");
        display.setEndTime("Mon 4:30 PM");
        display.setDifficulty("Intermediate");
        display.setRole("DPS");
        //Implemented Logic
        TextView gameName = findViewById(R.id.game_name_field);
        TextView difficulty = findViewById(R.id.difficulty_field);
        TextView environment = findViewById(R.id.environment_field);
        TextView startTime = findViewById(R.id.start_time_field);
        TextView endTime = findViewById(R.id.end_time_field);
        TextView role = findViewById(R.id.role_field);
        gameName.setText(display.getGameName());
        difficulty.setText(display.getDifficulty());
        environment.setText(display.getEnvironment());
        startTime.setText(display.getStartTime());
        endTime.setText(display.getEndTime());
        role.setText(display.getRole());
    }
}