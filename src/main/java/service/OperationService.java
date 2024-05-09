package main.java.service;

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
        if(email != null && !email.isEmpty()) {
            User user = dao.getCurrentUser();
            if(user != null && user.getEmail().equals(email)) {
                System.out.println("User already registered with email: " + email);
                return;
            }
            user = dao.findUserByEmail(email);
            if(user != null) {
                dao.setCurrentUser(user);
                System.out.println("Email " + email + " set as current user");
            } else {
                dao.registerUser(email);
            };
        } else {
            System.out.println("Email cannot be empty");
        }
    }

}
