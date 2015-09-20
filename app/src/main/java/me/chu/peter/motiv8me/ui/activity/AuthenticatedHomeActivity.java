package me.chu.peter.motiv8me.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerViewAdapter;

import me.chu.peter.motiv8me.R;
import me.chu.peter.motiv8me.models.Sprint;
import me.chu.peter.motiv8me.util.Common;

/**
 * Created by peter on 19/09/15.
 */
public class AuthenticatedHomeActivity extends AppCompatActivity {
    private FirebaseRecyclerViewAdapter<Sprint, SprintViewHolder> mAdapter;
    private Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authenticated_home_activity);
        BootstrapButton addSprintBtn = (BootstrapButton) findViewById(R.id.add_sprint_btn);
        addSprintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRef = new Firebase(Common.FIREBASE_URL + Sprint.SPRINTS_PATH);
        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler_view);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new FirebaseRecyclerViewAdapter<Sprint, SprintViewHolder>(mRef, Sprint.class) {
            @Override
            public SprintViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new SprintViewHolder(parent);
            }

            @Override
            public void onBindViewHolder(SprintViewHolder holder, int position) {
                holder.nameText.setText(getItem(position).getName());
                holder.messageText.setText(getItem(position).getDescription());
            }
        };
        recycler.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }

    private static class SprintViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView nameText;

        public SprintViewHolder(View itemView) {
            super(itemView);
            nameText = (TextView)itemView.findViewById(android.R.id.text1);
            messageText = (TextView) itemView.findViewById(android.R.id.text2);
        }
    }
}
