/**
 * this is a placeholder class that implements ICapitalName
 * @author shaox
 *
 */
public class City implements ICapitalName{
  private String name;
  private Double distance;
  
  public City(String name) {
    this.name = name;
  }
  
  @Override
  public String name() {
    // TODO Auto-generated method stub
    return this.name;
  }

  @Override
  public Double Longitude() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Double Latitude() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Double Distance() {
    // TODO Auto-generated method stub
    return this.distance;
  }

}
