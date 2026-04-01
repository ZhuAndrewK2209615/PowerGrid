package powergrid.core;
import java.io.IOException;

import powergrid.PowerGridFrame;
import powergrid.utils.*;

public class PowerGridRunner {
    public static void main(String[]args) throws IOException
    {
        GameState.startGame();

        //testing, remove this later
        PowerGridFrame game = new PowerGridFrame("Testing");
    }
}
