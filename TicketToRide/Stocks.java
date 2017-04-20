
/**
 * This class represents a Stock Card object within the game Ticket to Ride
 * 
 * @author Chris, Ronald, Luis, Matt, Ryan
 * @version 1.0
 */
public class Stocks extends Card
{
    protected int numCards;
    protected String label;
    protected int numWinners;
    
    public Stocks(CardTypes type, int numCardsIn, String name, int numWinnersIn)
    {
        cardType = type;
        numCards = numCardsIn;
        label = name;
        numWinners = numWinnersIn;
    }
}
