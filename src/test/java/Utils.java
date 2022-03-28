import java.util.Random;

/**
 * Класс с различными функциями и генераторами для проведения тестирования функций.
 */
public class Utils {
    private static final Random random = new Random();

    public static int getRandom() {
        return random.nextInt();
    }

    public static int getRandom(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Value of 'min' should be less or equals to 'max'");
        }
        return random.nextInt(max - min) + min;
    }


    static class TestCases {
        String string;
        String pattern;
        Integer[] expected;

        TestCases(String s, String p, Integer[] e) {
            string = s;
            pattern = p;
            expected = e;
        }
    }
}
