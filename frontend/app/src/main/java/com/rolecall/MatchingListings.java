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

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Request;

public class MatchingListings extends AppCompatActivity {
    private ArrayList<Listing> matches;
    private ArrayList<String> scores;
    private boolean fetched;
    private boolean hasMatches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_matching_listings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        String listId = intent.getStringExtra("listingId");
        String userId = intent.getStringExtra("userId");
        searchListings(listId, userId);
        while ((!fetched)){

        }
        if(hasMatches){
            displayListings(new View((this)));
        }
    }

    private void searchListings(String listId, String userId){
        FlaskClient flaskClient = new FlaskClient();
        flaskClient.requestMatches(listId, userId, new ResponseCallback() {
            @Override
            public void onSuccess(String response) {
                hasMatches = !response.equals("None");
                if(hasMatches){
                    extractListings(response);
                    System.out.println(response);
                }
                fetched = true;
            }

            @Override
            public void onError(IOException e) {

            }
        });


    }

    private void extractListings(String response){
        String removeFront = response.replace("[", "");
    }

    private void noResults(View v){
        TextView header = findViewById(R.id.header);
        header.setText(R.string.no_matches);
    }

    private void displayListings(View v){

    }
}