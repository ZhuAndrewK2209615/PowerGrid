package powergrid.core;
import java.io.*;
import java.util.*;
import powergrid.managers.*;
import java.awt.Color;

public class GameState {
    //Manager instances
    public static AuctionManager auctionManager;
    public static MarketManager marketManager;
    public static MapGraph mapGraph;
    public static ResourceMarket resourceMarket;
    public static RoundManager roundManager;

    //Players + important variables
    public static ArrayList<Player> players;
    public static int currentRound; //how many total rounds passed
    public static int currentPhase; // what the current phase of the round is (eg., 2 -> auction, 3 -> buying resources, etc.)
    public static int step; // current step of the game, is either 1, 2, or 3
    public static int phase2Requirement;
    public static int gameEndRequirement;
    public static boolean gameEnded;
    public static Player activePlayer;
    public static HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> refillData = new HashMap<>();
    public static int numRegions;
    public static ArrayList<Color> playerColors;
    public static boolean firstRound;
    // The above map means HashMap<NumberOfPlayers, HashMap<CurrentStep, [coal, oil, garbage, uranim] refill values>>>

    public static void startGame()
    {
        //Instantiate managers
        ImageLibrary.loadImages();
        auctionManager = new AuctionManager();
        marketManager = new MarketManager();
        mapGraph = new MapGraph();
        resourceMarket = new ResourceMarket();
        roundManager = new RoundManager();

        //Initialize other variables
        players = new ArrayList<>();
        currentRound = 1;
        currentPhase = 1;
        step = 1;
        phase2Requirement = 7;
        gameEndRequirement = 17;
        gameEnded = false;
        firstRound = true;
        playerColors = new ArrayList<>();
        playerColors.add(Color.RED);
        playerColors.add(Color.PINK);
        playerColors.add(new Color(90, 29, 161));
        playerColors.add(new Color(186, 186, 0));
        playerColors.add(new Color(7, 145, 19));
        playerColors.add(new Color(9, 96, 150));


        //Load data into refillData
        Scanner refillScanner = new Scanner(GameState.class.getResourceAsStream("/powergrid/data/RefillData.txt"));
        while (refillScanner.hasNextLine())
        {
            int numPlayers = refillScanner.nextInt();
            HashMap<Integer, ArrayList<Integer>> resourceTable = new HashMap<>();
            for(int i=1; i<=3; i++)
            {
                ArrayList<Integer> resourceValues = new ArrayList<>();
                for(int j=0; j<4; j++)
                {
                    resourceValues.add(refillScanner.nextInt());
                }
                resourceTable.put(i, resourceValues);
            }
            refillData.put(numPlayers, resourceTable);
        }
        refillScanner.close();
        
        //Load data into MapGraph and Deck (will be added once these classes are implemented)
        mapGraph.initializeMap();
    }

    public static void initializePlayers(int amount) //Use after number of players is chosen and game has started
    {
        for(int i=0; i<amount; i++)
        {
            players.add(new Player());
        }
        numRegions = Math.min(amount, 5);
        if (amount == 6)
        {
            phase2Requirement = 6;
            gameEndRequirement = 14;
        }
        if (amount == 5)
        {
            gameEndRequirement = 15;
        }
        marketManager.setupMarket();
    }

    public static void nextPhase()
    {
        currentPhase++;
    }

    public static void nextRound()
    {
        currentRound++;
        currentPhase = 1;
    }

    public static void updateTurnOrder()
    {
        Collections.sort(players);
    }

    public static void triggerPhase2()
    {
        step = 2;
        marketManager.removePlant(marketManager.getCurrentMarket().get(0));
        marketManager.refillMarket();
    }
}
