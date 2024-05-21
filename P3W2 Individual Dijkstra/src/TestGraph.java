import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestGraph {

  private CS400Graph<String,Integer> graph;
  
  /**
   * Instantiate graph.
   */
  @BeforeEach
  public void createGraph() {
      graph = new CS400Graph<>();
      // insert vertices A-F
      graph.insertVertex("A");
      graph.insertVertex("B");
      graph.insertVertex("C");
      graph.insertVertex("D");
      graph.insertVertex("E");
      graph.insertVertex("F");
      // insert edges
      graph.insertEdge("A","B",6);
      graph.insertEdge("A","C",2);
      graph.insertEdge("A","D",5);
      graph.insertEdge("B","E",1);
      graph.insertEdge("B","C",2);
      graph.insertEdge("C","B",3);
      graph.insertEdge("C","F",1);
      graph.insertEdge("D","E",3);
      graph.insertEdge("E","A",4);
      graph.insertEdge("F","A",1);
      graph.insertEdge("F","D",1);
      System.out.println("HI");
  }

  /**
   * Checks the distance/total weight cost from the vertex A to F.
   */
  @Test
  public void testPathCostAtoF() {
      assertTrue(graph.getPathCost("A", "F") == 3);
  }

  /**
   * Checks the distance/total weight cost from the vertex A to D.
   */
  @Test
  public void testPathCostAtoD() {
      assertTrue(graph.getPathCost("A", "D") == 4);
  }

  /**
   * Checks the ordered sequence of data within vertices from the vertex 
   * A to D.
   */
  @Test
  public void testPathAtoD() {
      assertTrue(graph.shortestPath("A", "D").toString().equals(
          "[A, C, F, D]"
      ));
  }

  /**
   * Checks the ordered sequence of data within vertices from the vertex 
   * A to F.
   */
  @Test
  public void testPathAtoF() {
      assertTrue(graph.shortestPath("A", "F").toString().equals(
          "[A, C, F]"
      ));
  }

}
