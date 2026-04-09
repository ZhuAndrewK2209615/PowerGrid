package powergrid.managers;
import java.util.ArrayList;
import java.util.Collections;
import powergrid.utils.PowerPlant;
import java.io.*;

// one private specifc to class helper method: STEP 3 HAS NOT BEEN INITIATED

public class MarketManager {
  private ArrayList<PowerPlant> currentMarket;
  private ArrayList<PowerPlant> futureMarket;
  private Deck plantDeck;  // assuming its randomized properly

  public MarketManager(){
    currentMarket = new ArrayList<>();  // max size of 4 unless step 3
    futureMarket = new ArrayList<>(); // max size of 4 unless step 3
    plantDeck = new Deck();  
  }


  public void setupMarket() throws IOException{ // adds 4 powerplants to each market type
    plantDeck.initializeDeck();
    ArrayList<PowerPlant> entireChosen = new ArrayList<>();

    if(!plantDeck.isEmpty()){
      for(int i =0; i < 8; i++){
        entireChosen.add(plantDeck.draw());  // draws 8 cards
      }

      Collections.sort(entireChosen);  // acesending order
      // divides 4 each 
      for(int i =0; i<4; i++){  
        currentMarket.add(entireChosen.get(i));
      }
      for(int i =4; i<8; i++){
        futureMarket.add(entireChosen.get(i));
      }
    }
  }

  public void refillMarket(){

    if(!plantDeck.isEmpty()){
      PowerPlant toAdd;
      if(!plantDeck.isEmpty()){
        toAdd = plantDeck.draw();
      }else return;
      addPlantToMarket(toAdd);  // uses helper method
    }
  }

  public PowerPlant removePlant(PowerPlant P){  // removes powerplant

    if(P == null){
      return null;
    }

    currentMarket.remove(P);
    return P;
  }

  private void addPlantToMarket(PowerPlant P){  // helper method specific to this class

    if(P == null){
      return;
    }

      if (currentMarket.size() == 4 && futureMarket.size() == 4) {
      System.out.println("No space to fill: market is already filled");
      return;
    }

    int numToCheck = P.getPlantNumber();  // number of the plant
    
    for(int i =0; i<currentMarket.size(); i++){
      if(currentMarket.get(i).getPlantNumber() < numToCheck){  // checks if we can fit a plant based of number of the plant in current market
        currentMarket.add(i,P);
        break; // ends the process here
      } 
    }

    if(!currentMarket.contains(P)){  // if the plant was not added into currentMarket, add it to future Market
      for(int i =0; i<futureMarket.size(); i++){
        if(futureMarket.get(i).getPlantNumber() < numToCheck){ // checks if we can fit a plant based of number of the plant in current market
          futureMarket.add(i,P);
          break;
        }
      }
    }
 
    if(!currentMarket.contains(P) && !futureMarket.contains(P)){  // if it was not added at all and there is still space left in the market,
      // we add it to the end (just for precaution)
      futureMarket.add(P);
    }
  }

  public ArrayList<PowerPlant> getCurrentMarket()
  {
    return currentMarket;
  }

  public ArrayList<PowerPlant> getFutureMarket()
  {
    return futureMarket;
  }

  public Deck getDeck()
  {
    return plantDeck;
  }
}
