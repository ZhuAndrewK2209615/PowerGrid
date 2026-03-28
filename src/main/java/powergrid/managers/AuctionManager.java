package powergrid.managers;
import java.util.ArrayList;
import powergrid.core.Player;
import powergrid.utils.PowerPlant;

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
    private void startAuction(PowerPlant P){
      currentPlant = P;
      currentBid = P.getPlantNumber(); // setting currentBid to the lowest cost or number on plant card 
      highestBidder = null;
      activePlayers = new ArrayList<>(); // resets the playerAuction, a method outside this class
      // will have to update or place in the players into the arraylist
    }

    private boolean placeBid(Player P, int amount){
      if(activePlayers.contains(P)){   // player exists
        if(amount > currentBid){
          currentBid = amount;  // his new bid will be higher and so will become the curentBid
          highestBidder = P;
          return true;
        }
      }
      return false;
    }

    private void passBid(Player P){
      activePlayers.remove(P);  // we remove player from activePlayers
      //discardedPlayers.add(P);
    }

    private Player resolveAuction(){

      if(activePlayers.size() == 1 && highestBidder == null){
        highestBidder = activePlayers.get(0);
        currentBid = currentPlant.getPlantNumber();
        return highestBidder; // if there is only one player, they will be on index 0 
        // and will by default win
      }

      if(highestBidder != null && currentPlant != null){  // if multiple people remain
        highestBidder.addPowerPlant(currentPlant); // award highestBidder the current plant
        highestBidder.spendElektro(currentBid);
        MarketManager.removePlant(currentPlant);
        currentPlant = null;
        Player toReturn = highestBidder;
        highestBidder = null;
        currentBid = 0;
        activePlayers = new ArrayList<>();
        return toReturn;
      }
      return null;
    }

    private int getCurrentBid(){
      return currentBid;
    }
    
    private PowerPlant getCurrentPlant(){
      return currentPlant;
    }

    private Player getHighestBidder(){
      return highestBidder;
    }

    private ArrayList<Player> getActiveBidders(){
      return activePlayers;
    }

}

