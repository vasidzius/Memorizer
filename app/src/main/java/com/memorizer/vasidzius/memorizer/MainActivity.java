package com.memorizer.vasidzius.memorizer;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

  static final String EXTRA_MESSAGE = "com.memorizer.vasidzius.memorizer.MESSAGE";
  private final int RC_SIGN_IN = 1;
  private GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
  private SignInButton signInButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    SignInButton signInButton = initSignInButton();

    checkSignedIn(signInButton);
  }

  private void checkSignedIn(SignInButton signInButton) {

    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    if (account == null) {
      signInButton.setVisibility(View.VISIBLE);
    }
  }

  private SignInButton initSignInButton() {
    signInButton = findViewById(R.id.sign_in_button);
    signInButton.setVisibility(View.INVISIBLE);
    signInButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        signIn(v);
      }
    });
    return signInButton;
  }

  public void sendMessage(View view) {
    Intent intent = new Intent(this, DisplayMessageActivity.class);
    EditText head = findViewById(R.id.head);
    String message = head.getText().toString();
    intent.putExtra(EXTRA_MESSAGE, message);
    startActivity(intent);
  }

  public void signIn(View view) {

    GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    Intent signInIntent = mGoogleSignInClient.getSignInIntent();
    startActivityForResult(signInIntent, RC_SIGN_IN);

  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
    if (requestCode == RC_SIGN_IN) {
      // The Task returned from this call is always completed, no need to attach
      // a listener.
      Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
      handleSignInResult(task);
    }
  }

  private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
    try {
      GoogleSignInAccount account = completedTask.getResult(ApiException.class);
      // Signed in successfully, show authenticated UI.
      signInButton.setVisibility(View.INVISIBLE);
    }
    catch (ApiException e) {
      // The ApiException status code indicates the detailed failure reason.
      // Please refer to the GoogleSignInStatusCodes class reference for more information.
      Log.w(MainActivity.class.getName(), "signInResult:failed code=" + e.getStatusCode());
    }
  }
}
