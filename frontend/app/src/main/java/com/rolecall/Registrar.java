package com.rolecall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Objects;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rolecall.R;

public class Registrar extends AppCompatActivity {
    String enteredData;
    String enteredPassword;
    String enteredEmail;

    public String getEnteredPassword() {
        return enteredPassword;
    }

    public void setEnteredPassword(String enteredPassword) {
        this.enteredPassword = enteredPassword;
    }

    public String getEnteredEmail() {
        return enteredEmail;
    }

    public void setEnteredEmail(String enteredEmail) {
        this.enteredEmail = enteredEmail;
    }

    public String getEnteredData() {
        return enteredData;
    }

    public void setEnteredData(String enteredData) {
        this.enteredData = enteredData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar);

        EditText editText;
        EditText editPassword;
        EditText editEmail;
        Button submitButton;

        editText = (EditText) findViewById(R.id.editText);
        editPassword= (EditText)findViewById(R.id.editpassword);
        editEmail= (EditText)findViewById(R.id.editemail);
        submitButton = (Button) findViewById(R.id.submitButton);


        submitButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        setEnteredData(editText.getText().toString());
                        setEnteredPassword(editPassword.getText().toString());
                        setEnteredEmail(editEmail.getText().toString());

                        if(Objects.equals(getEnteredEmail(), "") || Objects.equals(getEnteredPassword(), "")|| Objects.equals(getEnteredData(), "")){
                            Intent intent = getIntent();
                            Intent Mover= new Intent(getApplicationContext(),MainActivity.class);
                            runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Please fill in all Entries.", Toast.LENGTH_SHORT).show());
                            startActivity(Mover);
                            return;
                        }

                         Thread HttpThread= new  Thread(() -> {
                            try {
                                String urlString = "http://10.0.2.2:5000/register";
                                String Inputs = "username=" + URLEncoder.encode(getEnteredData(), "UTF-8") +
                                        "&password=" + URLEncoder.encode(getEnteredPassword(), "UTF-8") +
                                        "&email=" + URLEncoder.encode(getEnteredEmail(), "UTF-8");

                                urlString += "?" + Inputs;
                                URL url = new URL(urlString);
                                HttpURLConnection myConnection = (HttpURLConnection) url.openConnection();
                                myConnection.setRequestMethod("GET");

                                int responseCode = myConnection.getResponseCode();
                                if (responseCode == 200) {
                                    BufferedReader in = new BufferedReader(new InputStreamReader(myConnection.getInputStream()));
                                    in.close();
                                    myConnection.disconnect();
                                    Intent intent = getIntent();
                                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_SHORT).show());
                                    Intent Mover= new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(Mover);

                                } else {
                                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Request Failed", Toast.LENGTH_SHORT).show());
                                }
                            } catch (Exception e) {
                                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Massive Failure", Toast.LENGTH_SHORT).show());
                            }
                        });HttpThread.start();
                    }
                });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}