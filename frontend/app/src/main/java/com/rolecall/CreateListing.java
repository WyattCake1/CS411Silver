package com.rolecall;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rolecall.R;

import java.util.ArrayList;
import java.util.List;

public class CreateListing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_listing);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        //------------------------------------------------------------------------------------------
        String spinSelect = "Select";

        // Roles for dialog recycler view
        ArrayList<Pair<String, String>> roles = new ArrayList<>();
        roles.add(new Pair<>("0", "Tank"));
        roles.add(new Pair<>("0", "DPS"));
        roles.add(new Pair<>("0", "Face"));
        roles.add(new Pair<>("0", "Healer"));
        roles.add(new Pair<>("0", "Support"));

        // Adapter for the recycler view
        RecyclerView recyclerView = findViewById(R.id.roles_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ListAdapter adapter = new ListAdapter(roles);
        recyclerView.setAdapter(adapter);

        // Preferred environment
        ArrayList<String> arrayEnv = new ArrayList<>();
        arrayEnv.add("In-person");
        arrayEnv.add("Digital");
        ArrayAdapter<String> adapterEnv = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayEnv);
        adapterEnv.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        Spinner environment_spinner = findViewById(R.id.pref_environment_spinner);
        environment_spinner.setAdapter(adapterEnv);
        environment_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView)parent.getChildAt(0)).setTextSize(18);
                //((TextView)parent.getChildAt(0)).setText(spinSelect);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Max distance
        ArrayList<String> arrayDist = new ArrayList<>();
        arrayDist.add("5");
        arrayDist.add("10");
        arrayDist.add("25");
        arrayDist.add("50");
        arrayDist.add("100");
        ArrayAdapter<String> adapterDist = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayDist);
        adapterEnv.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        Spinner distance_spinner = findViewById(R.id.max_distance_spinner);
        distance_spinner.setAdapter(adapterDist);
        distance_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView)parent.getChildAt(0)).setTextSize(18);
                //((TextView)parent.getChildAt(0)).setText(spinSelect);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Campaign difficulty
        ArrayList<String> arrayDiff = new ArrayList<>();
        arrayDiff.add("First Game");
        arrayDiff.add("Casual");
        arrayDiff.add("Intermediate");
        arrayDiff.add("Advanced");
        ArrayAdapter<String> adapterDiff = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayDiff);
        adapterDiff.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        Spinner difficulty_spinner = findViewById(R.id.campaign_difficulty_spinner);
        difficulty_spinner.setAdapter(adapterDiff);
        difficulty_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView)parent.getChildAt(0)).setTextSize(18);
                //((TextView)parent.getChildAt(0)).setText(spinSelect);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //------------------------------------------------------------------------------------------
        // Setting Importance
        ArrayList<String> arrayImportance = new ArrayList<>();
        arrayImportance.add("Non-negotiable");
        arrayImportance.add("Important");
        arrayImportance.add("Nice to Have");
        arrayImportance.add("Not Important");
        Spinner environmentSpinner = findViewById(R.id.env_pref_spinner);
        setupSpinner(environmentSpinner, arrayImportance, spinSelect);
        Spinner charRoleSpinner = findViewById(R.id.char_pref_spinner);
        setupSpinner(charRoleSpinner, arrayImportance, spinSelect);
        Spinner campaignRoleSpinner = findViewById(R.id.campaign_pref_spinner);
        setupSpinner(campaignRoleSpinner, arrayImportance, spinSelect);
        Spinner difficultyPrefSpinner = findViewById(R.id.difficulty_pref_spinner);
        setupSpinner(difficultyPrefSpinner, arrayImportance, spinSelect);
        Spinner scheduleSpinner = findViewById(R.id.schedule_pref_spinner);
        setupSpinner(scheduleSpinner, arrayImportance, spinSelect);

        // Listing Toggle Switch
        EditText charSlots = findViewById(R.id.char_slots_input);
        charSlots.setText("1");
        SwitchCompat listingTypeSwitchCompat = findViewById(R.id.listing_type_switch);
        listingTypeSwitchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                charSlots.setText("");
                charSlots.setFocusable(true);
                charSlots.setEnabled(true);
                charSlots.setCursorVisible(true);
                environmentSpinner.setEnabled(false);
                charRoleSpinner.setEnabled(false);
                campaignRoleSpinner.setEnabled(false);
                difficultyPrefSpinner.setEnabled(false);
                scheduleSpinner.setEnabled(false);
            }
            else {
                charSlots.setText("1");
                charSlots.setFocusable(false);
                charSlots.setEnabled(false);
                charSlots.setCursorVisible(false);
            }
        });
        //
        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(v -> {
            // getRoles() is a helper function that's defined in the ListAdapter class. Below
            // is the loop to get the entered data.
            List<Pair<String, String>> updatedRoles = adapter.getRoles();
            for (Pair<String, String> role : updatedRoles) {

            }
            processListing("This is a test");

        });




    } // End onCreate

    /**
     *      boolean campaign,
     *      String gameName,
     *      String environment,
     *      String startTime,
     *      String endTime,
     *      String difficulty,
     *      String role,
     *      String userProfileId
     */
    Listing newListing = new Listing(false, "testGame", "testEnv", "testStartTime", "testEndTime", "tesDiff", "testRole", "testProfileID");

    public void processListing(String p) {
        Log.i("test", p);
    }

    public void viewListing(View v){
        startActivity(new Intent(CreateListing.this, ViewListing.class));
    }

    // Helper function to reuse preference spinner for all options
    private void setupSpinner(Spinner spinner, ArrayList<String> items, String defaultText) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) parent.getChildAt(0);
                if (textView != null) {
                    textView.setTextColor(Color.WHITE);
                    textView.setTextSize(18);
                    if (position == 0) {
                        textView.setText(defaultText);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}