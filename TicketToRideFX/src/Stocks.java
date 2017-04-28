
/**
 * This class represents a Stock Card object within the game Ticket to Ride
 * 
 * @author Chris, Ronald, Luis, Matt, Ryan
 * @version 1.0
 */
public class Stocks extends Card
{
    protected int numCard;
    protected String label;
    protected int numWinners;
    
    public Stocks(CardTypes type, int numCardIn, String name, int numWinnersIn)
    {
        cardType = type;
        numCard = numCardIn;
        label = name;
        numWinners = numWinnersIn;
    }
    
    public int scoreBO(int place)
    {
        if(place == 1)
        {
            return 20;
        }
        if(place == 2)
        {
            return 14;
        }
        if(place == 3)
        {
            return 9;
        }
        if(place == 4)
        {
            return 5;
        }
        if(place == 5)
        {
            return 2;
        }
        return 0;
    }
    
    public int scoreWM(int place)
    {
        if(place == 1)
        {
            return 9;
        }
        if(place == 2)
        {
            return 5;
        }
        return 0;
    }
    
    public int scoreLehigh(int place)
    {
        if(place == 1)
        {
            return 12;
        }
        if(place == 2)
        {
            return 7;
        }
        if(place == 3)
        {
            return 3;
        }
        return 0;
    }
    
    public int scorePR(int place)
    {
        if(place == 1)
        {
            return 30;
        }
        if(place == 2)
        {
            return 21;
        }
        if(place == 3)
        {
            return 14;
        }
        if(place == 4)
        {
            return 9;
        }
        if(place == 5)
        {
            return 6;
        }
        return 0;
    }
    
    public int scoreNYC(int place)
    {
        if(place == 1)
        {
            return 10;
        }
        if(place == 2)
        {
            return 6;
        }
        if(place == 3)
        {
            return 3;
        }
        return 0;
    }
    
    public int scoreReading(int place)
    {
        if(place == 1)
        {
            return 14;
        }
        if(place == 2)
        {
            return 9;
        }
        if(place == 3)
        {
            return 5;
        }
        return 0;
    }
    
    public int scoreJCL(int place)
    {
        if(place == 1)
        {
            return 8;
        }
        if(place == 2)
        {
            return 5;
        }
        return 0;
    }
    
    public int scoreBRP(int place)
    {
        if(place == 1)
        {
            return 7;
        }
        if(place == 2)
        {
            return 4;
        }
        return 0;
    }
    
    public int scoreErie(int place)
    {
        if(place == 1)
        {
            return 16;
        }
        if(place == 2)
        {
            return 10;
        }
        if(place == 3)
        {
            return 5;
        }
        if(place == 4)
        {
            return 1;
        }
        return 0;
    }
}
