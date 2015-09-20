package me.chu.peter.motiv8me.models;

/**
 * Created by peter on 19/09/15.
 */
public class Sprint {
    public static final String SPRINTS_PATH = "sprints";
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String description;
}
