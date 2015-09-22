package me.chu.peter.motiv8me.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.firebase.client.Firebase;

import me.chu.peter.motiv8me.R;
import me.chu.peter.motiv8me.util.Common;

/**
 * Created by peter on 20/09/15.
 */
public class LikesActivity extends AppCompatActivity {
    private Firebase mLikesRef;
    private Firebase mDislikesRef;
    private ViewPager mViewPager;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.likes_dislikes);
        Firebase.setAndroidContext(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mLikesRef = new Firebase(Common.FIREBASE_URL + "likes");
        mDislikesRef = new Firebase(Common.FIREBASE_URL + "dislikes");
    }
}
