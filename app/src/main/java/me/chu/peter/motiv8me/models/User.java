package me.chu.peter.motiv8me.models;

/**
 * Created by peter on 19/09/15.
 */
public class User {
    public static final String FIRST_NAME_KEY = "first_name";
    public static final String LAST_NAME_KEY= "last_name";
    public static final String EMAIL_KEY = "email";
    public static final String USERS_PATH = "users";
    private String firstName;
    private String lastName;
    private String email;

    public User() {

    }
    
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
