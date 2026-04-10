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

public class SetupPanel extends JPanel implements MouseListener, KeyListener {
    private boolean isSetupComplete;
    private String selectedMap;
    private int playerCount;
    private int regionCount;

    private boolean choosingPlayers; // first screen
    private boolean choosingRegions; // second screen

    private HashMap<Integer, Rectangle> playerButtons;
    private Rectangle startButton;
    private Rectangle finishButton;

    private BufferedImage backgroundImage;
    private BufferedImage dec1Image;
    private BufferedImage dec2Image;
    private BufferedImage dec3HouseImage;
    private BufferedImage mapImage;
    private BufferedImage checkMarkImage;

    private HashMap<String, Rectangle> regionBoxes;

    public SetupPanel() {
        try {
            backgroundImage = ImageIO.read(SetupPanel.class.getResource("/powergrid/Images/Background.png"));
            dec1Image = ImageIO.read(SetupPanel.class.getResource("/powergrid/Images/Setup Dec1.png"));
            dec2Image = ImageIO.read(SetupPanel.class.getResource("/powergrid/Images/Setup Dec2.png"));
            dec3HouseImage = ImageIO.read(SetupPanel.class.getResource("/powergrid/Images/Setup House.png"));
            mapImage = ImageIO.read(SetupPanel.class.getResource("/powergrid/Images/Germany Map.jpeg"));
            checkMarkImage = ImageIO.read(SetupPanel.class.getResource("/powergrid/Images/Check Mark.png"));
        } catch (Exception e) {
            System.out.println("Background image failed to load");
        }

        setFocusable(true);
        addMouseListener(this);
        addKeyListener(this);

        isSetupComplete = false;
        selectedMap = "Germany";
        playerCount = 0;
        regionCount = 0;

        choosingPlayers = true;
        choosingRegions = false;

        playerButtons = new HashMap<>();
        playerButtons.put(3, new Rectangle(600, 600, 80, 80));
        playerButtons.put(4, new Rectangle(813, 600, 80, 80));
        playerButtons.put(5, new Rectangle(1027, 600, 80, 80));
        playerButtons.put(6, new Rectangle(1240, 600, 80, 80));

        startButton = new Rectangle(810, 800, 300, 100);
        finishButton = new Rectangle(1400, 700, 120, 50);

        regionBoxes = new HashMap<>();
        regionBoxes.put("Red", new Rectangle(1540, 320, 50, 50));
        regionBoxes.put("Blue", new Rectangle(1540, 410, 50, 50));
        regionBoxes.put("Yellow", new Rectangle(1540, 500, 50, 50));
        regionBoxes.put("Teal", new Rectangle(1540, 590, 50, 50));
        regionBoxes.put("Brown", new Rectangle(1540, 680, 50, 50));
        regionBoxes.put("Purple", new Rectangle(1540, 770, 50, 50));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;

        if (choosingPlayers) {
            drawPlayerSelectionScreen(g2);
        } else if (choosingRegions) {
            drawRegionSelectionScreen(g2);
        }
    }

