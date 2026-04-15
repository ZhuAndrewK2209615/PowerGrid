package powergrid.core;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ImageLibrary {
    
    public static BufferedImage background, background2, mapImage, marketImage, turnOrderCard, paymentCard, restockCard, coal, oil, garbage, uranium, electricity, leftArrow, rightArrow, upArrow, downArrow, x, mapSymbol, questionMark;

    public static void loadImages()
    {
        try
        {
            background = ImageIO.read(ImageLibrary.class.getResource("/powergrid/Images/background.png"));
            background2 = ImageIO.read(ImageLibrary.class.getResource("/powergrid/Images/Background2.png"));
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
            upArrow = ImageIO.read(ImageLibrary.class.getResource("/powergrid/Images/UpArrow.png"));
            downArrow = ImageIO.read(ImageLibrary.class.getResource("/powergrid/Images/DownArrow.png"));
            x = ImageIO.read(ImageLibrary.class.getResource("/powergrid/Images/X.png"));
            mapSymbol = ImageIO.read(ImageLibrary.class.getResource("/powergrid/Images/MapSymbol.png"));
            questionMark = ImageIO.read(ImageLibrary.class.getResource("/powergrid/Images/QuestionSymbol.png"));
            paymentCard = ImageIO.read(ImageLibrary.class.getResource("/powergrid/Images/PaymentCard.png"));
            restockCard = ImageIO.read(ImageLibrary.class.getResource("/powergrid/Images/RestockCard.png"));
        }
        catch (Exception e)
        {
            System.out.println("Failed to load an image");
        }
    }
}
