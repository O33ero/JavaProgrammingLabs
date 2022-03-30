import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import substring.Substring;

import java.util.stream.IntStream;

class SubstringTest {

    Utils.TestCases[] testCases = {
            new Utils.TestCases("персональные данные",           "данные",       new Integer[]{13}),
            new Utils.TestCases("метадата",                      "дата",         new Integer[]{4}),
            new Utils.TestCases("персональные данные переданы",  "дан",          new Integer[]{13, 24}),
            new Utils.TestCases("ддддддд",                       "д",            new Integer[]{0, 1, 2, 3, 4, 5, 6}),
            new Utils.TestCases("дададад",                       "д",            new Integer[]{0, 2, 4, 6}),
            new Utils.TestCases("abababababa",                   "aba",          new Integer[]{0, 2, 4, 6, 8}),
            new Utils.TestCases("abababababa",                   "ab",           new Integer[]{0, 2, 4, 6, 8}),
            new Utils.TestCases("aaaaaaaaaaa",                   "aaa",          new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8}),
            // FIXME: maybe should to remove this last crazy test :)
            new Utils.TestCases(
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "a",
                   IntStream.rangeClosed(0, 255).boxed().toList().toArray(new Integer[0]))
    };

    @Test
    @DisplayName("Should be true")
    void Substring_test_0() {
    }

    @Test
    @DisplayName("BoyerMoore: 'персональные данные' ('данные')")
    void Substring_boyerMoore_0() {
        // 0123456789012345678
        // персональные данные
        // данные
        //   данные
        //         данные
        //              данные Done! Answer: [13]
        Assertions.assertArrayEquals(testCases[0].expected, Substring.boyerMoore(testCases[0].string, testCases[0].pattern));
    }

    @Test
    @DisplayName("BoyerMoore: 'метадата' ('дата')")
    void Substring_boyerMoore_1() {
        // 01234567
        // метадата
        // дата
        //   дата
        //     дата Done! Answer: [4]
        Assertions.assertArrayEquals(testCases[1].expected, Substring.boyerMoore(testCases[1].string, testCases[1].pattern));
    }

    @Test
    @DisplayName("BoyerMoore: 'персональные данные переданы' ('дан')")
    void Substring_boyerMoore_2() {
        // 0123456789012345678901234567
        // персональные данные переданы
        //              дан
        //                         дан
        Assertions.assertArrayEquals(testCases[2].expected, Substring.boyerMoore(testCases[2].string, testCases[2].pattern));
    }

    @Test
    @DisplayName("BoyerMoore: 'ддддддд' ('д')")
    void Substring_boyerMoore_3() {
        // 0123456
        // ддддддд
        // д
        //  д
        //   д
        //    ...
        Assertions.assertArrayEquals(testCases[3].expected, Substring.boyerMoore(testCases[3].string, testCases[3].pattern));
    }

    @Test
    @DisplayName("BoyerMoore: 'дададад' ('д')")
    void Substring_boyerMoore_4() {
        // 0123456
        // дададад
        // д
        //   д
        //     д
        //       д

        Assertions.assertArrayEquals(testCases[4].expected, Substring.boyerMoore(testCases[4].string, testCases[4].pattern));
    }

    @Test
    @DisplayName("BoyerMoore: 'abababababa' ('aba')")
    void Substring_boyerMoore_5() {
        // 01234567890
        // abababababa
        // aba
        //   aba
        //     aba
        //       aba
        //         aba

        Assertions.assertArrayEquals(testCases[5].expected, Substring.boyerMoore(testCases[5].string, testCases[5].pattern));
    }

    @Test
    @DisplayName("BoyerMoore: 'abababababa' ('ab')")
    void Substring_boyerMoore_6() {
        // 01234567890
        // abababababa
        // ab
        //   ab
        //     ab
        //       ab
        //         ab

        Assertions.assertArrayEquals(testCases[6].expected, Substring.boyerMoore(testCases[6].string, testCases[6].pattern));
    }

    @Test
    @DisplayName("BoyerMoore: 'aaaaaaaaaaa' ('aaa')")
    void Substring_boyerMoore_7() {
        // 01234567890
        // aaaaaaaaaaa
        // aaa
        //  aaa
        //   aaa
        //    ...
        Assertions.assertArrayEquals(testCases[7].expected, Substring.boyerMoore(testCases[7].string, testCases[7].pattern));
    }

    @Test
    @DisplayName("BoyerMoore: 'a' x 256 ('a')")
    void Substring_boyerMoore_8() {
        // 01234567890
        // aaaaaaaaaaaaaaaaaaa...
        // a
        //  a
        //   a
        //    ...
        Assertions.assertArrayEquals(testCases[8].expected, Substring.boyerMoore(testCases[8].string, testCases[8].pattern));
    }

    @Test
    @DisplayName("BoyerMoore: All in one")
    void Substring_boyerMoore_allInOne() {
        for(Utils.TestCases test : testCases) {
            Assertions.assertArrayEquals(test.expected, Substring.boyerMoore(test.string, test.pattern));
        }
    }

    @Test
    @DisplayName("RabinKarp: All in one (.polynomialHash)")
    void Substring_rabinKarp_allInOne() {
        for(Utils.TestCases test : testCases) {
            Assertions.assertArrayEquals(test.expected, Substring.rabinKarp(test.string, test.pattern));
        }
    }

    @Test
    @DisplayName("RabinKarp: All in one (.hashCode)")
    void Substring_rabinKarp_allInOne_hashCode() {
        for(Utils.TestCases test : testCases) {
            Assertions.assertArrayEquals(test.expected, Substring.rabinKarp(test.string, test.pattern, String::hashCode));
        }
    }

    @Test
    @DisplayName("KnuthMorrisPratt: All in one")
    void Substring_knuthMorrisPratt_allInOne() {
        for(Utils.TestCases test : testCases) {
            Assertions.assertArrayEquals(test.expected, Substring.knuthMorrisPratt(test.string, test.pattern));
        }
    }
}
