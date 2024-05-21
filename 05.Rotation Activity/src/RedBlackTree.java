// --== CS400 Project One File Header ==--
// Name: Shao Xuan Lee
// CSL Username: shaol
// Email: slee2268@wisc.edu
// Lecture #: 003 @2:25pm
// Notes to Grader: <any optional extra notes to your grader>
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Stack;

/**
 * Red-Black Tree implementation with a Node inner class for representing the nodes of the tree.
 * Currently, this implements a Binary Search Tree that we will turn into a red black tree by
 * modifying the insert functionality. In this activity, we will start with implementing rotations
 * for the binary search tree insert algorithm. You can use this class' insert method to build a
 * regular binary search tree, and its toString method to display a level-order traversal of the
 * tree.
 */
public class RedBlackTree<T extends Comparable<T>> {

  /**
   * This class represents a node holding a single value within a binary tree the parent, left, and
   * right child references are always maintained.
   */
  protected static class Node<T> {
    public T data;
    public Node<T> parent; // null for root node
    public Node<T> leftChild;
    public Node<T> rightChild;

    public Node(T data) {
      this.data = data;
    }

    /**
     * @return true when this node has a parent and is the left child of that parent, otherwise
     *         return false
     */
    public boolean isLeftChild() {
      return parent != null && parent.leftChild == this;
    }

  }

  protected Node<T> root; // reference to root node of tree, null when empty
  protected int size = 0; // the number of values in the tree

  /**
   * Performs a naive insertion into a binary search tree: adding the input data value to a new node
   * in a leaf position within the tree. After this insertion, no attempt is made to restructure or
   * balance the tree. This tree will not hold null references, nor duplicate data values.
   * 
   * @param data to be added into this binary search tree
   * @return true if the value was inserted, false if not
   * @throws NullPointerException     when the provided data argument is null
   * @throws IllegalArgumentException when the newNode and subtree contain equal data references
   */
  public boolean insert(T data) throws NullPointerException, IllegalArgumentException {
    // null references cannot be stored within this tree
    if (data == null)
      throw new NullPointerException("This RedBlackTree cannot store null references.");

    Node<T> newNode = new Node<>(data);
    if (root == null) {
      root = newNode;
      size++;
      return true;
    } // add first node to an empty tree
    else {
      boolean returnValue = insertHelper(newNode, root); // recursively insert into subtree
      if (returnValue)
        size++;
      else
        throw new IllegalArgumentException("This RedBlackTree already contains that value.");
      return returnValue;
    }
  }

  /**
   * Recursive helper method to find the subtree with a null reference in the position that the
   * newNode should be inserted, and then extend this tree by the newNode in that position.
   * 
   * @param newNode is the new node that is being added to this tree
   * @param subtree is the reference to a node within this tree which the newNode should be inserted
   *                as a descenedent beneath
   * @return true is the value was inserted in subtree, false if not
   */
  private boolean insertHelper(Node<T> newNode, Node<T> subtree) {
    int compare = newNode.data.compareTo(subtree.data);
    // do not allow duplicate values to be stored within this tree
    if (compare == 0)
      return false;

    // store newNode within left subtree of subtree
    else if (compare < 0) {
      if (subtree.leftChild == null) { // left subtree empty, add here
        subtree.leftChild = newNode;
        newNode.parent = subtree;
        return true;
        // otherwise continue recursive search for location to insert
      } else
        return insertHelper(newNode, subtree.leftChild);
    }

    // store newNode within the right subtree of subtree
    else {
      if (subtree.rightChild == null) { // right subtree empty, add here
        subtree.rightChild = newNode;
        newNode.parent = subtree;
        return true;
        // otherwise continue recursive search for location to insert
      } else
        return insertHelper(newNode, subtree.rightChild);
    }
  }

