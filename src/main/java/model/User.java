package main.java.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class User {
    private String email;
    private List<Movie> favorites;

    public User(String email) {
        this.email = email;
        this.favorites = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public List<Movie> getFavorites() {
        return favorites;
    }

    public String getFavoritesStr() {
        return String.join(", ", favorites.stream().map(Movie::getTitle).collect(Collectors.toList()));
    }
}
