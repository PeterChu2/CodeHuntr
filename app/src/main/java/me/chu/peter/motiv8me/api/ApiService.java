package me.chu.peter.motiv8me.api;

import android.content.Context;

import java.util.List;

import me.chu.peter.motiv8me.models.Project;
import me.chu.peter.motiv8me.models.TinderItem;
import me.chu.peter.motiv8me.models.User;

/**
 * Created by peter on 19/09/15.
 */
public interface ApiService {
    void getTinderUsers(Context context, String criteria);
    void getGithubProjects(Context context, String criteria);
    void login(Context context, String githubHandle, String password);
    void signup(Context context, String githubHandle, String password, String interest);
    void signUpAsProject(Context context, String githubHandle, String repo, String lookup);
    void loginAsProject(Context context, String githubHandle, String repo);
}
