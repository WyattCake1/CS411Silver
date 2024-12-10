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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateListing extends AppCompatActivity {

    private boolean listingType = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        String userProfileId = intent.getStringExtra("userId");

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
        ArrayList<Pair<String, String>> arrayRoles = new ArrayList<>();
        arrayRoles.add(new Pair<>("0", "tank"));
        arrayRoles.add(new Pair<>("0", "dps"));
        arrayRoles.add(new Pair<>("0", "face"));
        arrayRoles.add(new Pair<>("0", "healer"));
        arrayRoles.add(new Pair<>("0", "support"));
        //------------------------------------------------------------------------------------------
        // Adapter for the recycler view
        RecyclerView recyclerView = findViewById(R.id.roles_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ListAdapter adapter = new ListAdapter(arrayRoles);
        recyclerView.setAdapter(adapter);
        //------------------------------------------------------------------------------------------
        // Preferred environment
        ArrayList<String> arrayEnv = new ArrayList<>();
        arrayEnv.add("select");
        arrayEnv.add("in-person");
        arrayEnv.add("online");
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
        arrayDist.add("select");
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
        arrayDiff.add("select");
        arrayDiff.add("first Game");
        arrayDiff.add("casual");
        arrayDiff.add("intermediate");
        arrayDiff.add("advanced");
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
        ArrayList<String> arrayDay = new ArrayList<>();
        arrayDay.add("select day");
        arrayDay.add("sunday");
        arrayDay.add("monday");
        arrayDay.add("tuesday");
        arrayDay.add("wednesday");
        arrayDay.add("thursday");
        arrayDay.add("friday");
        arrayDay.add("saturday");
        ArrayAdapter<String> adapterSchedule = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayDay);
        adapterSchedule.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        Spinner day_spinner = findViewById(R.id.schedule_spinner);
        day_spinner.setAdapter(adapterSchedule);
        day_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        // Listing Toggle Switch
        EditText charSlots = findViewById(R.id.char_slots_input);
        charSlots.setText("1");
        SwitchCompat listingTypeSwitchCompat = findViewById(R.id.listing_type_switch);
        listingTypeSwitchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                listingType = true; // Campaign listing
                charSlots.setText("character slots");
                charSlots.setFocusable(true);
                charSlots.setEnabled(true);
                charSlots.setCursorVisible(true);
                distance_spinner.setFocusable(false);
                distance_spinner.setEnabled(false);
                distance_spinner.setSelection(0);
                environment_spinner.setSelection(0);
                difficulty_spinner.setSelection(0);
            }
            else {
                listingType = false; // Character listing
                charSlots.setText("1");
                charSlots.setFocusable(false);
                charSlots.setEnabled(false);
                charSlots.setCursorVisible(false);
                distance_spinner.setEnabled(true);
                distance_spinner.setSelection(0);
            }
        });
        //------------------------------------------------------------------------------------------
        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            Intent back_intent = new Intent(CreateListing.this, MainActivity.class);
            startActivity(back_intent);
            finish();
        });
        //------------------------------------------------------------------------------------------
        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(v -> {

            EditText gameName = findViewById(R.id.game_name_input);
            String name = String.valueOf(gameName.getText());

            String environment = environment_spinner.getSelectedItem().toString();
            String difficulty = difficulty_spinner.getSelectedItem().toString();

            String day = day_spinner.getSelectedItem().toString();
            EditText startTime = findViewById(R.id.start_time);
            EditText endTime = findViewById(R.id.end_time);
            String start = String.valueOf(startTime.getText());
            String end = String.valueOf(endTime.getText());

            List<Pair<String, String>> updatedRoles = adapter.getRoles();
            String roles = rolesToJson(updatedRoles);

            processListing(listingType, name, environment ,day, start, end, difficulty, roles, userProfileId);

            Intent finish_intent = new Intent(CreateListing.this, MainActivity.class);
            startActivity(finish_intent);
            finish();
        });
    } // End onCreate

    /**
     * This method creates a listing object out of the passed parameter. The listing object is then
     * passed to the flask client which then saves the user listing in the database.
     * @param campaign - Boolean representing campaign yes or no
     * @param gameName - String game title
     * @param environment - String session location
     * @param day - String session day
     * @param startTime - String session start time
     * @param endTime - String session end time
     * @param difficulty - String session difficulty level
     * @param role - String role(s) chosen
     * @param userProfileId - Used to reference what user created the listing
     */
    public void processListing(boolean campaign, String gameName, String environment,String day, String startTime, String endTime,
                               String difficulty, String role, String userProfileId) {

        Listing newListing = new Listing(campaign, gameName, environment ,day, startTime, endTime, difficulty, role, userProfileId);
        FlaskClient flask = new FlaskClient();
        ResponseCallback response = new ResponseCallback() {
            @Override
            public void onSuccess(String response) {}
            @Override
            public void onError(IOException e) {Log.e("FlaskClient Listing Call", "Request failed: " + e.getMessage(), e);}
        };

        flask.saveListing(newListing, response);

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

        sb.append("{\n \"importance\": {\n");
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