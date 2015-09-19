package me.chu.peter.motiv8me.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

import me.chu.peter.motiv8me.R;

/**
 * Created by peter on 19/09/15.
 */
public class LoginActivity extends AppCompatActivity {
    private BootstrapEditText mEtEmail;
    private BootstrapEditText mEtPassword;
    private ProgressDialog mProgressDialog;
    private Firebase mRef;
    private static String firebaseURL = "https://crackling-inferno-3664.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Firebase.setAndroidContext(this);
        BootstrapButton signInButton = (BootstrapButton) findViewById(R.id.btnSignInSubmit);
        mEtEmail = (BootstrapEditText) findViewById(R.id.sign_in_et_email);
        mEtPassword = (BootstrapEditText) findViewById(R.id.sign_in_et_pass);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRef = new Firebase(String.format(firebaseURL));
        mRef.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    // user is logged in
                } else {
                    // user is not logged in
                }
            }
        });
    }

    private void signIn() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.show();
        String email = mEtEmail.getText().toString();
        String password = mEtPassword.getText().toString();
        // Create a handler to handle the result of the authentication
        final Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                // Authenticated successfully with payload authData
                // Authentication just completed successfully :)
                mProgressDialog.dismiss();
                Map<String, String> map = new HashMap<String, String>();
                map.put("provider", authData.getProvider());
                if(authData.getProviderData().containsKey("displayName")) {
                    map.put("displayName", authData.getProviderData().get("displayName").toString());
                }
                mRef.child("users").child(authData.getUid()).setValue(map);

                Intent i = new Intent(LoginActivity.this, AuthenticatedHomeActivity.class);
                startActivity(i);
            }
            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // Authenticated failed with error firebaseError
                mProgressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Authentication Failed!", Toast.LENGTH_LONG);
            }
        };
        mRef.authWithPassword(email, password, authResultHandler);
        mProgressDialog.setMessage("Logging in");
        mProgressDialog.show();
    }
}