package powergrid.ui;

import powergrid.core.GameState;
import powergrid.utils.PowerPlant;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DiscardedPanel extends JPanel implements MouseListener, KeyListener {

    private BufferedImage backgroundImage;

    public DiscardedPanel() {
        try {
            backgroundImage = ImageIO.read(DiscardedPanel.class.getResource("/powergrid/Images/background.png"));
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

        drawDiscardedScreen(g2);
        drawDiscardedPlants(g2);
    }

    private void drawDiscardedScreen(Graphics2D g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }

        Color myYellow = new Color(255, 250, 191);
        g.setColor(myYellow);

        g.fillRect(200, 130, 1500, 790); // Fills the rectangle
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(5));
        g.drawRect(200, 130, 1500, 790); // Draws the border

        g.setColor(myYellow);

        g.setFont(new Font("Serif", Font.BOLD, 58));
        g.drawString("Discarded Power Plants",680,100);

        g.setFont(new Font("Serif", Font.BOLD, 38));
        g.drawString("Press  'r'  to  return",800, 970);
    }

    private void drawDiscardedPlants(Graphics2D g) {
        if (GameState.marketManager.getDeck() == null) return;

        ArrayList<PowerPlant> plants = new ArrayList<>(GameState.marketManager.getDeck().getDiscardedPlants());

        int rows = 5;
        int cols = 10;

        int startX = 250;
        int startY = 180;

        int size = 120;
        int gap = 22;

        for (int i = 0; i < plants.size(); i++) {
            int row = i / cols;
            int col = i % cols;

            if (row >= rows) break;

            int x = startX + col * (size + gap);
            int y = startY + row * (size + gap);

            PowerPlant p = plants.get(i);

            BufferedImage img = p.getImage();

            if (img != null) {
                g.drawImage(img, x, y, size, size, null);
            } else {
                g.setColor(Color.GRAY);
                g.fillRect(x, y, size, size);

                g.setColor(Color.BLACK);
                g.drawRect(x, y, size, size);

                g.drawString("" + p.getPlantNumber(), x + 40, y + 65);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
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
}
