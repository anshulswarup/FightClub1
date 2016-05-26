package com.example.android.fightclub.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Saxena on 5/25/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {
    private String name;
    private String description;

    public Movie() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
