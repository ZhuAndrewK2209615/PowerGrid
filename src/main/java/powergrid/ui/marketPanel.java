package powergrid.ui;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class marketPanel extends JPanel implements MouseInputListener{

    private BufferedImage GermanMap, playerPlant, coal, garbage, oil, uranium;

    public marketPanel(){
        try {
            GermanMap = ImageIO.read(getClass().getResource("Images/Germany Map.jpeg"));
            // code for playerPlant on display
            // code for coal 
            // code for garbage 
            // code for oil
            // code for uranium

        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("Error");
            return;
        }
        addMouseListener(this); 
    }

    public void paint(Graphics g){
        super.paint(g);
    }
    
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseClicked'");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseDragged'");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseMoved'");
    }
    
}
