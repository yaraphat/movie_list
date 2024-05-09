package main.java;

import java.util.Scanner;

import main.java.service.OperationService;

public class Main {
    static OperationService service = OperationService.getInstance();

    public static void main(String[] args) {
        System.out.println("Welcome to Movie List!");
        try (Scanner scanner = new Scanner(System.in)) {
            printOptions();
            while (true) {
                System.out.print("Enter your choice (Any number from 1 to 9): ");
                int choice = 0;
                try {
                    choice = scanner.nextInt();
                } catch (Exception e) {
                } finally {
                    scanner.nextLine();
                }

                switch (choice) {
                    case 1:
                        service.registerUser(scanner);
                        break;
                    case 2:
                        service.searchMovies(scanner);
                        break;
                    case 3:
                        service.viewMovieDetails(scanner);
                        break;
                    case 8:
                        printOptions();
                        break;
                    case 9:
                        System.out.println("Thanks for using our app. Goodbye!");
                        return;
                    default:
                        System.out.println("Please input the choise number only from 1 to 8.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printOptions() {
        System.out.println("1. Register");
        System.out.println("2. Search Movies");
        System.out.println("3. View Movie Details");
        System.out.println("4. Add Movie to Favorites");
        System.out.println("5. Remove Movie from Favorites");
        System.out.println("6. View Personal Details");
        System.out.println("7. Search Favorite Movies");
        System.out.println("8. Show options again");
        System.out.println("9. Exit");
    }

}