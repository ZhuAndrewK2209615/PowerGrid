package powergrid.ui;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import javax.swing.*;
import powergrid.PowerGridFrame;
import powergrid.core.*;
import powergrid.utils.*;

public class EndPanel extends JPanel implements MouseListener{
    
    private PowerGridFrame parent;
    private MapUI mapUI;
    private MarketUI marketUI;
    private PlayerMenu playerMenu;
    private HashMap<Player, Integer> housesPowered;
    private Player winner;

    public EndPanel(PowerGridFrame parent)
    {
        this.parent = parent;
        mapUI = new MapUI(this);
        marketUI = new MarketUI(this);
        playerMenu = new PlayerMenu();
        housesPowered = new HashMap<>();
        winner = null;
        for(Player p: GameState.players)
        {
            housesPowered.put(p, 0);
        }
        addMouseListener(this);
        addMouseListener(playerMenu);
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        g.drawImage(ImageLibrary.background, 0, 0, getWidth(), getHeight(), null);
        mapUI.drawMap(g);
        marketUI.drawMarket(g);
        playerMenu.drawMenu(g);
        if (winner == null)
            drawPowerButtons(g);
        drawFinishButtons(g);
        drawInfoButtons(g);
        if (GameState.gameEnded)
        {
            if (winner == null)
            {
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.drawString(GameState.gameEndRequirement + " houses have been built by a player; the game has ended", 1100, 65);
                g.drawString("Each player powers as many houses as possible", 1100, 105);
            }
            else
            {
                g.setFont(new Font("Arial", Font.BOLD, 35));
                g.drawString(winner.getName() + " has won!", 1250, 80);
                g.setColor(winner.getColor());
                g.fillOval(960, 40, 75, 75);
            }
        }
    }

