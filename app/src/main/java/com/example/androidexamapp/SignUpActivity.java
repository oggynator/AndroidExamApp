package com.example.androidexamapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidexamapp.Repository.AuthRepo;

public class SignUpActivity extends AppCompatActivity {

    private EditText signUpEmail;
    private EditText signUpPassword;
    private AuthRepo authRepo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpEmail = findViewById(R.id.signUpEmail);
        signUpPassword = findViewById(R.id.signUpPassword);
        authRepo= new AuthRepo();


    }

    //Takes the user input and uses the signUp() method to create the user.
    //Activity is finished afterwards, since we dont want the user coming back to this activity
    public void onSignUpPressed(View view){
        String email = signUpEmail.getText().toString();
        String password = signUpPassword.getText().toString();
        if(email.length() > 0 && password.length()>0) {
            authRepo.signUp(email, password);
            Toast.makeText(this,"Your account has been created!",Toast.LENGTH_LONG).show();
        }
        finish();
    }


}
