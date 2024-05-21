/**
 * this is the tester for Backend
 * @author Shao Xuan Lee
 */
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BackendDeveloperTests {

  City city;
  City city1;
  BackendMapper testBackend;

  @BeforeEach
  public void createCities() {
    city = new City("Madison");
    city1 = new City("Chicago");
    testBackend = new BackendMapper();
  }

  /**
   * test backend constructor, BackendMapper(), will return true because the graph is still empty
   */
  @Test
  public void test1() {
    assertTrue(testBackend.graph.isEmpty());
  }

  /**
   * test addCity()
   */
  @Test
  public void test2() {
    testBackend.addCity(city);
    assertTrue(testBackend.graph.containsVertex(city));
  }

  /**
   * test addRoad()
   */
  @Test
  public void test3() {
    testBackend.addCity(city);
    testBackend.addCity(city1);
    testBackend.addRoad(city, city1, 10);
    assertTrue(testBackend.graph.containsEdge(city, city1));
  }

  /**
   * test getDistance()
   */
  @Test
  public void test4() {
    testBackend.addCity(city);
    testBackend.addCity(city1);
    testBackend.addRoad(city, city1, 10);
    assertTrue(testBackend.getDistance(city, city1) == 10);
  }

  /**
   * test getPath()
   * create String str to hold the name of the cities as getPath will return List<City>
   */
  @Test
  public void test5() {
    String str = "";
    testBackend.addCity(city);
    testBackend.addCity(city1);
    testBackend.addRoad(city, city1, 10);
    for (City c : testBackend.getPath(city, city1)) {
      str += c.name() + " ";
    }
    assertTrue(str.equals("Madison Chicago "));
  }


}
