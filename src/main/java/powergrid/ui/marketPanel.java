package powergrid.ui;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import java.awt.BasicStroke;
import java.awt.Color;
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

    private BufferedImage background, GermanMap, market, playerPlant, coal, garbage, oil, uranium, rulesSymbol, currentAuction, rightArrow, leftArrow;

    public marketPanel(){
        try {
            GermanMap = ImageIO.read(getClass().getResource("/powergrid/Images/Germany Map.jpeg"));
            market = ImageIO.read(getClass().getResource("/powergrid/Images/market.png"));
            rightArrow = ImageIO.read(getClass().getResource("/powergrid/Images/right-arrow.png"));
            leftArrow = ImageIO.read(getClass().getResource("/powergrid/Images/left-arrow.png"));
            // will code player plant get image
            coal = ImageIO.read(getClass().getResource("/powergrid/Images/coal.png"));
            garbage = ImageIO.read(getClass().getResource("/powergrid/Images/Garbage.png"));
            oil = ImageIO.read(getClass().getResource("/powergrid/Images/Oil.png"));
            uranium = ImageIO.read(getClass().getResource("/powergrid/Images/uranium.png"));
            background = ImageIO.read(getClass().getResource("/powergrid/Images/toChange.png"));
           // rulesSymbol = ImageIO.read(getClass().getResource("powergrid/Images/QuestionSymbol.png"));
           // currentAuction = ImageIO.read(getClass().getResource("powergrid/Images/energy.png"));

        } catch (Exception e) {
            System.out.println("Error");
            return;
        }
        
        addMouseListener(this); 
    }

    public void paintComponent(Graphics g){  // do all background color changes here, do not touch the pain method
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);

        g.setColor(new Color(255,250,191));
        g.fillRect(924, 29, 945, 95);
        g.fillRect(924, 153, 145, 50);
        g.fillRect(1104, 150, 500, 55);
        g.fillRect(1104, 150, 105, 55);
        g.fillRect(1504, 150, 100, 55);
        g.fillRect(1630, 150, 238, 55);
        g.fillRect(920, 220, 950, 300);
        g.fillRect(920,530, 470, 200);  // coal
        g.fillRect(920+490, 530, 460, 200);  // oil
        g.fillRect(920, 740, 470, 200);  // garbage
        g.fillRect(920+490, 740, 460, 200); // uranium



        g.setColor(Color.lightGray);
        g.fillRect(935, 230, 290, 275);  // powerplant section 1
        g.fillRect(1250, 230, 290, 275);  // powerplant section 2
        g.fillRect(1565, 230, 290, 275);  // powerplant section 3
        g.fillRect(1120,600,240,100);  // button to buy resource (changes color we need to code)
        g.fillRect(1120,800,240,100);  // button to buy resource (changes color we need to code)
        g.fillRect(1608,600,240,100);  // button to buy resource (changes color we need to code)
        g.fillRect(1608,800,240,100);  // button to buy resource (changes color we need to code)





        
    }

    public void paint(Graphics g){
        super.paint(g);
        g.drawImage(GermanMap, 0,0, getWidth()/2-148,getHeight(),null);  // the map
        g.drawImage(market, 800, 0, 100, 1050, null);  // resource market

        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(7));
        g.drawRect(920, 25, 950, 100);  // top rectangle
        g.setColor(Color.red);  // we have to change this
        g2d.setFont(new Font("Arial", Font.BOLD, 33));
        g.drawString("Player n", 1300, 190);
        g.fillOval(960, 40, 75, 75);

        g.setColor(Color.black);
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

        g2d.setStroke(new BasicStroke(7));  // powerplant section
        g.drawRect(920, 220, 950, 300);
        g2d.setStroke(new BasicStroke(5));
        g.drawRect(935, 230, 290, 275);  // powerplant section 1
        g.drawString("Empty Power Plant", 970, 360);
        g.drawRect(1250, 230, 290, 275);  // powerplant section 2
        g.drawString("Empty Power Plant", 1290, 360);
        g.drawRect(1565, 230, 290, 275);  // powerplant section 3
        g.drawString("Empty Power Plant", 1610, 360);
          

        g2d.setStroke(new BasicStroke(5));

        g.drawRect(920,530, 470, 200);  // coal
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Coal", 950, 580);
        g.drawImage(coal, 950, 600, 100, 100, null);
        g2d.setFont(new Font("Arial", Font.PLAIN, 35));
        g.drawString("Current stock: 99", 1110, 580);  // we have to change stock
        g.drawRect(1120,600,240,100);  // button to buy resource (changes color we need to code)
        g2d.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Buy 1 for $x", 1165, 660);


        g.drawRect(920+490, 530, 460, 200);  // oil
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Oil", 1450, 580);
        g.drawImage(oil, 1450, 600, 100, 100, null);
        g2d.setFont(new Font("Arial", Font.PLAIN, 35));
        g.drawString("Current stock: 99", 1600, 580); // we have to change stock
        g.drawRect(1120,800,240,100);  // button to buy resource (changes color we need to code)
        g2d.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Buy 1 for $x", 1165, 860);
        

        g.drawRect(920, 740, 470, 200);  // garbage
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Garbage", 925, 780);
        g.drawImage(garbage, 950, 800, 100, 100, null);
        g2d.setFont(new Font("Arial", Font.PLAIN, 35));
        g.drawString("Current stock: 99", 1110, 780); // we have to change stock
        g.drawRect(1608,600,240,100);  // button to buy resource (changes color we need to code)
        g2d.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Buy 1 for $x", 1650, 660);

        g.drawRect(920+490, 740, 460, 200); // uranium
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Uranium", 1420, 780);
        g.drawImage(uranium, 1410, 770, 150, 150, null);
        g2d.setFont(new Font("Arial", Font.PLAIN, 35));
        g.drawString("Current stock: 99", 1600, 780); // we have to change stock
        g.drawRect(1608,800,240,100);  // button to buy resource (changes color we need to code)
        g2d.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Buy 1 for $x", 1650, 860);

        g.drawRect(920+360, 960, 200, 70);
        g.setColor(new Color(64,218,53));
        g.fillRect(920+362, 962, 196, 66);

        
        // sperators 
        g.setColor(Color.BLACK);
        g.drawString("Finish",920+420, 1010 );
        g.drawLine(1100, 530, 1100, 730);
        g.drawLine(1100, 745, 1100, 940);
        g.drawLine(1590,530, 1590, 730);
        g.drawLine(1590,745, 1590, 940);

        

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
