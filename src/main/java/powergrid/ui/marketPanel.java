package powergrid.ui;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class marketPanel extends JPanel implements MouseInputListener{

    private BufferedImage GermanMap, market, playerPlant, coal, garbage, oil, uranium, rulesSymbol, currentAuction, rightArrow;

    public marketPanel(){
        try {
            GermanMap = ImageIO.read(getClass().getResource("/powergrid/Images/Germany Map.jpeg"));
            market = ImageIO.read(getClass().getResource("/powergrid/Images/market.png"));
            rightArrow = ImageIO.read(getClass().getResource("/powergrid/Images/right-arrow.png")
            // will code player plant
           // coal = ImageIO.read(getClass().getResource("powergrid/Images/coal.png"));
           // garbage = ImageIO.read(getClass().getResource("powergrid/Images/Garbage.png"));
            //oil = ImageIO.read(getClass().getResource("powergrid/Images/Oil.png"));
           // uranium = ImageIO.read(getClass().getResource("powergrid/Images/Uranium.png"));
           // rulesSymbol = ImageIO.read(getClass().getResource("powergrid/Images/QuestionSymbol.png"));
           // currentAuction = ImageIO.read(getClass().getResource("powergrid/Images/energy.png"));

        } catch (Exception e) {
            System.out.println("Error");
            return;
        }
        
        addMouseListener(this); 
    }

    public void paint(Graphics g){
        super.paint(g);
        g.drawImage(GermanMap, 0,0, getWidth()/2-148,1080,null);  // the map
        g.drawImage(market, 800, 0, 100, 1050, null);  // resource market

        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(7));
        g.drawRect(920, 25, 950, 100);  // top rectangle
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Player " + "n " + "is buying resources", 1220, 65);   // define n
        g.drawString("(Next: player " + "n+1)", 1300, 100);  // define n+1

        g2d.setStroke(new BasicStroke(5));  // number of houses code
        g.drawRect(920, 150, 150, 55);
        g.setFont(new Font("Arial", Font.PLAIN, 25));
        g.drawString("Houses: " + "N", 940, 185);  // N represents number of houses owned by the current player

        g2d.setStroke(new BasicStroke(7));
        g.drawRect(1100, 150, 500, 55);
        g.drawRect(1100, 150, 105, 55);
        g.drawRect(1500, 150, 100, 55);
        
        g.drawImage(rightArrow, 1200, 160, 200, 200, null);



    }
    
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
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

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
    
}
