package com.rolecall;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rolecall.R;


import java.io.IOException;
import java.util.ArrayList;

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
        matches = new ArrayList<>();
        scores = new ArrayList<>();
        searchListings(listId, userId);
        while (!fetched){

        }
        if(hasMatches){
            displayListings(new View((this)));
        }
        else {
            noResults(new View(this));
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
                }
                fetched = true;
            }

            @Override
            public void onError(IOException e) {

            }
        });


    }

    private void extractListings(String response){
        String[] matchesAndScores = response.replace("[", "").split("],");
        for(String s : matchesAndScores){
            String[] separated = s.split("\\},");
            matches.add(new Listing(separated[0]));
            scores.add(separated[1].replace("]","").trim());
        }

    }

    private void noResults(View v){
        LinearLayout display = findViewById(R.id.display);
        display.addView(setupHeader("No matches found"));
    }

    private void displayListings(View v){
        LinearLayout display = findViewById(R.id.display);
        display.addView(setupHeader("Matches"));
        for(int i=0; i<matches.size(); i++){
            LinearLayout frame = new LinearLayout(this);
            frame.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            frame.setOrientation(LinearLayout.VERTICAL);
            frame.setBackgroundResource(R.drawable.listing_background);
            display.addView(frame);
            LinearLayout scoreAndAccept = new LinearLayout(this);
            scoreAndAccept.setOrientation(LinearLayout.HORIZONTAL);
            frame.addView(setupMatchName(i));
            frame.addView(setupMatchLocation(i));
            frame.addView(setupMatchDifficulty(i));
            frame.addView(setupMatchSchedule(i));
            frame.addView(scoreAndAccept);
            scoreAndAccept.addView(setupScore(i));
            scoreAndAccept.addView(setupAccept());
        }
    }

    private TextView setupHeader(String label){
        TextView header = new TextView(this);
        header.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(300), dpToPx(70)));
        header.setBackgroundResource(R.drawable.listing_background);
        header.setGravity(Gravity.CENTER);
        header.setText(label);
        header.setTextColor(Color.WHITE);
        header.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        return header;
    }

    private TextView setupMatchName(int i){
        TextView name = new TextView(this);
        name.setLayoutParams(new ViewGroup.LayoutParams(dpToPx(300), ViewGroup.LayoutParams.WRAP_CONTENT));
        name.setGravity(Gravity.CENTER_HORIZONTAL);
        name.setText(matches.get(i).getGameName());
        name.setTextColor(Color.WHITE);
        name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        return name;
    }

    private TextView setupMatchLocation(int i){
        TextView location = new TextView(this);
        location.setText(matches.get(i).getEnvironment());
        location.setTextColor(Color.WHITE);
        location.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        return location;
    }

    private TextView setupMatchDifficulty(int i){
        TextView difficulty = new TextView(this);
        difficulty.setText(matches.get(i).getDifficulty());
        difficulty.setTextColor(Color.WHITE);
        difficulty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        return difficulty;
    }

    private TextView setupMatchSchedule(int i){
        TextView schedule = new TextView(this);
        String meetTime = String.format("Schedule: %s %s - %s", matches.get(i).getDay(), matches.get(i).getStartTime(), matches.get(i).getEndTime());
        schedule.setText(meetTime);
        schedule.setTextColor(Color.WHITE);
        schedule.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        return schedule;
    }

    private TextView setupScore(int i){
        TextView score = new TextView(this);
        score.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        float percent = Float.parseFloat(scores.get(i).trim());
        String matchPercent = String.format("Match: %5.1f%%", percent);
        score.setText(matchPercent);
        score.setTextColor(Color.YELLOW);
        score.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        return score;
    }

    private Button setupAccept(){
        Button accept = new Button(this);
        accept.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        accept.setBackgroundResource(R.drawable.match_button);
        accept.setText(R.string.accept_button_label);
        accept.setTextColor(Color.WHITE);
        accept.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        return accept;
    }

    private int dpToPx (int dp){
        float density = this.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}