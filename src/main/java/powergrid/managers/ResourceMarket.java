package powergrid.managers;
import java.util.*;
import powergrid.core.*;
import powergrid.utils.*;

public class ResourceMarket {
    private HashMap<ResourceType, Integer> currentStock; //amount of tokens available for purchase in the market
    private HashMap<ResourceType, Integer> availableTokens; //amount of tokens in the background / ready to be refilled into the market

    public ResourceMarket()
    {
        currentStock = new HashMap<>();
        availableTokens = new HashMap<>();
        
        //initialize currentStock
        currentStock.put(ResourceType.COAL, 24);
        currentStock.put(ResourceType.OIL, 18);
        currentStock.put(ResourceType.GARBAGE, 6);
        currentStock.put(ResourceType.URANIUM, 2);

        //initialize availableTokens
        availableTokens.put(ResourceType.COAL, 0);
        availableTokens.put(ResourceType.OIL, 6);
        availableTokens.put(ResourceType.GARBAGE, 18);
        availableTokens.put(ResourceType.URANIUM, 10);
    }

    public int getPrice(ResourceType type)
    {
        int amountAvailable = currentStock.get(type);
        if (type != ResourceType.URANIUM)
        {
            int cost = 8 - amountAvailable / 3;
            if (amountAvailable % 3 == 0)
            {
                cost++;
            }
            return cost;
        }
        else
        {
            if (amountAvailable <= 4)
            {
                return 16 - (amountAvailable - 1) * 2;
            }
            else
            {
                return 8 - (amountAvailable - 5);
            }
        }
    }

    public boolean canBuy(ResourceType r, Player p)
    {
        if (p.getElektro() >= getPrice(r) && currentStock.get(r) > 0)
        {
            boolean yes = false;
            for(PowerPlant plant: p.getOwnedPlants())
            {
                if (plant.getResourceType() == r || (plant.getResourceType() == ResourceType.HYBRID && (r == ResourceType.COAL || r == ResourceType.OIL)))
                {
                    yes = true;
                }
            }
            if (yes)
            {
                return true;
            }
        }
        return false;
    }

    public void buyResource(ResourceType type)
    {
        currentStock.put(type, currentStock.get(type) - 1);
    }

    public void refillResources()
    {
        ResourceType[] order = {ResourceType.COAL, ResourceType.OIL, ResourceType.GARBAGE, ResourceType.URANIUM};
        for(int i=0; i<4; i++)
        {
            int availableForRestock = availableTokens.get(order[i]);
            int maxRestock = Math.min(GameState.refillData.get(GameState.players.size()).get(GameState.step).get(i), 24 - currentStock.get(order[i]));
            int totalRestock = Math.min(availableForRestock, maxRestock);
            availableTokens.put(order[i], availableTokens.get(order[i]) - totalRestock);
            currentStock.put(order[i], currentStock.get(order[i]) - totalRestock);
        }
    }

    public int getSupply(ResourceType type)
    {
        return currentStock.get(type);
    }

    public int getAvailableTokens(ResourceType type)
    {
        return availableTokens.get(type);
    }

    public HashMap<ResourceType, Integer> getStockMap()
    {
        return currentStock;
    }
}
