import java.util.Hashtable;
import java.util.List;

public class CS400placeholder<NodeType, EdgeType extends Number> implements GraphADT<NodeType, EdgeType> {

  protected Hashtable<City, Double> vertices;


  @Override
  public boolean insertVertex(NodeType data) {
    // TODO Auto-generated method stub
    
    return false;
  }

  @Override
  public boolean removeVertex(NodeType data) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean insertEdge(NodeType source, NodeType target, EdgeType weight) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean removeEdge(NodeType source, NodeType target) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean containsVertex(NodeType data) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean containsEdge(NodeType source, NodeType target) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public EdgeType getWeight(NodeType source, NodeType target) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<NodeType> shortestPath(NodeType start, NodeType end) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public double getPathCost(NodeType start, NodeType end) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public boolean isEmpty() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public int getEdgeCount() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int getVertexCount() {
    // TODO Auto-generated method stub
    return 0;
  }
}
