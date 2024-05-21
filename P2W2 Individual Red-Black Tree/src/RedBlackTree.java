// --== CS400 Fall 2022 File Header Information ==--
// Name: Shao Xuan Lee
// Email: slee2268@wisc.edu
// Team: DY
// TA: Sujitha
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Stack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Red-Black Tree implementation with a Node inner class for representing the nodes of the tree.
 * Currently, this implements a Binary Search Tree that we will turn into a red black tree by
 * modifying the insert functionality. In this activity, we will start with implementing rotations
 * for the binary search tree insert algorithm. You can use this class' insert method to build a
 * regular binary search tree, and its toString method to display a level-order traversal of the
 * tree.
 */
public class RedBlackTree<T extends Comparable<T>>  {

  /**
   * This class represents a node holding a single value within a binary tree the parent, left, and
   * right child references are always maintained.
   */
  protected static class Node<T> {
    public T data;
    public Node<T> parent; // null for root node
    public Node<T> leftChild;
    public Node<T> rightChild;
    public int blackHeight;

    public Node(T data) {
      this.data = data;
      this.blackHeight = 0;
    }

    // null node
    public Node() {
      // TODO Auto-generated constructor stub
      this.blackHeight = 2;
    }

    /**
     * @return true when this node has a parent and is the left child of that parent, otherwise
     *         return false
     */
    public boolean isLeftChild() {
      return parent != null && parent.leftChild == this;
    }

  }
  protected static class NullNode extends Node {
    private NullNode() {
      super(-1);
      this.blackHeight = 1;
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
      root.blackHeight = 1;
      return true;
    } // add first node to an empty tree
    else {
      boolean returnValue = insertHelper(newNode, root); // recursively insert into subtree
      if (returnValue)
        size++;
      else
        throw new IllegalArgumentException("This RedBlackTree already contains that value.");
      root.blackHeight = 1;
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
        enforceRBTreePropertiesAfterInsert(newNode);
        return true;
        // otherwise continue recursive search for location to insert
      } else {
        return insertHelper(newNode, subtree.leftChild);
      }
    }

