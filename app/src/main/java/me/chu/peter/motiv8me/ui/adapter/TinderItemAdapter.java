package me.chu.peter.motiv8me.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.List;

import me.chu.peter.motiv8me.R;
import me.chu.peter.motiv8me.models.Project;
import me.chu.peter.motiv8me.models.TinderItem;
import me.chu.peter.motiv8me.models.User;

/**
 * Created by peter on 19/09/15.
 */
public class TinderItemAdapter<T extends TinderItem> extends ArrayAdapter<T> {
    List<T> mObjects;
    public TinderItemAdapter(Context context, int resource, List<T> objects) {
        super(context, resource, objects);
        mObjects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            v = layoutInflater.inflate(R.layout.item, parent, false);
        }
        final TinderItem item = mObjects.get(position);
        if (item != null) {
            ImageView imageView = (ImageView) v.findViewById(R.id.image);
            imageView.setBackgroundColor(Color.rgb(255,255,255));
            TextView title = (TextView) v.findViewById(R.id.title);
            title.setBackgroundColor(Color.rgb(255,255,255));
            TextView desc = (TextView) v.findViewById(R.id.desc);
            if (item instanceof User) {
                Ion.with(imageView).load(((User) item).getPicUrl());
                title.setText(((User) item).getName());
                desc.setText(((User) item).getGitHandle());
            } else {
                Ion.with(imageView).load(((Project) item).getPicUrl());
                title.setText(((Project) item).getName());
                desc.setText(((Project) item).getDescription());
            }
        }
        return v;
    }
}
