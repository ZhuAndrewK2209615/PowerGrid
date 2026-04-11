package powergrid.managers;
import java.util.ArrayList;

import powergrid.utils.PowerPlant;
import powergrid.core.*;

// all methods are public, no helper methods used specific to this class

public class AuctionManager {
    private PowerPlant currentPlant;
    private int currentBid;
    private Player highestBidder;
    private ArrayList<Player> activePlayers;
    private Player currentBidder;
    private int pendingBid;
    //private ArrayList<Player> discardedPlayers;

    public AuctionManager(){
      currentPlant = null;
      currentBid = 0;
      pendingBid = 0;
      highestBidder = null;
      currentBidder = null;
      activePlayers = new ArrayList<>();
    }
    public void startAuction(PowerPlant P){
      currentPlant = P;
      currentBid = P.getPlantNumber() - 1; // setting currentBid to the lowest cost or number on plant card 
      currentBidder = GameState.activePlayer;
      pendingBid = P.getPlantNumber();
      //activePlayers = new ArrayList<>(); // resets the playerAuction, a method outside this class
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

    public void confirmBid()
    {
      currentBid = pendingBid;
      highestBidder = currentBidder;
      pendingBid = currentBid + 1;
    }

    public void resetAuction()
    {
      currentPlant = null;
      currentBid = 0;
      pendingBid = 0;
      highestBidder = null;
      currentBidder = null;
    }

    public boolean resolveAuction(){  // only one person can exist at this stage
      // either all players have passed or won an auction in the game effectively removing them from 
      // the arraylist of activePlayers

      if(activePlayers.size() == 1 && highestBidder == null){
        highestBidder = activePlayers.get(0);
        highestBidder.spendElektro(currentBid);
        highestBidder.addPowerPlant(currentPlant);
        GameState.marketManager.removePlant(currentPlant);
        currentBid = currentPlant.getPlantNumber();
        return true;
      }
      return false;
    }

    public void setNextBidder()
    {
      do
      {
        int currentPlayerIndex = activePlayers.indexOf(currentBidder);
        if (currentPlayerIndex == activePlayers.size() - 1)
        {
          currentBidder = activePlayers.get(0);
        }
        else
        {
          currentBidder = activePlayers.get(currentPlayerIndex + 1);
        }
      }
      while (currentBidder.hasPassedBid());

    }

    public void incrementPendingBid(boolean increase)
    {
      if (increase && currentBidder.getElektro() > pendingBid)
      {
        pendingBid++;
      }
      if (!increase && pendingBid > currentBid + 1)
      {
        pendingBid--;
      }
    }

    public int getPendingBid()
    {
      return pendingBid;
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

    public Player getCurrentBidder()
    {
      return currentBidder;
    }

}
