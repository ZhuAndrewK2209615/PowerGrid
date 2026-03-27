package powergrid.utils;

public class Connection {
    private City from;
    private City to;
    private int cost;

    public Connection(City from, City to, int cost)
    {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }

    public int getCost()
    {
        return cost;
    }

    public City getOrigin()
    {
        return from;
    }

    public City getDestination()
    {
        return to;
    }

    public String toString()
    {
        try
        {
            return from.getName() + "->" + to.getName() + " for $" + cost;
        }
        catch (Exception e)
        {
            System.out.println(from.getName() + " has an issue");
            return "";
        }
    }
}
