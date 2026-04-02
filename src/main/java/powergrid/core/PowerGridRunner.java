package powergrid.core;
import java.awt.Color;
import java.io.*;
import powergrid.*;

public class PowerGridRunner {
    public static void main(String[]args) throws IOException
    {
        GameState.startGame();
        //-------------------------------------------All code within these lines are for testing; should be removed before game is finalized-----------------------
        GameState.initializePlayers(4);
        GameState.mapGraph.addRegion("Blue");
        GameState.mapGraph.addRegion("Purple");
        GameState.mapGraph.addRegion("Yellow");
        GameState.mapGraph.addRegion("Red");
        GameState.mapGraph.setRegions();
        GameState.activePlayer = GameState.players.get(0);
        GameState.activePlayer.setColor(Color.ORANGE);
        GameState.players.get(1).addCity(GameState.mapGraph.getCity("Dusseldorf"));
        GameState.players.get(2).addCity(GameState.mapGraph.getCity("Dusseldorf"));
        GameState.activePlayer.addCity(GameState.mapGraph.getCity("Dusseldorf"));
        GameState.mapGraph.getCity("Dusseldorf").addOwner(GameState.activePlayer);
        GameState.roundManager.determinePlayerOrder();
        //---------------------------------------------------------------------------------------------------------------------------------------------------------

        PowerGridFrame game = new PowerGridFrame("Power Grid");
        
    }
}
