package powergrid.ui;
import powergrid.core.GameState;
import powergrid.utils.ResourceType;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class MarketUI {

    private JPanel origin;
    private BufferedImage marketImage;

    public MarketUI(JPanel origin)
    {
        this.origin = origin;
        try
        {
            marketImage = ImageIO.read(MapPanel.class.getResource("/powergrid/Images/market.png"));
        }
        catch (Exception e)
        {
            System.out.println("Failed to load market image");
        }
    }
    
    public void drawMarket(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;

        g2d.drawImage(marketImage, origin.getWidth() / 2 - 148, 0, 100, origin.getHeight(), null);

        //draw coal
        g2d.setColor(new Color(41, 24, 0));
        int x = 870;
        int y = 900;
        for(int i=1; i<=GameState.resourceMarket.getSupply(ResourceType.COAL); i++)
        {
            g2d.fillRect(x, y, 20, 20);
            y -= 32;
            if (i % 3 == 0)
            {
                y -= 20;
                x--;
            }
        }

        //draw oil
        g2d.setColor(Color.BLACK);
        x = 846;
        y = 882;
        for(int i=1; i<=GameState.resourceMarket.getSupply(ResourceType.OIL); i++)
        {
            g2d.fillOval(x, y, 15, 15);
            y -= 25;
            if (i % 3 == 0)
            {
                y -= 40;
                x--;
            }
        }

        //draw garbage
        g2d.setColor(Color.YELLOW);
        x = 823;
        y = 902;
        for(int i=1; i<=GameState.resourceMarket.getSupply(ResourceType.GARBAGE); i++)
        {
            g2d.fillRect(x, y, 15, 20);
            y -= 32;
            if (i % 3 == 0)
            {
                y -= 20;
                x -= 2;
            }
        }

        //draw uranium
        g2d.setColor(Color.RED);
        x = 846;
        y = 907;
        for(int i=1; i<=GameState.resourceMarket.getSupply(ResourceType.URANIUM); i++)
        {
            if (i == 1)
            {
                g2d.fillRect(824, 994, 15, 15);
            }
            else if (i == 2)
            {
                g2d.fillRect(824, 951, 15, 15);
            }
            else if (i == 3)
            {
                g2d.fillRect(870, 994, 15, 15);
            }
            else if (i == 4)
            {
                g2d.fillRect(870, 951, 15, 15);
            }
            else
            {
                g2d.fillRect(x, y, 10, 10);
                y -= 115;
                x--;
            }
        }
    }
}
