package com.rolecall;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rolecall.R;

public class Profile extends AppCompatActivity {
    Registrar myObject = new Registrar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        Intent intent= getIntent();
        String holder= intent.getStringExtra("Username");


        TextView textView = (TextView) findViewById(R.id.usertext);
        textView.setText(holder);
        holder= intent.getStringExtra("Password");
        textView= (TextView) findViewById(R.id.userpassword);
        textView.setText(holder);
        holder= intent.getStringExtra("Email");
        textView= (TextView) findViewById(R.id.useremail);
        textView.setText(holder);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}