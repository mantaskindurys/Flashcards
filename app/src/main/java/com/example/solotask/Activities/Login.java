package com.example.solotask.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;


import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

import com.example.solotask.R;

public class Login extends AppCompatActivity {

  EditText mEmailAddress, mPassword;
  private FirebaseAuth Auth;

  private int RC_SIGN_IN = 0;

  private CallbackManager callbackManager;
  private List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build(),new AuthUI.IdpConfig.FacebookBuilder().build());

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(requestCode==RC_SIGN_IN)
    {
      if(resultCode==RESULT_OK)
      {
        Log.d("AUTH", Auth.getCurrentUser().getEmail());
        startActivity(new Intent(Login.this,MainActivity.class));
        finish();
      }
      else
      {
        Log.d("AUTH", "NOT AUTHENTICATED");
      }
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    mEmailAddress = findViewById(R.id.emailAddress);
    mPassword = findViewById(R.id.password);

    Auth = FirebaseAuth.getInstance();

    callbackManager = CallbackManager.Factory.create();
    startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(),RC_SIGN_IN);

  }

}
