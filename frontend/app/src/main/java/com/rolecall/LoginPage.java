
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.json.simple.JSONArray;

import org.json.simple.JSONObject;

import org.json.simple.parser.*;
import java.net.URLEncoder;
import java.util.concurrent.CountDownLatch;


public class LoginPage extends AppCompatActivity {
    String enteredData;
    String enteredPassword;


    public String getEnteredPassword() {
        return enteredPassword;
    }

    public void setEnteredPassword(String enteredPassword) {
        this.enteredPassword = enteredPassword;
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
        setContentView(R.layout.activity_login_page);
        EditText editText;
        EditText editPassword;
        EditText editEmail;
        Button submitButton;

        editText = (EditText) findViewById(R.id.editText);
        editPassword= (EditText)findViewById(R.id.editpassword);
        submitButton = (Button) findViewById(R.id.submitButton);


        submitButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        setEnteredData(editText.getText().toString());
                        setEnteredPassword(editPassword.getText().toString());

                        Thread HttpThread= new  Thread(() -> {
                            try {

                                String urlString = "http://10.0.2.2:5000/login";
                                String username = "Berhans";
                                String password = "BEESecret";
                                JSONParser jsonParser = new JSONParser();


                                String encodedParams = "username=" + URLEncoder.encode(getEnteredData(), "UTF-8") +
                                        "&password=" + URLEncoder.encode(getEnteredPassword(), "UTF-8");

                                urlString+= "?"+encodedParams;
                                URL url = new URL(urlString);


                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");


                                int responseCode = conn.getResponseCode();
                                if (responseCode == 200) {

                                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                    String inputLine;
                                    StringBuilder content = new StringBuilder();

                                    while ((inputLine = in.readLine()) != null) {
                                        content.append(inputLine);
                                    }

                                    in.close();
                                    conn.disconnect();

                                    String holder = "";
                                    JSONParser parser = new JSONParser();
                                    JSONArray jsonResponse = (JSONArray) parser.parse(content.toString());
                                    for (Object element : jsonResponse) {
                                        JSONObject jsonObject = (JSONObject) element;

                                        String name = (String) jsonObject.get("name");
                                        String SQLpassword = (String) jsonObject.get("password");
                                        holder=SQLpassword;

                                        System.out.println("Your Username: " + name + ", Your Password: " + SQLpassword);
                                    }


                                    if (holder.isEmpty()) {
                                        View.OnClickListener listener = new View.OnClickListener() {

                                        @Override
                                        public void onClick (View view){
                                            Intent intent = new Intent(view.getContext(), MainActivity.class);
                                            view.getContext().startActivity(intent);
                                        }
                                    };
                                        submitButton.setOnClickListener(listener);
                                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show());


                                    } else {
                                        final String[] userId = new String[1];
                                        CountDownLatch latch = new CountDownLatch(1);
                                        if(!jsonResponse.isEmpty()){
                                            JSONObject jsonObject = (JSONObject) jsonResponse.get(0);
                                            String name = (String) jsonObject.get("name");
                                            FlaskClient flaskClient = new FlaskClient();
                                            flaskClient.getActiveUsersId(name, new ResponseCallback() {
                                                @Override
                                                public void onSuccess(String response) {
                                                    try{
                                                        JSONArray jsonArray = (JSONArray) parser.parse(response);
                                                        JSONObject responseObject = (JSONObject) jsonArray.get(0);
                                                        if (responseObject != null) {
                                                            userId[0] = responseObject.get("id").toString();
                                                        }
                                                    } catch (ParseException e){
                                                        e.printStackTrace();
                                                    } finally {
                                                        latch.countDown();
                                                    }
                                                }
                                                @Override
                                                public void onError(IOException e) {
                                                    e.printStackTrace();
                                                    latch.countDown();
                                                }
                                            });
                                        }
                                        latch.await();
                                        if(userId[0] != null){
                                            runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show());
                                            Intent intent = new Intent(view.getContext(), UserListingsPage.class);
                                            intent.putExtra("userId", userId[0]);
                                            view.getContext().startActivity(intent);
                                        } else {
                                            runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show());
                                        }
                                    }
                                } else {
                                    System.out.println("Request failed with status: " + responseCode);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });HttpThread.start();
                    }
                });

        Button mybutton = findViewById(R.id.nextButton);
        mybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Profile.class);
                intent.putExtra("Username",enteredData);
                intent.putExtra("Password",enteredPassword);
                view.getContext().startActivity(intent);}
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}

