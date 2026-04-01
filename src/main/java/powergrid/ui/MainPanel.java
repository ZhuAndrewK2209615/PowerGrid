package powergrid.ui;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import powergrid.utils.*;
import powergrid.core.*;

public class MainPanel extends JPanel implements MouseListener{
    private BufferedImage mapImage;
    private City cityClicked;

    public MainPanel()
    {
        try
        {
            mapImage = ImageIO.read(MainPanel.class.getResource("/powergrid/Images/Germany Map.jpeg"));
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
    }

    public void drawMap(Graphics2D g2d)
    {
        g2d.drawImage(mapImage, 0, 0, getWidth() / 2 - 148, getHeight(), null);
        if (cityClicked != null)
        {
            g2d.drawString(cityClicked.getName(), getWidth() / 2, getHeight() / 2);
        }

        
        g2d.setStroke(new BasicStroke(8));
        
    }

    public void drawPath()
    {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        cityClicked = GameState.mapGraph.getCity(x, y);
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
