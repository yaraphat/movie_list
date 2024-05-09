package main.java.service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import main.java.dao.MovieListingDao;
import main.java.model.Movie;
import main.java.model.User;

public class OperationService {
    private final MovieListingDao dao = MovieListingDao.getInstance();
    private static OperationService instance = null;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private OperationService() {
    }

    public static OperationService getInstance() {
        if (instance == null) {
            instance = new OperationService();
        }
        return instance;
    }

    public void registerUser(Scanner scanner) {
        System.out.print("Enter your email address: ");
        String email = scanner.nextLine();
        if (isValidEmail(email)) {
            User user = dao.getCurrentUser();
            if (user != null && user.getEmail().equals(email)) {
                System.out.println("User already registered with email: " + email);
                return;
            }
            user = dao.findUserByEmail(email);
            if (user != null) {
                dao.setCurrentUser(user);
                System.out.println("Email " + email + " set as current user");
            } else {
                dao.registerUser(email);
                System.out.println("User registered successfully with email: " + email);
            }
            ;
        } else {
            System.out.println("Please enter a valid email address. Registration incomlete.");
        }
    }

    public void searchMovies(Scanner scanner) {
        System.out.print("Enter search keyword (title/cast/category): ");
        String keyword = scanner.nextLine();
        List<Movie> movies = dao.searchMovies(keyword);
        if (!movies.isEmpty()) {
            printMovies(movies);
        } else {
            System.out.println("No movies found with keyword: " + keyword);
        }
    }

    public void printMovies(List<Movie> movies) {
        int titleMaxLength = "Title".length();
        int castMaxLength = "Cast".length();
        int categoryMaxLength = "Category".length();
        int budgetMaxLength = "Budget".length();
        int releaseDateMaxLength = "Release Date".length();

        for (Movie movie : movies) {
            titleMaxLength = Math.max(titleMaxLength, movie.getTitle().length());
            castMaxLength = Math.max(castMaxLength, movie.getCastStr().toString().length());
            categoryMaxLength = Math.max(categoryMaxLength, movie.getCategory().length());
            budgetMaxLength = Math.max(budgetMaxLength, movie.getBudgetStr().length());
            releaseDateMaxLength = Math.max(releaseDateMaxLength, movie.getReleaseDate().toString().length());
        }

        String separator = "+-" + "-".repeat(titleMaxLength) + "-+-" + "-".repeat(castMaxLength) + "-+-"
                + "-".repeat(categoryMaxLength) + "-+-" + "-".repeat(budgetMaxLength) + "-+-"
                + "-".repeat(releaseDateMaxLength) + "-+";

        System.out.println(separator);
        System.out.printf("| %-" + titleMaxLength + "s | %-" + castMaxLength + "s | %-" + categoryMaxLength + "s | %-"
                + budgetMaxLength + "s | %-"
                + releaseDateMaxLength + "s |\n", "Title", "Cast", "Category", "Budget", "Release Date");
        System.out.println(separator);

        for (Movie movie : movies) {
            String releaseDate = movie.getReleaseDate() == null ? "" : sdf.format(movie.getReleaseDate());
            System.out.printf(
                    "| %-" + titleMaxLength + "s | %-" + castMaxLength + "s | %-" + categoryMaxLength + "s | %-"
                            + budgetMaxLength + "s | %-" + releaseDateMaxLength + "s |\n",
                    movie.getTitle(), movie.getCastStr(), movie.getCategory(), movie.getBudgetStr(), releaseDate);

            System.out.println(separator);
        }
    }

    public void viewMovieDetails(Scanner scanner) {
        System.out.print("Enter movie title: ");
        String title = scanner.nextLine();
        if (title != null && !title.isEmpty()) {
            Movie movie = dao.findMovieByTitle(title);
            if (movie != null) {
                System.out.println("Movie Details:");
                System.out.println("Title: " + movie.getTitle());
                System.out.println("Cast: " + movie.getCastStr());
                System.out.println("Category: " + movie.getCategory());
                System.out.println("Release Date: " + sdf.format(movie.getReleaseDate()));
                System.out.println("Budget: " + movie.getBudgetStr());
            }
        }
        System.out.println("No movie found with title: " + title);
    }

    public void addMovieToFavorites(Scanner scanner) {
        boolean registered = checkRegistration(scanner);
        if (registered) {
            System.out.println("Enter movie title to add to favorites: ");
            String title = scanner.nextLine();
            Movie movie = dao.findMovieByTitle(title);
            if (movie != null) {
                List<Movie> favorites = dao.getCurrentUser().getFavorites();
                if (favorites.contains(movie)) {
                    System.out.println("Movie already added to favorites");
                    return;
                }
                dao.addToFavorites(movie);
                System.out.println("Movie added to favorites");
            } else {
                System.out.println("No movie found with title: " + title);
            }
        }
    }

    private boolean checkRegistration(Scanner scanner) {
        User user = dao.getCurrentUser();
        if (user == null) {
            System.out.println("Please register first");
            registerUser(scanner);
            return dao.getCurrentUser() != null;
        }
        return true;
    }

    public boolean isValidEmail(String email) {
        return email != null
                && email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }

    public void removeMovieFromFavorites(Scanner scanner) {
        boolean registered = checkRegistration(scanner);
        if (registered) {
            System.out.println("Enter movie title to remove from favorites: ");
            String title = scanner.nextLine();
            Movie movie = dao.findMovieByTitle(title);
            if (movie != null) {
                List<Movie> favorites = dao.getCurrentUser().getFavorites();
                if (!favorites.contains(movie)) {
                    System.out.println("Movie not found in favorites");
                    return;
                }
                dao.removeFromFavorites(movie);
                System.out.println("Movie removed from favorites");
            } else {
                System.out.println("No movie found with title: " + title);
            }
        }
    }

    public void viewPersonalDetails(Scanner scanner) {
        boolean registered = checkRegistration(scanner);
        if (registered) {
            User user = dao.getCurrentUser();
            System.out.println("Personal Details:");
            System.out.println("Email: " + user.getEmail());
            System.out.println("Favorites: " + user.getFavoritesStr());
        }
    }

    public void searchFavoriteMovies(Scanner scanner) {
        boolean registered = checkRegistration(scanner);
        if (registered) {
            System.out.print("Enter search keyword (title/cast/category): ");
            String keyword = scanner.nextLine();
            List<Movie> movies = dao.searchFavoriteMovies(keyword);
            if (!movies.isEmpty()) {
                printMovies(movies);
            } else {
                System.out.println("No movies found with keyword: " + keyword);
            }
        }
    }

}
