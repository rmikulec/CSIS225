import java.awt.*;
import java.util.*;
/**
 * Track is a class to represent the edges between 
 * cities on the game board
 * 
 * @author Chris, Matt, Ronald, Ryan, Luis
 * @version 1.0
 */
public class Track
{
    protected String color;
    protected String label;
    protected City city1;
    protected City city2;
    protected int length;
    protected boolean isTaken;
    protected int numLoco;
    protected ArrayList<Stocks> stockOptions;
    
    public Track(City cityOne, City cityTwo, String colorIn, int lengthIn, int numLocomotives, ArrayList<Stocks> stocks)
    { 
        isTaken = false;
        color  = colorIn;
        city1 = cityOne;
        city2 = cityTwo;
        label = city1.name + "," + city2.name;
        length = lengthIn;
        numLoco = numLocomotives;
        stockOptions = stocks;
    }
    
    public City getCity1()
    {
        return city1;
    }
    
    public City getCity2()
    {
        return city2;
    }
    
    public void setTaken(boolean taken)
    {
        isTaken = taken;
    }
    
    public boolean hasStock()
    {
        if(stockOptions.size() == 0)
        {
            return false;
        }
        return true;
    }
}
