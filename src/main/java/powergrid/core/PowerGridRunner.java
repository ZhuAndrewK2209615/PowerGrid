package powergrid.core;
import java.awt.Color;
import java.io.*;
import powergrid.*;
import powergrid.utils.*;

public class PowerGridRunner {
    public static void main(String[]args) throws IOException
    {
        GameState.startGame();
        //-------------------------------------------All code within these lines are for testing; should be removed before game is finalized-----------------------
        GameState.initializePlayers(3);
        GameState.mapGraph.addRegion("Blue");
        GameState.mapGraph.addRegion("Yellow");
        GameState.mapGraph.addRegion("Purple");
        GameState.mapGraph.setRegions();
        GameState.roundManager.determinePlayerOrder();
        GameState.roundManager.advancePhase();
        GameState.activePlayer = GameState.roundManager.getPlayerOrder().get(0);
        for(Player p: GameState.players)
        {
            for(int i=0; i<3; i++)
            {
                p.addPowerPlant(GameState.marketManager.getDeck().draw());
                for (ResourceType r: GameState.resourceMarket.getStockMap().keySet())
                {
                    for(int j=0; j<2; j++)
                    {
                        p.getOwnedPlants().get(i).addResource(r);
                    }
                }
            }
        }
        //---------------------------------------------------------------------------------------------------------------------------------------------------------

        //testing, remove this later
        PowerGridFrame game = new PowerGridFrame("Testing");
    }
}