  /**
   * Performs the rotation operation on the provided nodes within this tree. When the provided child
   * is a leftChild of the provided parent, this method will perform a right rotation. When the
   * provided child is a rightChild of the provided parent, this method will perform a left
   * rotation. When the provided nodes are not related in one of these ways, this method will throw
   * an IllegalArgumentException.
   * 
   * @param child  is the node being rotated from child to parent position (between these two node
   *               arguments)
   * @param parent is the node being rotated from parent to child position (between these two node
   *               arguments)
   * @throws IllegalArgumentException when the provided child and parent node references are not
   *                                  initially (pre-rotation) related that way
   */
  private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {

    if (child.parent == null)
      throw new IllegalArgumentException("Parent is null");
    if (child.parent.equals(parent) && child.isLeftChild()) {
      this.rotateRight(parent);
    } else if ((child.parent.equals(parent) && !child.isLeftChild())) {
      this.rotateLeft(parent);
    } else {
      throw new IllegalArgumentException("This is not a parent and child relation");
    }
  }

  /**
   * helper method that handle the relationship between grandparent and child
   * 
   * @param grandparent parent of parent
   * @param parent      parent node
   * @param child       child node
   */
  private void rotationWithRoot(Node grandparent, Node parent, Node child) {
    if (grandparent == null) {
      this.root = child;
    } else if (grandparent.leftChild == parent) {
      grandparent.leftChild = child;
      child.parent = grandparent;
    } else if (grandparent.rightChild == parent) {
      grandparent.rightChild = child;
      child.parent = grandparent;
    }
  }

  /**
   * helper method that handle right rotation
   * 
   * @param parent
   */
  private void rotateRight(Node<T> parent) {
    Node grandParent = parent.parent;
    Node leftChild = parent.leftChild;
    // handle the right child of leftChild first
    parent.leftChild = leftChild.rightChild;
    if (leftChild.rightChild != null) {
      leftChild.rightChild.parent = parent;
    }
    // handle the node
    leftChild.rightChild = parent;
    parent.parent = leftChild;
    // handle the leftChild
    this.rotationWithRoot(grandParent, parent, leftChild);
  }

  /**
   * helper method that handle left rotation
   * 
   * @param parent
   */
  private void rotateLeft(Node<T> parent) {
    Node grandParent = parent.parent;
    Node rightChild = parent.rightChild;
    // handle the left child of rightChild first
    parent.rightChild = rightChild.leftChild;
    if (rightChild.leftChild != null) {
      rightChild.leftChild.parent = parent;
    }
    // handle the node
    rightChild.leftChild = parent;
    parent.parent = rightChild;
    // handle the rightChild
    this.rotationWithRoot(grandParent, parent, rightChild);
  }

  /**
   * Get the size of the tree (its number of nodes).
   * 
   * @return the number of nodes in the tree
   */
  public int size() {
    return size;
  }

  /**
   * Method to check if the tree is empty (does not contain any node).
   * 
   * @return true of this.size() return 0, false if this.size() > 0
   */
  public boolean isEmpty() {
    return this.size() == 0;
  }

  /**
   * Checks whether the tree contains the value *data*.
   * 
   * @param data the data value to test for
   * @return true if *data* is in the tree, false if it is not in the tree
   */
  public boolean contains(T data) {
    // null references will not be stored within this tree
    if (data == null)
      throw new NullPointerException("This RedBlackTree cannot store null references.");
    return this.containsHelper(data, root);
  }

  /**
   * Recursive helper method that recurses through the tree and looks for the value *data*.
   * 
   * @param data    the data value to look for
   * @param subtree the subtree to search through
   * @return true of the value is in the subtree, false if not
   */
  private boolean containsHelper(T data, Node<T> subtree) {
    if (subtree == null) {
      // we are at a null child, value is not in tree
      return false;
    } else {
      int compare = data.compareTo(subtree.data);
      if (compare < 0) {
        // go left in the tree
        return containsHelper(data, subtree.leftChild);
      } else if (compare > 0) {
        // go right in the tree
        return containsHelper(data, subtree.rightChild);
      } else {
        // we found it :)
        return true;
      }
    }
  }


