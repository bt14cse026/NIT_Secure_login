package com.example.vikram.newpageupdated.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.vikram.newpageupdated.R;


/**
 * Created by Ankit on 1/27/2016.
 */
public class SplashActivity extends Activity {

    //setting time duration for splash screen
    private static int SPLASH_TIME_OUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting default screen to activity_spalsh.xml
        setContentView(R.layout.activity_splash);

        //getting id for widgets
        ImageView logo = (ImageView) findViewById(R.id.logo);

        //create & load animation for app logo
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.zoom_out);
        logo.startAnimation(animation);

        //start new activity through handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
