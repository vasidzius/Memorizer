package com.memorizer.vasidzius.memorizer;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

  public static final String EXTRA_MESSAGE = "com.memorizer.vasidzius.memorizer.MESSAGE";



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
    signInButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v)
      {
        signIn(v);
      }
    });
  }

  public void sendMessage(View view){
    Intent intent = new Intent(this, DisplayMessageActivity.class);
    EditText head = (EditText) findViewById(R.id.head);
    String message = head.getText().toString();
    intent.putExtra(EXTRA_MESSAGE, message);
    startActivity(intent);
  }

  public void signIn(View view) {

    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestEmail()
      .build();

    GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    Intent signInIntent = mGoogleSignInClient.getSignInIntent();
    startActivityForResult(signInIntent, 1);
  }
}
