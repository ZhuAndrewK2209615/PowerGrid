package powergrid.ui;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.imageio.ImageIO;
import powergrid.core.*;
import powergrid.utils.*;

public class PlayerMenu implements MouseListener{

    public boolean menuEnabled = true;
    public boolean isBuying = false;
    public Player viewedPlayer;
    private HashMap<ResourceType, BufferedImage> resourcesImages = new HashMap<>();
    private HashMap<ResourceType, ArrayList<Pair>> resourcePositions = new HashMap<>();
    private HashMap<PowerPlant, Pair> powerPlantPositions = new HashMap<>();
    private ResourceType selectedResource = null;
    private Pair selectedResourcePos = null;
    private PowerPlant selectedResourcePowerPlant = null;

    public PlayerMenu()
    {
        try
        {
            resourcesImages.put(ResourceType.COAL, ImageLibrary.coal);
            resourcesImages.put(ResourceType.OIL, ImageLibrary.oil);
            resourcesImages.put(ResourceType.GARBAGE, ImageLibrary.garbage);
            resourcesImages.put(ResourceType.URANIUM, ImageLibrary.uranium);
            for(ResourceType r: GameState.resourceMarket.getStockMap().keySet())
            {
                resourcePositions.put(r, new ArrayList<>());
            }
        }
        catch (Exception e)
        {
            System.out.println("Failed to load player menu images");
        }
        viewedPlayer = GameState.activePlayer;
    }
    
