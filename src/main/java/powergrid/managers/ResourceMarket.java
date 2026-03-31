package powergrid.managers;
import java.util.*;
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

    
}
