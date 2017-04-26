import javax.swing.*;
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
    protected ImageIcon pic;
    
    public Stocks(CardTypes type, int numCardIn, String name, int numWinnersIn)
    {
        cardType = type;
        numCard = numCardIn;
        label = name;
        numWinners = numWinnersIn;
        
        if(cardType.equals(CardTypes.ERIE))
        {
            pic = new ImageIcon(".\\Pics\\Stocks\\Stocks 008");
        }
        if(cardType.equals(CardTypes.READING))
        {
            pic = new ImageIcon(".\\Pics\\Stocks\\Stocks 005");
        }
        if(cardType.equals(CardTypes.LEHIGH))
        {
            pic = new ImageIcon(".\\Pics\\Stocks\\Stocks 002");
        }
        if(cardType.equals(CardTypes.PR))
        {
            pic = new ImageIcon(".\\Pics\\Stocks\\Stocks 003");
        }
        if(cardType.equals(CardTypes.NYC))
        {
            pic = new ImageIcon(".\\Pics\\Stocks\\Stocks 004");
        }
        if(cardType.equals(CardTypes.JCL))
        {
            pic = new ImageIcon(".\\Pics\\Stocks\\Stocks 006");
        }
        if(cardType.equals(CardTypes.BO))
        {
            pic = new ImageIcon(".\\Pics\\Stocks\\Stocks 009");
        }
        if(cardType.equals(CardTypes.BRP))
        {
            pic = new ImageIcon(".\\Pics\\Stocks\\Stocks 007");
        }
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
