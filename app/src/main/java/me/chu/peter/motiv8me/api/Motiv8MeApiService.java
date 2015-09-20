package me.chu.peter.motiv8me.api;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.chu.peter.motiv8me.models.Project;
import me.chu.peter.motiv8me.models.User;
import me.chu.peter.motiv8me.util.Common;

/**
 * Created by peter on 19/09/15.
 */
public class Motiv8MeApiService implements ApiService {
    private AsyncHttpClient mAsyncHttpClient;
    ApiClient.ApiResponseCallbackListener mListener;
    public Motiv8MeApiService(ApiClient.ApiResponseCallbackListener listener) {
        mListener = listener;
        mAsyncHttpClient = new AsyncHttpClient();
    }

    @Override
    public void getTinderUsers(Context context, String criteria) {
        mAsyncHttpClient.addHeader("Content-Type", "application/json");
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("query", criteria);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonParams.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mAsyncHttpClient.addHeader("Content-Type", "application/json");
        mAsyncHttpClient.post(context, Common.GOOGLE_ENDPOINT + "developer/match/", entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    List<User> users = new ArrayList<User>();
                    Iterator<String> itr = response.keys();
                    while(itr.hasNext()) {
                        users.add(objectMapper.readValue(response.get(itr.next()).toString(), User.class));
                    }
                    mListener.onUsersLoaded(users);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
//                ObjectMapper objectMapper = new ObjectMapper();
//                try {
//                    List<User> users = new ArrayList<User>();
//                    Iterator<String> itr = response.keys();
//                    while(itr.hasNext()) {
//                        users.add(objectMapper.readValue(response.get(itr.next()).toString(), User.class));
//                    }
//                    mListener.onUsersLoaded(users);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    List<User> users = objectMapper.readValue(responseString, new TypeReference<List<User>>(){});
                    mListener.onUsersLoaded(users);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void getGithubProjects(Context context, String criteria) {
        mAsyncHttpClient.addHeader("Content-Type", "application/json");
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("query", criteria);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonParams.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mAsyncHttpClient.addHeader("Content-Type", "application/json");
        mAsyncHttpClient.post(context, Common.GOOGLE_ENDPOINT + "project/match/", entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    List<Project> projects = new ArrayList<Project>();
                    Iterator<String> itr = response.keys();
                    while(itr.hasNext()) {
                        projects.add(objectMapper.readValue(response.get(itr.next()).toString(), Project.class));
                    }
                    mListener.onProjectsLoaded(projects);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
//                ObjectMapper objectMapper = new ObjectMapper();
//                try {
//                    List<User> users = objectMapper.readValue(response.toString(), new TypeReference<List<User>>(){});
//                    mListener.onUsersLoaded(users);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                ObjectMapper objectMapper = new ObjectMapper();
//                try {
//                    List<User> projects = objectMapper.readValue(responseString, new TypeReference<List<User>>(){});
//                    mListener.onProjectsLoaded(projects);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        });
    }

    @Override
    public void login(Context context, String githubHandle, String password) {
        mAsyncHttpClient.addHeader("Content-Type", "application/json");
        mAsyncHttpClient.addHeader("Accept", "application/json");
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("user", githubHandle);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonParams.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mAsyncHttpClient.post(context, Common.GOOGLE_ENDPOINT + "developer/login", entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                ObjectMapper mapper = new ObjectMapper();
                try {
                    User user = mapper.readValue(response.toString(), User.class);
                    mListener.onLogin(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                ObjectMapper mapper = new ObjectMapper();
                try {
                    User user = mapper.readValue(response.toString(), User.class);
                    mListener.onLogin(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                ObjectMapper mapper = new ObjectMapper();
                try {
                    User user = mapper.readValue(responseString, User.class);
                    mListener.onLogin(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("PETER", statusCode + " " + responseString);
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    @Override
    public void loginAsProject(Context context, String githubHandle, String repo) {
        mAsyncHttpClient.addHeader("Content-Type", "application/json");
        mAsyncHttpClient.addHeader("Accept", "application/json");
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("user", githubHandle);
            jsonParams.put("repo", repo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonParams.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mAsyncHttpClient.post(context, Common.GOOGLE_ENDPOINT + "project/login", entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                ObjectMapper mapper = new ObjectMapper();
                try {
                    Project project = mapper.readValue(response.toString(), Project.class);
                    mListener.onLogin(project);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                ObjectMapper mapper = new ObjectMapper();
                try {
                    User user = mapper.readValue(response.toString(), User.class);
                    mListener.onLogin(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                ObjectMapper mapper = new ObjectMapper();
                try {
                    User user = mapper.readValue(responseString, User.class);
                    mListener.onLogin(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("PETER", statusCode + " " + responseString);
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }


    @Override
    public void signup(Context context, String githubHandle, String password, String interest) {
        mAsyncHttpClient.addHeader("Content-Type", "application/json");
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("user", githubHandle);
            jsonParams.put("lookup", interest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonParams.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mAsyncHttpClient.post(context, Common.GOOGLE_ENDPOINT + "developer/signup/", entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                ObjectMapper mapper = new ObjectMapper();
                try {
                    User user = mapper.readValue(response.toString(), User.class);
                    mListener.onLogin(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                ObjectMapper mapper = new ObjectMapper();
                try {
                    User user = mapper.readValue(response.toString(), User.class);
                    mListener.onLogin(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                ObjectMapper mapper = new ObjectMapper();
                try {
                    User user = mapper.readValue(responseString, User.class);
                    mListener.onLogin(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void signUpAsProject(Context context, String githubHandle, String repo, String lookup) {
        mAsyncHttpClient.addHeader("Content-Type", "application/json");
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("user", githubHandle);
            jsonParams.put("lookup", lookup);
            jsonParams.put("repo", repo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonParams.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mAsyncHttpClient.post(context, Common.GOOGLE_ENDPOINT + "project/signup/", entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                ObjectMapper mapper = new ObjectMapper();
                try {
                    Project project = mapper.readValue(response.toString(), Project.class);
                    mListener.onLogin(project);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                ObjectMapper mapper = new ObjectMapper();
                try {
                    Project project = mapper.readValue(response.toString(), Project.class);
                    mListener.onLogin(project);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                ObjectMapper mapper = new ObjectMapper();
                try {
                    User user = mapper.readValue(responseString, User.class);
                    mListener.onLogin(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
