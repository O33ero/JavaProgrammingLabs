package substring;

import java.math.BigInteger;
import java.util.*;
import java.util.function.ToIntFunction;

public class Substring {

    private Substring() {
    }

    /** ------------------------Алгоритм Бойера-Мура-------------------- **/

    /**
     * <p>
     * Нахождение подстроки алгоритмом Бойера-Мура.
     * </p>
     *
     * @param string    Исходная строка
     * @param substring Искомая подстрока
     * @return Массив с индексами начала подстрок в строке или пустой массив, если строка не содержит искомой подстроки.
     */
    public static Integer[] boyerMoore(String string, String substring) {
        int patternLength = substring.length();
        int stringLength = string.length();

        if (patternLength > stringLength) {
            return new Integer[0];
        }

        Map<Character, Integer> offsetMap = new HashMap<>();

        for (int i = 1; i <= patternLength - 1; i++) {
            char ch = substring.charAt(patternLength - i - 1);
            if (!offsetMap.containsKey(ch)) {
                offsetMap.put(ch, i);
            }
        }
        char ch = substring.charAt(patternLength - 1);
        if (!offsetMap.containsKey(ch)) {
            offsetMap.put(ch, patternLength);
        }

        List<Integer> result = new ArrayList<>();
        int i = patternLength - 1;
        while (i < stringLength) {
            if (string.charAt(i) == substring.charAt(patternLength - 1)) {
                if (helperBoyerMoore(string, i, substring)) {
                    result.add(i - patternLength + 1);
                    i++;
                } else {
                    i += offsetMap.get(substring.charAt(patternLength - 1));
                }
            } else {
                i += offsetMap.getOrDefault(string.charAt(i), patternLength);
            }
        }

        return result.toArray(new Integer[0]);
    }

    /**
     * Дополнительная функция для алгоритма Бойера-Мура. Функция совершает сравнение подстроки со строкой начиная с {@code index},
     * причем поиск идет с конца (справа налево).
     *
     * @param string  Строка
     * @param index   Индекс начала поиска
     * @param pattern Подстрока
     * @return Результат сравнения подстрок
     */
    private static boolean helperBoyerMoore(String string, int index, String pattern) {
        for (int i = pattern.length() - 1; i >= 0; i--) {
            if (string.charAt(index) == pattern.charAt(i)) {
                index--;
            } else {
                return false;
            }
        }
        return true;
    }

    /** ------------------------Алгоритм Рабина-Карпа-------------------- **/

    /**
     * <p>
     *     Нахождение подстроки алгоритмом Рабина-Карпа.
     * </p>
     * <p>
     *     Алгоритм основывается на вычисление хеш функции для каждой подстроки (от 0 до n - m символа), и при совпадении значений хешей,
     *     делается вывод что в строке имеется искомая подстрока.
     * </p>
     * <p>
     *     Естественно, всё упирается в "эффективность" хеш функции. Если хеш функция может содержать коллизии (например, стандартная хеш-функция java {@code hashCode}),
     *     то такая функция не может иметь ложноотрицательные, но может иметь ложноположительные результаты, что, очевидно, не есть хорошо. Чтобы гарантировать отсутствие
     *     коллизий, нужно расширить диапазон возможных значений хеша больше чем {@link Integer} (а лучше больше чем {@link Long}). Для этого лучше использовать {@link BigInteger},
     *     которые имеет гораздо-гораздо больший диапазон значений. В качестве хеш функции можно использовать полиномиальный хеш, так как он самый простой в реализации
     *     (особенно с {@link BigInteger}).
     * </p>
     * <p>
     *     На тестах можно увидеть, что реализация через хеш с использованием {@code String.hashCode()} работает немного быстрее, чем через полиномиальный хеш
     *     (~1-2 ms против ~5-7 ms). На больших текстах эта разница будет сильно расти. Но реализация через {@code String.hashCode()} рано или поздно даст ошибку,
     *     в отличии от полиномиального хеша (который можно еще и оптимизировать разными штуками ({@code например, динамическим программированием}))
     * </p>
     *
     * <p>
     *     Нахождение подстроки алгоритмом Рабина-Карпа с хешем ввиде функционального интерфейса {@link ToIntFunction}.
     * </p>
     * <p>
     *     В качестве хеш-функции используется функциональный интерфейс {@link ToIntFunction}. В него можно передать любую функцию, которая будет превращать некоторый
     *     тип данных (в нашем случае {@code String}) в {@code Integer}. В тестах имеется пример с применением в качестве функции {@code String.hashCode()}
     * </p>
     *
     * @param string       Исходная строка
     * @param substring    Искомая подстрока
     * @param hashFunction Функция, для вычислений хеша
     * @return Массив с индексами начала подстрок в строке или пустой массив, если строка не содержит искомой подстроки.
     */
    public static Integer[] rabinKarp(String string, String substring, ToIntFunction<String> hashFunction) {
        int substringLength = substring.length();
        int stringLength = string.length();
        if (substringLength > stringLength) {
            return new Integer[0];
        }
        Integer hashSubstring = hashFunction.applyAsInt(substring);
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i <= stringLength - substringLength; i++) {
            String sub = string.substring(i, i + substringLength);
            Integer hashSub = hashFunction.applyAsInt(sub);
            boolean test = sub.equals(substring);
            if (hashSubstring.equals(hashSub)) {
                result.add(i);
            }
        }
        return result.toArray(new Integer[0]);
    }

    /**
     * <p>
     * Нахождение подстроки алгоритмом Рабина-Карпа с полиномиальным хешем.
     * </p>
     * <p>
     * Алгоритм используется реализацию хеша через полином.
     * </p>
     *
     * @param string    Исходная строка
     * @param substring Искомая подстрока
     * @return Массив с индексами начала подстрок в строке или пустой массив, если строка не содержит искомой подстроки.
     */
    public static Integer[] rabinKarp(String string, String substring) {
        int substringLength = substring.length();
        int stringLength = string.length();
        if (substringLength > stringLength) {
            return new Integer[0];
        }
//        BigInteger prime = BigInteger.probablePrime(new Random().nextInt(16 - 3) + 3, new Random()); // Random number with length in range [3; 16] bit
        BigInteger fixPrime = BigInteger.valueOf(47);
        BigInteger hashSubstring = hashRabinKarp(substring, fixPrime);
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i <= stringLength - substringLength; i++) {
            if (hashSubstring.equals(
                    hashRabinKarp(string.substring(i, i + substringLength), fixPrime)
            )) {
                result.add(i);
            }
        }
        return result.toArray(new Integer[0]);
    }

    /**
     * Вычисление хеша через полином.
     *
     * @param string Строка
     * @param prime  Простое число
     * @return Хеш строки
     */
    public static BigInteger hashRabinKarp(String string, BigInteger prime) {
        Iterator<Integer> iterator = string.chars().iterator();
        BigInteger result = BigInteger.ZERO;
        BigInteger subsum = BigInteger.ZERO;
        int m = string.length();
        // hash = c1 * p^m-1 + c2 * p^m-2 + c3 * p^m-3 + ... + cm * p^0
        for (int i = 0; i < m; i++) {
            int n = iterator.next();
            subsum = prime.pow(m - i - 1).multiply(BigInteger.valueOf(n)); // p^m-i-1 * ci
            result = result.add(subsum);
        }
        return result;
    }
}
