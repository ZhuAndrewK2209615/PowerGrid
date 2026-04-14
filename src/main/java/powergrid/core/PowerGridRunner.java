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
        GameState.roundManager.advancePhase();
        GameState.activePlayer = GameState.roundManager.getPlayerOrder().get(2);
        //---------------------------------------------------------------------------------------------------------------------------------------------------------

        //testing, remove this later
        PowerGridFrame game = new PowerGridFrame("Testing");
    }
}
