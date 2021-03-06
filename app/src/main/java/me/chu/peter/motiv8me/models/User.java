package me.chu.peter.motiv8me.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by peter on 19/09/15.
 */
public class User extends TinderItem {
    public static final String FIRST_NAME_KEY = "first_name";
    public static final String LAST_NAME_KEY= "last_name";
    public static final String EMAIL_KEY = "email";
    public static final String USERS_PATH = "users";
    @JsonProperty("gitHandle")
    private String gitHandle;
    @JsonProperty("gitUrl")
    private String gitUrl;
    @JsonProperty("picUrl")
    private String picUrl;
    @JsonProperty("description")
    private String description;
    @JsonProperty("name")
    private String name;
    @JsonProperty("uid")
    private Integer uid;
    @JsonProperty("lookup")
    private String interests;

    public User() {

    }

    public String getInterests() {
        return interests;
    }
    public void setInterests(String interest) {
        this.interests = interest;
    }
    public int getUid() {
        return uid;
    }

    public void setUid(Integer uid) { this.uid = uid; }

    public String getGitHandle() {
        return gitHandle;
    }

    public void setGitHandle(String gitHandle) {
        this.gitHandle = gitHandle;
    }

    public String getGitUrl() {
        return gitUrl;
    }

    public void setGitUrl(String gitUrl) {
        this.gitUrl = gitUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl= picUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
