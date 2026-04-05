package powergrid.core;
import java.util.*;
import powergrid.utils.*;

public class MapGraph {
    private HashMap<City, ArrayList<Connection>> mapGraph;
    private HashMap<String, ArrayList<String>> adjacentRegions;
    private ArrayList<String> mapRegions;
    private ArrayList<City> allCities;
    private boolean found; //used for path construction algorithm
    private final int CITY_RADIUS = 25; //will change later if necessary

    public MapGraph()
    {
        //initialize data structures
        mapGraph = new HashMap<>();
        mapRegions = new ArrayList<>();
        adjacentRegions = new HashMap<>();
        allCities = new ArrayList<>();
        found = false;

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

    public ArrayList<City> getAllCities()
    {
        return allCities;
    }

    public boolean cityInMap(City c)
    {
        return mapGraph.containsKey(c);
    }

    public void initializeMap()
    {
        //initialize all city objects
        Scanner mapScanner = new Scanner(MapGraph.class.getResourceAsStream("/powergrid/data/MapData.txt"));
        HashMap<String, City> locator = new HashMap<>(); //maps the name of every city to its respective city object to save time in the following operations
        while (mapScanner.hasNextLine())
        {
            String[] data = mapScanner.nextLine().split(" ");
            City newCity = new City(data[0], data[1], Integer.parseInt(data[data.length - 2]), Integer.parseInt(data[data.length - 1]));
            mapGraph.put(newCity, new ArrayList<Connection>());
            allCities.add(newCity);
            locator.put(data[0], newCity);
        }
        mapScanner.close();
        //read through file again, initialize all connection objects
        Scanner mapScanner2 = new Scanner(MapGraph.class.getResourceAsStream("/powergrid/data/MapData.txt"));
        while (mapScanner2.hasNextLine())
        {
            String[] data = mapScanner2.nextLine().split(" ");
            City originCity = locator.get(data[0]);
            for(int i=2; i<data.length-2; i+=2)
            {
                Connection connectedCity = new Connection(originCity, locator.get(data[i]), Integer.parseInt(data[i + 1]));
                mapGraph.get(originCity).add(connectedCity);
            }
        }
        mapScanner2.close();
        System.out.println("MapGraph successfully initialized");
    }

    public City getCity(int x, int y) // returns the city that the player clicked on, null if a city wasn't clicked on
    {
        for(City c: mapGraph.keySet())
        {
            if (Math.abs(x - c.getX()) <= CITY_RADIUS && Math.abs(y - c.getY()) <= CITY_RADIUS)
            {
                return c;
            }
        }
        return null;
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
    }

    public boolean validBuild(Player p, City destination) //Use when the player clicks on a city to build; returns true if it is legal for them to build there
    {
        if (!p.getCitiesBuilt().keySet().contains(destination) && destination.getOwners().size() < GameState.step)
        {
            return true;
        }
        return false;
    }

    public Path getShortestPath(Player p, City destination)
    {
        if (p.getCitiesBuilt().size() == 0) //only use this algorithm if the player has built at least 1 city
        {
            ArrayList<City> idk = new ArrayList<>();
            idk.add(destination);
            return new Path(0, idk);
        }

        //initialization
        int shortestDistance = 999999999;
        HashMap<City, Integer> smallestCosts = new HashMap<>();
        Set<City> ownedCities = p.getCitiesBuilt().keySet();
        List<City> cityPath = new ArrayList<>();

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
                if (smallestCity.equals(destination)) //no need to continue the algorithm if shortest distance for target city is already found
                {
                    break;
                }
                for(Connection connection: mapGraph.get(smallestCity))
                {
                    smallestCosts.put(connection.getDestination(), Math.min(smallestCosts.get(smallestCity) + connection.getCost(), smallestCosts.get(connection.getDestination())));
                }
                everyCityVisited = true; //loop checks if every city was visited, most likely unnecessary for finding only a specific city but here just in case
                for(City city: mapGraph.keySet())
                {
                    if (!city.wasVisited())
                    {
                        everyCityVisited = false;
                    }
                }
            }
            //reset cities visited so algorithm can be repeated correctly, then check if a shorter distance was found
            for (City city: mapGraph.keySet())
            {
                city.toggleVisited();
            }
            int previousShortest = shortestDistance;
            shortestDistance = Math.min(smallestCosts.get(destination), shortestDistance);
            if (shortestDistance < previousShortest) //if shorter distance was found, re-construct shortest path
            {
                cityPath.clear();
                found = false;
                constructPath(cityPath, destination, c, 0, shortestDistance);
                cityPath = cityPath.reversed();
            }
            for(City city: mapGraph.keySet())
            {
                if (city.wasVisited())
                {
                    city.toggleVisited();
                }
            }
        }
        return new Path(shortestDistance + 10 + destination.getOwners().size() * 5, cityPath);
    }

    private void constructPath(List<City> list, City origin, City destination, int currentCost, int targetCost) //brute force recursive algorithm for finding the sequnce of cities in the shortest path
    {
        if (found)
        {
            return;
        }
        list.add(origin);
        origin.toggleVisited();
        if (currentCost > targetCost)
        {
            origin.toggleVisited();
            list.remove(list.size() - 1);
        }
        else if (currentCost == targetCost && origin == destination)
        {
            found = true;
            return;
        }
        else
        {
            for(Connection c: mapGraph.get(origin))
            {
                if (!c.getDestination().wasVisited())
                {
                    constructPath(list, c.getDestination(), destination, currentCost + c.getCost(), targetCost);
                }
            }
            if (!found)
            {
                list.remove(list.size() - 1);
                origin.toggleVisited();
            }
        }
    }

    public HashMap<City, ArrayList<Connection>> getMapGraph()
    {
        return mapGraph;
    }
}
