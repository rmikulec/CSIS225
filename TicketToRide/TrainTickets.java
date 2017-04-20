import java.awt.*;
/**
 * This class represents a Train Ticket object within the game Ticket to Ride
 * 
 * @author Chris, Ronald, Luis, Matt, Ryan
 * @version 1.0
 */
public class TrainTickets extends Card
{
    protected Color color;
    protected boolean isLoco;
    
    
    public TrainTickets(CardTypes type, Color colorIn, boolean loco)
    {
        cardType = type;
        color = colorIn;
        isLoco = loco;
    }
}
