package com.challenge.androidchallenge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.challenge.androidchallenge.Retrofit.POJO.User;
import com.challenge.androidchallenge.Retrofit.ServiceClient;
import com.challenge.androidchallenge.Utility.SessionManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    SessionManager session;

    EditText etName, etEmail;
    Button bSignup;

    //Booleans and int used to trigger proper prompt to user when invalid data is entered into fields
    boolean validName, validEmail;
    int errorCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get the session to see if the user has registered already
        session = new SessionManager(this);

        //If the session user is signed up move to KingdomsListActivity
        if (session.isSignedUp()) {
            startKingdomsActivity();
        }
        //Else the user is not signed up so do necessary steps to setup the signup page to handle
        //signing up the user
        else {
            //Set up variables and look needed for this fragment
            init();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bSignup:
                signup();
                break;
        }
    }


    //Helper method to initialize variables needed for this fragment
    private void init(){
        //Get the EditText views so we can extract the inputted data from them
        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);

        //Get the Button view so we can signup the user when it's clicked
        bSignup = (Button) findViewById(R.id.bSignup);
        bSignup.setOnClickListener(this);

    }


    //Method to handle all necessary tasks on registering the user
    private void signup(){
        final String name, email;

        name = etName.getText().toString();
        email = etEmail.getText().toString();

        //Before processing any data to register, we must validate the user inputted data
        Boolean validData = validateData(name, email);

        //If the data entered is valid, register the user
        if(validData){
            User user = new User();
            user.setEmail(email);

            //Handle retrofit post to register the user
            //Attempt to register the user
            ServiceClient.get().registerUser(user, new Callback<User>() {
                @Override
                public void success(User user, Response response) {
                    //Successfully registered the user

                    //Store the users email in the preferences file
                    session.setEmail(email);

                    //Set the user as logged in
                    session.setSignedUp(true);

                    //Start the Kingdoms Activity
                    startKingdomsActivity();
                }

                @Override
                public void failure(RetrofitError error) {
                    //Failed to register the user
                }
            });

        }

    }

    //Helper method to handle validating user entered data in the EditText views for signup()
    private boolean validateData(String name, String email){

        validName = validateName(name);
        validEmail = validateEmail(email);

        //If both fields are valid the data is valid
        if(validName && validEmail) {
            return true;
        } else {
            String toastMessage = "";

            //If both fields are invalid display both fields invalid message
            if(errorCount == 2){
                toastMessage = "Invalid name and email entered.";
            } else if (!validName && errorCount == 1) {
                toastMessage = "Invalid name entered.";
            } else if (!validEmail && errorCount == 1){
                toastMessage = "Invalid email entered.";
            }

            //Make a toast to let the user know what fields are invalid
            Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();

            //Reset the error count to 0
            errorCount = 0;

            return false;
        }
    }

    //Helper method to validate the Name entered by user for the validateData() method
    private boolean validateName(String name) {
        //Regex string for a valid date input
        String NAME_PATTERN = "^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}";
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(name);

        //If the user entered name matches the regex pattern and
        //The name length is not 0, email is valid.
        if(matcher.matches() && name.length() != 0){
            return true;
        } else {
            errorCount += 1;
            return false;
        }

    }

    //Helper method to validate the Email entered by user for the validateData() method
    private boolean validateEmail(String email){
        //If the user entered email matches the Android.Util.Patterns.EMAIL_ADDRESS pattern and
        //The email length is not 0, email is valid.
        if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.length() != 0){
            return true;
        } else {
            errorCount += 1;
            return false;
        }
    }

    //Method to handle starting of the Kingdoms Activity
    private void startKingdomsActivity(){
        Intent intent = new Intent(this, KingdomsListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}
