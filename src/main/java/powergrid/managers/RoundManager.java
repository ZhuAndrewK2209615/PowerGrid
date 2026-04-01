package powergrid.managers;
import java.util.ArrayList;
import java.util.Collections;
import powergrid.core.GameState;
import powergrid.core.Player;

public class RoundManager {
    private int phase = 1;
    private int round = 1;
    private ArrayList<Player> playerOrder = new ArrayList<>();

    public void determinePlayerOrder() {
        for(Player p: GameState.players)
        {
            playerOrder.add(p);
        }
        Collections.sort(playerOrder);
    } 

    public ArrayList<Player> getPlayerOrder() {
        return playerOrder;
    }

    public int advancePhase()
    {
        if (phase<4) {
            phase=phase+1;
        }
        else {
        phase =1;
        }
        return phase;
    }

    public int advanceRound(){
        round++;
        return round;
    }
}
