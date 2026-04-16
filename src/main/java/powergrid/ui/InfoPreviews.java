package powergrid.ui;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;
import powergrid.core.*;
import powergrid.utils.PowerPlant;
import powergrid.utils.ResourceType;

public class InfoPreviews implements KeyListener, MouseListener{
    
    private JPanel origin;
    private MapUI mapUI;
    private MarketUI marketUI;
    private PlayerMenu playerMenu;
    public boolean isPreviewingMap;
    public boolean isPreviewingDiscard;
    public boolean isPreviewingInfo;
    public boolean isPreviewingAuction;
    public boolean isPreviewing;

    public InfoPreviews(JPanel origin)
    {
        this.origin = origin;
        mapUI = new MapUI(origin);
        marketUI = new MarketUI(origin);
        playerMenu = new PlayerMenu();
        isPreviewingMap = false;
        isPreviewingDiscard = false;
        isPreviewingInfo = false;
        isPreviewingAuction = false;
        isPreviewing = false;
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
        if (GameState.roundManager.getPhase() == 2)
        {
            g2d.drawImage(ImageLibrary.mapSymbol, 1800, 865, 60, 60, null);
        }
        else
        {
            g2d.drawImage(ImageLibrary.electricity, 1800, 865, 60, 60, null);
        }
        g2d.drawImage(ImageLibrary.garbage, 1800, 770, 60, 60, null);
    }

