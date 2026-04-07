package powergrid.ui;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import powergrid.utils.*;
import powergrid.core.*;
import powergrid.managers.*;
import java.util.ArrayList;
import java.util.List;
public class AuctionPanel extends JPanel implements MouseListener {
        private BufferedImage ArrowLeft,ArrowRight;
    private int selectedIndex = -1;
    private boolean selectedFuture = false;
    private int toggle=1;
    public AuctionPanel()
    {
        try
        {
           ArrowLeft = ImageIO.read(MapPanel.class.getResource("/powergrid/Images/left-arrow.jpeg"));
           ArrowRight = ImageIO.read(MapPanel.class.getResource("/powergrid/Images/right-arrow.jpeg"));
        }
        catch (Exception e)
        {
            System.out.println("Failed to load an image");
        }
        addMouseListener(this);
    }
    public void paint(Graphics g)
    {
        ArrayList<PowerPlant> currentMarket = GameState.marketManager.getCurrentMarket();
        ArrayList<PowerPlant> futureMarket = GameState.marketManager.getFutureMarket();
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        for(int i=0;i<GameState.players.size();i++){
        g2d.setColor(GameState.players.get(i).getColor());
        g2d.fillOval(500+i*200,925,100,100);
        }
        if(toggle==0){
        g2d.drawImage(ArrowLeft, 500+200, 800, 100, 100, null);
        g2d.drawImage(ArrowRight, 500+200+100, 800, 100, 100, null);
        }
        g2d.setStroke(new BasicStroke(4));
        for(int i=0;i<4;i++){
            int x = 500 + i * 250;
            int y = 0;
            g2d.drawImage(currentMarket.get(i).getImage(), x, y, 200, 200, null);
            if (!selectedFuture && selectedIndex == i)
            {
                g2d.setColor(Color.YELLOW);
                g2d.setStroke(new BasicStroke(6));
                g2d.drawRect(x - 4, y - 4, 208, 208);
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(4));
                toggle=0;
            }
        }
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawLine(0, 225, 2400, 225);
        for(int i=0;i<4;i++){
            int x = 500 + i * 250;
            int y = 250;
            g2d.drawImage(futureMarket.get(i).getImage(), x, y, 200, 200, null);
            if (selectedFuture && selectedIndex == i)
            {
                g2d.setColor(Color.YELLOW);
                g2d.setStroke(new BasicStroke(6));
                g2d.drawRect(x - 4, y - 4, 208, 208);
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(4));
                toggle=0;
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println("Mouse clicked at: " + x + ", " + y);
        if(toggle==1){
        for(int i=0;i<4;i++)
        {
            int cardX = 500 + i * 250;
            int cardY = 0;
            if (x >= cardX && x <= cardX + 200 && y >= cardY && y <= cardY + 200)
            {
                selectedIndex = i;
                selectedFuture = false;
                break;
            }
            cardY = 250;
            if(GameState.step==3){
            if (x >= cardX && x <= cardX + 200 && y >= cardY && y <= cardY + 200)
            {
                selectedIndex = i;
                selectedFuture = true;
                break;
            }
        }
        }
    }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
