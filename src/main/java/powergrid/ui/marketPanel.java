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

    private BufferedImage GermanMap, market, playerPlant, coal, garbage, oil, uranium, rulesSymbol, currentAuction, rightArrow, leftArrow;

    public marketPanel(){
        try {
            GermanMap = ImageIO.read(getClass().getResource("/powergrid/Images/Germany Map.jpeg"));
            market = ImageIO.read(getClass().getResource("/powergrid/Images/market.png"));
            rightArrow = ImageIO.read(getClass().getResource("/powergrid/Images/right-arrow.png"));
            leftArrow = ImageIO.read(getClass().getResource("/powergrid/Images/left-arrow.png"));
            // will code player plant
           coal = ImageIO.read(getClass().getResource("/powergrid/Images/coal.png"));
           garbage = ImageIO.read(getClass().getResource("/powergrid/Images/Garbage.png"));
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
        
        g.drawImage(rightArrow, 1520, 155, 60, 45, null);
        g.drawImage(leftArrow, 1130, 155, 60, 45, null);

        g2d.setStroke(new BasicStroke(5));
        g.drawRect(1630, 150, 238, 55);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Player Elecktros: 999", 1635, 185);   // define elecktros for player

        g2d.setStroke(new BasicStroke(7));  // powerplant section --> not finished
        g.drawRect(920, 220, 950, 300);
        g2d.setStroke(new BasicStroke(5));
        g.drawRect(935, 230, 290, 275);  // powerplant section 1
        g.drawRect(1250, 230, 290, 275);  // powerplant section 2
        g.drawRect(1565, 230, 290, 275);  // powerplant section 3
          

        g2d.setStroke(new BasicStroke(5));

        g.drawRect(920,530, 470, 200);  // coal
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Coal", 950, 580);
        g.drawImage(coal, 950, 600, 100, 100, null);

        g.drawRect(920+490, 530, 460, 200);  // oil

        g.drawRect(920, 740, 470, 200);  // garbage
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Garbage", 925, 780);
        g.drawImage(garbage, 950, 800, 100, 100, null);

        g.drawRect(920+490, 740, 460, 200); // uranium

        g.drawRect(920+360, 960, 200, 70);
        
        // placeHolder for powerplants


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
