package com.rolecall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

                        // check whether the retrieved data is empty or not

                        if (enteredData.isEmpty() || enteredPassword.isEmpty() || enteredEmail.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Please Enter the Data", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                        }

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