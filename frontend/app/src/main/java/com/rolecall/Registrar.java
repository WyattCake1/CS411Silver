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

                        new Thread(() -> {
                            try {
                                String urlString = "http://10.0.2.2:5000/register";
                                String encodedParams = "username=" + URLEncoder.encode(getEnteredData(), "UTF-8") +
                                        "&password=" + URLEncoder.encode(getEnteredPassword(), "UTF-8") +
                                        "&email=" + URLEncoder.encode(getEnteredEmail(), "UTF-8");
                                urlString += "?" + encodedParams;

                                URL url = new URL(urlString);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");

                                int responseCode = conn.getResponseCode();
                                if (responseCode == 200) {
                                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                                    in.close();
                                    conn.disconnect();

                                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Request Successful", Toast.LENGTH_SHORT).show());
                                } else {
                                    System.out.println("Request failed with status: " + responseCode);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Request Failed", Toast.LENGTH_SHORT).show());
                            }
                        }).start();
                    }
                });

        Button mybutton = findViewById(R.id.nextButton);
        mybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Profile.class);
                intent.putExtra("Username",enteredData);
                intent.putExtra("Password",enteredPassword);
                intent.putExtra("Email",enteredEmail);
                view.getContext().startActivity(intent);}
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}