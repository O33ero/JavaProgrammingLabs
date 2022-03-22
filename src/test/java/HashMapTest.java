import hashmap.HashMap;
import hashmap.HashMapException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Map;

class HashMapTest {

    @Test
    @DisplayName("Should be true")
    void HashMap_test_0() {
    }

    @Test
    void HashMap_constructor_0() {
        HashMap<Integer, String> hashMap0 = new HashMap<>(10);
        HashMap<Integer, String> hashMap1 = new HashMap<>(10f);
        HashMap<Integer, String> hashMap2 = new HashMap<>(10f, 10);
    }

    @Test
    void HashMap_put_0() {
        HashMap<Integer, String> hashMap = new HashMap<>();
        hashMap.put(1, "1");
        hashMap.put(2, "2");
        hashMap.put(3, "3");
        hashMap.put(4, "4");
        hashMap.put(5, "5");
        hashMap.put(6, "6");
        hashMap.put(7, "7");
        hashMap.put(8, "8");
        hashMap.put(9, "9");

        Assertions.assertEquals(9, hashMap.getSize());
        Assertions.assertEquals("2", hashMap.get(2));
        Assertions.assertNull(hashMap.getOrNull(10));
    }

    @Test
    void HashMap_put_1() {
        HashMap<Integer, String> hashMap = new HashMap<>();
        hashMap.put(1, "1");
        hashMap.put(1, "2");
        hashMap.put(1, "3");
        hashMap.put(1, "4");
        hashMap.put(1, "5");
        hashMap.put(1, "6");
        hashMap.put(1, "7");
        hashMap.put(1, "8");
        hashMap.put(1, "9");

        Assertions.assertEquals(1, hashMap.getSize());
        Assertions.assertEquals("9", hashMap.get(1));
    }

    @Test
    void HashMap_put_2() {
        HashMap<Integer, String> hashMap = new HashMap<>();
        hashMap.putIfNotExist(1, "1");
        hashMap.putIfNotExist(1, "2");
        hashMap.putIfNotExist(1, "3");
        hashMap.putIfNotExist(1, "4");
        hashMap.putIfNotExist(1, "5");
        hashMap.putIfNotExist(1, "6");
        hashMap.putIfNotExist(1, "7");
        hashMap.putIfNotExist(1, "8");
        hashMap.putIfNotExist(1, "9");

        Assertions.assertEquals(1, hashMap.getSize());
        Assertions.assertEquals("1", hashMap.get(1));
    }

    @Test
    void HashMap_put_3() {
        HashMap<Integer, String> hashMap = new HashMap<>(2000);
        for(int i = 0; i < 10000; i++) {
            hashMap.put(i, Integer.toString(i));
        }

        Assertions.assertEquals("2022", hashMap.get(2022));
    }
}
