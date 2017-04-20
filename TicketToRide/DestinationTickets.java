
/**
 * This class represents a Destionation Ticket object within the game Ticket to Ride
 * 
 * @author Chris, Ronald, Luis, Matt, Ryan
 * @version 1.0
 */
public class DestinationTickets extends Card
{
    protected City loc1;
    protected City loc2;
    protected boolean isComplete;
    protected int value;

    public DestinationTickets(CardTypes type, City loc1in, City loc2in, boolean complete, int valueIn)
    {
        loc1 = loc1in;
        loc2 = loc2in;
        cardType = type;
        value = valueIn;
    }
}
