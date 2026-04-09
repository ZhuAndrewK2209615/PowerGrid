package powergrid.core;
import java.io.IOException;

import powergrid.PowerGridFrame;
import powergrid.utils.*;
import powergrid.*;
import java.io.*;
import java.awt.Color;

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
        //---------------------------------------------------------------------------------------------------------------------------------------------------------

        //testing, remove this later
        PowerGridFrame game = new PowerGridFrame("Testing");
    }
}
