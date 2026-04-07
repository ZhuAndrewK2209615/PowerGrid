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
    private BufferedImage mapImage;

    private HashMap<String, Rectangle> regionBoxes;

    public SetupPanel() {
        try {
            backgroundImage = ImageIO.read(SetupPanel.class.getResource("/powergrid/Images/Background.png"));
            mapImage = ImageIO.read(SetupPanel.class.getResource("/powergrid/Images/Germany Map.jpeg"));
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

        startButton = new Rectangle(800, 750, 320, 100);
        finishButton = new Rectangle(1400, 700, 120, 50);

        regionBoxes = new HashMap<>();
        regionBoxes.put("Red", new Rectangle(1470, 320, 50, 50));
        regionBoxes.put("Blue", new Rectangle(1470, 380, 50, 50));
        regionBoxes.put("Yellow", new Rectangle(1470, 440, 50, 50));
        regionBoxes.put("Teal", new Rectangle(1470, 500, 50, 50));
        regionBoxes.put("Brown", new Rectangle(1470, 560, 50, 50));
        regionBoxes.put("Purple", new Rectangle(1470, 620, 50, 50));
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

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 68));
        g.drawString("Power Grid",780, 320);

        g.setFont(new Font("Arial", Font.PLAIN, 38));
        g.drawString("Number of players", 800, 500);

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

        g.setColor(Color.BLACK);
        g.fillRoundRect(startButton.x, startButton.y, startButton.width, startButton.height, 20, 20);

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

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 28));
        g.drawString("Choose " + regionCount + " adjacent regions on the map", 1100, 240);

        String[] regions = {"Red", "Blue", "Yellow", "Teal", "Brown", "Purple"};

        g.setFont(new Font("Arial", Font.PLAIN, 24));

        Stroke boldStroke = new BasicStroke(3);
        Stroke defaultStroke = g.getStroke();

        int y = 348;
        for (String region : regions) {
            g.setColor(Color.BLACK);
            g.setStroke(boldStroke);
            g.drawRect(1230, y - 28, 220, 50);
            
            g.setStroke(defaultStroke);
            g.drawString(region, 1250, y + 5);

            g.setStroke(boldStroke);
            Rectangle box = regionBoxes.get(region);
            g.drawRect(box.x, box.y, box.width, box.height);

            g.setStroke(defaultStroke);
            if (GameState.mapGraph.getMapRegions().contains(region)) {
                g.setFont(new Font("Arial", Font.BOLD, 28));
                g.drawString("✓", box.x + 8, box.y + 30);
                g.setFont(new Font("Arial", Font.PLAIN, 24));
            }

            y += 60;
        }
        g.setStroke(boldStroke);
        g.setColor(new Color(60, 220, 120));
        g.fillRoundRect(finishButton.x, finishButton.y, finishButton.width, finishButton.height, 20, 20);
        
        g.setStroke(defaultStroke);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Finish", finishButton.x + 20, finishButton.y + 32);

        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.drawString("Selected: " + GameState.mapGraph.getMapRegions().size() + " / " + regionCount, 1230, 730);
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