    private void drawPlayerSelectionScreen(Graphics2D g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }
        /*
        if (dec1Image != null){
            g.drawImage(dec1Image, -170, 620, 750, 450, null);
        }
        */
        if (dec2Image != null){
            g.drawImage(dec2Image, 1200, 600, 750, 450, null);
        }
        if (dec3HouseImage != null){
            g.drawImage(dec3HouseImage, 1200, -30, 780, 450, null);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 98));
        g.drawString("Power Grid",705, 200);

        g.setFont(new Font("Arial", Font.PLAIN, 38));
        g.drawString("Number of players", 800, 500);

        /*
        for (int count : playerButtons.keySet()) {
            Rectangle r = playerButtons.get(count);

            if (playerCount == count) {
                g.setColor(new Color(60, 220, 120));
            } else {
                g.setColor(Color.BLACK);
            }
            g.fillRoundRect(r.x, r.y, r.width, r.height, 20, 20);

            g.setColor(Color.WHITE);
            int numberFontSize = r.height / 2;
            g.setFont(new Font("Arial", Font.BOLD, numberFontSize));
            String buttonText = String.valueOf(count);
            FontMetrics fm = g.getFontMetrics();
            int textX = r.x + (r.width - fm.stringWidth(buttonText)) / 2;
            int textY = r.y + (r.height - fm.getHeight()) / 2 + fm.getAscent();
            g.drawString(buttonText, textX, textY);
        }
        */

        for (int count : playerButtons.keySet()) {
            Rectangle r = playerButtons.get(count);

            // 1. Create a copy of the graphics context
            Graphics2D g2d = (Graphics2D) g.create();
    
            // 2. Enable Anti-aliasing for smooth edges
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 3. Rotate 45 degrees around the center of the rectangle
            double centerX = r.x + r.width / 2.0;
            double centerY = r.y + r.height / 2.0;
            g2d.rotate(Math.toRadians(45), centerX, centerY);

            // 4. Draw the Rounded Rectangle
            if (playerCount == count) {
                g2d.setColor(new Color(60, 220, 120));
            } else {
                g2d.setColor(Color.BLACK);
            }
            g2d.fillRoundRect(r.x, r.y, r.width, r.height, 20, 20);

            // 5. Draw the Text (will also be rotated)
            g2d.rotate(Math.toRadians(-45), centerX, centerY);
            g2d.setColor(Color.WHITE);
            int numberFontSize = r.height / 2;
            g2d.setFont(new Font("Arial", Font.BOLD, numberFontSize));
            String buttonText = String.valueOf(count);
            FontMetrics fm = g2d.getFontMetrics();
            int textX = r.x + (r.width - fm.stringWidth(buttonText)) / 2;
            int textY = r.y + (r.height - fm.getHeight()) / 2 + fm.getAscent();
            g2d.drawString(buttonText, textX, textY);

            // 6. Dispose the copy to reset the state for the next button
            g2d.dispose();
        }

        g.setColor(Color.BLACK);
        g.fillRoundRect(startButton.x, startButton.y, startButton.width, startButton.height, 80, 80);

        g.setColor(Color.WHITE);
        int startFontSize = startButton.height / 2;
        g.setFont(new Font("Arial", Font.BOLD, startFontSize));
        String startText = "Start";
        FontMetrics fm = g.getFontMetrics();
        int textX = startButton.x + (startButton.width - fm.stringWidth(startText)) / 2;
        int textY = startButton.y + (startButton.height - fm.getHeight()) / 2 + fm.getAscent();
        g.drawString(startText, textX, textY);

        if (playerCount == 3){
            regionCount = 3;
        } else if (playerCount == 4){
            regionCount = 4;
        } else {
            regionCount = 5;
        }
    }

    private void drawRegionSelectionScreen(Graphics2D g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }
        if (mapImage != null) {
            g.drawImage(mapImage, 0, 0, getWidth()/2-148, getHeight(), null);
        }
    
        // 1. Set the color for the inside of the box
        Color myYellow = new Color(255, 250, 191);
        g.setColor(myYellow); 
        g.fillRect(805, 0, 1095, 200); // Fills the rectangle

        // 2. Set the color for the border
        g.setColor(Color.BLACK);

        // 3. Set the thickness of the border (optional, but makes it "bold")
        g.setStroke(new BasicStroke(5)); 

        // 4. Draw the outline
        g.drawRect(805, 0, 1095, 200); // Draws the border

        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif", Font.BOLD, 48));
        g.drawString("Choose " + regionCount + " adjacent regions on", 1050, 80);
        g.drawString("the map for playing the game", 1050, 150);

        String[] regions = {"Red", "Blue", "Yellow", "Teal", "Brown", "Purple"};

        g.setFont(new Font("Serif", Font.PLAIN, 24));

        Stroke boldStroke = new BasicStroke(3);
        Stroke defaultStroke = g.getStroke();

        int y = 348;
        for (String region : regions) {
            Color myBlue = new Color(222, 238, 242);
            g.setColor(myBlue);
            g.fillRect(1100, y - 28, 430, 90);
            Rectangle box = regionBoxes.get(region);
            g.fillRect(box.x-10, box.y, (box.width+10)/2*3, (box.height+10)/2*3);
            
            g.setColor(Color.BLACK);
            g.setStroke(boldStroke);
            g.drawRect(1100, y - 28, 430, 90);

            g.setStroke(defaultStroke);
            if (region == "Yellow" || region == "Brown" || region == "Purple"){
                g.drawString(region, 1290, y+15);
            }else{
                g.drawString(region, 1300, y+15);
            }

            g.setStroke(boldStroke);
            g.drawRect(box.x-10, box.y, (box.width+10)/2*3, (box.height+10)/2*3);

            g.setStroke(defaultStroke);
            if (GameState.mapGraph.getMapRegions().contains(region)) {
                g.setFont(new Font("Arial", Font.BOLD, 28));
                // g.drawString("✔", box.x + 8, box.y + 30);
                if (checkMarkImage != null) {
                    g.drawImage(checkMarkImage, box.x-50, box.y-25, 200, 125, null);
                }
                g.setFont(new Font("Arial", Font.PLAIN, 24));
            }

            y += 90;
        }
        g.setStroke(boldStroke);
        g.setColor(new Color(60, 220, 120));
        g.fillRoundRect(finishButton.x+100, finishButton.y + 200, finishButton.width, finishButton.height, 50, 50);
        
        g.setStroke(defaultStroke);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Finish", finishButton.x + 125, finishButton.y + 232);

        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.drawString("Selected: " + GameState.mapGraph.getMapRegions().size() + " / " + regionCount, 1100, 930);
        g.drawString("(click to include)", 1260, 300);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        requestFocusInWindow();

        int x = e.getX();
        int y = e.getY();

        if (choosingPlayers) {
            handlePlayerScreenClick(x, y);
        } else if (choosingRegions) {
            handleRegionScreenClick(x, y);
        }

        repaint();
    }

    private void handlePlayerScreenClick(int x, int y) {
        for (int count : playerButtons.keySet()) {
            if (playerButtons.get(count).contains(x, y)) {
                playerCount = count;
                return;
            }
        }

        if (startButton.contains(x, y) && playerCount >= 3 && playerCount <= 6) {
            GameState.initializePlayers(playerCount);
            choosingPlayers = false;
            choosingRegions = true;
        }
    }

    private void handleRegionScreenClick(int x, int y) {
        for (String region : regionBoxes.keySet()) {
            Rectangle box = regionBoxes.get(region);

            if (box.contains(x, y)) {
                if (GameState.mapGraph.getMapRegions().contains(region)) {
                    GameState.mapGraph.removeRegion(region);
                } else {
                    if (GameState.mapGraph.getMapRegions().size() < regionCount) {
                        boolean added = GameState.mapGraph.addRegion(region);
                        if (!added) {
                            JOptionPane.showMessageDialog(this,
                                    "That region is not adjacent to the current selection.",
                                    "Invalid Region Choice",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
                return;
            }
        }

        if (finishButton.contains(x, y)) {
            if (GameState.mapGraph.getMapRegions().size() == regionCount) {
                GameState.mapGraph.setRegions();
                isSetupComplete = true;

                JOptionPane.showMessageDialog(this,
                        "Setup complete. The game will now continue.",
                        "Setup Finished",
                        JOptionPane.INFORMATION_MESSAGE);

                // later you can switch to the main game panel here
                // PowerGridFrame.switchPanel("game");
            } else {
                JOptionPane.showMessageDialog(this,
                        "You must choose exactly " + regionCount + " regions.",
                        "Setup Incomplete",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (choosingPlayers && playerCount >= 3 && playerCount <= 6) {
                GameState.initializePlayers(playerCount);
                choosingPlayers = false;
                choosingRegions = true;
                repaint();
            } else if (choosingRegions && GameState.mapGraph.getMapRegions().size() == 4) {
                GameState.mapGraph.setRegions();
                isSetupComplete = true;
                repaint();
            }
        }
    }

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
}
