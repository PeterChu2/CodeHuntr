package me.chu.peter.motiv8me.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by peter on 19/09/15.
 */
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.firebase.client.Firebase;
import com.firebase.client.Firebase.ValueResultHandler;
import com.firebase.client.FirebaseError;

import java.util.Map;

import me.chu.peter.motiv8me.R;
import me.chu.peter.motiv8me.models.User;

public class SignUpActivity extends AppCompatActivity {

    private BootstrapEditText mEtFirstName;
    private BootstrapEditText mEtLastName;
    private BootstrapEditText mEtEmail;
    private BootstrapEditText mEtUsername;
    private BootstrapEditText mEtPassword;
    private BootstrapEditText mEtPasswordConfirmation;
    private static String firebaseURL = "https://crackling-inferno-3664.firebaseio.com/";
    private Firebase mRef;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        mEtEmail = (BootstrapEditText) findViewById(R.id.sign_up_et_email);
        mEtUsername = (BootstrapEditText) findViewById(R.id.sign_up_et_username);
        mEtFirstName = (BootstrapEditText) findViewById(R.id.sign_up_et_first_name);
        mEtLastName = (BootstrapEditText) findViewById(R.id.sign_up_et_last_name);
        mEtPassword = (BootstrapEditText) findViewById(R.id.sign_up_et_password);
        mEtPasswordConfirmation = (BootstrapEditText) findViewById(R.id.sign_up_et_password_confirmation);
        BootstrapButton mSignUpButton = (BootstrapButton) findViewById(R.id.btnSignUpSubmit);
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
        Firebase.setAndroidContext(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRef = new Firebase(firebaseURL);
    }

    private void signUp() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Signing up");
        mProgressDialog.show();
        if(mEtPassword.getText().toString().equals(mEtPasswordConfirmation.getText().toString())) {
            mRef.createUser(mEtEmail.getText().toString(), mEtPassword.getText().toString(), new ValueResultHandler<Map<String, Object>>() {
                @Override
                public void onSuccess(Map<String, Object> stringObjectMap) {
                    mProgressDialog.dismiss();
                    stringObjectMap.put(User.FIRST_NAME_KEY, mEtFirstName.getText().toString());
                    stringObjectMap.put(User.LAST_NAME_KEY, mEtLastName.getText().toString());
                    stringObjectMap.put(User.EMAIL_KEY, mEtEmail.getText().toString());
                    mRef.child(User.USERS_PATH).push().setValue(stringObjectMap);

                    Intent i = new Intent(SignUpActivity.this, AuthenticatedHomeActivity.class);
                    startActivity(i);
                }

                @Override
                public void onError(FirebaseError firebaseError) {
                    mProgressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, firebaseError.getMessage(), Toast.LENGTH_SHORT);
                }
            });
        } else {
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT);
        }
    }
}
