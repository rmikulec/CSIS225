import java.util.*;
import java.awt.*;
/**
 * This class represents the player for the game Ticket to Ride
 * The player's "hand" is contained
 * 
 * @author Chris, Ronald, Luis, Matt, Ryan
 * @version 1.0
 */
public class Player
{
    protected int trains;
    protected ArrayList<DestinationTickets> dest;
    protected ArrayList<Stocks> stock;
    protected ArrayList<TrainTickets> trainTix;
    protected ArrayList<DestinationTickets> completedTickets;
    protected Color color;
    //protected int[] pathLengths;
    protected ArrayList<Track> claimedRoutes;

    //boolean start;
    public Player()
    {
        trains = 45;
        dest = new ArrayList<DestinationTickets>();
        trainTix = new ArrayList<TrainTickets>();
        completedTickets = new ArrayList<DestinationTickets>();
        stock = new ArrayList<Stocks>();



    }

    public void addDest(ArrayList<DestinationTickets> cards){

            for(int i=0; i<cards.size(); i++){
                dest.add(cards.get(i));
            }
    }

    /**
     * Setter method for the color variable
     *
     * @param input the color you wish the player's
     * track objects to be on the board
     */
    public void setColor(Color input)
    {
        color = input;
    }

    /**
     * Getter method for the color variable
     *
     * @return the color of the players train pieces
     */
    public Color getColor()
    {
        return color;
    }

    public void addRoute(Track route)
    {
        claimedRoutes.add(route);
    }

    /**
     * Method to calculate the score for the given player
     *
     * @param players an arraylist of the players in game
     * @return the player's calculated score based on their "hand"
     */
    public int calculateScore(int numPlayers, ArrayList<Player> players)
    {
        int totalScore = 0;

        for(int i = 0; i < claimedRoutes.size(); i++)
        {
            if(claimedRoutes.get(i).length == 1)
            {
                totalScore += 1;
            }
            else if(claimedRoutes.get(i).length == 2)
            {
                totalScore += 2;
            }
            else if(claimedRoutes.get(i).length == 3)
            {
                totalScore += 4;
            }
            else if(claimedRoutes.get(i).length == 4)
            {
                totalScore += 7;
            }
            else if(claimedRoutes.get(i).length == 5)
            {
                totalScore += 10;
            }
            else if(claimedRoutes.get(i).length == 6)
            {
                totalScore += 15;
            }
            else if(claimedRoutes.get(i).length == 7)
            {
                totalScore += 18;
            }
        }

        for(int i = 0; i < completedTickets.size(); i++)
        {
            totalScore += completedTickets.get(i).value;
        }

        int mostDestTix = 0;

        //         for(int i = 0; i < players.size(); i++)
        //         {
        //             if(players.get(i).completedTickets.size() > mostDestTix)
        //             {
        //                 mostDestTix = players.get(i).completedTickets.size();
        //             }
        //         }
        //         //globetrotterBonus
        //         if(completedTickets.size() == mostDestTix)
        //         {
        //             totalScore += 15;
        //         }

        for(int i = 0; i < stock.size(); i++)
        {
            if(stock.get(i).cardType.equals(CardTypes.PR))
            {
                totalScore += stock.get(i).scorePR(getStockPlace(this, players, stock.get(i).cardType));
            }
            else if(stock.get(i).cardType.equals(CardTypes.READING))
            {
                totalScore += stock.get(i).scoreReading(getStockPlace(this, players, stock.get(i).cardType));
            }
            else if(stock.get(i).cardType.equals(CardTypes.LEHIGH))
            {
                totalScore += stock.get(i).scoreLehigh(getStockPlace(this, players, stock.get(i).cardType));
            }
            else if(stock.get(i).cardType.equals(CardTypes.JCL))
            {
                totalScore += stock.get(i).scoreJCL(getStockPlace(this, players, stock.get(i).cardType));
            }
            else if(stock.get(i).cardType.equals(CardTypes.ERIE))
            {
                totalScore += stock.get(i).scoreErie(getStockPlace(this, players, stock.get(i).cardType));
            }
            else if(stock.get(i).cardType.equals(CardTypes.BO))
            {
                totalScore += stock.get(i).scoreBO(getStockPlace(this, players, stock.get(i).cardType));
            }
            else if(stock.get(i).cardType.equals(CardTypes.WM))
            {
                totalScore += stock.get(i).scoreWM(getStockPlace(this, players, stock.get(i).cardType));
            }
            else if(stock.get(i).cardType.equals(CardTypes.NYC))
            {
                totalScore += stock.get(i).scoreNYC(getStockPlace(this, players, stock.get(i).cardType));
            }
            else if(stock.get(i).cardType.equals(CardTypes.BRP))
            {
                totalScore += stock.get(i).scoreBRP(getStockPlace(this, players, stock.get(i).cardType));
            }
        }

        return totalScore;
    }

    /**
     * Method to determine where the player ranks in terms of the number of cards of a particular stock
     *
     * @param p the player whose position is in question
     * @param players the arraylist of all players in game
     * @stock the type of stock we are determining the place of
     * @return the place value for the given stock
     */
    public int getStockPlace(Player p, ArrayList<Player> players, CardTypes stock)
    {
        Player currentPlayer = new Player();
        int pCount = 0;
        int place = 0;

        for(int i = 0; i < p.stock.size(); i++)
        {
            if(currentPlayer.stock.get(i).cardType.equals(stock))
            {
                pCount++;
            }
        }
        for(int i = 0; i < players.size(); i++)
        {
            currentPlayer = players.get(i);
            if(currentPlayer.equals(p))
            {
                continue;
            }
            int count = 0;

            for(int j = 0; i < currentPlayer.stock.size(); i++)
            {
                if(currentPlayer.stock.get(i).cardType.equals(stock))
                {
                    count++;
                }
            }
            if(pCount < count)
            {
                place++;
            }
        }
        return place;
    }
    public int[] colorCount()
    {
        int[] ret = new int[9];
        //order: Locomotive, Pink, Blue, Yellow, White, Orange, Black, Green, Red
        for(int i = 0; i < trainTix.size(); i++)
        {
            if(trainTix.get(i).color.equals(""))
            {
                ret[0]++;
            }
            if(trainTix.get(i).color.equals("Pink"))
            {
                ret[1]++;
            }
            if(trainTix.get(i).color.equals("Blue"))
            {
                ret[2]++;
            }
            if(trainTix.get(i).color.equals("Yellow"))
            {
                ret[3]++;
            }
            if(trainTix.get(i).color.equals("White"))
            {
                ret[4]++;
            }
            if(trainTix.get(i).color.equals("Orange"))
            {
                ret[5]++;
            }
            if(trainTix.get(i).color.equals("Black"))
            {
                ret[6]++;
            }
            if(trainTix.get(i).color.equals("Green"))
            {
                ret[7]++;
            }
            if(trainTix.get(i).color.equals("Red"))
            {
                ret[8]++;
            }
        }
        return ret;
    }
}
