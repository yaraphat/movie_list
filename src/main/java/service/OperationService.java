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
        if (email != null && !email.isEmpty()) {
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
            }
            ;
        } else {
            System.out.println("Email cannot be empty");
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

    public static void printMovies(List<Movie> movies) {
        int titleMaxLength = "Title".length();
        int castMaxLength = "Cast".length();
        int categoryMaxLength = "Category".length();
        int budgetMaxLength = "Budget".length();
        int releaseDateMaxLength = "Release Date".length();

        for (Movie movie : movies) {
            titleMaxLength = Math.max(titleMaxLength, movie.getTitle().length());
            castMaxLength = Math.max(castMaxLength, movie.getCast().toString().length());
            categoryMaxLength = Math.max(categoryMaxLength, movie.getCast().toString().length());
            budgetMaxLength = Math.max(budgetMaxLength, String.format("%.0f", movie.getBudget()).length());
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

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Movie movie : movies) {
            String budget = String.format("%.0f", movie.getBudget());
            String releaseDate = movie.getReleaseDate() == null ? "" : sdf.format(movie.getReleaseDate());
            System.out.printf(
                    "| %-" + titleMaxLength + "s | %-" + castMaxLength + "s | %-" + categoryMaxLength + "s | %-"
                            + budgetMaxLength + "s | %-" + releaseDateMaxLength + "s |\n",
                    movie.getTitle(), movie.getCast(), movie.getCategory(), budget, releaseDate);

            System.out.println(separator);
        }
    }

}
