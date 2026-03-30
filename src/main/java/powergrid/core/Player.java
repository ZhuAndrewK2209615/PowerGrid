package powergrid.core;
import java.util.*;
import powergrid.utils.*;

public class Player implements Comparable{
    private ArrayList<City> ownedCities = new ArrayList<>();

    public ArrayList<City> getCitiesBuilt()
    {
        return ownedCities;
    }

    public void addCity(City c)
    {
        ownedCities.add(c);
    }

    public int compareTo(Object o) {
        return 0; //placeholder
    }
    
}
