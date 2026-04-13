package powergrid.ui;

import powergrid.PowerGridFrame;
import powergrid.core.GameState;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public class InfoScreenPanel extends JPanel implements MouseListener, KeyListener {
    private BufferedImage backgroundImage;
    private BufferedImage turnImage;
    private BufferedImage paymentsImage;
    private BufferedImage marketImage;

    public InfoScreenPanel() {
        try {
            backgroundImage = ImageIO.read(SetupPanel.class.getResource("/powergrid/Images/background.png"));
            turnImage = ImageIO.read(SetupPanel.class.getResource("/powergrid/Images/TurnOrderCard.png"));
            paymentsImage = ImageIO.read(SetupPanel.class.getResource("/powergrid/Images/PaymentCard.png"));
            marketImage = ImageIO.read(SetupPanel.class.getResource("/powergrid/Images/RestockCard.png"));
        } catch (Exception e) {
            System.out.println("Background image failed to load");
        }
        setFocusable(true);
        addMouseListener(this);
        addKeyListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        drawInfoScreen(g2);
    }

    private void drawInfoScreen(Graphics2D g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }
        if (turnImage != null){
            g.drawImage(turnImage, 0, 520, 558, 525, null);
        }
        if (paymentsImage != null){
            g.drawImage(paymentsImage, 600, 600, 470, 470, null);
        }
        if (marketImage != null){
            g.drawImage(marketImage, 1112, 520, 792, 525, null);
        }

        Color myYellow = new Color(255, 250, 191);
        g.setColor(myYellow);
        g.fillRect(400, 0, 1095, 200); // Fills the rectangle
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(5));
        g.drawRect(400, 0, 1095, 200); // Draws the border
        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif", Font.BOLD, 58));
        g.drawString("Press  'r'  to  return",680, 110);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}
}
