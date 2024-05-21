/**
 * @author Shao Xuan Lee
 */
import java.util.List;

// This is the interface for backend
public interface BackendMapperInterface {

    /**
    This method will add city(node) to the Dikstra 
     */
    public void addCity(City city);

    public void addRoad(City start, City end, double distance);

/** This method will return the shortest route
 */
    public double getDistance(City city1, City city2);

/**
Search up the shortest path to a specific city
@param word the target of city that we are looking for
 */
    public List<City> getPath(City start, City end);


}