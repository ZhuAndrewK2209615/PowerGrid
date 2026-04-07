package powergrid.ui;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.List;
import javax.imageio.*;
import javax.swing.*;
import powergrid.core.*;
import powergrid.utils.*;

public class MapPanel extends JPanel implements MouseListener{
    private BufferedImage mapImage;
    private BufferedImage marketImage;
    private BufferedImage background;
    private BufferedImage rightArrow, leftArrow;
    private City selectedCity;
    private Path shortestPath;
    private final int HOUSE_SIZE = 12;
    private int panelX = 924;
    private int panelY = 550;
    private int panelWidth = 800;
    private int panelHeight = 400;
    private int pointerX = 0, pointerY = 0; //temp, used to find locations while building this panel
    private Player viewedPlayer;

    public MapPanel()
    {
        try
        {
            mapImage = ImageIO.read(MapPanel.class.getResource("/powergrid/Images/Germany Map.jpeg"));
            marketImage = ImageIO.read(MapPanel.class.getResource("/powergrid/Images/market.png"));
            background = ImageIO.read(MapPanel.class.getResource("/powergrid/Images/background.png"));
            rightArrow = ImageIO.read(getClass().getResource("/powergrid/Images/right-arrow.png"));
            leftArrow = ImageIO.read(getClass().getResource("/powergrid/Images/left-arrow.png"));
        }
        catch (Exception e)
        {
            System.out.println("Failed to load an image");
        }
        viewedPlayer = GameState.activePlayer;
        addMouseListener(this);
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        Graphics2D g2d = (Graphics2D)g;
        drawMap(g2d);
        if (selectedCity != null)
        {
            drawPath(g2d);
        }
        drawMarket(g2d);
        drawPlayerUI(g, g2d);
        if (selectedCity != null)
        {
            drawPathUI(g2d);
        }
        else
        {
            drawPrompt(g2d);
        }
        drawFinishButton(g2d);
        g.setColor(Color.RED);
        // g.drawOval(pointerX - 1, pointerY - 1, 2, 2);
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

    public void drawMarket(Graphics2D g2d)
    {
        g2d.drawImage(marketImage, getWidth() / 2 - 148, 0, 100, getHeight(), null);

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

    public void drawPathUI(Graphics2D g2d)
    {
        g2d.setColor(new Color(229, 239, 244));
        g2d.fillRoundRect(panelX, panelY, panelWidth, panelHeight, 10, 10);
        if (GameState.activePlayer.getElektro() >= shortestPath.getDistance())
        {
            g2d.setColor(new Color(0, 191, 99));
        }
        else
        {
            g2d.setColor(new Color(185, 181, 171));
        }
        g2d.fillRect(panelX + panelWidth / 2, panelY + 50, panelWidth / 2, panelHeight / 2 - 35);
        g2d.setColor(new Color(255, 87, 87));
        g2d.fillRect(panelX + panelWidth / 2, panelY + 50 + (panelHeight / 2 - 35), panelWidth / 2, panelHeight / 2 - 15);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRoundRect(panelX, panelY, panelWidth, panelHeight, 10, 10);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawLine(panelX, panelY + 50, panelX + panelWidth, panelY + 50);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 30));
        g2d.drawString("Building a house in: " + selectedCity.getName(), panelX + 179, panelY + 35);
        g2d.drawLine(panelX + panelWidth / 2, panelY + 50, panelX + panelWidth / 2, panelY + panelHeight);
        g2d.drawLine(panelX, panelY + 85, panelX + panelWidth / 2, panelY + 85);
        g2d.setFont(new Font(" SansSerif", Font.BOLD, 20));
        g2d.drawString("Shortest Path", panelX + 130, panelY + 75);

        //list out path
        g2d.setFont(new Font("SansSerif", Font.PLAIN, 15));
        int y = panelY + 110;
        for(int i=0; i<shortestPath.getPath().size(); i++)
        {
            String s = ">  " + shortestPath.getPath().get(i).getName() + " -> ";
            if (i == shortestPath.getPath().size() - 1)
            {
                s += "Build House ($" + (10 + selectedCity.getOwners().size() * 5) + ")";
            }
            else
            {
                for(Connection connect: GameState.mapGraph.getMapGraph().get(shortestPath.getPath().get(i)))
                {
                    if (connect.getDestination().getName().equals(shortestPath.getPath().get(i + 1).getName()))
                    {
                        s += connect.getDestination().getName() + " ($" + connect.getCost() + ")";
                    }
                }
            }
            g2d.drawString(s, panelX + 20, y);
            y += 20;
        }
        g2d.setFont(new Font("SansSerif", Font.BOLD, 30));
        g2d.drawString("Total Cost: $" + shortestPath.getDistance(), 953, y + 20);

        //draw confirm/cancel
        g2d.drawLine(panelX + panelWidth / 2, panelY + 50, panelX + panelWidth / 2, panelY + panelHeight);
        g2d.drawLine(panelX + panelWidth / 2, panelY + panelHeight / 2 + 15, panelX + panelWidth, panelY + panelHeight / 2 + 15);
        g2d.setFont(new Font("Arial", Font.BOLD, 45));
        g2d.drawString("Confirm", 1425, 700);
        g2d.drawString("Cancel", 1425, 875);
    }

    public void drawPrompt(Graphics2D g2d)
    {
        g2d.setColor(new Color(229, 239, 244));
        g2d.fillRect(1077, 621, 648, 219);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRect(1077, 621, 648, 219);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        g2d.drawString("Click on a city on the map to build in it", 1140, 735);
    }

