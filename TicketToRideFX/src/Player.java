import java.util.*;
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

    //     public DestinationTickets drawDestinationTickets(){
    //         return DestinationTickets.remove(0);
    //     }

    //     public Stocks drawStock(Stack<Stocks> stocks){
    //         //returns the stock you choose
    //         //stack of all the stocks
    //         Stack<Card> Stock = stocks;
    //         return Stock.pop();
    // 
    //     }
    // 
    //     public TrainTickets drawTrainTickets(){
    //         return TrainTickets.remove(0);
    //     }
    // 
    //     public DestinationTickets chooseDestinationTicket(){
    //         for(int x = 0; x < 5; x++){
    //             destinationTicket.Remove(0);
    //         }
    // 
    //         //click on cards that you dont want must keep at least one 
    //     }

    //     public TrainTickets chooseTrainTicket(){
    //         //if clicked on locomotive
    //         //end turn add locomotive to the hand
    //         //remove one card from the deck and add to the table
    //         //else choose at least two from the table or the deck 
    //         //remove from the deck and add to your hand
    // 
    //     }

    //     public void makePath(){
    //         if(trains != 0 && trains >= path){
    //             trains = trains - path;
    //             // graphic will put the train on the board.
    //             if(path.hasStock){
    //                 //choose the stock click on it
    //                 drawStock(Stock);
    //                 //
    // 
    //             }
    //         }
    //         if(destinationTickets.isComplete){
    //             TicketCompleted.add(Ticket.remove());
    // 
    //         }
    //     }
}
