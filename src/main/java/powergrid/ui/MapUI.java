package powergrid.ui;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import powergrid.core.*;
import powergrid.utils.*;
import javax.swing.*;

public class MapUI {

    private BufferedImage mapImage;
    private final int HOUSE_SIZE = 12;
    JPanel origin;

    public MapUI(JPanel origin)
    {
        try
        {
            mapImage = ImageIO.read(getClass().getResource("/powergrid/Images/Germany Map.jpeg"));
            this.origin = origin;
        }
        catch (Exception e)
        {
            System.out.println("Failed to load map images");
        }
    }
    
    public void drawMap(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(mapImage, 0, 0, origin.getWidth() / 2 - 148, origin.getHeight(), null);

        //draw an X on each unused city
        g2d.setStroke(new BasicStroke(8));
        g2d.setColor(Color.BLACK);
        for(City c: GameState.mapGraph.getAllCities())
        {
            if (!GameState.mapGraph.cityInMap(c))
            {
                g2d.drawLine(c.getX() - 12, c.getY() - 12, c.getX() + 12, c.getY() + 12);
                g2d.drawLine(c.getX() + 12, c.getY() - 12, c.getX() - 12, c.getY() + 12);
            }
        }

        //draw player houses
        g2d.setStroke(new BasicStroke(2));
        for(Player p: GameState.players)
        {
            g2d.setColor(p.getColor());
            for(City c: p.getCitiesBuilt().keySet())
            {
                //offset based on whether the player built this house on step 1, 2, or 3
                int xOffset = 0;
                int yOffset = 0;
                switch (p.getCitiesBuilt().get(c))
                {
                    case 1: xOffset = -5; yOffset = -18; break;
                    case 2: xOffset = -23; yOffset = 0; break;
                    case 3: xOffset = 10; yOffset = 0; break;
                }
                drawHouse(g2d, c.getX() + xOffset, c.getY() + yOffset);
            }
        }
        //draw houses in turn order
        int x = 63;
        int y = 40;
        for(Player p: GameState.roundManager.getPlayerOrder())
        {
            g2d.setColor(p.getColor());
            drawHouse(g2d, x, y);
            x += 30;
        }
        //draw houses in city counter
        x = 432;
        y = 35;
        for(int i=1; i<=6; i++)
        {
            int tempX = x;
            for(Player p: GameState.players)
            {
                if (p.getCitiesBuilt().size() == i)
                {
                    g2d.setColor(p.getColor());
                    drawHouse(g2d, tempX, y);
                    tempX += HOUSE_SIZE - 5;
                }
            }
            x += 55;
        }
        x += 3;
        y = 43;
        for(int i=7; i<=21; i++)
        {
            int tempY = y;
            for(Player p: GameState.players)
            {
                if (p.getCitiesBuilt().size() == i)
                {
                    g2d.setColor(p.getColor());
                    drawHouse(g2d, x, tempY);
                    tempY -= 3;
                }
            }
            if (i == 7)
            {
                x = 407;
                y = 79;
            }
            else
            {
                x += 27;
            }
            if (i == 13)
            {
                x += 5;
            }
        }
    }

    private void drawHouse(Graphics2D g2d, int x, int y)
    {
        g2d.fillRect(x, y, HOUSE_SIZE, HOUSE_SIZE);
        int[] xPoints = {x, x + HOUSE_SIZE / 2, x + HOUSE_SIZE};
        int[] yPoints = {y, y - HOUSE_SIZE / 2, y};
        g2d.fillPolygon(xPoints, yPoints, 3);
    }
}
