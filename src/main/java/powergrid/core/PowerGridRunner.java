package powergrid.core;
import powergrid.utils.*;
import powergrid.*;

public class PowerGridRunner {
    public static void main(String[]args)
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
    }
}
