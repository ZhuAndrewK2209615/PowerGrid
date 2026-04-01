package powergrid.utils;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.imageio.*;

public class PowerPlant implements Comparable{
    private int plantNumber;
    private ResourceType fuelType;
    private int resourceCost;
    private int citiesPowered;
    private ArrayList<ResourceType> resourcesStored;
    private int maxCapacity;
    private BufferedImage image;

    public PowerPlant(int plantNumber, ResourceType fuelType, int resourceCost, int citiesPowered)
    {
        this.plantNumber = plantNumber;
        this.fuelType = fuelType;
        this.resourceCost = resourceCost;
        this.resourcesStored = new ArrayList<>();
        this.maxCapacity = resourceCost * 2;
        try
        {
            this.image = ImageIO.read(PowerPlant.class.getResource("/powergrid/Images/PowerPlant - " + plantNumber + ".png"));
        }
        catch (Exception e)
        {
            System.out.println("A PowerPlant image failed to load");
        }
    }

    public boolean power(ArrayList<ResourceType> resourcesUsed) //removes resources and returns true if can power, false otherwise. If powering a hybrid plant, pass in an ArrayList of the resources used 
    {
        if (resourcesStored.size() >= resourceCost)
        {
            if (fuelType != ResourceType.HYBRID)
            {
                for(int i=0; i<resourceCost; i++)
                {
                    resourcesStored.remove(0);
                }
            }
            else
            {
                for(int i=0; i<resourcesUsed.size(); i++)
                {
                    resourcesStored.remove(resourcesUsed.get(i));
                }
            }
            return true;
        }
        return false;
    }

    public int getPlantNumber()
    {
        return plantNumber;
    }

    public ResourceType getResourcetype()
    {
        return fuelType;
    }

    public boolean addResource(ResourceType r)
    {
        if (resourcesStored.size() >= maxCapacity)
        {
            return false;
        }
        resourcesStored.add(r);
        return true;
    }

    @Override
    public int compareTo(Object o) {
        PowerPlant other = (PowerPlant)o;
        if (this.getPlantNumber() > other.getPlantNumber())
        {
            return 1;
        }
        else if (this.getPlantNumber() < other.getPlantNumber())
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }

    public int getCitiesPowered()
    {
        return citiesPowered;
    }

    public boolean canPower()
    {
        return resourcesStored.size() >= resourceCost;
    }
}
