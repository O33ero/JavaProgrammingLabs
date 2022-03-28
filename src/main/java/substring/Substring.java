package substring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Substring {

    private Substring(){}

    public static Integer[] boyerMoore(String string, String pattern) {
        int patternLength = pattern.length();
        int stringLength = string.length();

        if (patternLength > stringLength) {
            return new Integer[0];
        }

        Map<Character, Integer> offsetMap = new HashMap<>();

        for (int i = 1; i <= patternLength - 1; i++) {
            char ch = pattern.charAt(patternLength - i - 1);
            if (!offsetMap.containsKey(ch)) {
                offsetMap.put(ch, i);
            }
        }
        char ch = pattern.charAt(patternLength - 1);
        if (!offsetMap.containsKey(ch)) {
            offsetMap.put(ch, patternLength);
        }

        List<Integer> result = new ArrayList<>();
        int i = patternLength - 1;
        while(i < stringLength) {
            if (string.charAt(i) == pattern.charAt(patternLength - 1)) {
                if (helperBoyerMoore(string, i, pattern)) {
                    result.add(i - patternLength + 1);
                    i++;
                } else {
                    i += offsetMap.get(pattern.charAt(patternLength - 1));
                }
            } else {
                i += offsetMap.getOrDefault(string.charAt(i),  patternLength);
            }
        }

        return result.toArray(new Integer[0]);
    }

    private static boolean helperBoyerMoore(String string, int index, String pattern) {
        for(int i = pattern.length() - 1; i >= 0; i--) {
            if (string.charAt(index) == pattern.charAt(i)) {
                index--;
            } else {
                return false;
            }
        }
        return true;
    }
}
