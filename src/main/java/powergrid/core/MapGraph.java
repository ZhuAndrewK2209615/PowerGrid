package powergrid.core;
import java.util.*;
import powergrid.utils.*;

public class MapGraph {
    private HashMap<City, ArrayList<Connection>> mapGraph;

    public MapGraph()
    {
        mapGraph = new HashMap<>();
    }

    public void initializeMap()
    {
        //initialize all city objects
        Scanner mapScanner = new Scanner(MapGraph.class.getResourceAsStream("/powergrid/data/MapData.txt"));
        HashMap<String, City> locator = new HashMap<>(); //maps the name of every city to its respective city object to save time in the following operations
        while (mapScanner.hasNextLine())
        {
            String[] data = mapScanner.nextLine().split(" ");
            City newCity = new City(data[0], data[1]);
            mapGraph.put(newCity, new ArrayList<Connection>());
            locator.put(data[0], newCity);
        }
        mapScanner.close();
        //read through file again, initialize all connection objects
        Scanner mapScanner2 = new Scanner(MapGraph.class.getResourceAsStream("/powergrid/data/MapData.txt"));
        while (mapScanner2.hasNextLine())
        {
            String[] data = mapScanner2.nextLine().split(" ");
            City originCity = locator.get(data[0]);
            for(int i=2; i<data.length; i+=2)
            {
                Connection connectedCity = new Connection(originCity, locator.get(data[i]), Integer.parseInt(data[i + 1]));
                mapGraph.get(originCity).add(connectedCity);
            }
        }
        mapScanner2.close();
        System.out.println(mapGraph);
    }
}
