package powergrid.utils;
import java.util.*;
import powergrid.core.*;

public class City {
    private String name;
    private String region;
    private ArrayList<Player> owners;
    private boolean visited;
    private int x;
    private int y;

    public City(String name, String region, int x, int y)
    {
        this.name = name;
        this.region = region;
        this.x = x;
        this.y = y;
        owners = new ArrayList<>();
    }

    public void addOwner(Player p)
    {
        owners.add(p);
    }

    public boolean isOccupied()
    {
        return this.owners.size() == GameState.players.size();
    }

    public ArrayList<Player> getOwners()
    {
        return owners;
    }

    public String getRegion()
    {
        return region;
    }

    public String getName()
    {
        return name;
    }

    public String toString()
    {
        return name;
    }

    public boolean wasVisited()
    {
        return visited;
    }

    public void toggleVisited()
    {
        visited = !visited;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }
}
