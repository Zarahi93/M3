import java.util.*;

class Solution {
  public int romanToInt(String s) {
    // Crear un hasmap para guardar el numero romano y su valor en numerico
    HashMap<Character, Integer> romanMap = new HashMap<>();
    romanMap.put('I', 1);
    romanMap.put('V', 5);
    romanMap.put('X', 10);
    romanMap.put('L', 50);
    romanMap.put('C', 100);
    romanMap.put('D', 500);
    romanMap.put('M', 1000);
    //
    int integer = 0;
  }
}