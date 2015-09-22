package me.chu.peter.motiv8me.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

import java.util.List;

import me.chu.peter.motiv8me.R;
import me.chu.peter.motiv8me.api.ApiClient;
import me.chu.peter.motiv8me.api.ApiService;
import me.chu.peter.motiv8me.models.Project;
import me.chu.peter.motiv8me.models.TinderItem;
import me.chu.peter.motiv8me.models.User;
import me.chu.peter.motiv8me.util.Common;

/**
 * Created by peter on 19/09/15.
 */
public class LoginActivity extends AppCompatActivity implements ApiClient.ApiResponseCallbackListener {
    private BootstrapEditText mEtEmail;
    private BootstrapEditText mEtPassword;
    private ProgressDialog mProgressDialog;
    private CheckBox checkbox;
    private Firebase mRef;
    private final static String githubClientId = "39164e4659d215b8d94d";
    private final static String githubClientSecret = "cfa0c83b70f2b36553fd2d80e7d33e97a2130252";
    private final static String TOKEN_URL = "https://github.com/login/oauth/access_token?";
    private final static String callbackURL = "https://auth.firebase.com/v2/crackling-inferno-3664/auth/github/callback";
    private static final String AUTH_URL = "https://github.com/login/oauth/authorize?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Firebase.setAndroidContext(this);
        BootstrapButton signInButton = (BootstrapButton) findViewById(R.id.btnSignInSubmit);
        mEtEmail = (BootstrapEditText) findViewById(R.id.sign_in_et_email);
//        mEtPassword = (BootstrapEditText) findViewById(R.id.sign_in_et_pass);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        CheckBox checkbox = (CheckBox) findViewById(R.id.project_checkbox);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                BootstrapEditText repo = (BootstrapEditText) findViewById(R.id.repo_edit_text);
                if(isChecked) {
                    repo.setVisibility(View.VISIBLE);
                }
                else {
                    repo.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRef = new Firebase(String.format(Common.FIREBASE_URL));
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
        authWithGithub();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Logging in");
        mProgressDialog.show();
    }

    private void authWithGithub() {
        ApiService service = ApiClient.getApiService(this);
        checkbox = (CheckBox)findViewById(R.id.project_checkbox);
        if(checkbox.isChecked()) {
            BootstrapEditText repo = (BootstrapEditText) findViewById(R.id.repo_edit_text);
            service.loginAsProject(this, mEtEmail.getText().toString(), repo.getText().toString());
        } else {
            service.login(this, mEtEmail.getText().toString(), null);//mEtPassword.getText().toString());
        }
//        AsyncHttpClient mAsyncHttpClient = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//        params.put("username", mEtEmail.getText().toString());
//        params.put("password", mEtPassword.getText().toString());
//        Intent i = new Intent(LoginActivity.this, AuthenticatedHomeActivity.class);
//        i = new Intent(LoginActivity.this, CodeHuntrActivity.class);
//        i.putExtra(Common.KEY_USER_TYPE, "developers");
//        startActivity(i);
//        mAsyncHttpClient.post(Common.FIREBASE_URL + "MAIDISENDPOINT", params, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, org.apache.http.Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
//                String accessToken = null;
//                try {
//                    accessToken = (String) response.get("access_token");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                mRef.authWithOAuthToken("github", accessToken, new Firebase.AuthResultHandler() {
//                    @Override
//                    public void onAuthenticated(AuthData authData) {
//                        // Authenticated successfully with payload authData
//                        // Authentication just completed successfully :)
//                        mProgressDialog.dismiss();
//                        Map<String, String> map = new HashMap<String, String>();
//                        map.put("provider", authData.getProvider());
//                        if (authData.getProviderData().containsKey("displayName")) {
//                            map.put("displayName", authData.getProviderData().get("displayName").toString());
//                        }
//                        mRef.child("users").child(authData.getUid()).setValue(map);
//
//                        Intent i = new Intent(LoginActivity.this, AuthenticatedHomeActivity.class);
//                        i = new Intent(LoginActivity.this, CodeHuntrActivity.class);
//                        startActivity(i);
//                    }
//
//                    @Override
//                    public void onAuthenticationError(FirebaseError firebaseError) {
//                        // Authenticated failed with error firebaseError
//                        mProgressDialog.dismiss();
//                        Toast.makeText(LoginActivity.this, firebaseError.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                });
//
//            }
//
//            @Override
//            public void onSuccess(int statusCode, org.apache.http.Header[] headers, JSONArray response) {
//                super.onSuccess(statusCode, headers, response);
//            }
//
//            @Override
//            public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString) {
//                super.onSuccess(statusCode, headers, responseString);
//            }
//        });
    }

    @Override
    public void onUsersLoaded(List<User> users) {

    }

    @Override
    public void onProjectsLoaded(List<Project> projects) {

    }

    @Override
    public void onLogin(TinderItem item) {
        mProgressDialog.dismiss();
        Intent i = new Intent(LoginActivity.this, CodeHuntrActivity.class);
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

    }
}