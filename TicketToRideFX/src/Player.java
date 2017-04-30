import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
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
    protected ArrayList<City> playerCities = new ArrayList<City>();
    protected ArrayList<Track> claimedRoutes;
    protected int[][] adjMatrix;
    protected City[] allCities;

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

    public int getNumStock(CardTypes type)
    {
        int ret = 0;
        for(int i = 0; i < stock.size(); i++)
        {
            if(stock.get(i).cardType.equals(type))
            {
                ret++;
            }
        }
        return ret;
    }

    public boolean destTicketComplete(DestinationTickets d)
    {
        allCities = new City[35];
        allCities[0] = new City("Ontario");
        allCities[1] = new City("Buffalo");
        allCities[2] = new City("Rochester");
        allCities[3] = new City("Syracuse");
        allCities[4] =  new City("Albany");
        allCities[5] =  new City("Erie");
        allCities[6] =  new City("Warren");
        allCities[7] = new City("Coudersport");
        allCities[8] = new City("Elmira");
        allCities[9] = new City("Binghamton");
        allCities[10] = new City("Towanda");
        allCities[11] = new City("Youngstown");
        allCities[12] = new City("OilCity");
        allCities[13] = new City("Dubois");
        allCities[14] = new City("Williamsport");
        allCities[15] = new City("ScrantonWilkesBarre");
        allCities[16] = new City("York");
        allCities[17] = new City("Pittsburg");
        allCities[18] = new City("Altoona");
        allCities[19] = new City("Lewiston");
        allCities[20] = new City("Harrisburg");
        allCities[21] = new City("Reading");
        allCities[22] = new City("Allentown");
        allCities[23] =  new City("Stroudsburg");
        allCities[24] = new City("Wheeling");
        allCities[25] = new City("Morgantown");
        allCities[26] = new City("Cumberland");
        allCities[27] = new City("Chambersburg");
        allCities[28] = new City("Gettysburg");
        allCities[29] = new City("NewYork");
        allCities[30] = new City("Lancaster");
        allCities[31] = new City("Baltimore");
        allCities[32] = new City("Philadelphia");
        allCities[33] = new City("AtlanticCity");
        allCities[34] = new City("Johnstown");

        //The adjacency matrix created here represents all of the connected
        //cities on the board. The indicies, 0..35, are in the same order as
        //the allCities array. If two cities are connected on the board, the
        //2-d array here at index city1, city2 will contain a 1. Otherwise,
        //that index will contain a 0 to signify they are not connected.
        int l;
        int m;
        adjMatrix = new int[35][35];
        Scanner s = null;
        try {
            s = new Scanner(new File("./src/AdjacencyMatrix.txt"));
            for (l = 0; l < 35; l++) {
                for (m = 0; m < 35; m++)
                {
                    adjMatrix[l][m] = s.nextInt();
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.err.print("File not found");
            System.exit(1);
        }
        s.close();


        for(int i = 0; i < claimedRoutes.size(); i++)
        {
            if(!playerCities.contains(claimedRoutes.get(i).city1))
            {
                playerCities.add(claimedRoutes.get(i).city1);
            }
            if(!playerCities.contains(claimedRoutes.get(i).city2))
            {
                playerCities.add(claimedRoutes.get(i).city2);
            }
        }
        for(int i = 0; i < allCities.length; i++)
        {
            if(!playerCities.contains(allCities[i]))
            {
                for(int k = 0; k < 35; k++)
                {
                    for(int j = 0; j < 35; j++)
                    {
                        if(k == i || j == i)
                        {
                            adjMatrix[k][j] = 0;
                        }
                    }
                }
            }
        }

        int loc1index = 0;
        int loc2index = 0;
        for(int i = 0; i < allCities.length; i++)
        {
            if(allCities[i].equals(d.loc1))
            {
                loc1index = i;
            }
            if(allCities[i].equals(d.loc2))
            {
                loc2index = i;
            }
        }

        ArrayList<Boolean> paths = new ArrayList<Boolean>();
        if(adjMatrix[loc1index][loc2index] == 1)
        {
            return true;
        }
        for(int i = 0; i < 35; i++)
        {
            if(adjMatrix[loc1index][i] == 1)
            {
                paths.add(recFindPath(loc2index, i));
            }
        }
        if(paths.contains(true))
        {
            return true;
        }
        return false;
    }

    public boolean recFindPath(int target, int curIndex)
    {
        ArrayList<Boolean> paths = new ArrayList<Boolean>();
        if(adjMatrix[curIndex][target] == 1)
        {
            return true;
        }
        for(int i = 0; i < 35; i++)
        {
            if(adjMatrix[curIndex][i] == 1)
            {
                paths.add(recFindPath(target, i));
            }
        }
        if(paths.contains(true))
        {
            return true;
        }
        return false;
    }

    public City nextCity(Track t, City currentCity)
    {
        if(t.getCity1().equals(currentCity))
        {
            return t.getCity2();
        }
        return t.getCity1();
    }
}
