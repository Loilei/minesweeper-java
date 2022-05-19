package utils;

import java.util.Random;

public class Randomizer {

    public static int getRandomNumberFromRange(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
