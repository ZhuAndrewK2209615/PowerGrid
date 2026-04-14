package powergrid.ui;
import java.awt.*;
import javax.swing.*;
import powergrid.core.*;
import powergrid.utils.PowerPlant;

public class InfoPreviews {
    
    private JPanel origin;
    private MapUI mapUI;
    private MarketUI marketUI;
    private PlayerMenu playerMenu;

    public InfoPreviews(JPanel origin)
    {
        this.origin = origin;
        mapUI = new MapUI(origin);
        marketUI = new MarketUI(origin);
        playerMenu = new PlayerMenu();
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
    }

    public void drawPowerPlants(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
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
            g2d.drawImage(p.getImage(), x, 370, 250, 250, null);
            x += 300;
        }
    }
}