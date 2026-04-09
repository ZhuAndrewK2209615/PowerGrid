package powergrid.ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import powergrid.PowerGridFrame;
import powergrid.core.*;
import powergrid.utils.*;

public class EndPanel extends JPanel implements MouseListener{
    
    private PowerGridFrame parent;
    private MapUI mapUI;
    private MarketUI marketUI;
    private PlayerMenu playerMenu;
    private int housesPowered;

    public EndPanel(PowerGridFrame parent)
    {
        this.parent = parent;
        mapUI = new MapUI(this);
        marketUI = new MarketUI(this);
        playerMenu = new PlayerMenu();
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
        drawPowerButtons(g);
    }

    public void drawPowerButtons(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        int x = 980;
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
            g2d.fillRoundRect(x, 540, 200, 50, 10, 10);
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(x, 540, 200, 50, 10, 10);
            g2d.setFont(new Font("Arial", Font.PLAIN, 20));
            g2d.drawString(status, x + 30 + ((status.length() - 12) * -5), 570);
            x += 315;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
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
