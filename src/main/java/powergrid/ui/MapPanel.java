package powergrid.ui;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import powergrid.utils.*;
import powergrid.core.*;
import java.util.List;

public class MapPanel extends JPanel implements MouseListener{
    private BufferedImage mapImage;
    private City selectedCity;
    private Path shortestPath;
    private final int HOUSE_SIZE = 12;

    public MapPanel()
    {
        try
        {
            mapImage = ImageIO.read(MapPanel.class.getResource("/powergrid/Images/Germany Map.jpeg"));
        }
        catch (Exception e)
        {
            System.out.println("Failed to load an image");
        }
        addMouseListener(this);
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        drawMap(g2d);
        if (selectedCity != null)
        {
            drawPath(g2d);
        }
    }

    public void drawMap(Graphics2D g2d)
    {
        g2d.drawImage(mapImage, 0, 0, getWidth() / 2 - 148, getHeight(), null);

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
    }

    public void drawHouse(Graphics2D g2d, int x, int y)
    {
        g2d.fillRect(x, y, HOUSE_SIZE, HOUSE_SIZE);
        int[] xPoints = {x, x + HOUSE_SIZE / 2, x + HOUSE_SIZE};
        int[] yPoints = {y, y - HOUSE_SIZE / 2, y};
        g2d.fillPolygon(xPoints, yPoints, 3);
    }

    public void drawPath(Graphics2D g2d)
    {
        g2d.setColor(Color.GREEN);
        g2d.setStroke(new BasicStroke(4));
        List<City> path = shortestPath.getPath();
        //draw a line between each consecutive city in the shortes path
        for(int i=1; i<path.size(); i++)
        {
            g2d.drawLine(path.get(i-1).getX(), path.get(i-1).getY(), path.get(i).getX(), path.get(i).getY());
        }
        //draw a circle to symbolize the end of the path
        g2d.fillOval(path.get(path.size() - 1).getX() - 10, path.get(path.size() - 1).getY() - 10, 20, 20);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        selectedCity = GameState.mapGraph.getCity(x, y);
        shortestPath = GameState.mapGraph.getShortestPath(GameState.activePlayer, selectedCity);
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

    public void addNotify()
    {
        super.addNotify();
        requestFocus();
    }
    
}
