package utils;

public class ScreenMaintenance {

    public static void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void quitGame() {
        System.exit(0);
    }
}
