package powergrid.ui;



import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import powergrid.core.GameState;

public class DiscardPanel extends JPanel implements MouseInputListener {

    private BufferedImage background;
    
    // Tracking variables for resource selection
    private boolean res1, res2, res3, res4, res5, res6;
    private boolean p1, p2,p3;

    public DiscardPanel() {
        try {
            background = ImageIO.read(getClass().getResource("/powergrid/Images/background.png"));
        } catch (Exception e) {
            System.out.println("No image found");
        }
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {  // draw all images here
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        }
        Graphics2D g2d = (Graphics2D) g;

        // box that says "player x has recieved powerplant x
        g2d.setStroke(new BasicStroke(7));
        g2d.setColor(new Color(255, 250, 191));
        g2d.fillRect(600, 75, 800, 100);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(600, 75, 800, 100);
        g2d.setFont(new Font("Arial", Font.BOLD, 35));
        g2d.drawString("Player x has received Power Plant xx", 685, 140); // We have to change this

        // box that shows current player powerplants
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRect(440, 200, 1100, 300);
        g2d.setColor(new Color(255, 250, 191));
        g2d.fillRect(444, 204, 1093, 293);

        // section wise
        g2d.setFont(new Font("Arial", Font.PLAIN, 24));
        g2d.setStroke(new BasicStroke(5));
        g2d.setColor(p1 ? Color.GREEN : Color.BLACK);
        g2d.drawRect(935 - 420, 213, 290, 275);  // powerplant section 1
        if(GameState.activePlayer.getOwnedPlants().get(0).getImage() != null){
            g.drawImage(GameState.activePlayer.getOwnedPlants().get(0).getImage(), 935-420, 213, 290, 275, null);
        }else {
            g2d.setColor(Color.BLACK);
            g2d.drawString("Empty Power Plant", 970 - 420, 360);
        }
        
        g2d.setColor(p2 ? Color.GREEN : Color.BLACK);
        g2d.drawRect(1250 - 420, 213, 290, 275);  // powerplant section 2
        if(GameState.activePlayer.getOwnedPlants().get(1).getImage() != null){
            g.drawImage(GameState.activePlayer.getOwnedPlants().get(1).getImage(), 1250-420, 213, 290, 275, null);
        }else {
            g2d.setColor(Color.BLACK);
            g2d.drawString("Empty Power Plant", 1290 - 420, 360);
        }

        g2d.setColor(p3 ? Color.GREEN : Color.BLACK);
        g2d.drawRect(1565 - 420, 213, 290, 275);  // powerplant section 3
        if(GameState.activePlayer.getOwnedPlants().size() == 3){
            g.drawImage(GameState.activePlayer.getOwnedPlants().get(2).getImage(), 1565-420, 213, 290, 275, null);
        }else {
            g2d.setColor(Color.BLACK);
            g2d.drawString("Empty Power Plant", 1565-380, 360);
        }

        // text section saying u discarded
        g2d.drawRect(440, 510, 350, 125);
        g2d.setColor(new Color(255, 250, 191));
        g2d.fillRect(443, 513, 345, 120);
        g2d.setColor(Color.black);
        g2d.setFont(new Font("Arial", Font.ITALIC, 26));
        g2d.drawString("Discarding Power Plant x;", 460, 560);  // we have to change this
        g2d.drawString("Redistribute Your Resources", 450, 600);

        // 6 boxes determining reosurces stored in the game
        drawResourceBox(g2d, 810, 510, res1);
        drawResourceBox(g2d, 810 + 135, 510, res2);
        drawResourceBox(g2d, 810 + 270, 510, res3);
        drawResourceBox(g2d, 810, 510 + 135, res4);
        drawResourceBox(g2d, 810 + 135, 510 + 135, res5);
        drawResourceBox(g2d, 810 + 270, 510 + 135, res6);

        // discard button
        g2d.setStroke(new BasicStroke(5));
        g2d.setColor(Color.BLACK);
        g2d.drawRect(1220, 510, 319, 125);
        g2d.setColor(new Color(223, 107, 107));
        g2d.fillRect(1222, 513, 315, 120);
        g2d.setColor(Color.black);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Give up remaining Resources", 1232, 580);
    }

