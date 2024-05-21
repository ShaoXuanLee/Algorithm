import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RedBlackTreeTest {

  protected RedBlackTree<Integer> tree = null;

  @BeforeEach
  public void createRBT() {
    tree = new RedBlackTree<Integer>();
  }

  /**
   * this test method test the case where the aunt/uncle is red by checking the color of nodes and
   * the Order by Level
   */
  @Test
  public void test1() {
    tree.insert(23);
    tree.insert(7);
    tree.insert(41);
    tree.insert(19);
    assertEquals(tree.root.blackHeight, 1);
    assertEquals(tree.root.leftChild.blackHeight, 1);
    assertEquals(tree.root.rightChild.blackHeight, 1);
    assertEquals(tree.root.leftChild.rightChild.blackHeight, 0);
    assertEquals(tree.toLevelOrderString(), "[ 23, 7, 41, 19 ]");
  }

  /**
   * this test method test the case where the aunt/uncle is black by checking the color of nodes and
   * the Order by Level
   */
  @Test
  public void test2() {
    tree.insert(46);
    tree.insert(26);
    tree.insert(72);
    tree.root.rightChild.blackHeight = 1;
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
  public void test3() {
    tree.insert(5);
    assertEquals(tree.root.blackHeight, 1);
    tree.insert(6);
    assertEquals(tree.root.blackHeight, 1);
    assertEquals(tree.root.rightChild.blackHeight, 0);
    assertEquals(tree.toLevelOrderString(), "[ 5, 6 ]");
  }

  /**
   * Test creating a big tree
   */
  @Test
  public void test4() {
    tree.insert(32);
    tree.insert(41);
    tree.insert(57);
    tree.insert(62);
    tree.insert(79);
    tree.insert(81);
    tree.insert(93);
    tree.insert(97);
    assertEquals(tree.toLevelOrderString(), "[ 62, 41, 81, 32, 57, 79, 93, 97 ]");
  }

  /**
   * test removing a leaf node
   */
  @Test
  public void test5() {
    tree.insert(32);
    tree.insert(41);
    tree.insert(57);
    tree.insert(62);
    tree.insert(79);
    tree.insert(81);
    tree.insert(93);
    tree.insert(97);
    tree.remove(97);
    assertEquals(tree.toLevelOrderString(), "[ 62, 41, 81, 32, 57, 79, 93 ]");
  }

  /**
   * test removing the root node
   */
  public void test6() {
    tree.insert(35);
    tree.remove(35);
    assertEquals(tree.toLevelOrderString(), "[  ]");
  }
}
