package utils;


import java.util.Scanner;

public class InputScanner {
    private Scanner scanner;

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
}
