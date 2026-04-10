package powergrid.core;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ImageLibrary {
    
    public static BufferedImage background, mapImage, marketImage, turnOrderCard, coal, oil, garbage, uranium, electricity, leftArrow, rightArrow, mapSymbol, questionMark;

    public static void loadImages()
    {
        try
        {
            background = ImageIO.read(ImageLibrary.class.getResource("/powergrid/Images/background.png"));
            mapImage = ImageIO.read(ImageLibrary.class.getResource("/powergrid/Images/Germany Map.jpeg"));
            marketImage = ImageIO.read(ImageLibrary.class.getResource("/powergrid/Images/market.png"));
            turnOrderCard = ImageIO.read(ImageLibrary.class.getResource("/powergrid/Images/TurnOrderCard.png"));
            coal = ImageIO.read(ImageLibrary.class.getResource("/powergrid/Images/coal.png"));
            oil = ImageIO.read(ImageLibrary.class.getResource("/powergrid/Images/Oil.png"));
            garbage = ImageIO.read(ImageLibrary.class.getResource("/powergrid/Images/Garbage.png"));
            uranium = ImageIO.read(ImageLibrary.class.getResource("/powergrid/Images/Uranium.png"));
            electricity = ImageIO.read(ImageLibrary.class.getResource("/powergrid/Images/energy.png"));
            leftArrow = ImageIO.read(ImageLibrary.class.getResource("/powergrid/Images/left-arrow.png"));
            rightArrow = ImageIO.read(ImageLibrary.class.getResource("/powergrid/Images/right-arrow.png"));
            mapSymbol = ImageIO.read(ImageLibrary.class.getResource("/powergrid/Images/MapSymbol.png"));
            questionMark = ImageIO.read(ImageLibrary.class.getResource("/powergrid/Images/QuestionSymbol.png"));
        }
        catch (Exception e)
        {
            System.out.println("Failed to load an image");
        }
    }
}
