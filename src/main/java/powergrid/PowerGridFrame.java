package powergrid;
import java.io.IOException;
import javax.swing.JFrame;
import powergrid.ui.*;

public class PowerGridFrame extends JFrame{
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;

    public PowerGridFrame(String name) throws IOException{
        super(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        add(new EndPanel(this));
        setVisible(true);
    }
}