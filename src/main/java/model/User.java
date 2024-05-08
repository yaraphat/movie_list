package main.java.model;

import java.util.ArrayList;
import java.util.List;

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

    public void addToFavorites(Movie movie) {
        favorites.add(movie);
    }

    public void removeFromFavorites(Movie movie) {
        favorites.remove(movie);
    }
}
