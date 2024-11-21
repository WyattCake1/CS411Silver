package com.rolecall;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rolecall.R;

import java.util.ArrayList;

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
        // Create listing page data structures for drop down menus.
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
                ((TextView)parent.getChildAt(0)).setTextSize(20);
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(CreateListing.this, "Selected Location: " + item, Toast.LENGTH_SHORT).show();
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
                ((TextView)parent.getChildAt(0)).setTextSize(20);
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(CreateListing.this, "Selected Max Distance: " + item, Toast.LENGTH_SHORT).show();
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
                ((TextView)parent.getChildAt(0)).setTextSize(20);
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(CreateListing.this, "Selected Difficulty: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //------------------------------------------------------------------------------------------
        // Create listing page logic
        Button roleCampaignButton = findViewById(R.id.pref_role_campaign_button);
        Button roleCharacterButton = findViewById(R.id.pref_role_character_button);
        EditText charSlots = findViewById(R.id.char_slots_input);
        // Character radio button
        RadioButton radioButtonChar = findViewById(R.id.radio_character);
        radioButtonChar.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Listing type is character, enable preferred character button and disable character slots input and preferred role campaign button
                roleCharacterButton.setEnabled(true);
                roleCharacterButton.setCursorVisible(true);

                roleCampaignButton.setEnabled(false);
                roleCampaignButton.setCursorVisible(false);

                charSlots.setFocusable(false);
                charSlots.setEnabled(false);
                charSlots.setCursorVisible(false);


            }
        });

        // Campaign radio button
        RadioButton radioButtonCamp = findViewById(R.id.radio_campaign);
        radioButtonCamp.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Listing type is campaign, enable number of character slots and preferred role campaign, disable preferred character role
                charSlots.setFocusable(true);
                charSlots.setEnabled(true);
                charSlots.setCursorVisible(true);

                roleCampaignButton.setEnabled(true);
                roleCampaignButton.setCursorVisible(true);

                roleCharacterButton.setEnabled(false);
                roleCharacterButton.setCursorVisible(false);
                roleCharacterButton.setKeyListener(null);
            }
        });


    } // End onCreate

    public void viewListing(View v){
        startActivity(new Intent(CreateListing.this, ViewListing.class));
    }
}