    public void drawFinishButton(Graphics2D g2d)
    {
        g2d.setColor(new Color(0, 191, 99));
        g2d.fillRoundRect(1200, 981, 320, 50, 4, 4);
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(1200, 981, 320, 50, 4, 4);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        g2d.drawString("Finish turn", 1275, 1015);
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

    public void drawPlayerUI(Graphics g, Graphics2D g2d)
    {
        g.setColor(new Color(229, 239, 244));
        g.fillRect(924, 29, 945, 95);
        g.fillRect(924, 153, 145, 50);
        g.fillRect(1104, 150, 500, 55);
        g.fillRect(1104, 150, 105, 55);
        g.fillRect(1504, 150, 100, 55);
        g.fillRect(1630, 150, 238, 55);
        g.fillRect(920, 220, 950, 300);

        g.setColor(Color.lightGray);
        g.fillRect(935, 230, 290, 275);  // powerplant section 1
        g.fillRect(1250, 230, 290, 275);  // powerplant section 2
        g.fillRect(1565, 230, 290, 275);  // powerplant section 3
        g2d.setStroke(new BasicStroke(7));
        g.setColor(Color.BLACK);
        g.drawRect(920, 25, 950, 100);  // top rectangle
        g.setColor(viewedPlayer.getColor());  // we have to change this
        g2d.setFont(new Font("Arial", Font.BOLD, 33));
        g.drawString(viewedPlayer.getName(), 1300, 190);
        g.setColor(GameState.activePlayer.getColor());
        g.fillOval(960, 40, 75, 75);

        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString(GameState.activePlayer.getName() + " is buying resources", 1220, 65);   // define n
        String s = "Bureaucracy";
        if (GameState.roundManager.getNextPlayer() != null)
        {
            s = GameState.roundManager.getNextPlayer().getName();
        }
        g.drawString("(Next: " + s + ")", 1300, 100);  // define n+1

        g2d.setStroke(new BasicStroke(5));  // number of houses code
        g.drawRect(920, 150, 150, 55);
        g.setFont(new Font("Arial", Font.PLAIN, 25));
        g.drawString("Houses: " + viewedPlayer.getCitiesBuilt().size(), 940, 185);  // N represents number of houses owned by the current player

        g2d.setStroke(new BasicStroke(7));
        g.drawRect(1100, 150, 500, 55);
        g.drawRect(1100, 150, 105, 55);
        g.drawRect(1500, 150, 100, 55);
        
        g.drawImage(rightArrow, 1520, 155, 60, 45, null);
        g.drawImage(leftArrow, 1130, 155, 60, 45, null);

        g2d.setStroke(new BasicStroke(5));
        g.drawRect(1630, 150, 238, 55);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Player Elecktros: " + viewedPlayer.getElektro(), 1635, 185);   // define elecktros for player

        g2d.setStroke(new BasicStroke(7));  // powerplant section
        g.drawRect(920, 220, 950, 300);
        g2d.setStroke(new BasicStroke(5));
        g.drawRect(935, 230, 290, 275);  // powerplant section 1
        g.drawString("Empty Power Plant", 970, 360);
        g.drawRect(1250, 230, 290, 275);  // powerplant section 2
        g.drawString("Empty Power Plant", 1290, 360);
        g.drawRect(1565, 230, 290, 275);  // powerplant section 3
        g.drawString("Empty Power Plant", 1610, 360);

        //draw player's power plants
        int x = 935;
        for(PowerPlant p: viewedPlayer.getOwnedPlants())
        {
            g.drawImage(p.getImage(), x, 230, 290, 275, null);
            x += 315;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println("x = " + x);
        System.out.println("y = " + y);
        pointerX = x;
        pointerY = y;
        //Detect when the player clicks on a city on the map
        if (GameState.mapGraph.getCity(x, y) != null)
        {
            selectedCity = GameState.mapGraph.getCity(x, y);
        }
        if (selectedCity != null && GameState.mapGraph.validBuild(GameState.activePlayer, selectedCity))
        {
            shortestPath = GameState.mapGraph.getShortestPath(GameState.activePlayer, selectedCity);
        }
        //Detect when the player clicks on "Confirm" or "Cancel"
        if (selectedCity != null && x > panelX + panelWidth / 2 && x < panelX + panelWidth && y > panelY + 50 && y < panelY + panelHeight / 2 + 15)
        {
            if (GameState.activePlayer.getElektro() >= shortestPath.getDistance())
            {
                GameState.activePlayer.addCity(selectedCity);
                selectedCity.addOwner(GameState.activePlayer);
                GameState.activePlayer.spendElektro(shortestPath.getDistance());
                selectedCity = null;
                shortestPath = null;
            }
        }
        if (x > panelX + panelWidth / 2 && x < panelX + panelWidth && y > panelY + 50 + (panelHeight / 2 - 35) && y < panelY + panelHeight)
        {
            selectedCity = null;
            shortestPath = null;
        }

        //detect when a player clicks the finish button
        if (x > 1200 && x < 1520 && y > 981 && y < 1031)
        {
            Player next = GameState.roundManager.getNextPlayer();
            if (next != null)
            {
                GameState.activePlayer = next;
                viewedPlayer = GameState.activePlayer;
            }
            else
            {
                //load next panel
            }
        }
        
        //detect when the player tries switching the viewed player
        //g.drawRect(1100, 150, 500, 55);
       // g.drawRect(1100, 150, 105, 55);
        //g.drawRect(1500, 150, 100, 55);
        if (x > 1100 && x < 1205 && y > 150 && y < 205)
        {
            viewedPlayer = GameState.roundManager.getLeftPlayer(viewedPlayer);
        }
        if (x > 1500 && x < 1600 && y > 150 && y < 205)
        {
            viewedPlayer = GameState.roundManager.getRightPlayer(viewedPlayer);
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

    public void addNotify()
    {
        super.addNotify();
        requestFocus();
    }
    
}
