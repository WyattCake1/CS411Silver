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

    private int listingType = 0;

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
        // Roles for dialog recycler view
        ArrayList<Pair<String, String>> roles = new ArrayList<>();
        roles.add(new Pair<>("0", "Tank"));
        roles.add(new Pair<>("0", "DPS"));
        roles.add(new Pair<>("0", "Face"));
        roles.add(new Pair<>("0", "Healer"));
        roles.add(new Pair<>("0", "Support"));
        //------------------------------------------------------------------------------------------
        // Adapter for the recycler view
        RecyclerView recyclerView = findViewById(R.id.roles_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ListAdapter adapter = new ListAdapter(roles);
        recyclerView.setAdapter(adapter);
        //------------------------------------------------------------------------------------------
        // Preferred environment
        ArrayList<String> arrayEnv = new ArrayList<>();
        arrayEnv.add("Select");
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //------------------------------------------------------------------------------------------
        // Max distance
        ArrayList<String> arrayDist = new ArrayList<>();
        arrayDist.add("Select");
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //------------------------------------------------------------------------------------------
        // Campaign difficulty
        ArrayList<String> arrayDiff = new ArrayList<>();
        arrayDiff.add("Select");
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

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //------------------------------------------------------------------------------------------
        // Setting Importance
        ArrayList<String> arrayImportance = new ArrayList<>();
        arrayImportance.add("Select");
        arrayImportance.add("Non-negotiable");
        arrayImportance.add("Important");
        arrayImportance.add("Nice to Have");
        arrayImportance.add("Not Important");
        Spinner environmentSpinner = findViewById(R.id.env_pref_spinner);
        setupSpinner(environmentSpinner, arrayImportance);
        Spinner charRoleSpinner = findViewById(R.id.char_pref_spinner);
        setupSpinner(charRoleSpinner, arrayImportance);
        Spinner campaignRoleSpinner = findViewById(R.id.campaign_pref_spinner);
        setupSpinner(campaignRoleSpinner, arrayImportance);
        Spinner difficultyPrefSpinner = findViewById(R.id.difficulty_pref_spinner);
        setupSpinner(difficultyPrefSpinner, arrayImportance);
        Spinner scheduleSpinner = findViewById(R.id.schedule_pref_spinner);
        setupSpinner(scheduleSpinner, arrayImportance);
        //------------------------------------------------------------------------------------------
        // Listing Toggle Switch
        EditText charSlots = findViewById(R.id.char_slots_input);
        charSlots.setText("1");
        SwitchCompat listingTypeSwitchCompat = findViewById(R.id.listing_type_switch);
        listingTypeSwitchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                listingType = 0;
                charSlots.setText("0");
                charSlots.setFocusable(true);
                charSlots.setEnabled(true);
                charSlots.setCursorVisible(true);
                distance_spinner.setFocusable(false);
                distance_spinner.setEnabled(false);
                distance_spinner.setSelection(0);
                environment_spinner.setSelection(0);
                difficulty_spinner.setSelection(0);
                environmentSpinner.setEnabled(false);
                environmentSpinner.setSelection(0);
                charRoleSpinner.setEnabled(false);
                charRoleSpinner.setSelection(0);
                campaignRoleSpinner.setEnabled(false);
                campaignRoleSpinner.setSelection(0);
                difficultyPrefSpinner.setEnabled(false);
                difficultyPrefSpinner.setSelection(0);
                scheduleSpinner.setEnabled(false);
                scheduleSpinner.setSelection(0);
            }
            else {
                Intent intent = new Intent(CreateListing.this, CreateListing.class);
                startActivity(intent);
                finish();
                listingType = 1;
                charSlots.setText("1");
                charSlots.setFocusable(false);
                charSlots.setEnabled(false);
                charSlots.setCursorVisible(false);
                distance_spinner.setEnabled(true);
                distance_spinner.setSelection(0);
                environmentSpinner.setEnabled(true);
                environmentSpinner.setSelection(0);
                charRoleSpinner.setEnabled(true);
                charRoleSpinner.setSelection(0);
                campaignRoleSpinner.setEnabled(true);
                campaignRoleSpinner.setSelection(0);
                difficultyPrefSpinner.setEnabled(true);
                difficultyPrefSpinner.setSelection(0);
                scheduleSpinner.setEnabled(true);
                scheduleSpinner.setSelection(0);
            }
        });
        //------------------------------------------------------------------------------------------
        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(CreateListing.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        //------------------------------------------------------------------------------------------
        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(v -> {
            // Get game name and character slots
            EditText gameName = findViewById(R.id.game_name_input);
            String name = String.valueOf(gameName.getText());
            String slots = String.valueOf(charSlots.getText());

            // Get distance, env, difficulty
            String distance = distance_spinner.getSelectedItem().toString();
            String environment = environment_spinner.getSelectedItem().toString();
            String difficulty = difficulty_spinner.getSelectedItem().toString();

            // Build schedule
            ArrayList<String> schedule = new ArrayList<String>();
            String start;
            String end;

            EditText sundayStart = findViewById(R.id.sunday_start_time);
            EditText sundayEnd = findViewById(R.id.sunday_end_time);
            schedule.add("sunday");
            start = String.valueOf(sundayStart.getText());
            end = String.valueOf(sundayEnd.getText());
            schedule.add(start);
            schedule.add(end);

            EditText mondayStart = findViewById(R.id.monday_start_time);
            EditText mondayEnd = findViewById(R.id.monday_end_time);
            schedule.add("monday");
            start = String.valueOf(mondayStart.getText());
            end = String.valueOf(mondayEnd.getText());
            schedule.add(start);
            schedule.add(end);

            EditText tuesdayStart = findViewById(R.id.tuesday_start_time);
            EditText tuesdayEnd = findViewById(R.id.tuesday_end_time);
            schedule.add("tuesday");
            start = String.valueOf(tuesdayStart.getText());
            end = String.valueOf(tuesdayEnd.getText());
            schedule.add(start);
            schedule.add(end);

            EditText wedStart = findViewById(R.id.wednesday_start_time);
            EditText wedEnd = findViewById(R.id.wednesday_end_time);
            schedule.add("wednesday");
            start = String.valueOf(wedStart.getText());
            end = String.valueOf(wedEnd.getText());
            schedule.add(start);
            schedule.add(end);

            EditText thursdayStart = findViewById(R.id.thursday_start_time);
            EditText thursdayEnd = findViewById(R.id.thursday_end_time);
            schedule.add("thursday");
            start = String.valueOf(thursdayStart.getText());
            end = String.valueOf(thursdayEnd.getText());
            schedule.add(start);
            schedule.add(end);

            EditText fridayStart = findViewById(R.id.friday_start_time);
            EditText fridayEnd = findViewById(R.id.friday_end_time);
            schedule.add("friday");
            start = String.valueOf(fridayStart.getText());
            end = String.valueOf(fridayEnd.getText());
            schedule.add(start);
            schedule.add(end);

            EditText saturdayStart = findViewById(R.id.saturday_start_time);
            EditText saturdayEnd = findViewById(R.id.saturday_end_time);
            schedule.add("saturday");
            start = String.valueOf(saturdayStart.getText());
            end = String.valueOf(saturdayEnd.getText());
            schedule.add(start);
            schedule.add(end);

            String finalSchedule = scheduleToJson(schedule);

            // Build preferences
            String envPref = environmentSpinner.getSelectedItem().toString();
            String charPref = charRoleSpinner.getSelectedItem().toString();
            String campPref = campaignRoleSpinner.getSelectedItem().toString();
            String diffPref = difficultyPrefSpinner.getSelectedItem().toString();
            String schedulePref = scheduleSpinner.getSelectedItem().toString();

            List<Pair<String, String>> updatedRoles = adapter.getRoles();
            String finalRoles = rolesToJson(updatedRoles);

            String finalPreferences = prefToJson(envPref, charPref, campPref, diffPref, schedulePref);

            processListing(listingType, name, slots, distance, environment, difficulty, finalSchedule, finalRoles, finalPreferences);

        });
    } // End onCreate

    public void processListing(int listingType, String name, String slots, String distance, String environment, String difficulty, String finalSchedule, String finalRoles, String finalPreferences) {
        Log.i("Game name: ", name);
        Log.i("Character Slots: ", slots);
        Log.i("Max Distance: ", distance);
        Log.i("Environment: ", environment);
        Log.i("Difficulty: ", difficulty);
        Log.i("Schedule: ", finalSchedule);
        Log.i("Roles: ", finalRoles);
        Log.i("Preferences: ", finalPreferences);
        Listing newListing = new Listing();
    }
    /**
     * This method creates a JSON from the listings roles(s)
     * @param roles - A list of key value pairs
     * @return JSON
     */
    public String rolesToJson(List<Pair<String, String>> roles) {
        StringBuilder sb = new StringBuilder();

        sb.append("{\n  \"roles\": {\n");
        for (Pair<String, String> role : roles) {
            String roleCount = role.first;
            String roleName = role.second;
            sb.append("    \"").append(roleName.toLowerCase()).append("\": ")
                    .append(roleCount).append(",\n");
        }

        if (!roles.isEmpty()) {
            sb.setLength(sb.length() - 2);
            sb.append("\n");
        }

        sb.append("  }\n}");
        return sb.toString();
    }

    /**
     * This method creates a JSON from the listings schedule
     * @param schedule - An array of days with starting and end times.
     * @return JSON
     */
    public String scheduleToJson(ArrayList<String> schedule) {
        StringBuilder sb = new StringBuilder();

        sb.append("{\n  \"schedule\": {\n");
        for (int i = 0; i < schedule.size(); i += 3) {
            String day = schedule.get(i).toLowerCase();
            String start = schedule.get(i + 1);
            String end = schedule.get(i + 2);
            sb.append("    \"").append(day).append("\": [\"")
                    .append(start).append("\", \"").append(end).append("\"]");

            if (i + 3 < schedule.size()) {
                sb.append(",");
            }

            sb.append("\n");
        }

        sb.append("  }\n}");
        return sb.toString();
    }

    /**
     * This method takes character listing preferences and puts them into a JSON format
     * @param env
     * @param character
     * @param campaign
     * @param difficulty
     * @param schedule
     * @return JSON
     */
    public String prefToJson(String env, String character, String campaign, String difficulty, String schedule) {
        StringBuilder sb = new StringBuilder();

        sb.append("{\n \"preferences\": {\n");
        sb.append("\"environment\": \"").append(env).append("\",\n");
        sb.append("\"character\": \"").append(character).append("\",\n");
        sb.append("\"campaign\": \"").append(campaign).append("\",\n");
        sb.append("\"difficulty\": \"").append(difficulty).append("\",\n");
        sb.append("\"schedule\": \"").append(schedule).append("\"\n");

        sb.append("  }\n}");
        return sb.toString();
    }


    public void viewListing(View v){
        startActivity(new Intent(CreateListing.this, ViewListing.class));
    }

    // Helper function to reuse preference spinner for all options
    private void setupSpinner(Spinner spinner, ArrayList<String> items) {
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

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}