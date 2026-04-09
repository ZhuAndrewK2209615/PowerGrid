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
import powergrid.core.Player;
import powergrid.utils.ResourceType;

public class marketPanel extends JPanel implements MouseInputListener{

    private BufferedImage background, GermanMap, market, playerPlant, coal, garbage, oil, uranium, rulesSymbol, currentAuction, rightArrow, leftArrow;
    private PlayerMenu playerMenu;
    private MapUI mapUI;
    private MarketUI marketUI;

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
        playerMenu = new PlayerMenu();
        mapUI = new MapUI(this);
        marketUI = new MarketUI(this);
        addMouseListener(this); 
        addMouseListener(playerMenu);
    }

    public void paintComponent(Graphics g){  // do all background color changes here, do not touch the pain method
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);

        g.setColor(new Color(255,250,191));
        g.fillRect(920,530, 470, 200);  // coal
        g.fillRect(920+490, 530, 460, 200);  // oil
        g.fillRect(920, 740, 470, 200);  // garbage
        g.fillRect(920+490, 740, 460, 200); // uranium

        
        if(GameState.resourceMarket.canBuy(ResourceType.COAL, GameState.activePlayer)){
             g.setColor(Color.green);
            g.fillRect(1120, 600, 240, 100);
        }else g.fillRect(1120,600,240,100);
        
        

        if(GameState.resourceMarket.getSupply(ResourceType.GARBAGE) > 0){
            if(GameState.resourceMarket.canBuy(ResourceType.GARBAGE, GameState.activePlayer)){
                g.setColor(Color.green);
                g.fillRect(1120, 800, 240, 100);
            }
        }else g.fillRect(1120,800,240,100);


        if(GameState.resourceMarket.getSupply(ResourceType.OIL) > 0){
            if(GameState.resourceMarket.canBuy(ResourceType.OIL, GameState.activePlayer)){
                g.setColor(Color.green);
                g.fillRect(1608, 600, 240, 100);
            }
        }else g.fillRect(1608,600,240,100);
        
        if(GameState.resourceMarket.getSupply(ResourceType.URANIUM) > 0){
            if(GameState.resourceMarket.canBuy(ResourceType.URANIUM, GameState.activePlayer)){
                g.setColor(Color.green);
                g.fillRect(1608, 800, 240, 100);
            }
        }else g.fillRect(1608,800,240,100);

        // got to remove this and replace it the code on top
        /*g.fillRect(1120,600,240,100);  // button to buy resource (changes color we need to code)
        g.fillRect(1120,800,240,100);  // button to buy resource (changes color we need to code)
        g.fillRect(1608,600,240,100);  // button to buy resource (changes color we need to code)
        g.fillRect(1608,800,240,100);  // button to buy resource (changes color we need to code)

        */
        
    }

    public void paint(Graphics g){
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;
        playerMenu.drawMenu(g);
        mapUI.drawMap(g);
        marketUI.drawMarket(g);
          

        g2d.setStroke(new BasicStroke(5));
        g.setColor(Color.BLACK);

        g.drawRect(920,530, 470, 200);  // coal
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Coal", 950, 580);
        g.drawImage(coal, 950, 600, 100, 100, null);
        g2d.setFont(new Font("Arial", Font.PLAIN, 35));
        g.drawString("Current stock: " + GameState.resourceMarket.getSupply(ResourceType.COAL), 1110, 580);  // we have to change stock
        
        g.drawRect(1120,600,240,100);  // button to buy resource (changes color we need to code)
        g2d.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Buy 1 for $" + GameState.resourceMarket.getPrice(ResourceType.COAL), 1165, 660); 


        g.drawRect(920+490, 530, 460, 200);  // oil
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Oil", 1450, 580);
        g.drawImage(oil, 1450, 600, 100, 100, null);
        g2d.setFont(new Font("Arial", Font.PLAIN, 35));
        g.drawString("Current stock: " + GameState.resourceMarket.getSupply(ResourceType.OIL), 1600, 580); // we have to change stock
        g.drawRect(1120,800,240,100);  // button to buy resource (changes color we need to code)
        g2d.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Buy 1 for $" + GameState.resourceMarket.getPrice(ResourceType.OIL), 1165, 860);
        

        g.drawRect(920, 740, 470, 200);  // garbage
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Garbage", 925, 780);
        g.drawImage(garbage, 950, 800, 100, 100, null);
        g2d.setFont(new Font("Arial", Font.PLAIN, 35));
        g.drawString("Current stock: " + GameState.resourceMarket.getSupply(ResourceType.GARBAGE), 1110, 780); // we have to change stock
        g.drawRect(1608,600,240,100);  // button to buy resource (changes color we need to code)
        g2d.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Buy 1 for $" + GameState.resourceMarket.getPrice(ResourceType.GARBAGE), 1650, 660);

        g.drawRect(920+490, 740, 460, 200); // uranium
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Uranium", 1420, 780);
        g.drawImage(uranium, 1410, 770, 150, 150, null);
        g2d.setFont(new Font("Arial", Font.PLAIN, 35));
        g.drawString("Current stock: " + GameState.resourceMarket.getSupply(ResourceType.URANIUM), 1600, 780); // we have to change stock
        g.drawRect(1608,800,240,100);  // button to buy resource (changes color we need to code)
        g2d.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Buy 1 for $" + GameState.resourceMarket.getPrice(ResourceType.URANIUM), 1650, 860);

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
        // buying phase
        int x = e.getX();
        int y = e.getY();

       if((x>= 1120 && x<= 1360) && (y>=600 && y<=700)){
        if(!GameState.resourceMarket.canBuy(ResourceType.COAL, GameState.activePlayer)){
            System.out.println("Cannot buy coal, you either don't have enough elecktro or the supply is low");
        } else GameState.activePlayer.buyResource(ResourceType.COAL, 1);
       }
       else if((x>= 1120 && x<= 1360) && (y>=800 && y<=900)){
            if(!GameState.resourceMarket.canBuy(ResourceType.GARBAGE, GameState.activePlayer)){
                System.out.println("Cannot buy garbage, you either don't have enough elecktro or the supply is low");
            } else GameState.activePlayer.buyResource(ResourceType.GARBAGE, 1);
        } 
        else if((x>= 1604 && x<= 1848) && (y>=600 && y<=700)){
            if(!GameState.resourceMarket.canBuy(ResourceType.OIL, GameState.activePlayer)){
                System.out.println("Cannot buy oil, you either don't have enough resources or the supply is low");
            } else GameState.activePlayer.buyResource(ResourceType.OIL, 1);
        } 
        else if((x>= 1604 && x<= 1848) && (y>=800 && y<=900)){
            if(!GameState.resourceMarket.canBuy(ResourceType.URANIUM, GameState.activePlayer)){
                System.out.println("Cannot buy uranium, you either don't have enough resources or the supply is low");
            } else GameState.activePlayer.buyResource(ResourceType.URANIUM, 1);
        }

        if((x>=920+360 && x <= 920+360+200) && (y>=960 && y<= 960+70)){
            Player next = GameState.roundManager.getNextPlayer();
            if (next != null)
            {
                GameState.activePlayer = next;
                playerMenu.viewedPlayer = GameState.activePlayer;
            }
            else
            {
                //load next panel
            }
        }
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

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
    
}
