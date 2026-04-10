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
        GameState.initializePlayers(6);
        GameState.mapGraph.addRegion("Blue");
        GameState.mapGraph.addRegion("Purple");
        GameState.mapGraph.addRegion("Yellow");
        GameState.mapGraph.addRegion("Red");
        GameState.mapGraph.addRegion("Brown");
        GameState.mapGraph.setRegions();
        GameState.players.get(1).setColor(Color.BLUE);
        GameState.players.get(2).setColor(Color.GREEN);
        GameState.players.get(3).setColor(Color.PINK);
        GameState.players.get(4).setColor(Color.RED);
        GameState.players.get(5).setColor(Color.MAGENTA);
        GameState.roundManager.determinePlayerOrder();
        GameState.activePlayer = GameState.roundManager.getPlayerOrder().get(GameState.players.size() - 1);
        for(int i=0; i<2; i++)
        {
            GameState.roundManager.advancePhase();
        }
        for(Player p: GameState.players)
        {
            for(int i=0; i<2; i++)
            {
                PowerPlant next = GameState.marketManager.getDeck().draw();
                while (next.getResourceType() != ResourceType.HYBRID)
                {
                    next = GameState.marketManager.getDeck().draw();
                }
                next.addResource(ResourceType.COAL);
                next.addResource(ResourceType.COAL);
                GameState.roundManager.getPlayerOrder().get(5).addPowerPlant(next);
            }
            break;
        }
        GameState.gameEnded = true;
        //---------------------------------------------------------------------------------------------------------------------------------------------------------

        //testing, remove this later
        PowerGridFrame game = new PowerGridFrame("Testing");
    }
}
