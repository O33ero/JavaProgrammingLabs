import map.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MapTest {

    @Test
    @DisplayName("Should be true")
    void Map_test_0 () {
    }

    @Test
    @DisplayName("Equality of two empty 'map'")
    void Map_Constructor_0() {
        Map<Integer, String> map0 = new Map<>();
        Map<Integer, String> map1 = new Map<>();
        Assertions.assertEquals(map0, map1);
    }

    @Test
    @DisplayName("Equality of two empty 'map' produce by copy constructor")
    void Map_Constructor_1() {
        Map<Integer, String> map0 = new Map<>();
        Map<Integer, String> map1 = new Map<>(map0);
        Assertions.assertEquals(map0, map1);
    }

    @Test
    @DisplayName("Equality of two 'map' produced by copy constructor")
    void Map_Constructor_2() {
        Map<Integer, String> map0 = new Map<>();
        map0.put(1, "One");
        map0.put(9, "Nine");
        map0.put(2, "Two");
        map0.put(8, "Eight");
        map0.put(3, "Three");
        map0.put(7, "Seven");
        Map<Integer, String> map1 = new Map<>(map0);
        Assertions.assertEquals(map0, map1);
    }

    @Test
    @DisplayName("'Map' with duplicates. Mustn't be too long or infinity loop")
    void Map_Constructor_3() {
        Map<Integer, String> map = new Map<>();
        map.put(1, "One");
        map.put(1, "One");
        map.put(1, "One");
        map.put(1, "One");
        map.put(1, "One");
        map.put(1, "One");
        map.put(1, "One");
    }

    @Test
    @DisplayName("'Map' with 100.000 element. Creation and searching. Value from 0 to 100")
    void Map_Get_0() {
        Map<Integer, Integer> map = new Map<>();
        int searchedKey = Utils.getRandom(0, 100);
        int searchedValue = Utils.getRandom(0, 100);
        map.put(searchedKey, searchedValue);
        for(int i = 0; i < 100000; i++) {
            map.put(Utils.getRandom(0, 100), Utils.getRandom(0, 100));
        }
        Assertions.assertEquals(searchedValue, map.get(searchedKey));
    }

    @Test
    @DisplayName("'Map' with 200.000 element. Creation and searching. Value from 0 to 100")
    void Map_Get_1() {
        Map<Integer, Integer> map = new Map<>();
        int searchedKey = Utils.getRandom(0, 100);
        int searchedValue = Utils.getRandom(0, 100);
        map.put(searchedKey, searchedValue);
        for(int i = 0; i < 200000; i++) {
            map.put(Utils.getRandom(0, 100), Utils.getRandom(0, 100));
        }
        Assertions.assertEquals(searchedValue, map.get(searchedKey));
    }

    @Test
    @DisplayName("'Map' with 400.000 element. Creation and searching.")
    void Map_Get_2() {
        Map<Integer, Integer> map = new Map<>();
        int searchedKey = Utils.getRandom();
        int searchedValue = Utils.getRandom();
        map.put(searchedKey, searchedValue);
        for(int i = 0; i < 400000; i++) {
            map.put(Utils.getRandom(), Utils.getRandom());
        }
        Assertions.assertEquals(searchedValue, map.get(searchedKey));
    }

    @Test
    @DisplayName("'Map' with 800.000 element. Creation and searching.")
    void Map_Get_3() {
        Map<Integer, Integer> map = new Map<>();
        int searchedKey = Utils.getRandom();
        int searchedValue = Utils.getRandom();
        map.put(searchedKey, searchedValue);
        for(int i = 0; i < 800000; i++) {
            map.put(Utils.getRandom(), Utils.getRandom());
        }
        Assertions.assertEquals(searchedValue, map.get(searchedKey));
    }

    @Test
    @DisplayName("'Map' with 2.000.000 element. Creation and searching.")
    void Map_Get_5() {
        Map<Integer, Integer> map = new Map<>();
        int searchedKey = Utils.getRandom();
        int searchedValue = Utils.getRandom();
        map.put(searchedKey, searchedValue);
        for(int i = 0; i < 2000000; i++) {
            map.put(Utils.getRandom(), Utils.getRandom());
        }
        Assertions.assertEquals(searchedValue, map.get(searchedKey));
    }

    @Test
    @DisplayName("'Map' with 6.000.000 element. Creation and searching.")
    void Map_Get_6() {
        Map<Integer, Integer> map = new Map<>();
        int searchedKey = Utils.getRandom();
        int searchedValue = Utils.getRandom();
        map.put(searchedKey, searchedValue);
        for(int i = 0; i < 6000000; i++) {
            map.put(Utils.getRandom(), Utils.getRandom());
        }
        Assertions.assertEquals(searchedValue, map.get(searchedKey));
    }

    @Test
    @DisplayName("Get from empty map")
    void Map_Get_7() {
        Map<Integer, Integer> map = new Map<>();
        Assertions.assertNull(map.get(1));
    }

}
