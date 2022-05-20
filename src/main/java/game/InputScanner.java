package game;

import lombok.NoArgsConstructor;

import java.util.Locale;
import java.util.Scanner;


public class InputScanner {
    private Scanner scanner;

    public InputScanner() {
        this.scanner = new Scanner(System.in);
    }

    public String getStringInput() {
        return scanner.nextLine().toUpperCase();
    }

    public int getIntInput() {
        int number = scanner.nextInt();
        scanner.nextLine();
        return number;
    }

}
