package com.example.solotask.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import com.example.solotask.R;

public class Register extends AppCompatActivity {

  EditText mFullName, mEmailAddress, mPassword;
  Button mRegisterButton;
  TextView mGoToLogin;
  ProgressBar mRegisterProgressBar;
  String userID;
  FirebaseAuth mAuth;
  FirebaseFirestore db;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);

    mFullName = findViewById(R.id.fullName);
    mEmailAddress = findViewById(R.id.emailAddress);
    mPassword = findViewById(R.id.password);
    mRegisterButton = findViewById(R.id.registerButton);
    mGoToLogin = findViewById(R.id.goToLogin);
    mRegisterProgressBar = findViewById(R.id.registerProgressBar);

    mAuth = FirebaseAuth.getInstance();
    db = FirebaseFirestore.getInstance();

    mRegisterButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        final String fullName = mFullName.getText().toString().trim();
        final String email = mEmailAddress.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if (mAuth.getCurrentUser() != null) {
          startActivity(new Intent(getApplicationContext(), MainActivity.class));
          finish();
        }

        if (TextUtils.isEmpty(fullName)) {
          mFullName.setError("Please enter your name.");
          return;
        }

        if (TextUtils.isEmpty(email)) {
          mEmailAddress.setError("You need to enter your email to register.");
          return;
        }

        if (password.length() < 6) {
          mPassword.setError("Password must be at least 6 characters long.");
        }

        mRegisterProgressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
            new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                  Toast.makeText(Register.this, "Account successfully registered.", Toast.LENGTH_SHORT).show();
                  userID = mAuth.getCurrentUser().getUid();
                  DocumentReference documentReference = db.collection("users").document(userID);
                  Map<String, Object> user = new HashMap<>();
                  user.put("fullName", fullName);
                  user.put("email", email);
                  documentReference.set(user);
                  startActivity(new Intent(getApplicationContext(), MainActivity.class));
                  finish();
                } else {
                  Toast.makeText(Register.this, "Error!" + task.getException().getMessage(),
                      Toast.LENGTH_SHORT).show();
                }
              }
            });
      }
    });

    mGoToLogin.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });
  }
}
