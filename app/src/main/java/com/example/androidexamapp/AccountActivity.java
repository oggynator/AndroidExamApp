package com.example.androidexamapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountActivity extends AppCompatActivity {
    //tag just used for logging
    private static final String TAG = "Account Activty";

    //gets the current user, used for the sign out and delete acount method.
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private TextView userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //Gets the users email address from firebase and sets the email as the email text.
        userEmail = findViewById(R.id.userEmailText);
        String email = user.getEmail();
        userEmail.setText(email);

    }


    //Signs out and removes the token, then returns the user to the auth activity
    public void onSignOutPressed(View view) {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this, "User signed out", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),AuthActivity.class));
        finish();
    }

    public void onDeleteAccountPressed (View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Adding text
        builder.setMessage(R.string.message);

        // Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteAccount(); //<-- on delete account method called
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    //This should be moved to authRepo, but it seems to be bugging a bit...
    //gets the current user
    private void deleteAccount(){

        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User account deleted.");
                        }
                    }
                });
        //if this is not called, the app will get stuck and crash. It seems that the token need to be removed
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(AccountActivity.this,AuthActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(this,"Your account has been deleted",Toast.LENGTH_LONG).show();
    }
}
