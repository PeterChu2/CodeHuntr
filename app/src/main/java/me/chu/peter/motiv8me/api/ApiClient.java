package me.chu.peter.motiv8me.api;

import java.util.List;

import me.chu.peter.motiv8me.models.Project;
import me.chu.peter.motiv8me.models.TinderItem;
import me.chu.peter.motiv8me.models.User;

/**
 * Created by peter on 19/09/15.
 */
public class ApiClient {
    public static ApiService getApiService(ApiResponseCallbackListener listener) {
        return new Motiv8MeApiService(listener);
    }
    public interface ApiResponseCallbackListener {
        void onUsersLoaded(List<User> users);
        void onProjectsLoaded(List<Project> projects);
        void onLogin(TinderItem item);
        void onLogout();
    }
}