  /**
   * This method performs an inorder traversal of the tree. The string representations of each data
   * value within this tree are assembled into a comma separated string within brackets (similar to
   * many implementations of java.util.Collection, like java.util.ArrayList, LinkedList, etc). Note
   * that this RedBlackTree class implementation of toString generates an inorder traversal. The
   * toString of the Node class class above produces a level order traversal of the nodes / values
   * of the tree.
   * 
   * @return string containing the ordered values of this tree (in-order traversal)
   */
  public String toInOrderString() {
    // generate a string of all values of the tree in (ordered) in-order
    // traversal sequence
    StringBuffer sb = new StringBuffer();
    sb.append("[ ");
    sb.append(toInOrderStringHelper("", this.root));
    if (this.root != null) {
      sb.setLength(sb.length() - 2);
    }
    sb.append(" ]");
    return sb.toString();
  }

  private String toInOrderStringHelper(String str, Node<T> node) {
    if (node == null) {
      return str;
    }
    str = toInOrderStringHelper(str, node.leftChild);
    str += (node.data.toString() + ", ");
    str = toInOrderStringHelper(str, node.rightChild);
    return str;
  }

  /**
   * This method performs a level order traversal of the tree rooted at the current node. The string
   * representations of each data value within this tree are assembled into a comma separated string
   * within brackets (similar to many implementations of java.util.Collection). Note that the Node's
   * implementation of toString generates a level order traversal. The toString of the RedBlackTree
   * class below produces an inorder traversal of the nodes / values of the tree. This method will
   * be helpful as a helper for the debugging and testing of your rotation implementation.
   * 
   * @return string containing the values of this tree in level order
   */
  public String toLevelOrderString() {
    String output = "[ ";
    if (this.root != null) {
      LinkedList<Node<T>> q = new LinkedList<>();
      q.add(this.root);
      while (!q.isEmpty()) {
        Node<T> next = q.removeFirst();
        if (next.leftChild != null)
          q.add(next.leftChild);
        if (next.rightChild != null)
          q.add(next.rightChild);
        output += next.data.toString();
        if (!q.isEmpty())
          output += ", ";
      }
    }
    return output + " ]";
  }

  public String toString() {
    return "level order: " + this.toLevelOrderString() + "\nin order: " + this.toInOrderString();
  }


  // Implement at least 3 boolean test methods by using the method signatures below,
  // removing the comments around them and addind your testing code to them. You can
  // use your notes from lecture for ideas on concrete examples of rotation to test for.
  // Make sure to include rotations within and at the root of a tree in your test cases.
  // If you are adding additional tests, then name the method similar to the ones given below.
  // Eg: public static boolean test4() {}
  // Do not change the method name or return type of the existing tests.
  // You can run your tests by commenting in the calls to the test methods

  
  /**
   * Testing right rotation at root
   * 
   * @return true if all nodes are at right position
   */
  public static boolean test1() { // TODO: Implement this test. return false;
    RedBlackTree<Integer> tree = new RedBlackTree();
    tree.insert(50);
    tree.insert(60);
    tree.insert(40);
    tree.insert(45);
    tree.insert(10);
    int root = tree.root.data; // 50
    int leftChild = tree.root.leftChild.data;// 40
    int rightChild = tree.root.rightChild.data; // 60
    int leftGrandChild = tree.root.leftChild.leftChild.data; // 10
    int rightChildofLeftChild = tree.root.leftChild.rightChild.data; // 45
    tree.rotate(tree.root.leftChild, tree.root);
    if (tree.root.data == root) { // 40 != 50
      return false;
    } else if (tree.root.leftChild.data == leftChild) { // 10 != 40
      return false;
    } else if (tree.root.rightChild.data == rightChild) { // 60 != 50
      return false;
    }
    try {
      if (tree.root.leftChild.leftChild.data == leftGrandChild) { // null != 10
        return false;
      }
    } catch (NullPointerException e) {
      System.out.println("leftGrandChild already Null");
    }
    if (tree.root.rightChild.leftChild.data != rightChildofLeftChild) { // 45==45
      return false;
    } else if (tree.root.rightChild.rightChild.data != rightChild) { // 60 == 60
      return false;
    }

    return true;
  }