    public void drawPowerButtons(Graphics g)
    {
        if (playerMenu.viewedPlayer != GameState.activePlayer)
        {
            return;
        }
        Graphics2D g2d = (Graphics2D)g;
        int x = 935;
        g2d.setStroke(new BasicStroke(5));
        for(PowerPlant p: GameState.activePlayer.getOwnedPlants())
        {
            String status = "";
            if (p.canPower())
            {
                g2d.setColor(new Color(0, 191, 99));
                status = "Power";
            }
            else
            {
                g2d.setColor(new Color(185, 181, 171));
                status = "Cannot Power";
            }
            if (p.canPower() && p.getResourceType() == ResourceType.HYBRID && p.getQueuedResources().size() != p.getMaxCapacity() / 2)
            {
                g2d.setColor(new Color(185, 181, 171));
            }
            if (p.isPowered())
            {
                g2d.setColor(new Color(255, 222, 89));
                status = "Powered";
            }
            g2d.fillRoundRect(x, 540, 290, 50, 10, 10);
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(x, 540, 290, 50, 10, 10);
            g2d.setFont(new Font("Arial", Font.PLAIN, 20));
            g2d.drawString(status, x + 80 + ((status.length() - 12) * -5), 570);
            if (p.getResourceType() == ResourceType.HYBRID && !p.isPowered() && p.canPower())
            {
                g2d.setColor(new Color(185, 181, 171));
                g2d.fillRect(x + 10, 600, 270, 150);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x + 10, 600, 270, 150);
                g2d.drawLine(x + 10, 640, x + 280, 640);
                g2d.drawLine(x + 10, 695, x + 280, 695);
                g2d.drawLine(x + 180, 640, x + 180, 750);
                g2d.drawLine(x + 230, 640, x + 230, 750);
                g2d.setFont(new Font("Arial", Font.BOLD, 25));
                g2d.drawString("Using", x + 110, 630);
                g2d.drawString("Coal: " + p.getCoalQueued(), x + 50, 680);
                g2d.drawString("Oil: " + p.getOilQueued(), x + 50, 730);
                g2d.drawString("+", x + 200, 680);
                g2d.drawString("+", x + 200, 730);
                g2d.drawString("-", x + 250, 680);
                g2d.drawString("-", x + 250, 730);
            }
            x += 315;
        }
    }

    public void drawFinishButtons(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(5));
        //houses powered counter
        if (!GameState.gameEnded)
        {
            g2d.setColor(new Color(229, 239, 244));
            g2d.setStroke(new BasicStroke(5));
            g2d.fillRoundRect(925, getHeight() - 100, 350, 80, 10, 10);
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(925, getHeight() - 100, 350, 80, 10, 10);
            g2d.setFont(new Font("Arial", Font.BOLD, 35));
            g2d.drawString("Houses powered: " + housesPowered.get(GameState.activePlayer), 945, getHeight() - 50);
        }
        else
        {
            g2d.setColor(new Color(229, 239, 244));
            g2d.fillRoundRect(925, getHeight() - 225, 350, 215, 10, 10);
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(925, getHeight() - 225, 350, 215, 10, 10);
            g2d.drawLine(925, getHeight() - 185, 1275, getHeight() - 185);
            g2d.setFont(new Font("Arial", Font.BOLD, 25));
            g2d.drawString("Total Houses Powered: ", 955, getHeight() - 195);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            int y = getHeight() - 160;
            for(Player p: housesPowered.keySet())
            {
                String value = "" + housesPowered.get(p);
                if (!p.finishedPowering())
                {
                    value = "?";
                }
                g2d.drawString(p.getName() + ": " + value + " houses", 945, y);
                y += 25;
            }
        }
        //finish button
        if (winner == null)
        {
            g2d.setFont(new Font("Arial", Font.BOLD, 35));
            g2d.setColor(new Color(0, 191, 99));
            g2d.fillRoundRect(1350, getHeight() - 100, 400, 80, 10, 10);
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(1350, getHeight() - 100, 400, 80, 10, 10);
            String s = "Finish and claim $" + GameState.activePlayer.calculateIncome(housesPowered.get(GameState.activePlayer));
            if (GameState.gameEnded)
            {
                s = "Proceed to next player";
            }
            g2d.drawString(s, 1370, getHeight() - 50);
        }
        
    }

    public void drawInfoButtons(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(new Color(255, 222, 89));
        g2d.fillOval(1790, 950, 80, 80);
        g2d.fillOval(1790, 855, 80, 80);
        g2d.fillOval(1790, 760, 80, 80);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(1790, 950, 80, 80);
        g2d.drawOval(1790, 855, 80, 80);
        g2d.drawOval(1790, 760, 80, 80);
        g2d.drawImage(ImageLibrary.questionMark, 1800, 960, 60, 60, null);
        g2d.drawImage(ImageLibrary.electricity, 1800, 865, 60, 60, null);
        g2d.drawImage(ImageLibrary.garbage, 1800, 770, 60, 60, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        //detect clicks on power buttons and coal/oil ratios
        int tempX = 935;
        for(int i=0; i<Math.min(3, GameState.activePlayer.getOwnedPlants().size()); i++)
        {
            PowerPlant currentPlant = GameState.activePlayer.getOwnedPlants().get(i);
            if (x > tempX && x < tempX + 290 && y > 540 && y < 590 && winner == null)
            {
                if (currentPlant.canPower() && !(currentPlant.getResourceType() == ResourceType.HYBRID && currentPlant.getQueuedResources().size() != currentPlant.getMaxCapacity() / 2))
                {
                    currentPlant.power();
                    int newAmount = Math.min(housesPowered.get(GameState.activePlayer) + currentPlant.getCitiesPowered(), GameState.activePlayer.getCitiesBuilt().size());
                    housesPowered.put(GameState.activePlayer, newAmount);

                }
            }
            if (currentPlant.getResourceType() == ResourceType.HYBRID)
            {
                int coalStored = 0;
                int oilStored = 0;
                for(ResourceType r: currentPlant.getResourcesStored())
                {
                    if (r == ResourceType.COAL)
                        coalStored++;
                    else
                        oilStored++;
                }
                if (x > tempX + 180 && x < tempX + 230 && y > 640 && y < 695 && winner == null)
                {
                    if (currentPlant.getCoalQueued() < coalStored)
                    {
                        currentPlant.addQueuedResource(ResourceType.COAL);
                    }
                }
                if (x > tempX + 180 && x < tempX + 230 && y > 695 && y < 750 && winner == null)
                {
                    if (currentPlant.getOilQueued() < oilStored)
                    {
                        currentPlant.addQueuedResource(ResourceType.OIL);
                    }
                }
                if (x > tempX + 230 && x < tempX + 280 && y > 640 && y < 695 && winner == null)
                {
                    if (currentPlant.getCoalQueued() > 0)
                    {
                        currentPlant.removeQueuedResource(ResourceType.COAL);
                    }
                }
                if (x > tempX + 230 && x < tempX + 280 && y > 695 && y < 750 && winner == null)
                {
                    if (currentPlant.getOilQueued() > 0)
                    {
                        currentPlant.removeQueuedResource(ResourceType.OIL);
                    }
                }
            }
            tempX += 315;
        }
        
        if (x > 1350 && x < 1750 && y > getHeight() - 100 && y < getHeight() - 20 && winner == null)
        {
            for(PowerPlant p: GameState.activePlayer.getOwnedPlants())
            {
                p.clearQueue();
                p.dePower();
            }
            if (!GameState.gameEnded)
                GameState.activePlayer.gainElektro(GameState.activePlayer.calculateIncome(housesPowered.get(GameState.activePlayer)));
            if (GameState.gameEnded)
            {
                GameState.activePlayer.setFinished();
            }
            Player next = GameState.roundManager.getNextPlayer();
            if (next != null)
            {
                GameState.activePlayer = next;
                playerMenu.viewedPlayer = GameState.activePlayer;
            }
            else
            {
                if (!GameState.gameEnded)
                {
                    GameState.activePlayer = GameState.roundManager.getPlayerOrder().get(0);
                    GameState.roundManager.advancePhase();
                    setVisible(false);
                    // parent.add(new AuctionPanel());
                    parent.repaint();
                    parent.remove(this);
                }
                else
                {
                    int highestAmount = -1;
                    ArrayList<Player> potentialWinners = new ArrayList<>();
                    for(Player p: housesPowered.keySet())
                    {
                        if (housesPowered.get(p) > highestAmount)
                        {
                            potentialWinners.clear();
                            potentialWinners.add(p);
                            highestAmount = housesPowered.get(p);
                        }
                        else if (housesPowered.get(p) == highestAmount)
                        {
                            potentialWinners.add(p);
                        }
                    }
                    int highestElektro = -1;
                    Player highestPlayer = null;
                    for(Player p: potentialWinners)
                    {
                        if (p.getElektro() > highestElektro)
                        {
                            highestElektro = p.getElektro();
                            highestPlayer = p;
                        }
                    }
                    winner = highestPlayer;
                }
            }
        }
        repaint();
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