    // store newNode within the right subtree of subtree
    else {
      if (subtree.rightChild == null) { // right subtree empty, add here
        subtree.rightChild = newNode;
        newNode.parent = subtree;
        enforceRBTreePropertiesAfterInsert(newNode);
        return true;
        // otherwise continue recursive search for location to insert
      } else {
        return insertHelper(newNode, subtree.rightChild);
      }
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
      this.root.parent = null;
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

  /**
   * This class ensure that the properties of RBT are being followed
   * 
   * @param newChild new child or red node
   */
  protected void enforceRBTreePropertiesAfterInsert(Node newChild) {
    Node parent = newChild.parent;
    // Case 1: the root become red during the recursion
    if (parent == null) {
      newChild.blackHeight = 1;
      return; // return to avoid exception being thrown
    }
    // Case 2: if the parent is black, no rules are violated
    if (parent.blackHeight == 1)
      return; // do nothing because parent is black

    // Cases with Aunt starting from here
    Node grandParent = newChild.parent.parent;
    Node aunt = getAunt(parent);
    int blackHeight = 0;

    // the If block is used to decide the blackHeight of Aunt
    if (aunt != null) {
      blackHeight = aunt.blackHeight;
    } else {
      blackHeight = 1;
    }
    switch (blackHeight) {
      // aunt is red, do recolor and check if any properties are violated
      case 0:
        grandParent.blackHeight = 0;
        parent.blackHeight = 1;
        aunt.blackHeight = 1;
        enforceRBTreePropertiesAfterInsert(grandParent);
        break;

      // aunt is black
      case 1:
        if (parent.isLeftChild()) {
          // this if block consider the case where the grandparent,parent and child are in straight
          // line
          if (newChild.isLeftChild()) {
            rotate(parent, grandParent);
            grandParent.blackHeight = 0;
            parent.blackHeight = 1;
          } else {
            // the relationship in this else block between GP, P and C is left,left, right
            rotate(newChild, parent);
            rotate(newChild, grandParent);
            grandParent.blackHeight = 0;
            newChild.blackHeight = 1;
          }
        } else {
          // the relationship in this else block between GP, P and C is right, right, right
          if (!newChild.isLeftChild()) {
            rotate(parent, grandParent);
            grandParent.blackHeight = 0;
            parent.blackHeight = 1;
          } else {
            // the relationship in this else block between GP, P and C is right, right, left
            rotate(newChild, parent);
            rotate(newChild, grandParent);
            grandParent.blackHeight = 0;
            newChild.blackHeight = 1;
          }
        }
        break;
    }
  }

  /**
   * this is a helper method to determine the aunt
   * 
   * @param parent
   * @return the opposite node of parent
   */
  public Node getAunt(Node parent) {
    Node grandParent = parent.parent;
    if (parent.isLeftChild())
      return grandParent.rightChild;
    else if (!parent.isLeftChild())
      return grandParent.leftChild;
    else
      throw new IllegalStateException("This is not grandparent-parent relationship");
  }

  public boolean remove(T data) {
    Node currNode = root;
    while (currNode != null && data.compareTo((T) currNode.data) != 0) {
      if (data.compareTo((T) currNode.data) < 0)
        currNode = currNode.leftChild;
      else
        currNode = currNode.rightChild;
    }
    if (currNode == null)
      return false;
    Node replacementNode = null;
    int deletedNodeColor = 10;

    if (currNode.leftChild == null || currNode.rightChild == null) {
      deletedNodeColor = currNode.blackHeight;
      replacementNode = removeHelper(currNode);
    } else {
      Node inOrderSuccessor = null;
      while (currNode.leftChild != null) {
        inOrderSuccessor = currNode.leftChild;
        while (inOrderSuccessor.rightChild != null)
          inOrderSuccessor = inOrderSuccessor.rightChild;
        break;
      }
      currNode.data = inOrderSuccessor.data;
      deletedNodeColor = inOrderSuccessor.blackHeight;
      replacementNode = removeHelper(inOrderSuccessor);

    }
    if (deletedNodeColor == 1) {
      enforceRBTPropertiesAfterRemove(replacementNode);

      if (replacementNode.blackHeight == 2) {
        fixRelationshipOfNodes(replacementNode.parent, replacementNode, null);
      }
    }

    return true;
  }

  public Node removeHelper(Node<T> rmNode) {
    Node parent = rmNode.parent;
    Node leftChild = rmNode.leftChild;
    Node rightChild = rmNode.rightChild;
    if (rmNode == root) {
      return root = null;
    }
    if (leftChild != null) {
      parent.leftChild = leftChild;
      leftChild.parent = parent;
      return leftChild;
    } else if (rightChild != null) {
      parent.rightChild = rightChild;
      rightChild.parent = parent;
      return rightChild;
    } else {
      Node newChild = rmNode.blackHeight == 1 ? new Node() : null;
      fixRelationshipOfNodes(rmNode.parent, rmNode, newChild);
      return newChild;
    }
  }

  public void fixRelationshipOfNodes(Node parent, Node oldChild, Node newChild) {
    if (parent == null) {
      root = newChild;
    } else if (oldChild.isLeftChild()) {
      parent.leftChild = newChild;
    } else if (!oldChild.isLeftChild()) {
      parent.rightChild = newChild;
    } else {
      throw new IllegalStateException();
    }
    if (newChild != null) {
      newChild.parent = parent;
    }
  }

  protected void enforceRBTPropertiesAfterRemove(Node node) {
    // case 1: removed node is root
    if (node == root) {
      // node.blackHeight = 1;
      return;
    }
    Node sibling = getSibling(node);

    // case 2: sibling is red, graph 4
    if (!isBlack(sibling)) {
      // color swap then rotate
      sibling.blackHeight = 1;
      node.parent.blackHeight = 0;
      if (node.isLeftChild()) {
        rotateLeft(node.parent);
      } else {
        rotateRight(node.parent);
      }

      sibling = getSibling(node); // retrieve the sibling to further enforce the RBT
    }

    // case 3: black sibling with 2 black child and with red or black parent, graph 3
    if (isBlack(sibling.leftChild) && isBlack(sibling.rightChild)) {
      sibling.blackHeight = 0;
      if (node.parent.blackHeight == 0) {
        node.parent.blackHeight = 1;
        return;
      } else
        enforceRBTPropertiesAfterRemove(node.parent);
    }

    // case 4: black sibling with 1 red child, graph 1
    // first consider the double black node is left child
    if (isBlack(sibling) && isBlack(sibling.rightChild) && node.isLeftChild()) {
      sibling.leftChild.blackHeight = 1;
      sibling.blackHeight = 0;
      rotateRight(sibling);
      sibling = node.parent.rightChild;
    } else if (isBlack(sibling) && isBlack(sibling.leftChild) && !node.isLeftChild()) {
      // mirror case
      sibling.rightChild.blackHeight = 1;
      sibling.blackHeight = 0;
      rotateLeft(sibling);
      sibling = node.parent.leftChild;
    }
    // case 5: black sibling with 2 child and at least 1 of them is red child, graph 2
    if (isBlack(sibling) && (!isBlack(sibling.leftChild) || !isBlack(sibling.rightChild))) {
      sibling.blackHeight = node.parent.blackHeight;
      node.parent.blackHeight = 1;
      if (node.isLeftChild()) {
        sibling.rightChild.blackHeight = 1;
        rotateLeft(node.parent);
      }
      // mirror case
      else {
        sibling.leftChild.blackHeight = 1;
        rotateRight(node.parent);
      }
    }
  }

  private Node getSibling(Node node) {
    Node parent = node.parent;
    if (node.isLeftChild()) {
      return parent.rightChild;
    } else if (!node.isLeftChild()) {
      return parent.leftChild;
    } else
      throw new IllegalStateException();
  }

  private boolean isBlack(Node node) {
    if (node == null || node.blackHeight == 1)
      return true;
    return false;
  }
  /*
   * public static boolean test1() { RedBlackTree<Integer> tree = new RedBlackTree();
   * tree.insert(23); tree.insert(7); tree.insert(41); tree.insert(19);
   * System.out.println(tree.root.data); System.out.println(tree.root.blackHeight);
   * System.out.println(tree.root.leftChild.data);
   * System.out.println(tree.root.leftChild.blackHeight);
   * System.out.println(tree.root.rightChild.data);
   * System.out.println(tree.root.rightChild.blackHeight);
   * System.out.println(tree.root.leftChild.rightChild.data);
   * System.out.println(tree.root.leftChild.rightChild.blackHeight);
   * System.out.println(tree.toLevelOrderString()); return true; }
   * 
   * public static boolean test2() { RedBlackTree<Integer> tree = new RedBlackTree();
   * tree.insert(46); tree.insert(26); tree.insert(72); tree.root.rightChild.blackHeight = 1;
   * tree.insert(18); System.out.println(tree.root.data); System.out.println(tree.root.blackHeight);
   * System.out.println(tree.root.leftChild.data);
   * System.out.println(tree.root.leftChild.blackHeight);
   * System.out.println(tree.root.rightChild.data);
   * System.out.println(tree.root.rightChild.blackHeight);
   * System.out.println(tree.root.rightChild.rightChild.data);
   * System.out.println(tree.root.rightChild.rightChild.blackHeight);
   * System.out.println(tree.toLevelOrderString()); return true; }
   */

  // initialize the tree
  protected RedBlackTree<Integer> tree = null;

  // create the tree before every test
  @BeforeEach
  public void createRBT() {
    tree = new RedBlackTree<Integer>();
  }

  /**
   * this test method test the case where the aunt/uncle is red by checking the color of nodes and
   * the Order by Level
   */
  @Test
  public void testRedUncle() {
    tree.insert(23);
    tree.insert(7);
    tree.insert(41);
    tree.insert(19);
    assertEquals(tree.root.blackHeight, 1);
    assertEquals(tree.root.leftChild.blackHeight, 1);
    assertEquals(tree.root.rightChild.blackHeight, 1);
    assertEquals(tree.root.leftChild.rightChild.blackHeight, 0);
    assertEquals(tree.toLevelOrderString(), "[ 23, 7, 41, 19]");
  }

  /**
   * this test method test the case where the aunt/uncle is black by checking the color of nodes and
   * the Order by Level
   */
  @Test
  public void testBlackUncle() {
    tree.insert(46);
    tree.insert(26);
    tree.insert(72);
    tree.root.rightChild.blackHeight = 1; // purposely set 72 to black(default is red)
    tree.insert(18);
    assertEquals(tree.root.blackHeight, 1);
    assertEquals(tree.root.leftChild.blackHeight, 0);
    assertEquals(tree.root.rightChild.blackHeight, 0);
    assertEquals(tree.root.rightChild.rightChild.blackHeight, 1);
    assertEquals(tree.toLevelOrderString(), "[ 26, 18, 46, 72 ]");
  }

  /**
   * Testing edge cases when the first node is inserted and the tree after the second node is
   * inserted
   */
  @Test
  public void testInsert1and2Nodes() {
    tree.insert(5);
    assertEquals(tree.root.blackHeight, 1);
    tree.insert(6);
    assertEquals(tree.root.blackHeight, 1);
    assertEquals(tree.root.rightChild.blackHeight, 0);
    assertEquals(tree.toLevelOrderString(), "[ 5, 6 ]");
  }


  /**
   * Main method to run tests. Comment out the lines for each test to run them.
   * 
   * @param args
   */
  public static void main(String[] args) {
    RedBlackTree<Integer> tree = new RedBlackTree();
    tree.insert(32);
    tree.insert(41);
    tree.insert(57);
    tree.insert(62);
    tree.insert(79);
    tree.insert(81);
    tree.insert(93);
    //tree.insert(97);
    tree.remove(57);
    // tree.remove(32);
    // System.out.println(tree.root.rightChild.rightChild.rightChild.data);
    System.out.println(tree.toLevelOrderString());
    System.out.println(tree.root.data);
    System.out.println(tree.root.blackHeight);
    System.out.println(tree.root.leftChild.data);
    System.out.println(tree.root.leftChild.blackHeight);
    System.out.println(tree.root.rightChild.data);
    System.out.println(tree.root.rightChild.blackHeight);
    System.out.println(tree.root.rightChild.rightChild.data);
    System.out.println(tree.root.rightChild.rightChild.blackHeight);
    System.out.println(tree.root.rightChild.leftChild.data);
    System.out.println(tree.root.rightChild.leftChild.blackHeight);
    System.out.println(tree.root.rightChild.leftChild.rightChild.data);
    System.out.println(tree.root.rightChild.leftChild.rightChild.blackHeight);
    //System.out.println(tree.root.blackHeight);
    //System.out.println(tree.root.leftChild.blackHeight);
    //System.out.println(tree.root.leftChild.leftChild.blackHeight);
     //System.out.println(tree.root.rightChild.blackHeight);
     //System.out.println(tree.root.rightChild.rightChild.rightChild.blackHeight);

  }

}
