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
import powergrid.utils.ResourceType;
import powergrid.PowerGridFrame;
import powergrid.core.*;

public class marketPanel extends JPanel implements MouseInputListener{

    private PlayerMenu playerMenu;
    private MapUI mapUI;
    private MarketUI marketUI;
    private PowerGridFrame parent;

    public marketPanel(PowerGridFrame parent){
        this.parent = parent;
        playerMenu = new PlayerMenu();
        mapUI = new MapUI(this);
        marketUI = new MarketUI(this);
        addMouseListener(this); 
        addMouseListener(playerMenu);
    }

    public void paintComponent(Graphics g){  // do all background color changes here, do not touch the pain method
        super.paintComponent(g);
        g.drawImage(ImageLibrary.background, 0, 0, getWidth(), getHeight(), null);

        g.setColor(new Color(255,250,191));
        g.fillRect(920,530, 470, 200);  // coal
        g.fillRect(920+490, 530, 460, 200);  // oil
        g.fillRect(920, 740, 470, 200);  // garbage
        g.fillRect(920+490, 740, 460, 200); // uranium

        
        if(GameState.resourceMarket.canBuy(ResourceType.COAL, GameState.activePlayer) && !playerMenu.isBuying){
             g.setColor(Color.green);
            g.fillRect(1120, 600, 240, 100);
        }else g.fillRect(1120,600,240,100);
        
        

        if(GameState.resourceMarket.getSupply(ResourceType.GARBAGE) > 0){
            if(GameState.resourceMarket.canBuy(ResourceType.GARBAGE, GameState.activePlayer) && !playerMenu.isBuying){
                g.setColor(Color.green);
                g.fillRect(1120, 800, 240, 100);
            }
        }else g.fillRect(1120,800,240,100);


        if(GameState.resourceMarket.getSupply(ResourceType.OIL) > 0){
            if(GameState.resourceMarket.canBuy(ResourceType.OIL, GameState.activePlayer) && !playerMenu.isBuying){
                g.setColor(Color.green);
                g.fillRect(1608, 600, 240, 100);
            }
        }else g.fillRect(1608,600,240,100);
        
        if(GameState.resourceMarket.getSupply(ResourceType.URANIUM) > 0){
            if(GameState.resourceMarket.canBuy(ResourceType.URANIUM, GameState.activePlayer) && !playerMenu.isBuying){
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
        g.drawImage(ImageLibrary.coal, 950, 600, 100, 100, null);
        g2d.setFont(new Font("Arial", Font.PLAIN, 35));
        g.drawString("Current stock: " + GameState.resourceMarket.getSupply(ResourceType.COAL), 1110, 580);  // we have to change stock
        
        g.drawRect(1120,600,240,100);  // button to buy resource (changes color we need to code)
        g2d.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Buy 1 for $" + GameState.resourceMarket.getPrice(ResourceType.COAL), 1165, 660); 


        g.drawRect(920+490, 530, 460, 200);  // oil
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Oil", 1450, 580);
        g.drawImage(ImageLibrary.oil, 1450, 600, 100, 100, null);
        g2d.setFont(new Font("Arial", Font.PLAIN, 35));
        g.drawString("Current stock: " + GameState.resourceMarket.getSupply(ResourceType.OIL), 1600, 580); // we have to change stock
        g.drawRect(1120,800,240,100);  // button to buy resource (changes color we need to code)
        g2d.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Buy 1 for $" + GameState.resourceMarket.getPrice(ResourceType.GARBAGE), 1165, 860);
        

        g.drawRect(920, 740, 470, 200);  // garbage
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Garbage", 925, 780);
        g.drawImage(ImageLibrary.garbage, 950, 800, 100, 100, null);
        g2d.setFont(new Font("Arial", Font.PLAIN, 35));
        g.drawString("Current stock: " + GameState.resourceMarket.getSupply(ResourceType.GARBAGE), 1110, 780); // we have to change stock
        g.drawRect(1608,600,240,100);  // button to buy resource (changes color we need to code)
        g2d.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Buy 1 for $" + GameState.resourceMarket.getPrice(ResourceType.OIL), 1650, 660);

        g.drawRect(920+490, 740, 460, 200); // uranium
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Uranium", 1420, 780);
        g.drawImage(ImageLibrary.uranium, 1410, 770, 150, 150, null);
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

       if((x>= 1120 && x<= 1360) && (y>=600 && y<=700) && !playerMenu.isBuying){
        if(GameState.resourceMarket.canBuy(ResourceType.COAL, GameState.activePlayer)){
            buyResource(ResourceType.COAL);
        }
       }
       else if((x>= 1120 && x<= 1360) && (y>=800 && y<=900) && !playerMenu.isBuying){
            if(GameState.resourceMarket.canBuy(ResourceType.GARBAGE, GameState.activePlayer)){
                buyResource(ResourceType.GARBAGE);
            }
        } 
        else if((x>= 1604 && x<= 1848) && (y>=600 && y<=700) && !playerMenu.isBuying){
            if(GameState.resourceMarket.canBuy(ResourceType.OIL, GameState.activePlayer)){
                buyResource(ResourceType.OIL);
            }
        } 
        else if((x>= 1604 && x<= 1848) && (y>=800 && y<=900) && !playerMenu.isBuying){
            if(GameState.resourceMarket.canBuy(ResourceType.URANIUM, GameState.activePlayer)){
                buyResource(ResourceType.URANIUM);
            }
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
                GameState.activePlayer = GameState.roundManager.getPlayerOrder().get(GameState.players.size() - 1);
                GameState.roundManager.advancePhase();
                setVisible(false);
                parent.add(new MapPanel(parent));
                parent.repaint();
                parent.remove(this);
            }
        }
        repaint();
    }

    public void buyResource(ResourceType r)
    {
        GameState.activePlayer.spendElektro(GameState.resourceMarket.getPrice(r));
        GameState.resourceMarket.buyResource(r);
        playerMenu.isBuying = true;
        playerMenu.addResource(r);
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
