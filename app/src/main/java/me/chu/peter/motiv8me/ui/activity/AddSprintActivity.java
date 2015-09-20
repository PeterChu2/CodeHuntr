package me.chu.peter.motiv8me.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.firebase.client.Firebase;

import me.chu.peter.motiv8me.R;
import me.chu.peter.motiv8me.models.Sprint;
import me.chu.peter.motiv8me.util.Common;

/**
 * Created by peter on 19/09/15.
 */
public class AddSprintActivity extends AppCompatActivity {
    private Firebase mRef;
    private BootstrapEditText mName;
    private BootstrapEditText mDesc;
    private BootstrapEditText mPoints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_sprint_activity);
        Firebase.setAndroidContext(this);

        mName = (BootstrapEditText) findViewById(R.id.add_sprint_name_et);
        mDesc = (BootstrapEditText) findViewById(R.id.add_sprint_desc_et);
        mPoints = (BootstrapEditText) findViewById(R.id.add_sprint_pts_et);
        BootstrapButton button = (BootstrapButton) findViewById(R.id.add_sprint_activity_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mRef = new Firebase(Common.FIREBASE_URL + Sprint.SPRINTS_PATH);
    }
}
