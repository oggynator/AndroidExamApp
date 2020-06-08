package com.example.androidexamapp.Repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.androidexamapp.AuthActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthRepo {


    //FirebaseAuth
    private FirebaseAuth firebaseAuth;

    //Auth Activity
    private AuthActivity authActivity;

    //constructor, that calls the checkCredentials() method, so that the user is automatically signed in, if there is a valid login token
    public AuthRepo(AuthActivity authActivity) {
        this.authActivity = authActivity;
        firebaseAuth = FirebaseAuth.getInstance();
        checkCredentials();
    }

    //Constructor with no parameters, that only gets the auth instance. This is used in the Sign up activity.
    public AuthRepo() {
        firebaseAuth = FirebaseAuth.getInstance();
    }


    // Checks if there is a valid login token,if there is it calls the onloginsucces, if not the user is prompted for mail and password
    private void checkCredentials() {
        firebaseAuth.addIdTokenListener(new FirebaseAuth.IdTokenListener() {
            @Override
            public void onIdTokenChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    //Removed so that this messaged isnt displayed before the user logs in
                    //authActivity.onLoginFail();

                } else {
                    Log.d("success", firebaseAuth.getCurrentUser().getEmail());
                    authActivity.onLoginSuccess(firebaseAuth.getCurrentUser());
                }
            }
        });
    }

    //Takes the email and password from the mainactivty , and sign the user in if it is correct. This is done via the signInWithEmailAndPassword() method
    public void signUserIn(String email, String password, final AuthActivity authActivity) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(authActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            authActivity.onLoginSuccess(task.getResult().getUser());
                        } else {
                            authActivity.onLoginFail();
                        }
                    }
                });
    }

    //Takes the email/password from the sign up activity and uses the createUserWithEmailAndPassword to sign the user up.
    public void signUp(String email, String pwd){
        firebaseAuth.createUserWithEmailAndPassword(email,pwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            System.out.println("Sign up success " +
                                    task.getResult().getUser().getEmail());
                        }else {
                            System.out.println("Sign up failed " + task.getException());
                        }
                    }
                });
    }

}
