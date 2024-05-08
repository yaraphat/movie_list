package main.java;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Movie List!");
        try (Scanner scanner = new Scanner(System.in)) {
            String input = "";
            while (input != null) {
                System.out.print("Enter a command: ");
                input = scanner.nextLine();
                switch (input) {
                    case "exit":
                        input = null;
                        break;

                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Thanks for using our app. Goodbye!");
    }
}