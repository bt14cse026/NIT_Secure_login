package com.example.vikram.newpageupdated.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.vikram.newpageupdated.R;
import com.example.vikram.newpageupdated.adapter.LoginDatabaseAdapter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 01/27/2016.
 */
public class LoginActivity extends AppCompatActivity {

    //declare variable
    EditText login,password;
    Button signin;
    TextView forgot;
    LoginDatabaseAdapter loginDatabaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting default screen to next_login.xml
        setContentView(R.layout.next_login);

        //getting id for widgets
        login = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        signin = (Button) findViewById(R.id.log_button);
        forgot = (TextView) findViewById(R.id.forgot);

        // create a instance of SQLite Database
        loginDatabaseAdapter=new LoginDatabaseAdapter(this);
        loginDatabaseAdapter=loginDatabaseAdapter.open();

        //add up button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //stop popup of keyboard automatically
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //add click listener for login
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = login.getText().toString();
                Log.d("user",""+user);

                String pass = password.getText().toString();
                Log.d("pass",""+pass);

                //fetch data from database
                String storedPassword=loginDatabaseAdapter.getSinlgeEntry(user);
                if(!isValidUsername(user)){
                    login.setError("Invalid UserID");
                }else if(!isValidPassword(pass)){
                    password.setError("Invalid Password");
                }else {
                    if (pass.equals(storedPassword)) {

                        Toast.makeText(getApplicationContext(), "You are successfully log in You are going to NIT UTTARAKHAND website.", Toast.LENGTH_SHORT).show();
                        Uri uri = Uri.parse("http://nituk.ac.in"); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter correct data", Toast.LENGTH_SHORT).show();

                    }
            }
        }

        });

        //add click listener for forgot password
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"For password Recovery: Log on to www.nituk.ac.in.org",Toast.LENGTH_LONG).show();
            }
        });
    }

        //method for checking condition of valid password
        private boolean isValidPassword(String pass) {
            if (pass != null && pass.length() > 6) {
                return true;
            }
            return false;
        }

        //method for checking condition of valid user
        private boolean isValidUsername(String user) {
            String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";
            Pattern pattern = Pattern.compile(USERNAME_PATTERN);
            Matcher matcher = pattern.matcher(user);
            return matcher.matches();
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
                Toast.makeText(getApplicationContext(),"Please visit on: www.drupalchamp.org",Toast.LENGTH_LONG).show();
                return true;
            case R.id.logout:
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            case R.id.action_settings:
                return true;
            case R.id.Refresh:
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }

        }
}
