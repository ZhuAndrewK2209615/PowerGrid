package powergrid.ui;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import powergrid.PowerGridFrame;
import powergrid.core.*;
import powergrid.utils.*;

public class AuctionPanel extends JPanel implements MouseListener, KeyListener{
    private PowerGridFrame parent;
    private HashMap<PowerPlant, Pair> currentMarketPositions;
    private boolean auctionStarted;
    private int upArrowX;
    private int downArrowX;
    private boolean discardingPlant;
    private Player auctionWinner;
    private InfoPreviews infoPreview;
    private PowerPlant plantToDiscard = null;
    private ArrayList<ResourceType> discardedResources = new ArrayList<>();
    private ResourceType selectedResource = null;
    private int selectedResourceIndex = -1;
    private boolean boughtPowerPlant = false;

    public AuctionPanel(PowerGridFrame parent)
    {
        for(Player p: GameState.roundManager.getPlayerOrder())
        {
            GameState.auctionManager.getActiveBidders().add(p);
        }
        currentMarketPositions = new HashMap<>();
        this.parent = parent;
        infoPreview = new InfoPreviews(this);
        addMouseListener(this);
        addMouseListener(infoPreview);
        addMouseListener(infoPreview.getPlayerMenu());
        addKeyListener(this);
        addKeyListener(infoPreview);
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        g.drawImage(ImageLibrary.background2, 0, 0, getWidth(), getHeight(), null);
        if (infoPreview.isPreviewingMap)
        {
            infoPreview.drawMapPreview(g);
        }
        else if (infoPreview.isPreviewingInfo)
        {
            infoPreview.drawInfoPreview(g2d);
        }
        else if (infoPreview.isPreviewingDiscard)
        {
            infoPreview.drawDiscardPreview(g);
        }
        else
        {
            if (discardingPlant)
            {
                drawDashboard(g);
                infoPreview.drawInfoButtons(g);
                drawDiscardScreen(g);
            }
            else
            {
                drawMarket(g);
                if (!auctionStarted || (auctionWinner != null && !discardingPlant))
                    drawPrompt(g);
                drawDashboard(g);
                infoPreview.drawInfoButtons(g);
                if (auctionStarted)
                    drawAuctionInfo(g);
            }
        }
    }

