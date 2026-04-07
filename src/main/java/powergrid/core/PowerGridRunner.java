package powergrid.core;
import java.awt.Color;
import java.io.*;
import powergrid.*;

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
        GameState.players.get(5).setColor(Color.YELLOW);
        String[] cities = {"Dusseldorf"};
        for(String city: cities)
        {
            for (Player p: GameState.players)
            {
                p.addCity(GameState.mapGraph.getCity(city));
                break;
            }
        }
        GameState.roundManager.determinePlayerOrder();
        GameState.activePlayer = GameState.roundManager.getPlayerOrder().get(GameState.players.size() - 1);
        for(int i=0; i<3; i++)
        {
            GameState.roundManager.advancePhase();
            GameState.activePlayer.addPowerPlant(GameState.marketManager.getDeck().draw());
        }
        GameState.activePlayer.setColor(Color.ORANGE);
        //---------------------------------------------------------------------------------------------------------------------------------------------------------

        PowerGridFrame game = new PowerGridFrame("Power Grid");
        
    }
}
