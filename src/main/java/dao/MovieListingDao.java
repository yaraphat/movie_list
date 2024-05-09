package main.java.dao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import main.java.model.Movie;
import main.java.model.User;
import main.java.util.DateUtil;

public class MovieListingDao {
    private List<Movie> movies;
    private List<User> users;
    private User currentUser;

    private static MovieListingDao instance = null;

    private MovieListingDao() {
        this.movies = getInitialMovieList();
        this.users = new ArrayList<>();
    }

    public static MovieListingDao getInstance() {
        if (instance == null) {
            instance = new MovieListingDao();
        }
        return instance;
    }

    public void registerUser(String email) {
        User user = new User(email);
        users.add(user);
        currentUser = user;
    }

    public List<Movie> searchMovies(String keyword) {
        return searchMovies(movies, keyword);
    }

    public List<Movie> searchFavoriteMovies(String keyword) {
        List<Movie> result = new ArrayList<>();
        if (currentUser != null && !currentUser.getFavorites().isEmpty()) {
            result = searchMovies(currentUser.getFavorites(), keyword);
        }
        return result;
    }

    private List<Movie> searchMovies(List<Movie> movieList, String keyword) {
        List<Movie> result = new ArrayList<>();
        if (keyword == null || keyword.isEmpty()) {
            movieList.sort(Comparator.comparing(Movie::getTitle));
            return movieList;
        }
        keyword = keyword.toLowerCase();
        for (Movie movie : movieList) {
            if (movie.getTitle().toLowerCase().contains(keyword) ||
                    movie.getCast().toString().toLowerCase().contains(keyword) ||
                    movie.getCategory().toLowerCase().contains(keyword)) {
                result.add(movie);
            }
        }
        result.sort(Comparator.comparing(Movie::getTitle));
        return result;
    }

    public void addToFavorites(Movie movie) {
        if (currentUser != null) {
            List<Movie> favorites = currentUser.getFavorites();
            favorites.add(movie);
        }
    }

    public void removeFromFavorites(Movie movie) {
        if (currentUser != null) {
            List<Movie> favorites = currentUser.getFavorites();
            favorites.remove(movie);
        }
    }

    public User findUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Movie findMovieByTitle(String title) {
        for (Movie movie : movies) {
            if (movie.getTitle().equalsIgnoreCase(title)) {
                return movie;
            }
        }
        return null;
    }

    public static List<Movie> getInitialMovieList() {
        List<Movie> movies = new ArrayList<>();

        movies.add(new Movie("The Shawshank Redemption", List.of("Tim Robbins", "Morgan Freeman"), "Drama",
                DateUtil.getDate("1994-09-14"), 25000000));
        movies.add(new Movie("The Godfather", List.of("Marlon Brando", "Al Pacino"), "Crime", DateUtil.getDate("1972-02-24"),
                6000000));
        movies.add(new Movie("The Dark Knight", List.of("Christian Bale", "Heath Ledger"), "Action",
                DateUtil.getDate("2008-07-18"), 185000000));
        movies.add(new Movie("Schindler's List", List.of("Liam Neeson", "Ben Kingsley"), "Biography",
                DateUtil.getDate("1993-02-04"), 22000000));
        movies.add(new Movie("Pulp Fiction", List.of("John Travolta", "Uma Thurman"), "Crime", DateUtil.getDate("1994-09-14"),
                8000000));
        movies.add(new Movie("The Lord of the Rings: The Return of the King", List.of("Elijah Wood", "Viggo Mortensen"),
                "Adventure", DateUtil.getDate("2003-12-01"), 94000000));
        movies.add(
                new Movie("Forrest Gump", List.of("Tom Hanks", "Robin Wright"), "Drama", DateUtil.getDate("1994-06-23"), 55000000));
        movies.add(new Movie("The Matrix", List.of("Keanu Reeves", "Laurence Fishburne"), "Action", DateUtil.getDate("1999-03-31"),
                63000000));
        movies.add(new Movie("Inception", List.of("Leonardo DiCaprio", "Joseph Gordon-Levitt"), "Action",
                DateUtil.getDate("2010-07-16"), 160000000));
        movies.add(new Movie("The Silence of the Lambs", List.of("Jodie Foster", "Anthony Hopkins"), "Crime",
                DateUtil.getDate("1991-02-14"), 19000000));
        movies.add(new Movie("The Green Mile", List.of("Tom Hanks", "Michael Clarke Duncan"), "Drama",
                DateUtil.getDate("1999-12-10"), 60000000));
        movies.add(new Movie("Saving Private Ryan", List.of("Tom Hanks", "Matt Damon"), "Drama", DateUtil.getDate("1998-07-24"),
                70000000));
        movies.add(new Movie("Gladiator", List.of("Russell Crowe", "Joaquin Phoenix"), "Action", DateUtil.getDate("2000-05-01"),
                103000000));
        movies.add(new Movie("Titanic", List.of("Leonardo DiCaprio", "Kate Winslet"), "Romance", DateUtil.getDate("1997-12-19"),
                200000000));
        movies.add(new Movie("Avatar", List.of("Sam Worthington", "Zoe Saldana"), "Action", DateUtil.getDate("2009-12-18"),
                237000000));

        return movies;
    }

}
