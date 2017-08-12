package com.example.vikram.newpageupdated.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.vikram.newpageupdated.R;


/**
 * Created by user on 01/27/2016.
 */
public class HomeActivity extends Activity {

    //declare variable
    Button login_page1, registration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting default screen to login_page.xml
        setContentView(R.layout.login_page);

        //add click listener for going to login page
        login_page1=(Button) findViewById(R.id.button1);
        login_page1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Please wait for login:", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        //add click listener for going to registration page
        registration=(Button) findViewById(R.id.button2);
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Please wait",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
            }
        });


    }
}
