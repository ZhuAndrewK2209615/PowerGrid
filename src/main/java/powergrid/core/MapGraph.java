package powergrid.core;
import java.util.*;
import powergrid.utils.*;

public class MapGraph {
    private HashMap<City, ArrayList<Connection>> mapGraph;
    private HashMap<String, ArrayList<String>> adjacentRegions;
    private ArrayList<String> mapRegions;

    public MapGraph()
    {
        //initialize data structures
        mapGraph = new HashMap<>();
        mapRegions = new ArrayList<>();
        adjacentRegions = new HashMap<>();

        //initialize regions adjacency list
        Scanner regionScanner = new Scanner(MapGraph.class.getResourceAsStream("/powergrid/data/RegionData.txt"));
        while (regionScanner.hasNextLine())
        {
            String[] data = regionScanner.nextLine().split(" ");
            adjacentRegions.put(data[0], new ArrayList<>());
            for(int i=1; i<data.length; i++)
            {
                adjacentRegions.get(data[0]).add(data[i]);
            }
        }
        regionScanner.close();
    }

    public City getCity(String name)
    {
        for(City c: mapGraph.keySet())
        {
            if (c.getName().equals(name))
            return c;
        }
        return null;
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
        System.out.println("MapGraph successfully initialized");
    }

    public boolean addRegion(String regionAdded) //Use this method during map selection, when each player chooses a region
    {
        boolean adjacent = false;
        if (!mapRegions.isEmpty()) // First, ensure that the region being added is adjacent by seeing if at least one previously selected region is adjacent to it
        {
            for(String region: mapRegions)
            {
                if (adjacentRegions.get(region).contains(regionAdded))
                {
                    adjacent = true;
                    break;
                }
            }
        }
        else
        {
            adjacent = true;
        }
        if (!adjacent)
        {
            return false;
        }
        mapRegions.add(regionAdded);
        return true;
    }

    public void setRegions() //Use this method when the map selection is confirmed; removes all unused cities from the Map Graph
    {
        ArrayList<City> citiesToRemove = new ArrayList<>();
        for(City c: mapGraph.keySet())
        {
            if (!mapRegions.contains(c.getRegion()))
            {
                citiesToRemove.add(c);
            }
            else
            {
                ArrayList<Connection> connectedCities = mapGraph.get(c);
                for(int i=0; i<connectedCities.size(); i++)
                {
                    if (!mapRegions.contains(connectedCities.get(i).getDestination().getRegion()))
                    {
                        connectedCities.remove(i);
                        i--;
                    }
                }
            }
        }
        for(City c: citiesToRemove)
        {
            mapGraph.remove(c);
        }
        System.out.println(mapGraph);
    }

    public boolean validBuild(Player p, City destination) //Use when the player clicks on a city to build; returns true if it is legal for them to build there
    {
        if (!p.getCitiesBuilt().contains(destination) && destination.getOwners().size() < GameState.step)
        {
            return true;
        }
        return false;
    }

    public int getBuildCost(Player p, City destination) //Use when player clicks on a valid city; returns the smallest cost to build there
    {
        return getShortestPath(p, destination) + 10 + destination.getOwners().size();
    }

    private int getShortestPath(Player p, City destination)
    {
        if (p.getCitiesBuilt().size() == 0) //only use this algorithm if the player has built at least 1 city
        {
            return 0;
        }

        //initialization
        int shortestDistance = 999999999;
        HashMap<City, Integer> smallestCosts = new HashMap<>();
        ArrayList<City> ownedCities = p.getCitiesBuilt();

        //Dijkstra's algorithm performed on every city
        for(City c: ownedCities)
        {
            for(City city: mapGraph.keySet())
            {
                smallestCosts.put(city, 999999999);
            }
            smallestCosts.put(c, 0);
            boolean everyCityVisited = false;
            while (!everyCityVisited)
            {
                int smallestDistance = 99999999;
                City smallestCity = null;
                for(City city: mapGraph.keySet())
                {
                    if (smallestCosts.get(city) < smallestDistance && !city.wasVisited())
                    {
                        smallestDistance = smallestCosts.get(city);
                        smallestCity = city;
                    }
                }
                smallestCity.toggleVisited();
                for(Connection connection: mapGraph.get(smallestCity))
                {
                    smallestCosts.put(connection.getDestination(), Math.min(smallestCosts.get(smallestCity) + connection.getCost(), smallestCosts.get(connection.getDestination())));
                }
                everyCityVisited = true;
                for(City city: mapGraph.keySet())
                {
                    if (!city.wasVisited())
                    {
                        everyCityVisited = false;
                    }
                }
            }
            shortestDistance = Math.min(smallestCosts.get(destination), shortestDistance);
            for (City city: mapGraph.keySet())
            {
                city.toggleVisited();
            }
        }
        return shortestDistance;
    }
}
