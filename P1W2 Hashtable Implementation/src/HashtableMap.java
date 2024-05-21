// --== CS400 Project One File Header ==--
// Name: Shao Xuan Lee
// CSL Username: shaol
// Email: slee2268@wisc.edu
// Lecture #: 003 @2:25pm
// Notes to Grader: <any optional extra notes to your grader>

import java.util.NoSuchElementException;
import java.util.LinkedList;

/**
 * This class combine key and value of hash node
 * 
 * @author Shao Xuan Lee
 *
 * @param <K> Type of key
 * @param <V> Type of Value
 */
class HashNode<K, V> {
  private K key;
  private V value;

  /**
   * Constructor that create the node for linked list
   * 
   * @param key   key
   * @param value value
   */
  public HashNode(K key, V value) {
    this.key = key;
    this.value = value;
  }

  /**
   * 
   * @return the key of the node
   */
  public K getKey() {
    return this.key;
  }

  /**
   * 
   * @return the value of the node
   */
  public V getValue() {
    return this.value;
  }
}


/**
 * a public class that implements MapADT and functions to manipulate hash map table
 * 
 * @author Shao Xuan lee
 *
 * @param <K> key
 * @param <V> value
 */
public class HashtableMap<K, V> implements MapADT {

  protected LinkedList<HashNode<K, V>>[] hashArray; // array
  private int capacity;
  private final double LOAD_FACTOR = 0.7; // constant for load factor

  // constructor
  /**
   * initialize the value
   * 
   * @param capacity space of array
   */
  public HashtableMap(int capacity) {
    this.capacity = capacity;
    hashArray = (LinkedList<HashNode<K, V>>[]) new LinkedList<?>[capacity];
  }

  // constructor
  /**
   * initialize the value
   */
  public HashtableMap() {
    this.capacity = 15;
    hashArray = (LinkedList<HashNode<K, V>>[]) new LinkedList<?>[capacity];
  } // with default capacity = 15

  /**
   * Inserts a new (key, value) pair into the map if the map does not contain a value mapped to key
   * yet.
   * 
   * @param key   the key of the (key, value) pair to store
   * @param value the value that the key will map to
   * @return true if the (key, value) pair was inserted into the map, false if a mapping for key
   *         already exists and the new (key, value) pair could not be inserted
   */
  @Override
  public boolean put(Object key, Object value) {
    // TODO Auto-generated method stub
    if (key == null) {
      return false;
    }
    HashNode<K, V> node = new HashNode(key, value);
    int index = Math.abs(key.hashCode()) % hashArray.length;
    if (this.hashArray[index] == null) {
      this.hashArray[index] = new LinkedList<HashNode<K, V>>();
    }
    LinkedList<HashNode<K, V>> currLinkedList = this.hashArray[index];
    for (int i = 0; i < currLinkedList.size(); i++) {
      if (key.equals(currLinkedList.get(i).getKey())) {
        return false;
      }
    }

    currLinkedList.add(node);
    this.LFchecker();
    return true;
  }

  /**
   * Returns the value mapped to a key if the map contains such a mapping.
   * 
   * @param key the key for which to look up the value
   * @return the value mapped to the key
   * @throws NoSuchElementException if the map does not contain a mapping for the key
   */
  @Override
  public Object get(Object key) throws NoSuchElementException {
    // TODO Auto-generated method stub
    if (key == null) {
      throw new NoSuchElementException("key is null");
    }
    int index = Math.abs(key.hashCode()) % hashArray.length;
    if (hashArray[index] == null){
      throw new NoSuchElementException("No key found.");
    }
    for (int i = 0; i < hashArray[index].size(); i++) {
      if (key.equals(hashArray[index].get(i).getKey())) {
        return hashArray[index].get(i).getValue();
      }
    }
    throw new NoSuchElementException("No key found.");
  }

  /**
   * Removes a key and its value from the map.
   * 
   * @param key the key for the (key, value) pair to remove
   * @return the value for the (key, value) pair that was removed, or null if the map did not
   *         contain a mapping for key
   */
  @Override
  public Object remove(Object key) {
    // TODO Auto-generated method stub
    int index = Math.abs(key.hashCode()) % hashArray.length;
    for (int i = 0; i < hashArray[index].size(); i++) {
      if (key.equals(hashArray[index].get(i).getKey())) {
        return hashArray[index].remove(i).getValue();
      }
    }
    return null;
  }

  /**
   * Checks if a key is stored in the map.
   * 
   * @param key the key to check for
   * @return true if the key is stored (mapped to a value) by the map and false otherwise
   */
  @Override
  public boolean containsKey(Object key) {
    // TODO Auto-generated method stub
    int index = Math.abs(key.hashCode()) % hashArray.length;
    for (int i = 0; i < hashArray[index].size(); i++) {
      if (key.equals(hashArray[index].get(i).getKey())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the number of (key, value) pairs stored in the map.
   * 
   * @return the number of (key, value) pairs stored in the map
   */
  @Override
  public int size() {
    // TODO Auto-generated method stub
    int count = 0;
    for (int i = 0; i < this.capacity; i++) {
      if (hashArray[i] == null) {
        continue;
      }
      count += hashArray[i].size();
    }
    return count;
  }

  /**
   * Removes all (key, value) pairs from the map.
   */
  @Override
  public void clear() {
    // TODO Auto-generated method stub
    hashArray = (LinkedList<HashNode<K, V>>[]) new LinkedList<?>[capacity];
  }


  /**
   * Helper method that check the load factor and call rehash if equal or bigger LOAD_FACTOR
   * 
   * @return true if rehash successfully, false if the new LF is smaller than old LF
   */
  public boolean LFchecker() {
    if ((double) this.size() / this.capacity >= this.LOAD_FACTOR) {
      this.rehash();
      return true;
    }
    return false;
  }

  /**
   * this method double the capacity of array. Reposition all old existing elements in array
   */
  public void rehash() {
    this.capacity = this.capacity * 2;
    LinkedList<HashNode<K, V>>[] temp = this.hashArray;
    this.hashArray = (LinkedList<HashNode<K, V>>[]) new LinkedList<?>[this.capacity];
    for (int i = 0; i < temp.length; i++) {
      if (temp[i] == null)
        continue;
      for (int j = 0; j < temp[i].size(); j++) {
        this.put(temp[i].get(j).getKey(), temp[i].get(j).getValue());
      }
    }
  }

  /**
   * This method return capacity of array
   * 
   * @return capacity of array
   */
  public int getCapacity() {
    return this.capacity;
  }

  /**
   * This method return current load factor
   * 
   * @return current load factor
   */
  public double getLoadFactor() {
    return (double) this.size() / this.capacity;
  }

}


