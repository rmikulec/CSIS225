import javax.swing.JFrame;

/**
 * This is the main that will be used to run Ticket to Ride
 * 
 * @author Matthew MacFadyen
 * @version April
 */
public class TicketToRide
{
    public static void main(String [] args)
    {

        // create a new object of the train master

        TrainMaster game = new TrainMaster();
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setResizable(false);
        game.setSize(620, 841);
        game.setVisible(true);

      
        
        
    }
}
