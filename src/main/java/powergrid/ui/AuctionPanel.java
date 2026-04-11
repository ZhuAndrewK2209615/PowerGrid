package powergrid.ui;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import powergrid.utils.*;
import powergrid.core.*;
import powergrid.managers.*;
import java.util.*;
import powergrid.PowerGridFrame;

public class AuctionPanel extends JPanel implements MouseListener, KeyListener{
    private PowerGridFrame parent;
    private HashMap<PowerPlant, Pair> currentMarketPositions;
    private boolean auctionStarted;
    private int upArrowX;
    private int downArrowX;
    private boolean discardingPlant;
    private Player auctionWinner;

    public AuctionPanel(PowerGridFrame parent)
    {
        for(Player p: GameState.roundManager.getPlayerOrder())
        {
            GameState.auctionManager.getActiveBidders().add(p);
        }
        currentMarketPositions = new HashMap<>();
        this.parent = parent;
        auctionStarted = false;
        addMouseListener(this);
        addKeyListener(this);
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        g.drawImage(ImageLibrary.background2, 0, 0, getWidth(), getHeight(), null);
        drawMarket(g);
        if (!auctionStarted || (auctionWinner != null && !discardingPlant))
            drawPrompt(g);
        drawDashboard(g);
        drawInfoButtons(g);
        if (auctionStarted)
            drawAuctionInfo(g);

    }

    public void drawMarket(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        currentMarketPositions.clear();
        g2d.setColor(new Color(0, 191, 99));
        int x = 377;
        for(PowerPlant p: GameState.marketManager.getCurrentMarket())
        {
            if (!auctionStarted)
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

    public void drawInfoButtons(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(5));
        g2d.setColor(new Color(255, 222, 89));
        g2d.fillOval(1790, 950, 80, 80);
        g2d.fillOval(1790, 855, 80, 80);
        g2d.fillOval(1790, 760, 80, 80);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(1790, 950, 80, 80);
        g2d.drawOval(1790, 855, 80, 80);
        g2d.drawOval(1790, 760, 80, 80);
        g2d.drawImage(ImageLibrary.questionMark, 1800, 960, 60, 60, null);
        g2d.drawImage(ImageLibrary.mapSymbol, 1800, 865, 60, 60, null);
        g2d.drawImage(ImageLibrary.garbage, 1800, 770, 60, 60, null);
    }


    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        
        for(PowerPlant p: currentMarketPositions.keySet())
        {
            Pair pos = currentMarketPositions.get(p);
            if (x > pos.getX() && x < pos.getX() + 250 && y > pos.getY() && y < pos.getY() + 250)
            {
                GameState.auctionManager.startAuction(p);
                auctionStarted = true;
            }
        }
        //g2d.drawImage(ImageLibrary.upArrow, x - 7, getHeight() - 205, 50, 50, null);
        //g2d.drawImage(ImageLibrary.downArrow, x + 57, getHeight() - 205, 50, 50, null);
        if (auctionStarted && x > upArrowX && x < upArrowX + 50 && y > getHeight() - 205 && y < getHeight() - 165)
        {
            GameState.auctionManager.incrementPendingBid(true);
        }
        if (auctionStarted && x > downArrowX && x < downArrowX + 50 && y > getHeight() - 205 && y < getHeight() - 165)
        {
            GameState.auctionManager.incrementPendingBid(false);
        }
        repaint();
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
        if (c == 'x' && !GameState.firstRound && !auctionStarted)
        {
            GameState.auctionManager.getActiveBidders().remove(0);
            if (!GameState.auctionManager.getActiveBidders().isEmpty())
                GameState.activePlayer = GameState.auctionManager.getActiveBidders().get(0);
            else
            {
                GameState.roundManager.advancePhase();
                GameState.activePlayer = GameState.roundManager.getPlayerOrder().get(GameState.players.size() - 1);
                setVisible(false);
                parent.add(new marketPanel(parent));
                parent.repaint();
                parent.remove(this);
            }
        }
        if (c == 'x' && auctionStarted && auctionWinner == null && GameState.auctionManager.getHighestBidder() != null)
        {
            GameState.auctionManager.getCurrentBidder().setPassedBid(true);
            GameState.auctionManager.setNextBidder();
            resolveAuction();
        }
        if (c == 'c' && auctionStarted && auctionWinner == null)
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
            discardingPlant = true; //then load discard screen
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_ENTER:
                if (auctionWinner != null && !discardingPlant)
                {
                    GameState.marketManager.removePlant(GameState.auctionManager.getCurrentPlant());
                    System.out.println(GameState.marketManager.getCurrentMarket());
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
                } break;
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





