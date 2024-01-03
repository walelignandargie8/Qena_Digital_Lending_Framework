package portal.utils;

import java.util.Random;

public class IntUtils {
    /**
     * generates a random number
     * @param until limit
     * @return any number from 0 to limit
     */
    public static int getRandomNumber(int until) {
        Random rand = new Random();
        return rand.nextInt(until);
    }
}
