import java.util.*;
import java.io.*;
/**
 * Write a description of class destinationTickets here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class destinationTickets
{
    String Loc1;
    String Loc2;
    int cardValue;
    public destinationTickets(){
        File file = new File("DestinationTickets.txt");
        
        try{
             Scanner sc = new Scanner(file);
             while(sc.hasNextLine()){
                 String Line = sc.nextLine();
                 String[] split = Line.split("\\s+");
                 Loc1 = split[0];
                 Loc2 = split[1];
                 cardValue = Integer.parseInt(split[2]);
                 
                }
               sc.close();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        
    }
    
}
