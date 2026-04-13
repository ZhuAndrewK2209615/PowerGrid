package powergrid.ui;
import java.awt.*;
import javax.swing.*;

public class MapPreview {
    
    private JPanel origin;
    private MapUI mapUI;
    private MarketUI marketUI;
    private PlayerMenu playerMenu;

    public MapPreview(JPanel origin)
    {
        this.origin = origin;
        mapUI = new MapUI(origin);
        marketUI = new MarketUI(origin);
        playerMenu = new PlayerMenu();
    }

    public void drawPreview(Graphics g)
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
}