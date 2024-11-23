package com.rolecall;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rolecall.R;
import com.google.android.material.snackbar.Snackbar;

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
        // Roles for dialog recycler view
        ArrayList<Pair<String, String>> roles = new ArrayList<>();
        roles.add(new Pair<>("0", "Tank"));
        roles.add(new Pair<>("0", "DPS"));
        roles.add(new Pair<>("0", "Face"));
        roles.add(new Pair<>("0", "Healer"));
        roles.add(new Pair<>("0", "Support"));
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
        Button roleCampaignButton = findViewById(R.id.pref_role_campaign_button);
        Button roleCharacterButton = findViewById(R.id.pref_role_character_button);
        // Turn off buttons on load
        roleCharacterButton.setEnabled(false);
        roleCharacterButton.setCursorVisible(false);
        roleCampaignButton.setEnabled(false);
        roleCampaignButton.setCursorVisible(false);
        // Listing Toggle Switch
        EditText charSlots = findViewById(R.id.char_slots_input);
        SwitchCompat listingTypeSwitchCompat = findViewById(R.id.listing_type_switch);
        listingTypeSwitchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                roleCharacterButton.setEnabled(true);
                roleCharacterButton.setCursorVisible(true);

                roleCampaignButton.setEnabled(false);
                roleCampaignButton.setCursorVisible(false);

                charSlots.setFocusable(false);
                charSlots.setEnabled(false);
                charSlots.setCursorVisible(false);
            }
            else {
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
        // Role Selection
        roleCampaignButton.setOnClickListener(v -> {
            ListRoleDialog dialogFragment = ListRoleDialog.newInstance(roles);
            dialogFragment.show(getSupportFragmentManager(), "ListRoleDialog");
        });
        roleCharacterButton.setOnClickListener(v -> {
            ListRoleDialog dialogFragment = ListRoleDialog.newInstance(roles);
            dialogFragment.show(getSupportFragmentManager(), "ListRoleDialog");
        });



    } // End onCreate

    public void viewListing(View v){
        startActivity(new Intent(CreateListing.this, ViewListing.class));
    }
}