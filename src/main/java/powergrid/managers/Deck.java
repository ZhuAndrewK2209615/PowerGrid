package powergrid.managers;
import java.io.*;
import java.util.*;
import powergrid.utils.*;
import powergrid.core.*;

public class Deck {
    
    private Deque<PowerPlant> powerPlants;
    private TreeSet<PowerPlant> discardedPlants;

    public Deck()
    {
        powerPlants = new LinkedList<>();
        discardedPlants = new TreeSet<>();
    }

    public void initializeDeck() //Only call this once number of players has been decided
    {
        ArrayList<PowerPlant> initialPlants = new ArrayList<>();
        ArrayList<PowerPlant> futurePlants = new ArrayList<>();
        PowerPlant step3 = new PowerPlant(999, ResourceType.NONE, 0, 0);

        //read the powerplant textfile
        Scanner scanner = new Scanner(Deck.class.getResourceAsStream("/powergrid/data/PlantData.txt"));
        while (scanner.hasNextLine())
        {
            String[] data = scanner.nextLine().split(" ");
            PowerPlant next = new PowerPlant(Integer.parseInt(data[0]), ResourceType.valueOf(data[2].toUpperCase()), Integer.parseInt(data[1]), Integer.parseInt(data[3]));
            if (next.getPlantNumber() <= 15)
            {
                initialPlants.add(next);
            }
            else
            {
                futurePlants.add(next);
            }
        }
        scanner.close();
        Collections.shuffle(initialPlants);
        Collections.shuffle(futurePlants);

        //discard plants based on number of players
        int initialDiscard = 0;
        int futureDiscard = 0;
        
        switch (GameState.players.size())
        {
            case 3:
                initialDiscard = 2; futureDiscard = 6; break;
            case 4:
                initialDiscard = 1; futureDiscard = 3; break;
        }
        for(int i=0; i<initialDiscard; i++)
        {
            discardedPlants.add(initialPlants.remove(0));
        }
        for(int i=0; i<futureDiscard; i++)
        {
            discardedPlants.add(futurePlants.remove(0));
        }

        //put power plants into the deck
        while (initialPlants.size() > 9)
        {
            futurePlants.add(initialPlants.remove(0));
        }
        Collections.shuffle(futurePlants);
        powerPlants.addFirst(step3);
        for(PowerPlant p: futurePlants)
        {
            powerPlants.addFirst(p);
        }
        for(PowerPlant p: initialPlants)
        {
            powerPlants.addFirst(p);
        }
        System.out.println("All Power Plants successfully initialized");
    }

    public boolean isEmpty()
    {
        return powerPlants.isEmpty();
    }
    
    public PowerPlant draw()
    {
        return powerPlants.pop();
    }

    @Override
    public String toString()
    {
        return powerPlants.toString();
    }
}