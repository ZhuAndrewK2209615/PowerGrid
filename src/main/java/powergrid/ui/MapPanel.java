package powergrid.ui;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import powergrid.PowerGridFrame;
import powergrid.core.*;
import powergrid.utils.*;

public class MapPanel extends JPanel implements MouseListener{
    private City selectedCity;
    private Path shortestPath;
    private int panelX = 924;
    private int panelY = 550;
    private int panelWidth = 800;
    private int panelHeight = 400;
    private PlayerMenu playerMenu;
    private MapUI mapUI;
    private MarketUI marketUI;
    private PowerGridFrame parent;
    private InfoPreviews infoPreview;

    public MapPanel(PowerGridFrame parent)
    {
        try
        {
            playerMenu = new PlayerMenu();
            mapUI = new MapUI(this);
            marketUI = new MarketUI(this);
        }
        catch (Exception e)
        {
            System.out.println("Failed to load an image");
        }
        this.parent = parent;
        infoPreview = new InfoPreviews(this);
        addMouseListener(this);
        addMouseListener(playerMenu);
        addKeyListener(infoPreview);
        addMouseListener(infoPreview);
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        g.drawImage(ImageLibrary.background, 0, 0, getWidth(), getHeight(), null);
        Graphics2D g2d = (Graphics2D)g;
        if (infoPreview.isPreviewingInfo)
        {
            infoPreview.drawInfoPreview(g2d);
        }
        else if (infoPreview.isPreviewingAuction)
        {
            infoPreview.drawPowerPlants(g);
        }
        else if (infoPreview.isPreviewingDiscard)
        {
            infoPreview.drawDiscardPreview(g);
        }
        else
        {
            mapUI.drawMap(g);
            if (selectedCity != null)
            {
                drawPath(g2d);
            }
            marketUI.drawMarket(g);
            playerMenu.drawMenu(g);
            if (selectedCity != null)
            {
                drawPathUI(g2d);
            }
            else
            {
                drawPrompt(g2d);
            }
            drawFinishButton(g2d);
            infoPreview.drawInfoButtons(g2d);
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
            g2d.setColor(new Color(185, 181, 171)); //gray
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
        g2d.setColor(new Color(0, 191, 99)); //green
        g2d.fillRoundRect(1200, 981, 320, 50, 4, 4);
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(1200, 981, 320, 50, 4, 4);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        g2d.drawString("Finish turn", 1275, 1015);
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
        //Detect when the player clicks on a city on the map
        if (GameState.mapGraph.getCity(x, y) != null && !infoPreview.isPreviewing)
        {
            selectedCity = GameState.mapGraph.getCity(x, y);
        }
        if (selectedCity != null && GameState.mapGraph.validBuild(GameState.activePlayer, selectedCity) && !infoPreview.isPreviewing)
        {
            shortestPath = GameState.mapGraph.getShortestPath(GameState.activePlayer, selectedCity);
        }
        //Detect when the player clicks on "Confirm" or "Cancel"
        if (selectedCity != null && x > panelX + panelWidth / 2 && x < panelX + panelWidth && y > panelY + 50 && y < panelY + panelHeight / 2 + 15 && !infoPreview.isPreviewing)
        {
            if (GameState.activePlayer.getElektro() >= shortestPath.getDistance())
            {
                GameState.activePlayer.addCity(selectedCity);
                selectedCity.addOwner(GameState.activePlayer);
                GameState.activePlayer.spendElektro(shortestPath.getDistance());
                selectedCity = null;
                shortestPath = null;
                if (GameState.activePlayer.getCitiesBuilt().size() == GameState.phase2Requirement && GameState.step == 1)
                {
                    GameState.triggerPhase2();
                }
                if (GameState.activePlayer.getCitiesBuilt().size() == GameState.gameEndRequirement)
                {
                    GameState.gameEnded = true;
                }
            }
        }
        if (x > panelX + panelWidth / 2 && x < panelX + panelWidth && y > panelY + 50 + (panelHeight / 2 - 35) && y < panelY + panelHeight && !infoPreview.isPreviewing)
        {
            selectedCity = null;
            shortestPath = null;
        }

        //detect when a player clicks the finish button
        if (x > 1200 && x < 1520 && y > 981 && y < 1031 && !infoPreview.isPreviewing)
        {
            Player next = GameState.roundManager.getNextPlayer();
            if (next != null)
            {
                GameState.activePlayer = next;
                playerMenu.viewedPlayer = GameState.activePlayer;
            }
            else
            {
                GameState.activePlayer = GameState.roundManager.getPlayerOrder().get(0);
                GameState.roundManager.advancePhase();
                setVisible(false);
                parent.add(new EndPanel(parent));
                parent.repaint();
                parent.remove(this);
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

    public void addNotify()
    {
        super.addNotify();
        requestFocus();
    }
}
