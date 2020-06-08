package com.example.androidexamapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidexamapp.Repository.AuthRepo;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity {

    private EditText emailText;
    private EditText passwordText;
    private AuthRepo authRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        emailText = findViewById(R.id.signInEmail);
        passwordText = findViewById(R.id.signInPassword);

        //The checkcredentials method from authrepo will be called to check if there is a login token, if not it will ask the user to sign in
        authRepo = new AuthRepo(this);


    }

    //get the emailtext and and password text and use the signUserIn method with these to newly created strings from the user input
    public void signIn(View view) {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        if(email.length() > 0 && password.length() > 0) {
            authRepo.signUserIn(email, password, this);
        }
    }

    //This will be called if the login is successful then sends the user to the mainactivity
    public void onLoginSuccess(FirebaseUser user) {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
     //   intent.putExtra("firebaseUser", user); <--  not needed
        startActivity(intent);
        finish();
    }


    //Send user to sign up page, we dont finish the activity, since we wont to sign the user in when the user has been signed up
    public void onSignUpPressed(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }



    //displays a toast if the credentials are incorrect
    public void onLoginFail() {
    Toast.makeText(this,"Incorrect username or password",Toast.LENGTH_LONG).show();
    }


}
