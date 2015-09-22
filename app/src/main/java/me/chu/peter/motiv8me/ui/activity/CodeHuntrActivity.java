package me.chu.peter.motiv8me.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import me.chu.peter.motiv8me.R;
import me.chu.peter.motiv8me.api.ApiClient;
import me.chu.peter.motiv8me.api.ApiService;
import me.chu.peter.motiv8me.models.Project;
import me.chu.peter.motiv8me.models.TinderItem;
import me.chu.peter.motiv8me.models.User;
import me.chu.peter.motiv8me.ui.adapter.TinderItemAdapter;
import me.chu.peter.motiv8me.util.Common;

/**
 * Created by peter on 19/09/15.
 */
public class CodeHuntrActivity extends AppCompatActivity implements ApiClient.ApiResponseCallbackListener {
    private SwipeFlingAdapterView mFlingContainer;
    private Firebase mLikeRef;
    private Firebase mDislikeRef;
    private ArrayAdapter mAdapter;
    private List<TinderItem> mAl;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.likes_activity) {
//            Intent i = new Intent(this, LikesActivity.class);
//            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.code_huntr_activity_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.tinder_activity);
        mFlingContainer = (SwipeFlingAdapterView) findViewById(R.id.fling_adapter_view);
        Toolbar tb = (Toolbar) findViewById(R.id.codehuntr_toolbar);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true); // show or hide the default home button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
        ab.setDisplayShowTitleEnabled(false); // disable the default title element here (for centered title)
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLikeRef = new Firebase(Common.FIREBASE_URL + "likes");
        mDislikeRef = new Firebase(Common.FIREBASE_URL + "dislikes");
        String userType = (String) getIntent().getExtras().get(Common.KEY_USER_TYPE);
        String criteria = (String) getIntent().getExtras().get(Common.KEY_CRITERIA);
        ApiService apiService = ApiClient.getApiService(this);
        mAl = new ArrayList<TinderItem>();
        if(userType.equals("developers")) {
            apiService.getGithubProjects(this, criteria);
        } else {
            apiService.getTinderUsers(this, criteria);
        }
        mAdapter = new TinderItemAdapter(this, 0, mAl);

        //set the listener and the adapter
        mFlingContainer.setAdapter(mAdapter);
        mFlingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                mAl.remove(0);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                Toast.makeText(CodeHuntrActivity.this, "Like", Toast.LENGTH_SHORT).show();
//                mLikeRef.push().setValue((TinderItem) dataObject);
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Toast.makeText(CodeHuntrActivity.this, "Nope", Toast.LENGTH_SHORT).show();
//                mLikeRef.push().setValue((TinderItem) dataObject);
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float v) {

            }
        });

        // Optionally add an OnItemClickListener
        mFlingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(CodeHuntrActivity.this, "Clicked!", Toast.LENGTH_LONG);
            }
        });
    }

    @Override
    public void onUsersLoaded(List<User> users) {
        for(TinderItem item : users) {
            mAl.add(item);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onProjectsLoaded(List<Project> projects) {
        for(TinderItem item : projects) {
            mAl.add(item);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLogin(TinderItem user) {
    }

    @Override
    public void onLogout() {
    }
}