    public void drawDiscardScreen(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;

        // box that says "player x has recieved powerplant x
        g2d.setStroke(new BasicStroke(7));
        g2d.setColor(new Color(255, 250, 191));
        g2d.fillRect(600, 75, 800, 100);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(600, 75, 800, 100);
        g2d.setFont(new Font("Arial", Font.BOLD, 35));
        g2d.drawString(auctionWinner.getName() + " has received Power Plant " + GameState.auctionManager.getCurrentPlant().getPlantNumber(), 685, 140); // We have to change this

        // box that shows current player powerplants
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRect(440, 200, 1100, 300);
        g2d.setColor(new Color(255, 250, 191));
        g2d.fillRect(444, 204, 1093, 293);

        // section wise
        g2d.setFont(new Font("Arial", Font.PLAIN, 24));
        g2d.setStroke(new BasicStroke(5));
        //g2d.setColor(p1 ? Color.GREEN : Color.BLACK);
        g2d.drawRect(935 - 420, 213, 290, 275);  // powerplant section 1
        if(auctionWinner.getOwnedPlants().get(0).getImage() != null){
            if (auctionWinner.getOwnedPlants().get(0) == plantToDiscard)
            {
                g2d.setColor(Color.RED);
                g2d.setStroke(new BasicStroke(8));
                g2d.drawRect(935-420, 213, 290, 275);
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(5));
            }
            else if (selectedResource != null && auctionWinner.getOwnedPlants().get(0).addResource(selectedResource))
            {
                auctionWinner.getOwnedPlants().get(0).removeResource(selectedResource);
                g2d.setColor(new Color(0, 191, 99));
                g2d.setStroke(new BasicStroke(8));
                g2d.drawRect(935-420, 213, 290, 275);
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(5));
            }
            g.drawImage(auctionWinner.getOwnedPlants().get(0).getImage(), 935-420, 213, 290, 275, null);
            int tempX = 935-420 + 100;
            int tempY = 243;
            for(ResourceType r: auctionWinner.getOwnedPlants().get(0).getResourcesStored())
            {
                g.drawImage(PlayerMenu.resourcesImages.get(r), tempX, tempY, 40, 40, null);
                tempX += 50;
                if (tempX - (935-420) > 249)
                {
                    tempX = (935-420) + 100;
                    tempY += 50;
                }
            }
        }else {
            g2d.setColor(Color.BLACK);
            g2d.drawString("Empty Power Plant", 970 - 420, 360);
        }
        
        //g2d.setColor(p2 ? Color.GREEN : Color.BLACK);
        g2d.drawRect(1250 - 420, 213, 290, 275);  // powerplant section 2
        if(auctionWinner.getOwnedPlants().get(1).getImage() != null){
            if (auctionWinner.getOwnedPlants().get(1) == plantToDiscard)
            {
                g2d.setColor(Color.RED);
                g2d.setStroke(new BasicStroke(8));
                g2d.drawRect(1250-420, 213, 290, 275);
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(5));
            }
            else if (selectedResource != null && auctionWinner.getOwnedPlants().get(1).addResource(selectedResource))
            {
                auctionWinner.getOwnedPlants().get(1).removeResource(selectedResource);
                g2d.setColor(new Color(0, 191, 99));
                g2d.setStroke(new BasicStroke(8));
                g2d.drawRect(1250-420, 213, 290, 275);
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(5));
            }
            g.drawImage(auctionWinner.getOwnedPlants().get(1).getImage(), 1250-420, 213, 290, 275, null);
            int tempX = 1250-420 + 100;
            int tempY = 243;
            for(ResourceType r: auctionWinner.getOwnedPlants().get(1).getResourcesStored())
            {
                g.drawImage(PlayerMenu.resourcesImages.get(r), tempX, tempY, 40, 40, null);
                tempX += 50;
                if (tempX - (1250-420) > 249)
                {
                    tempX = (1250-420) + 100;
                    tempY += 50;
                }
            }
        }else {
            g2d.setColor(Color.BLACK);
            g2d.drawString("Empty Power Plant", 1290 - 420, 360);
        }

        //g2d.setColor(p3 ? Color.GREEN : Color.BLACK);
        g2d.drawRect(1565 - 420, 213, 290, 275);  // powerplant section 3
        if(auctionWinner.getOwnedPlants().get(2).getImage() != null){
            if (auctionWinner.getOwnedPlants().get(2) == plantToDiscard)
            {
                g2d.setColor(Color.RED);
                g2d.setStroke(new BasicStroke(8));
                g2d.drawRect(1565-420, 213, 290, 275);
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(5));
            }
            else if (selectedResource != null && auctionWinner.getOwnedPlants().get(2).addResource(selectedResource))
            {
                auctionWinner.getOwnedPlants().get(2).removeResource(selectedResource);
                g2d.setColor(new Color(0, 191, 99));
                g2d.setStroke(new BasicStroke(8));
                g2d.drawRect(1565-420, 213, 290, 275);
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(5));
            }
            g.drawImage(auctionWinner.getOwnedPlants().get(2).getImage(), 1565-420, 213, 290, 275, null);
            int tempX = 1565-420 + 100;
            int tempY = 243;
            for(ResourceType r: auctionWinner.getOwnedPlants().get(2).getResourcesStored())
            {
                g.drawImage(PlayerMenu.resourcesImages.get(r), tempX, tempY, 40, 40, null);
                tempX += 50;
                if (tempX - (1565-420) > 249)
                {
                    tempX = (1565-420) + 100;
                    tempY += 50;
                }
            }
        }else {
            g2d.setColor(Color.BLACK);
            g2d.drawString("Empty Power Plant", 1565-380, 360);
        }
        //g2d.fillRect(getWidth() / 2 + 500, getHeight() - 110, 300, 110);
        if (GameState.auctionManager.getCurrentPlant() == plantToDiscard)
        {
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(8));
            g2d.drawRect(getWidth() / 2 + 500, getHeight() - 300, 300, 300);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(5));
        }
        else if (selectedResource != null && GameState.auctionManager.getCurrentPlant().addResource(selectedResource))
        {
            GameState.auctionManager.getCurrentPlant().removeResource(selectedResource);
            g2d.setColor(new Color(0, 191, 99));
            g2d.setStroke(new BasicStroke(8));
            g2d.drawRect(getWidth() / 2 + 500, getHeight() - 300, 300, 300);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(5));
        }
        g.drawImage(GameState.auctionManager.getCurrentPlant().getImage(), getWidth() / 2 + 500, getHeight() - 300, 300, 300, null);
        int tempX = getWidth() / 2 + 500 + 100;
        int tempY = getHeight() - 270;
        for(ResourceType r: GameState.auctionManager.getCurrentPlant().getResourcesStored())
        {
            g.drawImage(PlayerMenu.resourcesImages.get(r), tempX, tempY, 40, 40, null);
            tempX += 50;
            if (tempX - (getWidth() / 2 + 500) > 249)
            {
                tempX = (getWidth() / 2 + 500) + 100;
                tempY += 50;
            }
        }

        // text section saying u discarded\
        g2d.setColor(new Color(255, 250, 191));
        g2d.fillRect(443, 513, 345, 120);
        g2d.setColor(Color.black);
        g2d.drawRect(440, 510, 350, 125);
        g2d.setFont(new Font("Arial", Font.ITALIC, 26));
        if (plantToDiscard == null)
        {
            g2d.drawString("Click a plant to discard", 465, 560);
            g2d.setFont(new Font("Arial", Font.ITALIC, 15));
            g2d.drawString("Note: you may discard the one you just bought", 450, 600);
        }
        else
        {
                g2d.drawString("Discarding Power Plant " + plantToDiscard.getPlantNumber() + ";", 460, 560);  // we have to change this
            g2d.drawString("Redistribute Your Resources", 450, 600);
        }
        

        // 6 boxes determining reosurces stored in the game
        if (plantToDiscard != null)
        {
            drawResourceBox(g2d, 810, 510, 0);
            drawResourceBox(g2d, 810 + 135, 510, 1);
            drawResourceBox(g2d, 810 + 270, 510, 2);
            drawResourceBox(g2d, 810, 510 + 135, 3);
            drawResourceBox(g2d, 810 + 135, 510 + 135, 4);
            drawResourceBox(g2d, 810 + 270, 510 + 135, 5);
        }
        

        // discard button
        if (plantToDiscard != null)
        {
            g2d.setStroke(new BasicStroke(5));
            if (!discardedResources.isEmpty())
                g2d.setColor(new Color(223, 107, 107));
            else
                g2d.setColor(new Color(0, 191, 99));
            g2d.fillRect(1222, 513, 315, 120);
            g2d.setColor(Color.black);
            g2d.drawRect(1220, 510, 319, 125);
            if (!discardedResources.isEmpty())
            {
                g2d.setFont(new Font("Arial", Font.BOLD, 20));
                g2d.drawString("Give up remaining Resources", 1232, 580);
            }
            else
            {
                g2d.setFont(new Font("Arial", Font.BOLD, 35));
                g2d.drawString("Continue", 1300, 580);
            }
        }
        
    }

    private void drawResourceBox(Graphics2D g2d, int x, int y, int index) {
        g2d.setColor(new Color(255, 250, 191));
        g2d.fillRect(x, y, 125, 125);
        g2d.setStroke(new BasicStroke(5));
        if (selectedResourceIndex == index)
            g2d.setColor(Color.GREEN);
        else
            g2d.setColor(Color.black);
        g2d.drawRect(x, y, 125, 125);
        g2d.setFont(new Font("Arial", Font.ITALIC, 20));
        if (index < discardedResources.size())
        {
            g2d.drawImage(PlayerMenu.resourcesImages.get(discardedResources.get(index)), x + 20, y + 20, 80, 80, null);
        }
        else
        {
            g2d.drawString("Empty", x + 32, y + 65);
        }
    }

    public void drawMarket(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        currentMarketPositions.clear();
        if (GameState.step < 3)
        {
            g2d.setColor(new Color(0, 191, 99));
            int x = 377;
            for(PowerPlant p: GameState.marketManager.getCurrentMarket())
            {
                if (!auctionStarted && p.getPlantNumber() <= GameState.activePlayer.getElektro())
                    g2d.fillRoundRect(x - 10, 50, 270, 270, 10, 10);
                else
                {
                    if (p.equals(GameState.auctionManager.getCurrentPlant()))
                    {
                        g2d.setColor(new Color(255, 222, 89));
                        g2d.fillRoundRect(x - 10, 50, 270, 270, 10, 10);
                    }
                }
                g2d.drawImage(p.getImage(), x, 60, 250, 250, null);
                currentMarketPositions.put(p, new Pair(x, 60));
                x += 300;
            }
            g2d.setStroke(new BasicStroke(15));
            g2d.setColor(Color.BLACK);
            x = 0;
            while (x < getWidth())
            {
                g2d.drawLine(x, 350, x + 75, 350);
                x += 125;
            }
            x = 377;
            for(PowerPlant p: GameState.marketManager.getFutureMarket())
            {
                g2d.drawImage(p.getImage(), x, 370, 250, 250, null);
                x += 300;
            }
        }
        else
        {
            g2d.setColor(new Color(0, 191, 99));
            int x = 510;
            int y = 50;
            int count = 0;
            for(PowerPlant p: GameState.marketManager.getCurrentMarket())
            {
                if (!auctionStarted && p.getPlantNumber() <= GameState.activePlayer.getElektro())
                    g2d.fillRoundRect(x - 10, y - 10, 270, 270, 10, 10);
                else
                {
                    if (p.equals(GameState.auctionManager.getCurrentPlant()))
                    {
                        g2d.setColor(new Color(255, 222, 89));
                        g2d.fillRoundRect(x - 10, y - 10, 270, 270, 10, 10);
                    }
                }
                g2d.drawImage(p.getImage(), x, y, 250, 250, null);
                currentMarketPositions.put(p, new Pair(x, y));
                x += 300;
                count++;
                if (count == 3)
                {
                    x = 510;
                    y = 370;
                }
            }
        }
    }

    public void drawPrompt(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(new Color(237, 236, 229));
        g2d.setStroke(new BasicStroke(4));
        g2d.fillRect(450, 680, 1000, 100);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(450, 680, 1000, 100);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        if (auctionWinner != null && !discardingPlant)
        {
            g2d.drawString(auctionWinner.getName() + " has won Power Plant " + GameState.auctionManager.getCurrentPlant().getPlantNumber(), 700, 720);
        }
        if (!auctionStarted)
        {
            g2d.drawString(GameState.activePlayer.getName() + " is choosing a power plant for auction", 650, 720);
        }
        if (!GameState.firstRound)
        {
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            if (!auctionStarted)
            {
                g2d.drawString("(Press 'x' to pass)", 850, 750);
            }
        }
        if (auctionWinner != null && !discardingPlant)
        {
            g2d.drawString("(Press Enter to proceed)", 750, 750);
        }
    }

    public void drawDashboard(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(new Color(237, 236, 229));
        g2d.setStroke(new BasicStroke(8));
        int x = getWidth() / 2 - GameState.players.size() * 80;
        g2d.fillRect(x, getHeight() - 150, GameState.players.size() * 80 * 2, 150);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, getHeight() - 150, GameState.players.size() * 80 * 2, 150);
        x += 50;
        for(Player p: GameState.roundManager.getPlayerOrder())
        {
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.setColor(p.getColor());
            g2d.fillOval(x, getHeight() - 140, 100, 100);
            g2d.setColor(Color.BLACK);
            g2d.drawString(p.getName(), x + 15, getHeight() - 85);
            g2d.setFont(new Font("Arial", Font.BOLD, 25));
            g2d.drawString("$" + p.getElektro(), x + 27, getHeight() - 15);
            if (!GameState.auctionManager.getActiveBidders().contains(p) || p.hasPassedBid())
            {
                g2d.drawImage(ImageLibrary.x, x, getHeight() - 240, 100, 100, null);
            }
            if (GameState.auctionManager.getCurrentBidder() != null && GameState.auctionManager.getCurrentBidder().equals(p) && auctionStarted && auctionWinner == null)
            {
                g2d.setColor(new Color(237, 236, 229));
                g2d.fillRect(x - 15, getHeight() - 286, 130, 135);
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(4));
                g2d.drawRect(x - 15, getHeight() - 286, 130, 135);
                g2d.drawLine(x - 15, getHeight() - 210, x + 115, getHeight() - 210);
                g2d.drawLine(x + 50, getHeight() - 210, x + 50, getHeight() - 151);
                g2d.setFont(new Font("Arial", Font.BOLD, 22));
                g2d.drawString("Bidding:", x + 10, getHeight() - 255);
                g2d.drawString("$" + GameState.auctionManager.getPendingBid(), x + 30, getHeight() - 225);
                g2d.drawImage(ImageLibrary.upArrow, x - 7, getHeight() - 205, 50, 50, null);
                g2d.drawImage(ImageLibrary.downArrow, x + 57, getHeight() - 205, 50, 50, null);
                upArrowX = x - 7;
                downArrowX = x + 57; 
            }
            x += 150;
        }
    }

    public void drawAuctionInfo(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(new Color(237, 236, 229));
        g2d.fillRect(0, getHeight() - 110, 400, 110); //bid info
        g2d.fillRect(getWidth() / 2 + 500, getHeight() - 110, 300, 110); //controls
        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, getHeight() - 110, 400, 110);
        g2d.drawRect(getWidth() / 2 + 500, getHeight() - 110, 300, 110);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        String s = "Highest Bidder: ";
        if (GameState.auctionManager.getHighestBidder() == null)
            s += "None";
        else
            s += GameState.auctionManager.getHighestBidder().getName();
        g2d.drawString(s, 20, getHeight() - 70);
        s = "Highest Bid: $";
        if (GameState.auctionManager.getHighestBidder() == null)
            s += "0";
        else
            s += GameState.auctionManager.getCurrentBid();
        g2d.drawString(s, 20, getHeight() - 30);
        g2d.drawString("'c': Confirm Bid", getWidth() / 2 + 520, getHeight() - 70);
        g2d.drawString("'x': Pass Bid", getWidth() / 2 + 520, getHeight() - 30);
    }


    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        
        for(PowerPlant p: currentMarketPositions.keySet())
        {
            Pair pos = currentMarketPositions.get(p);
            if (x > pos.getX() && x < pos.getX() + 250 && y > pos.getY() && y < pos.getY() + 250 && p.getPlantNumber() <= GameState.activePlayer.getElektro() && !infoPreview.isPreviewing)
            {
                GameState.auctionManager.startAuction(p);
                auctionStarted = true;
            }
        }
        //g2d.drawImage(ImageLibrary.upArrow, x - 7, getHeight() - 205, 50, 50, null);
        //g2d.drawImage(ImageLibrary.downArrow, x + 57, getHeight() - 205, 50, 50, null);
        if (auctionStarted && x > upArrowX && x < upArrowX + 50 && y > getHeight() - 205 && y < getHeight() - 165 && !infoPreview.isPreviewing)
        {
            GameState.auctionManager.incrementPendingBid(true);
        }
        if (auctionStarted && x > downArrowX && x < downArrowX + 50 && y > getHeight() - 205 && y < getHeight() - 165 && !infoPreview.isPreviewing)
        {
            GameState.auctionManager.incrementPendingBid(false);
        }
        if (discardingPlant && !infoPreview.isPreviewing)
        {
            if ((x >= 935 - 420 && x <= 935 - 420 + 290) && (y >= 213 && y <= 213 + 275)) {
            //togglePowerPlantSection(1);
            if (plantToDiscard == null)
            {
                plantToDiscard = auctionWinner.getOwnedPlants().get(0);
                for(ResourceType r: plantToDiscard.getResourcesStored())
                {
                    discardedResources.add(r);
                }
                plantToDiscard.getResourcesStored().clear();
            }
            else if (selectedResource != null)
            {
                boolean added = auctionWinner.getOwnedPlants().get(0).addResource(selectedResource);
                if (added)
                {
                    discardedResources.remove(selectedResource);
                    selectedResource = null;
                    selectedResourceIndex = -1;
                }
            }
        }
        // powerplant section 2
        else if ((x >= 1250 - 420 && x <= 1250 - 420 + 290) && (y >= 213 && y <= 213 + 275)) {
            if (plantToDiscard == null)
            {
                plantToDiscard = auctionWinner.getOwnedPlants().get(1);
                for(ResourceType r: plantToDiscard.getResourcesStored())
                {
                    discardedResources.add(r);
                }
                plantToDiscard.getResourcesStored().clear();
            }
            else if (selectedResource != null)
            {
                boolean added = auctionWinner.getOwnedPlants().get(1).addResource(selectedResource);
                if (added)
                {
                    discardedResources.remove(selectedResource);
                    selectedResource = null;
                    selectedResourceIndex = -1;
                }
            }
        }
        // powerplant section 3
        else if ((x >= 1565 - 420 && x <= 1565 - 420 + 290) && (y >= 213 && y <= 213 + 275)) {
            if (plantToDiscard == null)
            {
                plantToDiscard = auctionWinner.getOwnedPlants().get(2);
                for(ResourceType r: plantToDiscard.getResourcesStored())
                {
                    discardedResources.add(r);
                }
                plantToDiscard.getResourcesStored().clear();
            }
            else if (selectedResource != null)
            {
                boolean added = auctionWinner.getOwnedPlants().get(2).addResource(selectedResource);
                if (added)
                {
                    discardedResources.remove(selectedResource);
                    selectedResource = null;
                    selectedResourceIndex = -1;
                }
            }
        }
        //getWidth() / 2 + 500, getHeight() - 300
        else if (x > getWidth() / 2 + 500 && x < getWidth() / 2 + 800 && y > getHeight() - 300 && y < getHeight())
        {
            if (plantToDiscard == null)
                plantToDiscard = GameState.auctionManager.getCurrentPlant();
            else if (selectedResource != null)
            {
                boolean added = GameState.auctionManager.getCurrentPlant().addResource(selectedResource);
                if (added)
                {
                    discardedResources.remove(selectedResource);
                    selectedResource = null;
                    selectedResourceIndex = -1;
                }
            }
        }
        // 6 boxes determining reosurces stored in the game
        else if (y >= 510 && y <= 635) {
            if (x >= 810 && x <= 810 + 125) toggleResource(1);
            else if (x >= 810 + 135 && x <= 810 + 135 + 125) toggleResource(2);
            else if (x >= 810 + 270 && x <= 810 + 270 + 125) toggleResource(3);
            
            // discard button 
            else if (x >= 1220 && x <= 1520) {
                auctionWinner.getOwnedPlants().remove(plantToDiscard);
                GameState.marketManager.getDeck().getDiscardedPlants().add(GameState.auctionManager.getCurrentPlant());
                endAuction();
            }
        } 
        else if (y >= 645 && y <= 770) {
            if (x >= 810 && x <= 810 + 125) toggleResource(4);
            else if (x >= 810 + 135 && x <= 810 + 135 + 125) toggleResource(5);
            else if (x >= 810 + 270 && x <= 810 + 270 + 125) toggleResource(6);
        }
        }
        repaint();
    }

    private void toggleResource(int a)
    {
        int index = a - 1;
        if (index < discardedResources.size())
        {
            selectedResource = discardedResources.get(index);
            selectedResourceIndex = index;
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {}


    @Override
    public void mouseReleased(MouseEvent e) {}


    @Override
    public void mouseEntered(MouseEvent e) {}


    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (c == 'x' && !GameState.firstRound && !auctionStarted && !infoPreview.isPreviewing)
        {
            GameState.auctionManager.getActiveBidders().remove(0);
            if (!GameState.auctionManager.getActiveBidders().isEmpty())
                GameState.activePlayer = GameState.auctionManager.getActiveBidders().get(0);
            else
            {
                GameState.roundManager.advancePhase();
                GameState.activePlayer = GameState.roundManager.getPlayerOrder().get(GameState.players.size() - 1);
                if (!boughtPowerPlant)
                {
                    GameState.marketManager.discardLowestPlant();
                }
                if (GameState.marketManager.containsStep3())
                {
                    GameState.triggerPhase3();
                }
                setVisible(false);
                parent.add(new marketPanel(parent));
                parent.repaint();
                parent.remove(this);
            }
        }
        if (c == 'x' && auctionStarted && auctionWinner == null && GameState.auctionManager.getHighestBidder() != null && !infoPreview.isPreviewing)
        {
            GameState.auctionManager.getCurrentBidder().setPassedBid(true);
            GameState.auctionManager.setNextBidder();
            resolveAuction();
        }
        if (c == 'c' && auctionStarted && auctionWinner == null && !infoPreview.isPreviewing)
        {
            GameState.auctionManager.confirmBid();
            GameState.auctionManager.setNextBidder();
            resolveAuction();
        }
        repaint();
    }

    private void resolveAuction()
    {
        int c = 0;
        Player lastPlayer = null;
        for(Player p: GameState.auctionManager.getActiveBidders())
        {
            if (!p.hasPassedBid())
            {
                c++;
                lastPlayer = p;
            }
        }
        if (c != 1)
            return;
        lastPlayer.addPowerPlant(GameState.auctionManager.getCurrentPlant());
        lastPlayer.spendElektro(GameState.auctionManager.getCurrentBid());
        auctionWinner = lastPlayer;
        // GameState.marketManager.removePlant(GameState.auctionManager.getCurrentPlant());
        // GameState.marketManager.refillMarket();
        if (lastPlayer.getOwnedPlants().size() == 4)
        {
            discardingPlant = true;
        }
        boughtPowerPlant = true;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_ENTER:
                if (auctionWinner != null && !discardingPlant && !infoPreview.isPreviewing)
                {
                    endAuction();
                } break;
        }
    }

    public void endAuction()
    {
        discardingPlant = false;
        plantToDiscard = null;
        discardedResources.clear();
        selectedResource = null;
        selectedResourceIndex = -1;
        GameState.marketManager.removePlant(GameState.auctionManager.getCurrentPlant());
        GameState.marketManager.refillMarket();
        GameState.auctionManager.getActiveBidders().remove(auctionWinner);
        auctionStarted = false;
        auctionWinner = null;
        GameState.auctionManager.resetAuction();
        for(Player p: GameState.players)
        {
            p.setPassedBid(false);
        }
        if (GameState.auctionManager.getActiveBidders().isEmpty())
        {
            if (GameState.firstRound)
            {
                GameState.firstRound = false;
                GameState.roundManager.determinePlayerOrder();
            }
            if (!boughtPowerPlant)
            {
                GameState.marketManager.discardLowestPlant();
            }
            if (GameState.marketManager.containsStep3())
            {
                GameState.triggerPhase3();
            }
            GameState.roundManager.advancePhase();
            GameState.activePlayer = GameState.roundManager.getPlayerOrder().get(GameState.players.size() - 1);
            setVisible(false);
            parent.add(new marketPanel(parent));
            parent.repaint();
            parent.remove(this);
        }
        else
        {
            GameState.activePlayer = GameState.auctionManager.getActiveBidders().get(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
       
    }

    public void addNotify()
    {
        super.addNotify();
        requestFocus();
    }
}