    public void drawMenu(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        for(ResourceType r: resourcePositions.keySet())
        {
            resourcePositions.get(r).clear();
        }
        powerPlantPositions.clear();
        g.setColor(new Color(255,250,191));
        g.fillRect(924, 29, 945, 95);
        g.fillRect(924, 153, 145, 50);
        g.fillRect(1104, 150, 500, 55);
        g.fillRect(1104, 150, 105, 55);
        g.fillRect(1504, 150, 100, 55);
        g.fillRect(1630, 150, 238, 55);
        g.fillRect(920, 220, 950, 300);

        g.setColor(Color.lightGray);
        g.fillRect(935, 230, 290, 275);  // powerplant section 1
        g.fillRect(1250, 230, 290, 275);  // powerplant section 2
        g.fillRect(1565, 230, 290, 275);  // powerplant section 3
        g2d.setStroke(new BasicStroke(7));
        g.setColor(Color.BLACK);
        g.drawRect(920, 25, 950, 100);  // top rectangle
        g.setColor(viewedPlayer.getColor());  // we have to change this
        g2d.setFont(new Font("Arial", Font.BOLD, 33));
        g.drawString(viewedPlayer.getName(), 1300, 190);
        g.setColor(GameState.activePlayer.getColor());
        g.fillOval(960, 40, 75, 75);

        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        String action = "";
        String s = "Bureaucracy";
        switch (GameState.roundManager.getPhase())
        {
            case 3: action = "buying resources"; s = "Building Houses"; break;
            case 4: action = "building houses"; s = "Bureaucracy"; break;
            case 5: action = "powering houses"; s = "Auction"; break;
        }
        g.drawString(GameState.activePlayer.getName() + " is " + action, 1220, 65);   // define n
        
        if (GameState.roundManager.getNextPlayer() != null)
        {
            s = GameState.roundManager.getNextPlayer().getName();
        }
        g.drawString("(Next: " + s + ")", 1300, 100);  // define n+1

        g2d.setStroke(new BasicStroke(5));  // number of houses code
        g.drawRect(920, 150, 150, 55);
        g.setFont(new Font("Arial", Font.PLAIN, 25));
        g.drawString("Houses: " + viewedPlayer.getCitiesBuilt().size(), 940, 185);  // N represents number of houses owned by the current player

        g2d.setStroke(new BasicStroke(7));
        g.drawRect(1100, 150, 500, 55);
        g.drawRect(1100, 150, 105, 55);
        g.drawRect(1500, 150, 100, 55);
        
        g.drawImage(ImageLibrary.rightArrow, 1520, 155, 60, 45, null);
        g.drawImage(ImageLibrary.leftArrow, 1130, 155, 60, 45, null);

        g2d.setStroke(new BasicStroke(5));
        g.drawRect(1630, 150, 238, 55);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Player Elecktros: " + viewedPlayer.getElektro(), 1635, 185);   // define elecktros for player

        g2d.setStroke(new BasicStroke(7));  // powerplant section
        g.drawRect(920, 220, 950, 300);
        g2d.setStroke(new BasicStroke(5));
        g.drawRect(935, 230, 290, 275);  // powerplant section 1
        g.drawString("Empty Power Plant", 970, 360);
        g.drawRect(1250, 230, 290, 275);  // powerplant section 2
        g.drawString("Empty Power Plant", 1290, 360);
        g.drawRect(1565, 230, 290, 275);  // powerplant section 3
        g.drawString("Empty Power Plant", 1610, 360);

        //draw player's power plants
        int x = 935;
        for(PowerPlant p: viewedPlayer.getOwnedPlants())
        {
            g.drawImage(p.getImage(), x, 230, 290, 275, null);
            powerPlantPositions.put(p, new Pair(x, 230));
            int tempX = x + 100;
            int tempY = 260;
            for(ResourceType r: p.getResourcesStored())
            {
                g.drawImage(resourcesImages.get(r), tempX, tempY, 40, 40, null);
                resourcePositions.get(r).add(new Pair(tempX, tempY));
                tempX += 50;
                if (tempX - x > 249)
                {
                    tempX = x + 100;
                    tempY += 50;
                }
            }
            x += 315;
        }
        if (selectedResource != null)
        {
            if (selectedResourcePos != null)
            {
                g2d.setColor(Color.BLUE);
                g2d.drawRect(selectedResourcePos.getX(), selectedResourcePos.getY(), 40, 40);
            }
           
            for(PowerPlant p: powerPlantPositions.keySet())
            {
                Pair pos = powerPlantPositions.get(p);
                if (p.addResource(selectedResource))
                {
                    p.removeResource(selectedResource);
                    g2d.setColor(new Color(0, 191, 99));
                    g2d.setStroke(new BasicStroke(4));
                    if (p != selectedResourcePowerPlant)
                        g2d.drawRect(pos.getX() - 2, pos.getY() - 2, 294, 279);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        
        if (!menuEnabled)
            return;
        if (selectedResource == null)
        {
            if (x > 1100 && x < 1205 && y > 150 && y < 205)
            {
                viewedPlayer = GameState.roundManager.getLeftPlayer(viewedPlayer);
            }
            if (x > 1500 && x < 1600 && y > 150 && y < 205)
            {
                viewedPlayer = GameState.roundManager.getRightPlayer(viewedPlayer);
            }
        }
        if (GameState.activePlayer == viewedPlayer)
        {
            for(ResourceType r: resourcePositions.keySet())
            {
                for(Pair p: resourcePositions.get(r))
                {
                    if (x > p.getX() && x < p.getX() + 40 && y > p.getY() && y < p.getY() + 40 && !isBuying)
                    {
                        selectedResource = r;
                        selectedResourcePos = new Pair(p.getX(), p.getY());
                        if (p.getX() < 1225)
                            selectedResourcePowerPlant = GameState.activePlayer.getOwnedPlants().get(0);
                        else if (p.getX() < 1540)
                            selectedResourcePowerPlant = GameState.activePlayer.getOwnedPlants().get(1);
                        else
                            selectedResourcePowerPlant = GameState.activePlayer.getOwnedPlants().get(2);
                        return;
                    }
                }
            }
        }
        if (selectedResource != null)
        {
            for(PowerPlant p: powerPlantPositions.keySet())
            {
                Pair pos = powerPlantPositions.get(p);
                if (x > pos.getX() && x < pos.getX() + 290 && y > pos.getY() && y < pos.getY() + 275)
                {
                    boolean added = p.addResource(selectedResource);
                    if (added)
                    {
                        if (!isBuying)
                            selectedResourcePowerPlant.removeResource(selectedResource);
                        selectedResource = null;
                        isBuying = false;
                    }
                }
            }
            if (!isBuying)
            {
                selectedResource = null;
            }
            selectedResourcePos = null;
            selectedResourcePowerPlant = null;
        }
    }

    public void addResource(ResourceType r)
    {
        viewedPlayer = GameState.activePlayer;
        selectedResource = r;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
}
