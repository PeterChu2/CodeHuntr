package me.chu.peter.motiv8me.models;

/**
 * Created by peter on 19/09/15.
 */
public class Task {
    String mDescription;
    String mName;
    int mReward;

    public Task(String name, String description, int reward) {
        mDescription = description;
        mName = name;
        mReward = reward;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getName() {
        return mName;
    }

    public int getReward() {
        return mReward;
    }
}
