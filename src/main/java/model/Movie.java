package main.java.model;

import java.util.Date;
import java.util.List;

public class Movie {
    private String title;
    private List<String> cast;
    private String category;
    private Date releaseDate;
    private double budget;

    public Movie(String title, List<String> cast, String category, Date releaseDate, double budget) {
        this.title = title;
        this.cast = cast;
        this.category = category;
        this.releaseDate = releaseDate;
        this.budget = budget;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getCast() {
        return cast;
    }

    public String getCategory() {
        return category;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public double getBudget() {
        return budget;
    }
}
