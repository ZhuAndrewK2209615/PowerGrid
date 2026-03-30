package powergrid.utils;
import java.util.*;

public class Path {
    private int distance;
    private List<City> path;

    public Path(int distance, List<City> path)
    {
        this.distance = distance;
        this.path = path;
    }

    public int getDistance()
    {
        return distance;
    }

    public List<City> getPath()
    {
        return path;
    }
}
