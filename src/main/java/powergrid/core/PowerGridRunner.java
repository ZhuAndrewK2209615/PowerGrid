package powergrid.core;
import powergrid.utils.*;

public class PowerGridRunner {
    public static void main(String[]args)
    {
        GameState.startGame();

        //testing, remove this later
        Player player = new Player();
        player.addCity(GameState.mapGraph.getCity("Flensburg"));
        Path shortest = GameState.mapGraph.getShortestPath(player, GameState.mapGraph.getCity("Konstanz"));
        System.out.println(shortest.getPath());
        System.out.println(shortest.getDistance());
    }
}
