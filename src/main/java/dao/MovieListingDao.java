package main.java.dao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import main.java.model.Movie;
import main.java.model.User;

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
        System.out.println("User registered successfully with email: " + email);
    }

    public List<Movie> searchMovies(String keyword) {
        List<Movie> result = new ArrayList<>();
        if (keyword == null || keyword.isEmpty()) {
            movies.sort(Comparator.comparing(Movie::getTitle));
            return movies;
        }
        keyword = keyword.toLowerCase();
        for (Movie movie : movies) {
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
        if (currentUser == null) {
            currentUser.addToFavorites(movie);
            System.out.println("Movie '" + movie.getTitle() + "' added to favorites.");
        }
    }

    public void removeFromFavorites(Movie movie) {
        if (currentUser != null) {
            currentUser.removeFromFavorites(movie);
            System.out.println("Movie '" + movie.getTitle() + "' removed from favorites.");
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

    public static List<Movie> getInitialMovieList() {
        List<Movie> movies = new ArrayList<>();

        movies.add(new Movie("The Shawshank Redemption", List.of("Tim Robbins", "Morgan Freeman"), "Drama",
                new Date(94, 9, 14), 25000000));
        movies.add(new Movie("The Godfather", List.of("Marlon Brando", "Al Pacino"), "Crime", new Date(72, 2, 24),
                6000000));
        movies.add(new Movie("The Dark Knight", List.of("Christian Bale", "Heath Ledger"), "Action",
                new Date(108, 6, 18), 185000000));
        movies.add(new Movie("Schindler's List", List.of("Liam Neeson", "Ben Kingsley"), "Biography",
                new Date(93, 11, 15), 22000000));
        movies.add(new Movie("Pulp Fiction", List.of("John Travolta", "Uma Thurman"), "Crime", new Date(94, 9, 14),
                8000000));
        movies.add(new Movie("The Lord of the Rings: The Return of the King", List.of("Elijah Wood", "Viggo Mortensen"),
                "Adventure", new Date(103, 11, 17), 94000000));
        movies.add(
                new Movie("Forrest Gump", List.of("Tom Hanks", "Robin Wright"), "Drama", new Date(94, 6, 6), 55000000));
        movies.add(new Movie("The Matrix", List.of("Keanu Reeves", "Laurence Fishburne"), "Action", new Date(99, 2, 31),
                63000000));
        movies.add(new Movie("Inception", List.of("Leonardo DiCaprio", "Joseph Gordon-Levitt"), "Action",
                new Date(110, 6, 16), 160000000));
        movies.add(new Movie("The Silence of the Lambs", List.of("Jodie Foster", "Anthony Hopkins"), "Crime",
                new Date(91, 1, 14), 19000000));
        movies.add(new Movie("The Green Mile", List.of("Tom Hanks", "Michael Clarke Duncan"), "Drama",
                new Date(99, 11, 10), 60000000));
        movies.add(new Movie("Saving Private Ryan", List.of("Tom Hanks", "Matt Damon"), "Drama", new Date(98, 6, 24),
                70000000));
        movies.add(new Movie("Gladiator", List.of("Russell Crowe", "Joaquin Phoenix"), "Action", new Date(100, 4, 5),
                103000000));
        movies.add(new Movie("Titanic", List.of("Leonardo DiCaprio", "Kate Winslet"), "Romance", new Date(97, 11, 19),
                200000000));
        movies.add(new Movie("Avatar", List.of("Sam Worthington", "Zoe Saldana"), "Action", new Date(109, 11, 18),
                237000000));

        return movies;
    }

}
