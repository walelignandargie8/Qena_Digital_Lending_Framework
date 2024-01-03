package portal.utils;

import java.util.Random;

public class ColorUtils {
    /**
     * Generates a random color on hex format
     *
     * @return random color on hex format
     */
    public static String getRandomColor() {
        Random random = new Random();
        // create a big random number - maximum is ffffff (hex) = 16777215 (dez)
        int nextInt = random.nextInt(0xffffff + 1);
        // format it as hexadecimal string (with hashtag and leading zeros)
        return String.format("#%06x", nextInt);
    }
}
