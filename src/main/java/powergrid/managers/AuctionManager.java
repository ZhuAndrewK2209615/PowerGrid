package powergrid.managers;
import java.util.ArrayList;
import powergrid.core.Player;
import powergrid.utils.PowerPlant;
import powergrid.*;
import powergrid.managers.MarketManager;

// all methods are public, no helper methods used specific to this class

public class AuctionManager {
    private PowerPlant currentPlant;
    private int currentBid;
    private Player highestBidder;
    private ArrayList<Player> activePlayers;
    //private ArrayList<Player> discardedPlayers;

    public AuctionManager(){
      currentPlant = null;
      currentBid = 0;
      highestBidder = null;
      activePlayers = new ArrayList<>();
    }
    public void startAuction(PowerPlant P){
      currentPlant = P;
      currentBid = P.getPlantNumber(); // setting currentBid to the lowest cost or number on plant card 
      highestBidder = null;
      activePlayers = new ArrayList<>(); // resets the playerAuction, a method outside this class
      // will have to update or place in the players into the arraylist
    }

    public boolean placeBid(Player P, int amount){
      if(activePlayers.contains(P)){
        if(amount > currentBid){
          currentBid = amount;
          highestBidder = P;
          return true;
        }
      }
      return false;
    }

    public void passBid(Player P){
      activePlayers.remove(P);
      //discardedPlayers.add(P);
    }

    public boolean resolveAuction(){  // only one person can exist at this stage
      // either all players have passed or won an auction in the game effectively removing them from 
      // the arraylist of activePlayers

      if(activePlayers.size() == 1 && highestBidder == null){
        highestBidder = activePlayers.get(0);
        highestBidder.spendElektro(currentBid);
        highestBidder.addPowerPlant(currentPlant);
        MarketManager.removePlant(currentPlant);
        currentBid = currentPlant.getPlantNumber();
        return true;
      }
      return false;
    }

    public int getCurrentBid(){
      return currentBid;
    }
    
    public PowerPlant getCurrentPlant(){
      return currentPlant;
    }

    public Player getHighestBidder(){
      return highestBidder;
    }

    public ArrayList<Player> getActiveBidders(){
      return activePlayers;
    }

}
