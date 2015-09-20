package me.chu.peter.motiv8me.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by peter on 19/09/15.
 */
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.firebase.client.Firebase;

import java.util.List;

import me.chu.peter.motiv8me.R;
import me.chu.peter.motiv8me.api.ApiClient;
import me.chu.peter.motiv8me.api.ApiService;
import me.chu.peter.motiv8me.models.Project;
import me.chu.peter.motiv8me.models.TinderItem;
import me.chu.peter.motiv8me.models.User;
import me.chu.peter.motiv8me.util.Common;

public class SignUpActivity extends AppCompatActivity implements ApiClient.ApiResponseCallbackListener {

    private BootstrapEditText mEtFirstName;
    private BootstrapEditText mEtLastName;
    private BootstrapEditText mEtEmail;
    private BootstrapEditText mEtUsername;
    private BootstrapEditText mEtPassword;
    private BootstrapEditText mEtPasswordConfirmation;
    private Firebase mRef;
    private ProgressDialog mProgressDialog;
    private CheckBox checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
//        mEtEmail = (BootstrapEditText) findViewById(R.id.sign_up_et_email);
        mEtUsername = (BootstrapEditText) findViewById(R.id.sign_up_et_username);
//        mEtFirstName = (BootstrapEditText) findViewById(R.id.sign_up_et_first_name);
//        mEtLastName = (BootstrapEditText) findViewById(R.id.sign_up_et_last_name);
//        mEtPassword = (BootstrapEditText) findViewById(R.id.sign_up_et_password);
//        mEtPasswordConfirmation = (BootstrapEditText) findViewById(R.id.sign_up_et_password_confirmation);
        BootstrapButton mSignUpButton = (BootstrapButton) findViewById(R.id.btnSignUpSubmit);
        checkbox = (CheckBox) findViewById(R.id.sign_upproject_checkbox);
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                BootstrapEditText repo = (BootstrapEditText) findViewById(R.id.sign_up_repo_edit_text);
                BootstrapEditText lookup = (BootstrapEditText) findViewById(R.id.interests_edit_text);
                if (isChecked) {
                    repo.setVisibility(View.VISIBLE);
                    lookup.setVisibility(View.VISIBLE);
                } else {
                    repo.setVisibility(View.INVISIBLE);
                    lookup.setVisibility(View.INVISIBLE);
                }
            }
        });
        Firebase.setAndroidContext(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRef = new Firebase(Common.FIREBASE_URL);
    }

    private void signUp() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Signing up");
        mProgressDialog.show();
//        if(mEtPassword.getText().toString().equals(mEtPasswordConfirmation.getText().toString())) {
//            mRef.createUser(mEtEmail.getText().toString(), mEtPassword.getText().toString(), new ValueResultHandler<Map<String, Object>>() {
//                @Override
//                public void onSuccess(Map<String, Object> stringObjectMap) {
//                    mProgressDialog.dismiss();
//                    stringObjectMap.put(User.FIRST_NAME_KEY, mEtFirstName.getText().toString());
//                    stringObjectMap.put(User.LAST_NAME_KEY, mEtLastName.getText().toString());
//                    stringObjectMap.put(User.EMAIL_KEY, mEtEmail.getText().toString());
//                    mRef.child(User.USERS_PATH).push().setValue(stringObjectMap);
//
//                    Intent i = new Intent(SignUpActivity.this, AuthenticatedHomeActivity.class);
//                    startActivity(i);
//                }
//
//                @Override
//                public void onError(FirebaseError firebaseError) {
//                    mProgressDialog.dismiss();
//                    Toast.makeText(SignUpActivity.this, firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
        BootstrapEditText et = (BootstrapEditText) findViewById(R.id.interests_edit_text);
        String interest = et.getText().toString();
        ApiService apiService = ApiClient.getApiService(this);

        if (checkbox.isChecked()) {
            BootstrapEditText repo = (BootstrapEditText) findViewById(R.id.sign_up_repo_edit_text);
            apiService.signUpAsProject(this, mEtUsername.getText().toString(), repo.getText().toString(), interest);
        } else {
            apiService.signup(this, mEtUsername.getText().toString(), null, interest);
        }
        //} //else {
//            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT);
//        }
    }

    @Override
    public void onUsersLoaded(List<User> users) {
        // nop
    }

    @Override
    public void onProjectsLoaded(List<Project> projects) {
        // nop
    }

    @Override
    public void onLogin(TinderItem item) {
        mProgressDialog.dismiss();
        Intent i = new Intent(SignUpActivity.this, TinderActivity.class);
        if(item.getClass() == User.class) {
            i.putExtra(Common.KEY_CRITERIA, ((User)item).getInterests());
            i.putExtra(Common.KEY_USER_TYPE, "developers");
        } else if(item.getClass() == Project.class) {
            i.putExtra(Common.KEY_CRITERIA, ((Project)item).getInterests());
            i.putExtra(Common.KEY_USER_TYPE, "projects");
        }
        startActivity(i);
    }

    @Override
    public void onLogout() {
        // nop
    }
}