  /**
   * testing left rotation at middle of tree
   * 
   * @return true if all nodes are at the right position
   */
  public static boolean test2() {
    RedBlackTree<Integer> tree = new RedBlackTree();
    tree.insert(10);
    tree.insert(30);
    tree.insert(20);
    tree.insert(50);
    tree.insert(40);
    tree.insert(60);
    int root = tree.root.data; // 10
    int parent = tree.root.rightChild.data; // 30
    int leftChildofParent = tree.root.rightChild.leftChild.data; // 20
    int rightChild = tree.root.rightChild.rightChild.data; // 50
    int leftChildofRightChild = tree.root.rightChild.rightChild.leftChild.data; // 40
    int rightChildofRightChild = tree.root.rightChild.rightChild.rightChild.data; // 60
    tree.rotate(tree.root.rightChild.rightChild, tree.root.rightChild);
    if (tree.root.data != root)
      return false; // 10 == 10
    if (tree.root.rightChild.data == parent)
      return false; // 30 != 50
    if (tree.root.rightChild.leftChild.data == leftChildofParent)
      return false; // 30 != 20
    if (tree.root.rightChild.rightChild.data != rightChildofRightChild)
      return false; // 60 == 60
    if (tree.root.rightChild.leftChild.leftChild.data != leftChildofParent)
      return false; // 20==20
    if (tree.root.rightChild.rightChild.data == rightChild)
      return false; // 60 != 50
    try {
      if (tree.root.rightChild.rightChild.leftChild.data == leftChildofRightChild) // null != 40
        return false;
    } catch (NullPointerException e) {
      System.out.println("the leftChildofRightChild already null");
    } ;

    return true;
  }



  /**
   * testing exception case
   * 
   * @return true if all exception are caught
   */
  public static boolean test3() {
    RedBlackTree<Integer> tree = new RedBlackTree();
    tree.insert(10);
    tree.insert(30);
    tree.insert(20);
    tree.insert(50);
    tree.insert(40);
    tree.insert(60);
    try {
      tree.rotate(tree.root, tree.root.rightChild.rightChild); // when the tree is treated as child
      return false;
    } catch (IllegalArgumentException e) {
      System.out.println("Child is root, root's parent is null");
    }
    try {
      tree.rotate(tree.root.rightChild.rightChild, tree.root); // when the relationship between two
                                                               // nodes are not parent-child
      return false;
    } catch (IllegalArgumentException e1) {
      System.out.println(e1.getMessage());
    }
    return true;
  }

  /**
   * testing left rotation at root
   * 
   * @return true if the nodes are at correct position
   */
  public static boolean test4() {
    RedBlackTree<Integer> tree = new RedBlackTree();
    tree.insert(7);
    tree.insert(8);
    tree.insert(9);
    int root = tree.root.data; // 7
    int rightChild = tree.root.rightChild.data; // 8
    int rightGrandChild = tree.root.rightChild.rightChild.data; // 9
    tree.rotate(tree.root.rightChild, tree.root);
    if (tree.root.data == root) { // root should change 8 !=7
      return false;
    } else if (tree.root.leftChild.data != root) { // 7 == 7
      return false;
    } else if (tree.root.rightChild.data != rightGrandChild) { // 9 == 9
      return false;
    }
    return true;
  }

  /**
   * testing right rotation at middle of tree
   * 
   * @return true if the nodes are at correct position
   */
  public static boolean test5() {
    RedBlackTree<Integer> tree = new RedBlackTree();
    tree.insert(9);
    tree.insert(8);
    tree.insert(7);
    int root = tree.root.data;// 9
    int leftChild = tree.root.leftChild.data; // 8
    int leftGrandChild = tree.root.leftChild.leftChild.data; // 7
    tree.rotate(tree.root.leftChild.leftChild, tree.root.leftChild);
    if (tree.root.data != root)
      return false; // 9==9
    if (tree.root.leftChild.data != leftGrandChild)
      return false; // 7==7
    if (tree.root.leftChild.rightChild.data != leftChild)
      return false; // 8==8
    if (tree.root.leftChild.leftChild != null)
      return false; // left grandchild should be null
    return true;
  }

  /**
   * Main method to run tests. Comment out the lines for each test to run them.
   * 
   * @param args
   */
  public static void main(String[] args) {
    System.out.println("Test 1 passed: " + test1());
    System.out.println("Test 2 passed: " + test2());
    System.out.println("Test 3 passed: " + test3());
    System.out.println("Test 4 passed: " + test4());
    System.out.println("Test 5 passed: " + test5());
  }

}
