package powergrid.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import powergrid.core.ImageLibrary;

public class DiscardPanel extends JPanel implements MouseInputListener {

    public void paintComponent(Graphics g){  // draw all images here
        super.paintComponent(g);
        g.drawImage(ImageLibrary.background, 0, 0,getWidth(),getHeight(), null);

        //g2d.setStroke(new BasicStroke(4));
        //g2d.drawRect(400, 100, 300, 200);
    }
    
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(7));
        g2d.drawRect(600, 75, 800, 100);
        g2d.setColor(new Color(216, 213, 213));
        g2d.fillRect(600, 75, 800, 100);
        g2d.setFont(new Font("Arial", Font.BOLD, 23));
        g2d.drawString("Player x has received Power Plant xx", 675, 125);
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
