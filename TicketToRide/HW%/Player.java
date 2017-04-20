import java.util.*;
/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player
{
    int trains = 45;
    boolean start;
    public Player(int trains, ArrayList<DestinationTickets> Tickets, ArrayList<TrainCards> cards, ArrayList<DestinationTickets> ticketsCompleted)
    {
        this.trains = trains;
        Tickets = new ArrayList<DestinationTickets>();
        cards = new ArrayList<TrainCards>();
        ticketsCompleted = new ArrayList<DestinationTickets>();
        start = true;
        if(start){
            //gets 5 destination cards
            for(int x = 0; x < 6; x++){
               Tickets.add(drawDestinationTickets()); 
            }
            //display cards if the user clicks on the cards then Tickets.remove();
            
            //gets 4 train tickets
            for(int x = 0; x < 5 ; x++){
                cards.add(drawTrainTickets());
            }
        }
        
        
    }
    
    
    public DestinationTicket drawDestinationTickets(){
        return DestinationTicket.remove(0);
    }
    
    
    public stock drawStock(Stack<cards> stocks){
        //returns the stock you choose
        //stack of all the stocks
        Stack<cards> Stock = stocks;
        return Stock.pop();
        
    }
    
    
    public trainTickets drawTrainTickets(){
        return TrainTickets.remove(0);
    }
    
    public DestinationTicket chooseDestinationTicket(){
        for(int x = 0; x < 5; x++){
            destinationTicket.Remove(0);
        }
        
        //click on cards that you dont want must keep at least one 
    }
    
    public trainTickets chooseTrainTicket(){
        //if clicked on locomotive
                //end turn add locomotive to the hand
                //remove one card from the deck and add to the table
        //else choose at least two from the table or the deck 
                    //remove from the deck and add to your hand
                    
              
        
    }
    
    public void makePath(){
        if(trains != 0 && trains >= path){
            trains = trains - path;
            // graphic will put the train on the board.
            if(path.hasStock){
                //choose the stock click on it
                    drawStock(Stock);
                //
                
            }
        }
        if(destinationTickets.isCompleted){
            TicketCompleted.add(Ticket.remove());
            
        }
    }
}