    public void drawMarketInfoButtons(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(5));
        g2d.setColor(new Color(255, 222, 89));
        g2d.fillOval(1790, 950, 80, 80);
        g2d.fillOval(1685, 950, 80, 80);
        g2d.fillOval(1580, 950, 80, 80);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(1790, 950, 80, 80);
        g2d.drawOval(1685, 950, 80, 80);
        g2d.drawOval(1580, 950, 80, 80);
        g2d.drawImage(ImageLibrary.questionMark, 1800, 960, 60, 60, null);
        g2d.drawImage(ImageLibrary.electricity, 1695, 960, 60, 60, null);
        g2d.drawImage(ImageLibrary.garbage, 1590, 960, 60, 60, null);
    }

    public void drawDiscardPreview(Graphics g2d)
    {
        Graphics2D g = (Graphics2D)g2d;
        if (ImageLibrary.background != null) {
            g.drawImage(ImageLibrary.background, 0, 0, origin.getWidth(), origin.getHeight(), null);
        }

        Color myYellow = new Color(255, 250, 191);
        g.setColor(myYellow);

        g.fillRect(200, 130, 1500, 790); // Fills the rectangle
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(5));
        g.drawRect(200, 130, 1500, 790); // Draws the border

        g.setColor(myYellow);

        g.setFont(new Font("Serif", Font.BOLD, 58));
        g.drawString("Discarded Power Plants",680,100);

        g.setFont(new Font("Serif", Font.BOLD, 38));
        g.drawString("Press  'r'  to  return",800, 970);
        drawDiscardedPlants(g);
    }

    private void drawDiscardedPlants(Graphics2D g) {
        if (GameState.marketManager.getDeck() == null) return;

        ArrayList<PowerPlant> plants = new ArrayList<>(GameState.marketManager.getDeck().getDiscardedPlants());

        int rows = 5;
        int cols = 10;

        int startX = 250;
        int startY = 180;

        int size = 120;
        int gap = 22;

        for (int i = 0; i < plants.size(); i++) {
            int row = i / cols;
            int col = i % cols;

            if (row >= rows) break;

            int x = startX + col * (size + gap);
            int y = startY + row * (size + gap);

            PowerPlant p = plants.get(i);

            BufferedImage img = p.getImage();

            if (img != null) {
                g.drawImage(img, x, y, size, size, null);
            } else {
                g.setColor(Color.GRAY);
                g.fillRect(x, y, size, size);

                g.setColor(Color.BLACK);
                g.drawRect(x, y, size, size);

                g.drawString("" + p.getPlantNumber(), x + 40, y + 65);
            }
        }
    }

    public void drawMapPreview(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        mapUI.drawMap(g);
        marketUI.drawMarket(g);
        playerMenu.drawMenu(g);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        g2d.setColor(new Color(237, 236, 229));
        g2d.setStroke(new BasicStroke(5));
        g2d.fillRect(1000, 600, 750,200);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(1000, 600, 750, 200);
        g2d.drawString("Press 'r' to return", 1200, 700);
    }

    public void drawInfoPreview(Graphics2D g)
    {

        if (ImageLibrary.background != null) {
            g.drawImage(ImageLibrary.background, 0, 0, origin.getWidth(), origin.getHeight(), null);
        }
        if (ImageLibrary.turnOrderCard != null){
            g.drawImage(ImageLibrary.turnOrderCard, 0, 520, 558, 525, null);
        }
        if (ImageLibrary.paymentCard != null){
            g.drawImage(ImageLibrary.paymentCard, 600, 600, 470, 470, null);
        }
        if (ImageLibrary.restockCard != null){
            g.drawImage(ImageLibrary.restockCard, 1112, 520, 792, 525, null);
        }

        Color myYellow = new Color(255, 250, 191);
        g.setColor(myYellow);
        g.fillRect(400, 0, 1095, 200); // Fills the rectangle
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(5));
        g.drawRect(400, 0, 1095, 200); // Draws the border
        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif", Font.BOLD, 58));
        g.drawString("Press  'r'  to  return",680, 110);
        g.setColor(myYellow);
        g.fillRect(0, 0, 250, 125);
        g.fillRect(origin.getWidth() - 300, 0, 300, 190);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, 250, 125);
        g.drawRect(origin.getWidth() - 300, 0, 300, 190);
        g.setFont(new Font("Serif", Font.BOLD, 30));
        g.drawString("Round: " + GameState.roundManager.getRoundNum(), 25, 25);
        g.drawString("Current step: " + GameState.step, 25, 70);
        g.drawString(GameState.roundManager.getRoundDescription(), 25, 115);
        g.drawString("Available resources", origin.getWidth() - 280, 25);
        g.drawString("for restock:", origin.getWidth() - 250, 45);
        g.drawLine(origin.getWidth() - 300, 55, origin.getWidth(), 55);
        g.drawString("Coal: " + GameState.resourceMarket.getAvailableTokens(ResourceType.COAL), origin.getWidth() - 280, 90);
        g.drawString("Oil: " + GameState.resourceMarket.getAvailableTokens(ResourceType.OIL), origin.getWidth() - 280, 120);
        g.drawString("Garbage: " + GameState.resourceMarket.getAvailableTokens(ResourceType.GARBAGE), origin.getWidth() - 280, 150);
        g.drawString("Uranium: " + GameState.resourceMarket.getAvailableTokens(ResourceType.URANIUM), origin.getWidth() - 280, 180);
    }

    public void drawPowerPlants(Graphics g)
    {
        Color myYellow = new Color(255, 250, 191);
        Graphics2D g2d = (Graphics2D)g;
        if (GameState.step < 3)
        {
            g2d.setStroke(new BasicStroke(5));
            g2d.setColor(myYellow);
            g2d.fillRect(350, 40, 1200, 290);
            g2d.fillRect(350, 370, 1200, 290);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(350, 40, 1200, 290);
            g2d.drawRect(350, 370, 1200, 290);
            g2d.setColor(new Color(0, 191, 99));
            int x = 377;
            for(PowerPlant p: GameState.marketManager.getCurrentMarket())
            {
                g2d.drawImage(p.getImage(), x, 60, 250, 250, null);
                x += 300;
            }
            g2d.setStroke(new BasicStroke(15));
            g2d.setColor(Color.BLACK);
            x = 0;
            while (x < origin.getWidth())
            {
                g2d.drawLine(x, 350, x + 75, 350);
                x += 125;
            }
            x = 377;
            for(PowerPlant p: GameState.marketManager.getFutureMarket())
            {
                g2d.drawImage(p.getImage(), x, 385, 250, 250, null);
                x += 300;
            }
        }
        else
        {
            g2d.setStroke(new BasicStroke(5));
            g2d.setColor(myYellow);
            g2d.fillRect(490, 40, 900, 600);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(490, 40, 900, 600);
            int x = 510;
            int y = 50;
            int count = 0;
            for(PowerPlant p: GameState.marketManager.getCurrentMarket())
            {
                g2d.drawImage(p.getImage(), x, y, 250, 250, null);
                x += 300;
                count++;
                if (count == 3)
                {
                    x = 510;
                    y = 370;
                }
            }
        }
        
        g2d.setColor(myYellow);
        g2d.fillRect(400, 800, 1095, 200); // Fills the rectangle
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRect(400, 800, 1095, 200); // Draws the border
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Serif", Font.BOLD, 58));
        g2d.drawString("Press  'r'  to  return",680, 910);
    }

    public PlayerMenu getPlayerMenu()
    {
        return playerMenu;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (x > 1790 && x < 1870 && GameState.roundManager.getPhase() != 3)
        {
            if (y > 855 && y < 935)
            {
                if (GameState.roundManager.getPhase() == 2)
                    isPreviewingMap = true;
                else
                    isPreviewingAuction = true;
                isPreviewing = true;
            }
            if (y > 950 && y < 1030)
            {
                isPreviewingInfo = true;
                isPreviewing  = true;
            }
            if (y > 760 && y < 840)
            {
                isPreviewingDiscard = true;
                isPreviewing = true;
            }
        }
        if (y > 950 && y < 1030 && GameState.roundManager.getPhase() == 3)
        {
            if (x > 1790 && x < 1870)
            {
                isPreviewingInfo = true;
                isPreviewing = true;
            }
            if (x > 1685 && x < 1765)
            {
                isPreviewingAuction = true;
                isPreviewing = true;
            }
            if (x > 1580 && x < 1640)
            {
                isPreviewingDiscard = true;
                isPreviewing = true;
            }
        }
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

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (c == 'r')
        {
            isPreviewing = false;
            isPreviewingAuction = false;
            isPreviewingDiscard = false;
            isPreviewingInfo = false;
            isPreviewingMap = false;
            origin.repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}