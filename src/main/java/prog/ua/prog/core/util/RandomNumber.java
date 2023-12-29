package prog.ua.prog.core.util;

import java.util.Random;

public class RandomNumber {
    private final Random random = new Random();
    public int randomValue(int start, int end) {
        // Validate the range
        if (start > end) {
            throw new IllegalArgumentException("Invalid range: start should be less than or equal to end");
        }

        // Generate a random number within the specified range
        return random.nextInt(end - start + 1) + start;
    }
}
