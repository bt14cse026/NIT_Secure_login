package com.example.vikram.newpageupdated.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vikram.newpageupdated.R;


/**
 * Created by Ankit on 1/29/2016.
 */
public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        Intent intent = getIntent();
        TextView name = (TextView) findViewById(R.id.name);
        TextView email = (TextView) findViewById(R.id.user_email);
        TextView password = (TextView) findViewById(R.id.password_user);
        ImageView img1 = (ImageView) findViewById(R.id.profile1);
        TextView scroll = (TextView) findViewById(R.id.scroll);
        TextView gender = (TextView) findViewById(R.id.gender);
        Button bt1=(Button)findViewById(R.id.create);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.google.co.in/search?q=nit+uttarakhand&oq=nit+uttarakhand&aqs=chrome.0.69i59l2j69i60.9875j0j9&sourceid=chrome&ie=UTF-8"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                 }
        });


        byte[] bytes = getIntent().getByteArrayExtra("bitmapbytes");
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        img1.setImageBitmap(bmp);

        name.setText(intent.getStringExtra("name"));
        email.setText(intent.getStringExtra("email"));
        password.setText(intent.getStringExtra("pass"));
        scroll.setText(intent.getStringExtra("dob"));
        gender.setText(intent.getStringExtra("gender"));


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.home:
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                return true;
            case R.id.help:
                Toast.makeText(getApplicationContext(), "Please visit on: www.nituk.ac.in", Toast.LENGTH_LONG).show();
                return true;
            case R.id.logout:
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                return true;
            case R.id.action_settings:
                return true;
            case R.id.Refresh:
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
