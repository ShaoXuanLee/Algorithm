// --== CS400 Project One File Header ==--
// Name: Shao Xuan Lee
// CSL Username: shaol
// Email: slee2268@wisc.edu
// Lecture #: 003 @2:25pm
// Notes to Grader: <any optional extra notes to your grader>

import java.util.NoSuchElementException;

/**
 * This is the tester class for HashtableMap.java
 * 
 * @author Shao Xuan Lee
 *
 */
public class HashtableMapTests {

  /**
   * This method test put() and size()
   * 
   * @return true if all test pass
   */
  public static boolean testPutandSize() {
    HashtableMap<Integer, Integer> hash = new HashtableMap<Integer, Integer>(15);
    hash.put(1, 1);
    hash.put(2, 2);
    hash.put(3, 3);
   System.out.println( hash.put(1, 2));
    if (hash.size() != 3) {
      return false;
    }
    if (hash.put(1, 2)) { // test the condition with repeat key
      return false;
    }
    return true;
  }

  /**
   * this tester test get and remove function
   * 
   * @return true if all test pass
   */
  public static boolean testGetandRemove() {
    HashtableMap<Integer, Integer> hash = new HashtableMap<Integer, Integer>(15);
    hash.put(1, 1);
    hash.put(2, 2);
    hash.put(3, 3);
    if (!hash.get(1).equals(1) && !hash.get(2).equals(2) && !hash.get(3).equals(3)) {
      return false;
    }
    if (!hash.remove(1).equals(1) && hash.size() != 2) { // size should be 2 after remote
      return false;
    }
    return true;
  }

  /**
   * test containskey() and catching exception in get()
   * 
   * @return true if all test pass
   */
  public static boolean testContainsKeyandCatchingException() {
    HashtableMap<Integer, Integer> hash = new HashtableMap<Integer, Integer>(15);
    try {
      hash.put(6, 12);
      hash.put(7, 14);
      hash.put(8, 16);
      hash.remove(8);
      if (hash.containsKey(8)) {
        return false;
      }
      hash.get(8);
      return false;
    } catch (NoSuchElementException e) {
      System.out.println(e);
      // expected to throw exception here.
      return true;
    }
  }

  /**
   * test LFchecker() and rehash()
   * 
   * @return true if all test pass
   */
  public static boolean testLFcheckerandRehashfromPut() {
    HashtableMap<Integer, Integer> hash = new HashtableMap<Integer, Integer>(5);
    hash.put(1, 2);
    hash.put(2, 3);
    hash.put(3, 4);
    hash.put(8, 5); // to use to test the reposition
    int updatedCapacity = hash.getCapacity();

    if (updatedCapacity != 10) {
      return false;
    }
    if (!hash.get(8).equals(5)) { // test the reposition after the array expanded.
      return false;
    }

    return true;
  }

  /**
   * test collision situation and clear()
   * 
   * @return true if all test pass
   */
  public static boolean testCollisionandClear() {
    int value = 0;
    HashtableMap<Integer, Integer> hash = new HashtableMap<Integer, Integer>(5);
    hash.put(1, 2);
    hash.put(6, 3);
    hash.put(11, 4);
    for (int i = 0; i < hash.hashArray[1].size(); i++) {
      value += hash.hashArray[1].get(i).getValue(); // get the sum of 2 and 3 and 4
    }
    if (value != 9) {
      return false;
    }
    if (hash.getLoadFactor() != 0.6) {
      return false;
    }
    hash.clear();
    if (hash.size() != 0) {
      return false;
    }
    return true;
  }

  /**
   * test all the tester function
   * 
   * @return true if all test pass
   */
  public static boolean testAll() {
    return testPutandSize() && testGetandRemove() && testContainsKeyandCatchingException()
        && testLFcheckerandRehashfromPut() && testCollisionandClear();
  }

  /**
   * the place the tester run
   * 
   * @param args
   */
  public static void main(String[] args) {
    //System.out.println(testAll());
    System.out.println(testPutandSize());
   
  }
}
