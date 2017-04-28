import javafx.scene.image.Image;
/**
 * This class represents a Train Ticket object within the game Ticket to Ride
 * 
 * @author Chris, Ronald, Luis, Matt, Ryan
 * @version 1.0
 */
public class TrainTickets extends Card
{
    protected String color;
    protected boolean isLoco;
    protected Image pic;
    
    public TrainTickets(CardTypes type, String colorIn, boolean loco, Image picIn)
    {
        cardType = type;
        color = colorIn;
        isLoco = loco;
        pic = picIn;
    }
}
