package me.chu.peter.motiv8me.models;

/**
 * Created by peter on 19/09/15.
 */
public class Task {
    String mDescription;
    String mName;
    int mReward;
    int mSprintId;

    public Task(String name, String description, int reward, int id) {
        mDescription = description;
        mName = name;
        mReward = reward;
        mSprintId = id;
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

    public int getSprintId() { return mSprintId; }
}