    private void drawResourceBox(Graphics2D g2d, int x, int y, boolean isSelected) {
        g2d.setColor(new Color(255, 250, 191));
        g2d.fillRect(x, y, 125, 125);

        g2d.setStroke(new BasicStroke(5));
        
        if (isSelected) {
            g2d.setColor(Color.GREEN);
        } else {
            g2d.setColor(Color.BLACK);
        }
        
        g2d.drawRect(x, y, 125, 125);
        g2d.setColor(Color.black);
        g2d.setFont(new Font("Arial", Font.ITALIC, 20));
        g2d.drawString("Empty", x + 32, y + 65);
    }

    private void togglePowerPlantSection(int sectionNumber) {
        // Only one section may be selected at once.
        boolean wasSelected = (sectionNumber == 1 && p1) || (sectionNumber == 2 && p2) || (sectionNumber == 3 && p3);
        p1 = p2 = p3 = false;
        if (!wasSelected) {
            if (sectionNumber == 1) p1 = true;
            else if (sectionNumber == 2) p2 = true;
            else if (sectionNumber == 3) p3 = true;
        }
        repaint();
    }

    // Updated method to ensure only one box can be selected at a time
    private void toggleResource(int boxNumber) {
        // Store current state of the clicked box
        boolean targetState = false;
        if (boxNumber == 1) targetState = !res1;
        else if (boxNumber == 2) targetState = !res2;
        else if (boxNumber == 3) targetState = !res3;
        else if (boxNumber == 4) targetState = !res4;
        else if (boxNumber == 5) targetState = !res5;
        else if (boxNumber == 6) targetState = !res6;

        // Reset all to false first (Single Select Logic)
        res1 = res2 = res3 = res4 = res5 = res6 = false;

        // Set the clicked box to its new state
        if (boxNumber == 1) res1 = targetState;
        else if (boxNumber == 2) res2 = targetState;
        else if (boxNumber == 3) res3 = targetState;
        else if (boxNumber == 4) res4 = targetState;
        else if (boxNumber == 5) res5 = targetState;
        else if (boxNumber == 6) res6 = targetState;

        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        // powerplant section 1
        if ((x >= 935 - 420 && x <= 935 - 420 + 290) && (y >= 213 && y <= 213 + 275)) {
            System.out.println("Powerplant section 1 clicked");
            togglePowerPlantSection(1);
            System.out.println("Number 1");
            
        }
        // powerplant section 2
        else if ((x >= 1250 - 420 && x <= 1250 - 420 + 290) && (y >= 213 && y <= 213 + 275)) {
            System.out.println("Powerplant section 2 clicked");
            togglePowerPlantSection(2);
            System.out.println("Number 2");
        }
        // powerplant section 3
        else if ((x >= 1565 - 420 && x <= 1565 - 420 + 290) && (y >= 213 && y <= 213 + 275)) {
            System.out.println("Powerplant section 3 clicked");
            togglePowerPlantSection(3);
            System.out.println("Number 3");
        }
        // 6 boxes determining reosurces stored in the game
        else if (y >= 510 && y <= 635) {
            if (x >= 810 && x <= 810 + 125) toggleResource(1);
            else if (x >= 810 + 135 && x <= 810 + 135 + 125) toggleResource(2);
            else if (x >= 810 + 270 && x <= 810 + 270 + 125) toggleResource(3);
            
            // discard button 
            else if (x >= 1220 && x <= 1520) {
                System.out.println("Give up remaining Resources clicked");
            }
        } 
        else if (y >= 645 && y <= 770) {
            if (x >= 810 && x <= 810 + 125) toggleResource(4);
            else if (x >= 810 + 135 && x <= 810 + 135 + 125) toggleResource(5);
            else if (x >= 810 + 270 && x <= 810 + 270 + 125) toggleResource(6);
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseDragged(MouseEvent e) {}
    @Override public void mouseMoved(MouseEvent e) {}
}
