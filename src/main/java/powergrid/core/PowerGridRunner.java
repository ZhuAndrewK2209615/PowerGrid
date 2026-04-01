package powergrid.core;
import powergrid.utils.*;
import powergrid.*;
import java.io.*;

public class PowerGridRunner {
    public static void main(String[]args) throws IOException
    {
        GameState.startGame();
        try
        {
            PowerGridFrame game = new PowerGridFrame("Power Grid");
        }
        catch (Exception e)
        {
            System.out.println("Failed to start game");
        }

        // GameState.initializePlayers(4);
        // while (!GameState.marketManager.getDeck().isEmpty())
        // {
        //     System.out.println(GameState.marketManager.getDeck().draw());
        // }
        // System.out.println(GameState.marketManager.getFutureMarket());
    }
}
