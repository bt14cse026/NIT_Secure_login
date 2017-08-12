package com.example.vikram.newpageupdated.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vikram.newpageupdated.R;
import com.example.vikram.newpageupdated.adapter.LoginDatabaseAdapter;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 01/27/2016.
 */
public class RegistrationActivity extends AppCompatActivity {

    //declare variable
    private static final int SELECT_PICTURE = 1;
    Bitmap bitmap;
    ImageView scroll_icon, profile, gender;
    EditText dateBirth, username, email, password, r_password, gen_text;
    Button create_button, choose_button;
    LoginDatabaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting default screen to registration_page.xml
        setContentView(R.layout.registration_page);

        // get Instance  of Database Adapter
        loginDataBaseAdapter=new LoginDatabaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        //getting id for widgets
        scroll_icon = (ImageView) findViewById(R.id.scroll);
        profile = (ImageView) findViewById(R.id.profile);
        dateBirth = (EditText) findViewById(R.id.dob);
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        r_password = (EditText) findViewById(R.id.retype_password);
        create_button = (Button) findViewById(R.id.create);
        choose_button = (Button) findViewById(R.id.choose);
        gender = (ImageView) findViewById(R.id.gender);
        gen_text = (EditText) findViewById(R.id.gen_text);

        //stop popup of keyboard automatically
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //add click listener for choosing sex
        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSex();
            }
        });

        //add click listener for choose profile picture
        choose_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        //add click listener for select date of birth
        scroll_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });

        //add click listener for creating profile of user
        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username1 = username.getText().toString();
                Log.d("username",""+username1);
                final String email1= email.getText().toString();
                Log.d("email",""+email1);
                final String pass = password.getText().toString();
                Log.d("pass",""+pass);
                String r_pass=r_password.getText().toString();
                Log.d("r_pass",""+r_pass);

                if (!isValidName(username1)) {
                    username.setError("Invalid Name");
                } else if (!isValidEmail(email1)) {
                    email.setError("Invalid email");
                } else if (!isValidPass(pass)) {
                    password.setError("Invalid Password");
                }else if (!r_pass.equals(pass)) {
                    r_password.setError("Password does not match.");
                }else{

                    //create dialog for last confirmation of profile creation
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegistrationActivity.this);
                    alertDialogBuilder.setMessage("Are you sure,You wanted to create profile");

                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            loginDataBaseAdapter.insertEntry(username1,pass,email1);
                            Toast.makeText(RegistrationActivity.this, "Your profile created successfully",Toast.LENGTH_SHORT).show();
                            profile.buildDrawingCache();
                            Bitmap image= profile.getDrawingCache();
                            Intent intent  = new Intent(RegistrationActivity.this,ProfileActivity.class);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                            byte[] bytes = stream.toByteArray();
                            intent.putExtra("bitmapbytes",bytes);
                            intent.putExtra("name",username1);
                            intent.putExtra("email",email1);
                            intent.putExtra("pass",pass);
                            intent.putExtra("dob",dateBirth.getText().toString());
                            intent.putExtra("gender",gen_text.getText().toString());
                            startActivity(intent);
                        }
                    });
                    alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(RegistrationActivity.this, "You are not create profile", Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog alertDialog= alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginDataBaseAdapter.close();
    }

    //method for open sex dialog
    private void selectSex() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
        final String[] gender = {"Male", "Female"};

        builder.setTitle("Choose a gender:");
        builder.setItems(gender, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0)
                {
                    gen_text.setText("Male");
                }else{
                    gen_text.setText("Female");
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        Dialog dialog = builder.create();
        dialog.show();
    }

    //method for password validation
    private boolean isValidPass(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;    }

    //method for email validation
    private boolean isValidEmail(String email1) {
        if (email1 == null) {
            return false;
        } else {

            return android.util.Patterns.EMAIL_ADDRESS.matcher(email1)
                    .matches();
        }
    }

    //method for name validation
    private boolean isValidName(String username1) {
        String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";

        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        Matcher matcher = pattern.matcher(username1);
        return matcher.matches();
    }

    //method for open calender for select DOB
    private void show() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(RegistrationActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        dateBirth.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);

                    }
                }, year, month, day);
        dpd.show();

    }

    //method for choose image
    private void chooseImage() {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK) try {
            if (bitmap != null) {
                bitmap.recycle();
            }
            InputStream stream = getContentResolver().openInputStream(data.getData());
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            bitmap = BitmapFactory.decodeStream(stream, null, options);
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            double scale = 100.0 / height;
            height = (int) (height * scale);
            width = (int) (width * scale);
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
            stream.close();
            profile.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
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
                return true;
            case R.id.action_settings:
                return true;
            case R.id.Refresh:
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
