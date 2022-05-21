package com.marcela.utils;

import java.util.List;
import java.util.Scanner;

public class InputScanner {
    private final Scanner scanner;

    public InputScanner() {
        this.scanner = new Scanner(System.in);
    }

    public String getStringInput() {
        return scanner.nextLine().toUpperCase();
    }

    public int getBoardSizeInput(String message) {
        int number;
        System.out.println(message);
        do {
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number");
                scanner.next();
            }
            number = scanner.nextInt();
            if (number > 10 || number < 5) {
                System.out.println("Please enter a number from range 5 to 10");
            }
        }
        while (number > 10 || number < 5);
        scanner.nextLine();
        return number;
    }

    public String getLimitedInput(List<String> options) {
        String userInput = "";
        do {
            userInput = scanner.nextLine().toUpperCase();
            if (!options.contains(userInput)) {
                System.out.println("Please provide a valid input");
            }
        } while (!options.contains(userInput));
        return userInput;
    }
}
