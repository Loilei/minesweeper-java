package utils;

import java.util.Random;

public class Randomizer {

    public static int getRandomNumberFromRange(int min, int max) {
        final var random = new Random();
        return random.nextInt(max - min) + min;
    }
}
