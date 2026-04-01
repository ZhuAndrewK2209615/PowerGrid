package powergrid.managers;
import java.util.ArrayList;
import java.util.Collections;

import powergrid.core.GameState;
import powergrid.core.Player;
public class RoundManager {
    private int Phases=1;
    private static String currentPhase="Auction";
    private static int Round=1;
    private static ArrayList<Player> playerOrder = new ArrayList<>();
}
public static void determinePlayerOrder(){
playerOrder = GameState.getPlayers();
  Collections.sort(playerOrder);
} 
public ArrayList<Player> getPlayerOrder(){
return playerOrder;
}
public static int advancePhase(int CurrentPhase) 
    {
if(CurrentPhase<4){
CurrentPhase=CurrentPhase+1;
}
else{
CurrentPhase=1;
}

return CurrentPhase;
}
public static int advanceRound(int CurrentRound){
        CurrentRound=CurrentRound+1;
        return CurrentRound;
}
}
