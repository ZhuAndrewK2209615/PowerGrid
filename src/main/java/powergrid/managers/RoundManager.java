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
        if (phase<5) {
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

    public Player getNextPlayer()
    {
        int currentPlayerIndex = playerOrder.indexOf(GameState.activePlayer);
        if (phase == 2 || phase == 5)
        {
            if (currentPlayerIndex == playerOrder.size() - 1)
            {
                return null;
            }
            return playerOrder.get(currentPlayerIndex + 1);
        }
        else if (phase == 3 || phase == 4)
        {
            if (currentPlayerIndex == 0)
            {
                return null;
            }
            return playerOrder.get(currentPlayerIndex - 1);
        }
        return null;
    }

    public Player getLeftPlayer(Player currentView)
    {
        int currentPlayerIndex = playerOrder.indexOf(currentView);
        if (currentPlayerIndex == 0)
        {
            return playerOrder.get(playerOrder.size() - 1);
        }
        return playerOrder.get(currentPlayerIndex - 1);
    }

    public Player getRightPlayer(Player currentView)
    {
        int currentPlayerIndex = playerOrder.indexOf(currentView);
        if (currentPlayerIndex == playerOrder.size() - 1)
        {
            return playerOrder.get(0);
        }
        return playerOrder.get(currentPlayerIndex + 1);
    }

    public int getPhase()
    {
        return phase;
    }
}
