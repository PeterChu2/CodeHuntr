package me.chu.peter.motiv8me.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.loopj.android.http.AsyncHttpClient;
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
public class TinderActivity extends AppCompatActivity implements ApiClient.ApiResponseCallbackListener {
    private SwipeFlingAdapterView mFlingContainer;
    private Firebase mLikeRef;
    private Firebase mDislikeRef;
    private ArrayAdapter mAdapter;
    private List<TinderItem> mAl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.tinder_activity);
        mFlingContainer = (SwipeFlingAdapterView) findViewById(R.id.fling_adapter_view);
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
                Toast.makeText(TinderActivity.this, "Like", Toast.LENGTH_SHORT).show();
                mLikeRef.push().setValue((TinderItem) dataObject);
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Toast.makeText(TinderActivity.this, "Nope", Toast.LENGTH_SHORT).show();
                mLikeRef.push().setValue((TinderItem) dataObject);
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
                Toast.makeText(TinderActivity.this, "Clicked!", Toast.LENGTH_LONG);
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
