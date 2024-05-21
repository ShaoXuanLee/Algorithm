/**
 * @author Shao Xuan Lee
 */
import java.util.List;

/**
 * this class contain method for backend
 * @author shaox
 *
 */
public class BackendMapper implements BackendMapperInterface {

  protected CS400Graph<City, Double> graph;

  public BackendMapper() {
    graph = new CS400Graph<>();
  }

  /**
   * add a city to the CS400 graph
   */
  @Override
  public void addCity(City city) {
    // TODO Auto-generated method stub
    graph.insertVertex(city);
  }

  /**
   * add a road between two cities
   */
  @Override
  public void addRoad(City start, City end, double distance) {
    graph.insertEdge(start, end, distance);
  }

  /**
   * this method will return the distance between 2 cities
   */
  @Override
  public double getDistance(City city1, City city2) {
    // TODO Auto-generated method stub
    return graph.getPathCost(city1, city2);
  }


  /**
   * this method will return the shortest path between 2 cities
   */
  @Override
  public List<City> getPath(City start, City end) {
    // TODO Auto-generated method stub
    return graph.shortestPath(start, end);
  }

}

/**
 * @author EdmundYuYi Tan
 *
 */
// public class BookMapperBackend implements IBookMapperBackend{
//
// private PlaceholderHashtableMapBD<String, IBook> bookMap;
// String authorFilter;
//
// public BookMapperBackend() {
// bookMap = new PlaceholderHashtableMapBD<String, IBook>();
// authorFilter = null;
// }
// @Override
// public void addBook(IBook book) {
// bookMap.put(book.getISBN13(), book);
// }
//
// @Override
// public int getNumberOfBooks() {
// return bookMap.size();
// }
//
// @Override
// public void setAuthorFilter(String filterBy) {
// authorFilter = filterBy;
//
// }
//
// @Override
// public String getAuthorFilter() {
// return authorFilter;
// }
//
// @Override
// public void resetAuthorFilter() {
// authorFilter = null;
//
// }
//
// @Override
// public List<IBook> searchByTitleWord(String word) {
// List<IBook> results = new LinkedList<IBook>();
// String[] authorBuffer;
// String[] titleBuffer;
// for (IBook book : bookMap) {
// authorBuffer = book.getAuthors().split("/");
// titleBuffer = book.getTitle().split(" ");
// if (wordSearch(titleBuffer, word)) {
// if (authorFilter != null) {
// if (wordSearch(authorBuffer, authorFilter)) {
// results.add(book);
// }
// } else {
// results.add(book);
// }
// }
// }
// return results;
// }
//
// @Override
// public IBook getByISBN(String ISBN) {
// try {
// IBook result = bookMap.get(ISBN);
// return result;
// } catch (NoSuchElementException e) {
// return null;
// }
// }
//
// private boolean wordSearch(String[] strArray, String word) {
// if (word.isBlank()) {
// return true;
// }
// for (String str : strArray) {
// if (str.toLowerCase().equals(word.toLowerCase())) {
// return true;
// }
// }
//
// return false;
// }

