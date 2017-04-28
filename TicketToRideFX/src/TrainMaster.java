import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import javafx.scene.image.Image;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.*;
import javax.imageio.*;
import java.awt.image.*;
/**
 * This Class will handle most of the GUI for Ticket to Ride
 * 
 * @author Matthew MacFadyen 
 * @version April 18th, 2017
 */
public class TrainMaster
{

    private boolean gameStarted;
    private boolean gameEnded;

    private Stack<TrainTickets> trainDeck;
    private Stack<TrainTickets> trainDiscard;
    private ArrayList<DestinationTickets> destDeck;

    private ArrayList<Player> players;
    protected Player p1;
    protected Player p2;
    protected Player p3;
    protected Player p4;
    protected Player p5;

    protected Stack<Stocks> readingLines = new Stack<Stocks>();
    protected Stack<Stocks> lehighValley = new Stack<Stocks>();
    protected Stack<Stocks> pennsylvaniaRailroad = new Stack<Stocks>();
    protected Stack<Stocks> jerseyCentralLine = new Stack<Stocks>();
    protected Stack<Stocks> baltimoreandOhioRailroad = new Stack<Stocks>();
    protected Stack<Stocks> bRPRailway = new Stack<Stocks>();
    protected Stack<Stocks> lackawannaErie = new Stack<Stocks>();
    protected Stack<Stocks> nyCentralSystem = new Stack<Stocks>();
    protected Stack<Stocks> westernMarylandRailway = new Stack<Stocks>();
    protected ArrayList<Track> tracks = new ArrayList<Track>();


    protected int numberOfPlayers;

    /**
     * The constructor for the Train Master class will set up all of the GUI stuff 
     * that needs to be handled in the game during the beginning of the game
     */
    public TrainMaster(int numPlayers)
    {
        numberOfPlayers = numPlayers;

    }

    public void setup(int numPlayers)
    {
        if(numPlayers < 2 || numPlayers > 5)
        {
            //error
        }
        // create all of the city objects 
        City Ontario = new City("Ontario");
        City Buffalo = new City("Buffalo");
        City Rochester = new City("Rochester");
        City Syracuse = new City("Syracuse");
        City Albany = new City("Albany");
        City Erie = new City("Erie");
        City Warren = new City("Warren");
        City Coudersport = new City("Coudersport");
        City Towanda = new City("Towanda");
        City Binghamton = new City("Binghamton");
        City Youngstown = new City("Youngstown");
        City OilCity = new City("Oil City");
        City Dubois = new City("Dubois");
        City Williamsport = new City("Williamsport");
        City ScrantonWilkesBarre = new City("Scranton/Wilkes Barre");
        City NewYork = new City("New York");
        City Pittsburg = new City("Pittsburg");
        City Altoona = new City("Altoona");
        City Lewiston = new City("Lewiston");
        City Harrisburg = new City("Harrisburg");
        City Reading = new City("Reading");
        City Allentown = new City("Allentown");
        City Stroudsburg = new City("Stroudsburg");
        City AtlanticCity = new City("Atlantic City");
        City Philadelphia = new City("Philadelphia");
        City Elmira = new City("Elmira");
        City York = new City("York");
        City Baltimore = new City("Baltimore");
        City Gettysburg = new City("Gettysburg");
        City Chambersburg = new City("Chambersburg");
        City Cumberland = new City("Cumberland");
        City Morgantown = new City("Morgantown");
        City Wheeling = new City("Wheeling");
        City Johnstown = new City("Johnstown");
        City Lancaster = new City("Lancaster");

        Track t1= new Track(Ontario, Erie, "gray", 3, 2, new ArrayList<String>());
        t1.stockOptions.add("Pennsylvania Railroad");
        t1.stockOptions.add("Lackawanna Erie");
        t1.stockOptions.add("Pennsylvania Railroad");
        tracks.add(t1);
        Track t2= new Track(Ontario, Erie, "gray", 3, 2, new ArrayList<String>());
        t2.stockOptions.add("Pennsylvania Railroad");
        t2.stockOptions.add("Lackawanna Erie");
        t2.stockOptions.add("Pennsylvania Railroad");
        tracks.add(t2);
        Track t3= new Track(Ontario, Buffalo, "gray", 1, 1, new ArrayList<String>());
        t3.stockOptions.add("Pennsylvania Railroad");
        t3.stockOptions.add("Lackawanna Erie");
        t3.stockOptions.add("Pennsylvania Railroad");
        tracks.add(t3);
        Track t4= new Track(Ontario, Buffalo, "gray", 1, 1, new ArrayList<String>());
        t4.stockOptions.add("Pennsylvania Railroad");
        t4.stockOptions.add("Lackawanna Erie");
        t4.stockOptions.add("Pennsylvania Railroad");
        tracks.add(t4);
        Track t5= new Track(Buffalo, Coudersport, "gray", 4, 0, new ArrayList<String>());
        t5.stockOptions.add("Pennsylvania Railroad");
        t5.stockOptions.add("Lackawanna Erie");
        tracks.add(t5);
        Track t6= new Track(Warren, Coudersport, "gray", 4, 0, new ArrayList<String>());
        t6.stockOptions.add("Pennsylvania Railroad");
        t6.stockOptions.add("Lackawanna Erie");
        t6.stockOptions.add("Pennsylvania Railroad");
        t6.stockOptions.add("Buffalo, Rochester and Pittsburgh Railway");
        t6.stockOptions.add("Baltimore and Ohio Railroad");
        tracks.add(t6);
        Track t7= new Track(Dubois, Altoona, "gray", 2, 0, new ArrayList<String>());
        t7.stockOptions.add("Pennsylvania Railroad");
        tracks.add(t7);
        Track t8= new Track(Lewiston, Harrisburg, "gray", 2, 0, new ArrayList<String>());
        t8.stockOptions.add("Pennsylvania Railroad");
        tracks.add(t8);
        Track t9= new Track(Harrisburg, Lancaster, "gray", 2, 0, new ArrayList<String>());
        t9.stockOptions.add("Pennsylvania Railroad");
        tracks.add(t9);
        Track t10= new Track(Harrisburg, Lancaster, "gray", 2, 0, new ArrayList<String>());
        t10.stockOptions.add("Pennsylvania Railroad");
        tracks.add(t10);
        Track t11= new Track(Gettysburg, York, "gray", 1, 0, new ArrayList<String>());
        t11.stockOptions.add("Pennsylvania Railroad");
        t11.stockOptions.add("Western Maryland Railway");
        tracks.add(t11);
        Track t12= new Track(Philadelphia, AtlanticCity, "gray", 2, 0, new ArrayList<String>());
        t12.stockOptions.add("Pennsylvania Railroad");
        t12.stockOptions.add("Reading Lines");
        t12.stockOptions.add("Jersey Central Line");
        tracks.add(t12);
        Track t13= new Track(Philadelphia, AtlanticCity, "gray", 2, 0, new ArrayList<String>());
        t13.stockOptions.add("Pennsylvania Railroad");
        t13.stockOptions.add("Reading Lines");
        t13.stockOptions.add("Jersey Central Line");
        tracks.add(t13);
        Track t14= new Track(Philadelphia, NewYork, "gray", 6, 0, new ArrayList<String>());
        t14.stockOptions.add("Pennsylvania Railroad");
        t14.stockOptions.add("Baltimore and Ohio Railroad");
        t14.stockOptions.add("Reading Lines");
        t14.stockOptions.add("Jersey Central Line");
        tracks.add(t14);
        Track t15= new Track(Philadelphia, NewYork, "gray", 6, 0, new ArrayList<String>());
        t15.stockOptions.add("Pennsylvania Railroad");
        t15.stockOptions.add("Baltimore and Ohio Railroad");
        t15.stockOptions.add("Reading Lines");
        t15.stockOptions.add("Jersey Central Line");
        tracks.add(t15);
        Track t16= new Track(Johnstown, Cumberland, "gray", 3, 0, new ArrayList<String>());
        t16.stockOptions.add("Pennsylvania Railroad");
        t16.stockOptions.add("Western Maryland Railway");
        t16.stockOptions.add("Baltimore and Ohio Railroad");
        tracks.add(t16);
        Track t17= new Track(Harrisburg, ScrantonWilkesBarre, "gray", 6, 0, new ArrayList<String>());
        t17.stockOptions.add("Pennsylvania Railroad");
        t17.stockOptions.add("Reading Lines");
        tracks.add(t17);
        Track t18= new Track(Towanda, ScrantonWilkesBarre, "gray", 3, 0, new ArrayList<String>());
        t18.stockOptions.add("Pennsylvania Railroad");
        t18.stockOptions.add("Lackawanna Erie");
        t18.stockOptions.add("Lehigh Valley");
        tracks.add(t18);
        Track t19= new Track(Erie, Buffalo, "White", 5, 0, new ArrayList<String>());
        t19.stockOptions.add("Lackawanna Erie");
        t19.stockOptions.add("New York Central System");
        tracks.add(t19);
        Track t20= new Track(Elmira, Binghamton, "White", 3, 0, new ArrayList<String>());
        t20.stockOptions.add("Lackawanna Erie");
        tracks.add(t20);
        Track t21= new Track(Syracuse, Albany, "White", 6, 0, new ArrayList<String>());
        t21.stockOptions.add("New York Central System");
        tracks.add(t21);
        Track t22= new Track(Youngstown, OilCity, "White", 3, 0, new ArrayList<String>());
        t22.stockOptions.add("Lackawanna Erie");
        tracks.add(t22);
        Track t23= new Track(Wheeling, Pittsburg, "White", 2, 0, new ArrayList<String>());
        t23.stockOptions.add("Pennsylvania Railroad");
        tracks.add(t23);
        Track t24= new Track(Dubois, Williamsport, "White", 6, 0, new ArrayList<String>());
        tracks.add(t24);
        Track t25= new Track(York, Baltimore, "White", 2, 0, new ArrayList<String>());
        t25.stockOptions.add("Western Maryland Railway");
        t25.stockOptions.add("Pennsylvania Railroad");
        tracks.add(t25);
        Track t26= new Track(ScrantonWilkesBarre, Allentown, "White", 3, 0, new ArrayList<String>());
        t26.stockOptions.add("Pennsylvania Railroad");
        t26.stockOptions.add("Lehigh Valley");
        t26.stockOptions.add("Reading Lines");
        t26.stockOptions.add("Jersey Central Line");
        tracks.add(t26);
        Track t27 = new Track(NewYork, AtlanticCity, "white", 6,0, new ArrayList<String>());
        t27.stockOptions.add("Jersey Central Line");
        tracks.add(t27);
        Track t28= new Track(Syracuse, Albany, "red", 6, 0, new ArrayList<String>());
        t28.stockOptions.add("New York Central System");
        tracks.add(t28);
        Track t29= new Track(ScrantonWilkesBarre, NewYork, "red", 5, 0, new ArrayList<String>());
        t29.stockOptions.add("Jersey Central Line");
        t29.stockOptions.add("Lackawanna Erie");
        t29.stockOptions.add("Lehigh Valley");
        tracks.add(t29);
        Track t30= new Track(Allentown, Philadelphia, "red", 3, 0, new ArrayList<String>());
        t30.stockOptions.add("Reading Lines");
        t30.stockOptions.add("Pennsylvania Railroad");
        tracks.add(t30);
        Track t31= new Track(Gettysburg, Baltimore, "red", 3, 0, new ArrayList<String>());
        t31.stockOptions.add("Pennsylvania Railroad");
        t31.stockOptions.add("Western Maryland Railway");
        tracks.add(t31);
        Track t32= new Track(Altoona, Harrisburg, "red", 5, 0, new ArrayList<String>());
        t31.stockOptions.add("Pennsylvania Railroad");
        tracks.add(t32);
        Track t33= new Track(Morgantown, Cumberland, "red", 5, 0, new ArrayList<String>());
        t33.stockOptions.add("Western Maryland Railway");
        t33.stockOptions.add("Baltimore and Ohio Railroad");
        tracks.add(t33);
        Track t34= new Track(OilCity, Pittsburg, "red", 4, 0, new ArrayList<String>());
        t34.stockOptions.add("Baltimore and Ohio Railroad");
        t34.stockOptions.add("Pennsylvania Railroad");
        tracks.add(t34);
        Track t35= new Track(Towanda, Binghamton, "red", 2, 0, new ArrayList<String>());
        t35.stockOptions.add("Lackawanna Erie");
        tracks.add(t35);
        Track t36= new Track(Rochester, Syracuse, "blue", 4, 0, new ArrayList<String>());
        t36.stockOptions.add("New York Central System");
        t36.stockOptions.add("Lehigh Valley");
        tracks.add(t36);
        Track t37= new Track(Albany, NewYork, "blue", 6, 0, new ArrayList<String>());
        t37.stockOptions.add("New York Central System");
        t37.stockOptions.add("Pennsylvania Railroad");
        t37.stockOptions.add("Baltimore and Ohio Railroad");
        tracks.add(t37);
        Track t38= new Track(ScrantonWilkesBarre, Allentown, "blue", 3, 0, new ArrayList<String>());
        t38.stockOptions.add("Pennsylvania Railroad");
        t38.stockOptions.add("Lehigh Valley");
        t38.stockOptions.add("Reading Lines");
        t38.stockOptions.add("Jersey Central Line");
        tracks.add(t38);
        Track t39= new Track(Cumberland, Baltimore, "blue", 7, 0, new ArrayList<String>());
        t39.stockOptions.add("Western Maryland Railway");
        t39.stockOptions.add("Baltimore and Ohio Railroad");
        tracks.add(t39);
        Track t40= new Track(Wheeling, Morgantown, "blue", 3, 0, new ArrayList<String>());
        t40.stockOptions.add("Baltimore and Ohio Railroad");
        tracks.add(t40);
        Track t41= new Track(Chambersburg, Harrisburg, "blue", 2, 0, new ArrayList<String>());
        t41.stockOptions.add("Reading Lines");
        t41.stockOptions.add("Pennsylvania Railroad");
        t41.stockOptions.add("Western Maryland Railway");
        tracks.add(t41);
        Track t42= new Track(Youngstown, Pittsburg, "blue", 4, 0, new ArrayList<String>());
        t42.stockOptions.add("New York Central System");
        t42.stockOptions.add("Pennsylvania Railroad");
        t42.stockOptions.add("Baltimore and Ohio Railroad");
        tracks.add(t42);
        Track t43= new Track(Erie, Warren, "blue", 3, 0, new ArrayList<String>());
        t43.stockOptions.add("Pennsylvania Railroad");
        t43.stockOptions.add("Lackawanna Erie");
        t43.stockOptions.add("Pennsylvania Railroad");
        tracks.add(t43);
        Track t44= new Track(Johnstown, Altoona, "blue", 1, 0, new ArrayList<String>());
        t44.stockOptions.add("Pennsylvania Railroad");
        tracks.add(t44);
        Track t45= new Track(Buffalo, Rochester, "Black", 5, 0, new ArrayList<String>());
        t45.stockOptions.add("New York Central System");
        t45.stockOptions.add("Pennsylvania Railroad");
        t45.stockOptions.add("Baltimore and Ohio Railroad");
        t45.stockOptions.add("Lehigh Valley");
        t45.stockOptions.add("Buffalo, Rochester and Pittsburgh Railway");
        tracks.add(t45);
        Track t46= new Track(Elmira, Syracuse, "Black", 4, 0, new ArrayList<String>());
        t46.stockOptions.add("Lehigh Valley");
        t46.stockOptions.add("New York Central System");
        tracks.add(t46);
        Track t47= new Track(Warren, Dubois, "Black", 3, 0, new ArrayList<String>());
        t47.stockOptions.add("Lackawanna Erie");
        t47.stockOptions.add("Buffalo, Rochester and Pittsburgh Railway");
        tracks.add(t47);
        Track t48= new Track(Erie, OilCity, "Black", 3, 0, new ArrayList<String>());
        t48.stockOptions.add("New York Central System");
        t48.stockOptions.add("Pennsylvania Railroad");
        t48.stockOptions.add("Lackawanna Erie");
        tracks.add(t48);
        Track t49= new Track(Pittsburg, Johnstown, "Black", 4, 0, new ArrayList<String>());
        t49.stockOptions.add("Pennsylvania Railroad");
        t49.stockOptions.add("Baltimore and Ohio Railroad");
        tracks.add(t49);
        Track t50= new Track(Chambersburg, Gettysburg, "Black", 1, 0, new ArrayList<String>());
        tracks.add(t50);
        Track t51= new Track(Harrisburg, York, "Black", 1, 0, new ArrayList<String>());
        t51.stockOptions.add("Pennsylvania Railroad");
        t51.stockOptions.add("Western Maryland Railway");
        tracks.add(t51);
        Track t52= new Track(Allentown, Philadelphia, "Black", 3, 0, new ArrayList<String>());
        t52.stockOptions.add("Pennsylvania Railroad");
        t52.stockOptions.add("Reading Lines");
        tracks.add(t52);
        Track t53= new Track(NewYork, AtlanticCity, "Black", 6, 0, new ArrayList<String>());
        t53.stockOptions.add("Jersey Central Line");
        tracks.add(t53);
        Track t54= new Track(Binghamton, ScrantonWilkesBarre, "Black", 3, 0, new ArrayList<String>());
        t54.stockOptions.add("Lackawanna Erie");
        tracks.add(t54);
        Track t55= new Track(Williamsport, Towanda, "Black", 2, 0, new ArrayList<String>());
        t55.stockOptions.add("Lackawanna Erie");
        t55.stockOptions.add("Reading Lines");
        tracks.add(t55);
        Track t56= new Track(Buffalo, Warren, "Green", 4, 0, new ArrayList<String>());
        t56.stockOptions.add("Lackawanna Erie");
        t56.stockOptions.add("Pennsylvania Railroad");
        t56.stockOptions.add("Baltimore and Ohio Railroad");
        t56.stockOptions.add("Buffalo, Rochester and Pittsburgh Railway");
        tracks.add(t56);
        Track t57= new Track(Erie, Youngstown, "Green", 4, 0, new ArrayList<String>());
        t57.stockOptions.add("Lackawanna Erie");
        t57.stockOptions.add("New York Central System");
        tracks.add(t57);
        Track t58= new Track(Wheeling, Pittsburg, "Green", 2, 0, new ArrayList<String>());
        t58.stockOptions.add("Pennsylvania Railroad");
        tracks.add(t58);
        Track t59= new Track(Cumberland, Chambersburg, "Green", 2, 0, new ArrayList<String>());
        t59.stockOptions.add("Western Maryland Railway");
        t59.stockOptions.add("Pennsylvania Railroad");
        tracks.add(t59);
        Track t60= new Track(Lancaster, Philadelphia, "Green", 4, 0, new ArrayList<String>());
        t60.stockOptions.add("Pennsylvania Railroad");
        tracks.add(t60);
        Track t61= new Track(Reading, Allentown, "Green", 2, 0, new ArrayList<String>());
        t61.stockOptions.add("Reading Lines");
        tracks.add(t61);
        Track t62= new Track(Coudersport, Williamsport, "Green", 4, 0, new ArrayList<String>());
        t62.stockOptions.add("Pennsylvania Railroad");
        tracks.add(t62);
        Track t63= new Track(Altoona, Lewiston, "Green", 2, 0, new ArrayList<String>());
        tracks.add(t63);
        Track t64= new Track(Albany, NewYork, "Green", 6, 0, new ArrayList<String>());
        t64.stockOptions.add("Pennsylvania Railroad");
        t64.stockOptions.add("New York Central System");
        t64.stockOptions.add("Baltimore and Ohio Railroad");
        tracks.add(t64);
        Track t65= new Track(Binghamton, ScrantonWilkesBarre, "Green", 3, 0, new ArrayList<String>());
        t65.stockOptions.add("Lackawanna Erie");
        tracks.add(t65);
        Track t66= new Track(Rochester, Elmira, "Green", 3, 0, new ArrayList<String>());
        t66.stockOptions.add("Lackawanna Erie");
        t66.stockOptions.add("Pennsylvania Railroad");
        t66.stockOptions.add("Baltimore and Ohio Railroad");
        t66.stockOptions.add("Lehigh Valley");
        t66.stockOptions.add("Buffalo, Rochester and Pittsburgh Railway");
        tracks.add(t66);
        Track t67= new Track(Buffalo, Rochester, "Yellow", 4, 0, new ArrayList<String>());
        t67.stockOptions.add("New York Central System");
        t67.stockOptions.add("Pennsylvania Railroad");
        t67.stockOptions.add("Baltimore and Ohio Railroad");
        t67.stockOptions.add("Lehigh Valley");
        t67.stockOptions.add("Buffalo, Rochester and Pittsburgh Railway");
        tracks.add(t67);
        Track t68= new Track(Erie, Youngstown, "Yellow", 4, 0, new ArrayList<String>());
        t68.stockOptions.add("Lackawanna Erie");
        t68.stockOptions.add("New York Central System");
        tracks.add(t68);
        Track t69= new Track(Harrisburg, Gettysburg, "Yellow", 2, 0, new ArrayList<String>());
        t69.stockOptions.add("Pennsylvania Railroad");
        t69.stockOptions.add("Reading Lines");
        tracks.add(t69);
        Track t70= new Track(Baltimore, Philadelphia, "Yellow", 4, 0, new ArrayList<String>());
        t70.stockOptions.add("Pennsylvania Railroad");
        t70.stockOptions.add("Baltimore and Ohio Railroad");
        tracks.add(t70);
        Track t71= new Track(ScrantonWilkesBarre, Stroudsburg, "Yellow", 2, 0, new ArrayList<String>());
        t71.stockOptions.add("Reading Lines");
        t71.stockOptions.add("Lehigh Valley");
        t71.stockOptions.add("Jersey Central Line");
        tracks.add(t71);
        Track t72= new Track(Lewiston, Williamsport, "Yellow", 3, 0, new ArrayList<String>());
        t72.stockOptions.add("Pennsylvania Railroad");
        t72.stockOptions.add("Reading Lines");
        tracks.add(t72);
        Track t73= new Track(Elmira, Towanda, "Yellow", 2, 0, new ArrayList<String>());
        t73.stockOptions.add("Pennsylvania Railroad");
        t73.stockOptions.add("Lehigh Valley");
        t73.stockOptions.add("Lackawanna Erie");
        tracks.add(t73);
        Track t74= new Track(Altoona, Johnstown, "Yellow", 1, 0, new ArrayList<String>());
        t74.stockOptions.add("Pennsylvania Railroad");
        tracks.add(t74);
        Track t75= new Track(Pittsburg, Morgantown, "Yellow", 3, 0, new ArrayList<String>());
        tracks.add(t75);
        Track t76= new Track(Reading, Lancaster, "Yellow", 1, 0, new ArrayList<String>());
        t76.stockOptions.add("Reading Lines");
        tracks.add(t76);
        Track t77= new Track(Syracuse, Binghamton, "Yellow", 2, 0, new ArrayList<String>());
        t77.stockOptions.add("Lackawanna Erie");
        tracks.add(t77);
        Track t78= new Track(Erie, Buffalo, "Orange", 5, 0, new ArrayList<String>());
        t78.stockOptions.add("Lackawanna Erie");
        t78.stockOptions.add("New York Central System");
        tracks.add(t78);
        Track t79= new Track(OilCity, Warren, "Orange", 2, 0, new ArrayList<String>());
        t79.stockOptions.add("Lackawanna Erie");
        t79.stockOptions.add("New York Central System");
        t79.stockOptions.add("Baltimore and Ohio Railroad");
        tracks.add(t79);
        Track t80= new Track(Coudersport, Elmira, "Orange", 4, 0, new ArrayList<String>());
        t80.stockOptions.add("Lackawanna Erie");
        t80.stockOptions.add("New York Central System");
        t80.stockOptions.add("Baltimore and Ohio Railroad");
        t80.stockOptions.add("Lackawanna Erie");
        t80.stockOptions.add("Buffalo, Rochester and Pittsburgh Railway");
        tracks.add(t80);
        Track t81= new Track(Syracuse, Binghamton, "Orange", 2, 0, new ArrayList<String>());
        t81.stockOptions.add("Lackawanna Erie");
        tracks.add(t81);
        Track t82= new Track(Williamsport, ScrantonWilkesBarre, "Orange", 5, 0, new ArrayList<String>());
        tracks.add(t82);
        Track t83= new Track(Altoona, Harrisburg, "Orange", 5, 0, new ArrayList<String>());
        t83.stockOptions.add("Pennsylvania Railroad");
        tracks.add(t83);
        Track t84= new Track(Lancaster, Philadelphia, "Orange", 4, 0, new ArrayList<String>());
        t84.stockOptions.add("Pennsylvania Railroad");
        tracks.add(t84);
        Track t85= new Track(Youngstown, Pittsburg, "Orange", 4, 0, new ArrayList<String>());
        t85.stockOptions.add("Pennsylvania Railroad");
        t85.stockOptions.add("Baltimore and Ohio Railroad");
        t85.stockOptions.add("New York Central System");
        tracks.add(t85);
        Track t86= new Track(Allentown, Stroudsburg, "Orange", 2, 0, new ArrayList<String>());
        t86.stockOptions.add("Reading Lines");
        t86.stockOptions.add("Lehigh Valley");
        t86.stockOptions.add("Jersey Central Line");
        tracks.add(t86);
        Track t87= new Track(Albany, Binghamton, "Pink", 6, 0, new ArrayList<String>());
        tracks.add(t87);
        Track t88= new Track(Reading, Harrisburg, "Pink", 2, 0, new ArrayList<String>());
        t88.stockOptions.add("Reading Lines");
        tracks.add(t88);
        Track t89= new Track(Pittsburg, Altoona, "Pink", 4, 0, new ArrayList<String>());
        t89.stockOptions.add("Pennsylvania Railroad");
        t89.stockOptions.add("Baltimore and Ohio Railroad");
        tracks.add(t89);
        Track t90= new Track(Youngstown, Wheeling, "Pink", 5, 0, new ArrayList<String>());
        t90.stockOptions.add("Pennsylvania Railroad");
        t90.stockOptions.add("Baltimore and Ohio Railroad");
        t90.stockOptions.add("New York Central System");
        tracks.add(t90);
        Track t91= new Track(OilCity, Dubois, "Pink", 3, 0, new ArrayList<String>());
        tracks.add(t91);
        Track t92= new Track(York, Lancaster, "Pink", 1, 0, new ArrayList<String>());
        t92.stockOptions.add("Pennsylvania Railroad");
        tracks.add(t92);
        Track t93= new Track(Baltimore, Philadelphia, "Pink", 4, 0, new ArrayList<String>());
        t93.stockOptions.add("Pennsylvania Railroad");
        t93.stockOptions.add("Baltimore and Ohio Railroad");
        tracks.add(t93);
        Track t94= new Track(Rochester, Syracuse, "Pink", 4, 0, new ArrayList<String>());
        t94.stockOptions.add("New York Central System");
        t94.stockOptions.add("Lehigh Valley");
        tracks.add(t94);
        Track t95= new Track(ScrantonWilkesBarre, NewYork, "Pink", 5, 0, new ArrayList<String>());
        t95.stockOptions.add("Jersey Central Line");
        t95.stockOptions.add("Lehigh Valley");
        t95.stockOptions.add("Lackawanna Erie");
        tracks.add(t95);
        // create all of the stocks for the paths
        Stocks readingLines1 = new Stocks(CardTypes.READING, 1, "Reading Lines", 3);
        Stocks readingLines2 = new Stocks(CardTypes.READING, 2, "Reading Lines", 3);
        Stocks readingLines3 = new Stocks(CardTypes.READING, 3, "Reading Lines", 3);
        Stocks readingLines4 = new Stocks(CardTypes.READING, 4, "Reading Lines", 3);
        Stocks readingLines5 = new Stocks(CardTypes.READING, 5, "Reading Lines", 3);
        Stocks readingLines6 = new Stocks(CardTypes.READING, 6, "Reading Lines", 3);
        Stocks readingLines7 = new Stocks(CardTypes.READING, 7, "Reading Lines", 3);
        readingLines.push(readingLines7);
        readingLines.push(readingLines6);
        readingLines.push(readingLines5);
        readingLines.push(readingLines4);
        readingLines.push(readingLines3);
        readingLines.push(readingLines2);
        readingLines.push(readingLines1);

        Stocks lehighValley1 = new Stocks(CardTypes.LEHIGH, 1, "Lehigh Valley", 3);
        Stocks lehighValley2 = new Stocks(CardTypes.LEHIGH, 2, "Lehigh Valley", 3);
        Stocks lehighValley3 = new Stocks(CardTypes.LEHIGH, 3, "Lehigh Valley", 3);
        Stocks lehighValley4 = new Stocks(CardTypes.LEHIGH, 4, "Lehigh Valley", 3);
        Stocks lehighValley5 = new Stocks(CardTypes.LEHIGH, 5, "Lehigh Valley", 3);
        Stocks lehighValley6 = new Stocks(CardTypes.LEHIGH, 6, "Lehigh Valley", 3);

        lehighValley.push(lehighValley6);
        lehighValley.push(lehighValley5);
        lehighValley.push(lehighValley4);
        lehighValley.push(lehighValley3);
        lehighValley.push(lehighValley2);
        lehighValley.push(lehighValley1);

        Stocks pennsylvaniaRailroad1 = new Stocks(CardTypes.PR, 1, "Pennsylvania Railroad", 5);
        Stocks pennsylvaniaRailroad2 = new Stocks(CardTypes.PR, 2, "Pennsylvania Railroad", 5);
        Stocks pennsylvaniaRailroad3 = new Stocks(CardTypes.PR, 3, "Pennsylvania Railroad", 5);
        Stocks pennsylvaniaRailroad4 = new Stocks(CardTypes.PR, 4, "Pennsylvania Railroad", 5);
        Stocks pennsylvaniaRailroad5 = new Stocks(CardTypes.PR, 5, "Pennsylvania Railroad", 5);
        Stocks pennsylvaniaRailroad6 = new Stocks(CardTypes.PR, 6, "Pennsylvania Railroad", 5);
        Stocks pennsylvaniaRailroad7 = new Stocks(CardTypes.PR, 7, "Pennsylvania Railroad", 5);
        Stocks pennsylvaniaRailroad8 = new Stocks(CardTypes.PR, 8, "Pennsylvania Railroad", 5);
        Stocks pennsylvaniaRailroad9 = new Stocks(CardTypes.PR, 9, "Pennsylvania Railroad", 5);
        Stocks pennsylvaniaRailroad10 = new Stocks(CardTypes.PR, 10, "Pennsylvania Railroad", 5);
        Stocks pennsylvaniaRailroad11 = new Stocks(CardTypes.PR, 11, "Pennsylvania Railroad", 5);
        Stocks pennsylvaniaRailroad12 = new Stocks(CardTypes.PR, 12, "Pennsylvania Railroad", 5);
        Stocks pennsylvaniaRailroad13 = new Stocks(CardTypes.PR, 13, "Pennsylvania Railroad", 5);
        Stocks pennsylvaniaRailroad14 = new Stocks(CardTypes.PR, 14, "Pennsylvania Railroad", 5);
        Stocks pennsylvaniaRailroad15 = new Stocks(CardTypes.PR, 15, "Pennsylvania Railroad", 5);
        
        lehighValley.push(pennsylvaniaRailroad15);
        lehighValley.push(pennsylvaniaRailroad14);
        lehighValley.push(pennsylvaniaRailroad13);
        lehighValley.push(pennsylvaniaRailroad12);
        lehighValley.push(pennsylvaniaRailroad11);
        lehighValley.push(pennsylvaniaRailroad10);
        lehighValley.push(pennsylvaniaRailroad9);
        lehighValley.push(pennsylvaniaRailroad8);
        lehighValley.push(pennsylvaniaRailroad7);
        lehighValley.push(pennsylvaniaRailroad6);
        lehighValley.push(pennsylvaniaRailroad5);
        lehighValley.push(pennsylvaniaRailroad4);
        lehighValley.push(pennsylvaniaRailroad3);
        lehighValley.push(pennsylvaniaRailroad2);
        lehighValley.push(pennsylvaniaRailroad1);

        Stocks jerseyCentralLine1 = new Stocks(CardTypes.JCL, 1, "Jersey Central Line", 2);
        Stocks jerseyCentralLine2 = new Stocks(CardTypes.JCL, 2, "Jersey Central Line", 2);
        Stocks jerseyCentralLine3 = new Stocks(CardTypes.JCL, 3, "Jersey Central Line", 2);
        
        jerseyCentralLine.push(jerseyCentralLine3);
        jerseyCentralLine.push(jerseyCentralLine2);
        jerseyCentralLine.push(jerseyCentralLine1);

        Stocks lackawannaErie1 = new Stocks(CardTypes.ERIE, 1, "Lackawanna Erie", 4);
        Stocks lackawannaErie2 = new Stocks(CardTypes.ERIE, 2, "Lackawanna Erie", 4);
        Stocks lackawannaErie3 = new Stocks(CardTypes.ERIE, 3, "Lackawanna Erie", 4);
        Stocks lackawannaErie4 = new Stocks(CardTypes.ERIE, 4, "Lackawanna Erie", 4);
        Stocks lackawannaErie5 = new Stocks(CardTypes.ERIE, 5, "Lackawanna Erie", 4);
        Stocks lackawannaErie6 = new Stocks(CardTypes.ERIE, 6, "Lackawanna Erie", 4);
        Stocks lackawannaErie7 = new Stocks(CardTypes.ERIE, 7, "Lackawanna Erie", 4);
        Stocks lackawannaErie8 = new Stocks(CardTypes.ERIE, 8, "Lackawanna Erie", 4);
        
        lackawannaErie.push(lackawannaErie8);
        lackawannaErie.push(lackawannaErie7);
        lackawannaErie.push(lackawannaErie6);
        lackawannaErie.push(lackawannaErie5);
        lackawannaErie.push(lackawannaErie4);
        lackawannaErie.push(lackawannaErie3);
        lackawannaErie.push(lackawannaErie2);
        lackawannaErie.push(lackawannaErie1);

        Stocks bRPRailway1 = new Stocks(CardTypes.BRP, 1, "Buffalo, Rochester and Pittsburgh Railway", 2);
        Stocks bRPRailway2 = new Stocks(CardTypes.BRP, 2, "Buffalo, Rochester and Pittsburgh Railway", 2);
        Stack<Stocks> bRPRailway = new Stack<Stocks>();
        bRPRailway.push(bRPRailway2);
        bRPRailway.push(bRPRailway1);

        Stocks nyCentralSystem1  = new Stocks(CardTypes.NYC, 1, "New York Central System ", 3);
        Stocks nyCentralSystem2  = new Stocks(CardTypes.NYC, 2, "New York Central System ", 3);
        Stocks nyCentralSystem3  = new Stocks(CardTypes.NYC, 3, "New York Central System ", 3);
        Stocks nyCentralSystem4  = new Stocks(CardTypes.NYC, 4, "New York Central System ", 3);
        Stocks nyCentralSystem5  = new Stocks(CardTypes.NYC, 5, "New York Central System ", 3);
        
        nyCentralSystem.push(nyCentralSystem5);
        nyCentralSystem.push(nyCentralSystem4);
        nyCentralSystem.push(nyCentralSystem3);
        nyCentralSystem.push(nyCentralSystem2);
        nyCentralSystem.push(nyCentralSystem1);

        Stocks westernMarylandRailway1 = new Stocks(CardTypes.WM, 1, "Western Maryland Railway", 2);
        Stocks westernMarylandRailway2 = new Stocks(CardTypes.WM, 2, "Western Maryland Railway", 2);
        Stocks westernMarylandRailway3 = new Stocks(CardTypes.WM, 3, "Western Maryland Railway", 2);
        Stocks westernMarylandRailway4 = new Stocks(CardTypes.WM, 4, "Western Maryland Railway", 2);
        
        westernMarylandRailway.push(westernMarylandRailway4);
        westernMarylandRailway.push(westernMarylandRailway3);
        westernMarylandRailway.push(westernMarylandRailway2);
        westernMarylandRailway.push(westernMarylandRailway1);

        Stocks baltimoreandOhioRailroad1 = new Stocks(CardTypes.BO, 1, "Baltimore and Ohio Railroad", 5);
        Stocks baltimoreandOhioRailroad2 = new Stocks(CardTypes.BO, 2, "Baltimore and Ohio Railroad", 5);
        Stocks baltimoreandOhioRailroad3 = new Stocks(CardTypes.BO, 3, "Baltimore and Ohio Railroad", 5);
        Stocks baltimoreandOhioRailroad4 = new Stocks(CardTypes.BO, 4, "Baltimore and Ohio Railroad", 5);
        Stocks baltimoreandOhioRailroad5 = new Stocks(CardTypes.BO, 5, "Baltimore and Ohio Railroad", 5);
        Stocks baltimoreandOhioRailroad6 = new Stocks(CardTypes.BO, 6, "Baltimore and Ohio Railroad", 5);
        Stocks baltimoreandOhioRailroad7 = new Stocks(CardTypes.BO, 7, "Baltimore and Ohio Railroad", 5);
        Stocks baltimoreandOhioRailroad8 = new Stocks(CardTypes.BO, 8, "Baltimore and Ohio Railroad", 5);
        Stocks baltimoreandOhioRailroad9= new Stocks(CardTypes.BO, 9, "Baltimore and Ohio Railroad", 5);
        Stocks baltimoreandOhioRailroad10 = new Stocks(CardTypes.BO, 10, "Baltimore and Ohio Railroad", 5);
        
        baltimoreandOhioRailroad.push(baltimoreandOhioRailroad10);
        baltimoreandOhioRailroad.push(baltimoreandOhioRailroad9);
        baltimoreandOhioRailroad.push(baltimoreandOhioRailroad8);
        baltimoreandOhioRailroad.push(baltimoreandOhioRailroad7);
        baltimoreandOhioRailroad.push(baltimoreandOhioRailroad6);
        baltimoreandOhioRailroad.push(baltimoreandOhioRailroad5);
        baltimoreandOhioRailroad.push(baltimoreandOhioRailroad4);
        baltimoreandOhioRailroad.push(baltimoreandOhioRailroad3);
        baltimoreandOhioRailroad.push(baltimoreandOhioRailroad2);
        baltimoreandOhioRailroad.push(baltimoreandOhioRailroad1);

        if(numPlayers == 2)
        {
            //rules for 2 players
        }
        else
        {
            //rules for 3-5 players
            trainDeck = new Stack<TrainTickets>();
            for(int i = 0; i < 12; i++)
            {
                trainDeck.push(new TrainTickets(CardTypes.TRAIN, "black", false, new Image("./src/Images/Pics/TrainColors/TrainColors007.jpg")));
                trainDeck.push(new TrainTickets(CardTypes.TRAIN, "blue", false, new Image("./src/Images/Pics/TrainColors/TrainColors003")));
                trainDeck.push(new TrainTickets(CardTypes.TRAIN, "pink", false, new Image("./src/Images/Pics/TrainColors/TrainColors002")));
                trainDeck.push(new TrainTickets(CardTypes.TRAIN, "red", false, new Image("./src/Images/Pics/TrainColors/TrainColors009")));
                trainDeck.push(new TrainTickets(CardTypes.TRAIN, "yellow", false, new Image("./src/Images/Pics/TrainColors/TrainColors004")));
                trainDeck.push(new TrainTickets(CardTypes.TRAIN, "orange", false, new Image("./src/Images/Pics/TrainColors/TrainColors006")));
                trainDeck.push(new TrainTickets(CardTypes.TRAIN, "green", false, new Image("./src/Images/Pics/TrainColors/TrainColors008")));
                trainDeck.push(new TrainTickets(CardTypes.TRAIN, "white", false, new Image("./src/Images/Pics/TrainColors/TrainColors005")));
                if(i == 0)//locomotives
                {
                    for(int j = 0; j < 20; j++)
                    {
                        trainDeck.push(new TrainTickets(CardTypes.TRAIN, "", true, new Image("./src/Images/Pics/TrainColors/TrainColors001")));
                    }
                }
            }
            Collections.shuffle(trainDeck);
            trainDiscard = new Stack<TrainTickets>();

            destDeck = new ArrayList<DestinationTickets>();
            destDeck.add(new DestinationTickets(CardTypes.DEST, Syracuse, Allentown, 8, new Image("./src/Images/Pics/Routes/routes13")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Baltimore, Philadelphia, 4, new Image("./src/Images/Pics/Routes/routes29")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Philadelphia, AtlanticCity, 2, new Image("./src/Images/Pics/Routes/routes18")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Towanda, Lancaster, 9, new Image("./src/Images/Pics/Routes/routes1")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Wheeling, Allentown, 15, new Image("./src/Images/Pics/Routes/routes4")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Baltimore, NewYork, 10, new Image("./src/Images/Pics/Routes/routes28")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Harrisburg, Philadelphia, 6, new Image("./src/Images/Pics/Routes/routes9")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Ontario, Syracuse, 10, new Image("./src/Images/Pics/Routes/routes16")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, ScrantonWilkesBarre, Allentown, 3, new Image("./src/Images/Pics/Routes/routes10")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Morgantown, Chambersburg, 7, new Image("./src/Images/Pics/Routes/routes15")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Pittsburg, Baltimore, 13, new Image("./src/Images/Pics/Routes/routes50")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Rochester, Reading, 13, new Image("./src/Images/Pics/Routes/routes40")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Pittsburg, Philadelphia, 15, new Image("./src/Images/Pics/Routes/routes31")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Buffalo, Harrisburg, 13, new Image("./src/Images/Pics/Routes/routes21")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Lewiston, Syracuse, 9, new Image("./src/Images/Pics/Routes/routes7")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Morgantown, Williamsport, 13, new Image("./src/Images/Pics/Routes/routes2")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, OilCity, Johnstown, 6, new Image("./src/Images/Pics/Routes/routes47")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Pittsburg, Harrisburg, 9, new Image("./src/Images/Pics/Routes/routes44")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Pittsburg, NewYork, 20, new Image("./src/Images/Pics/Routes/routes45")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Altoona, Binghamton, 9, new Image("./src/Images/Pics/Routes/routes6")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Dubois, Cumberland, 6, new Image("./src/Images/Pics/Routes/routes19")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Ontario, Warren, 5, new Image("./src/Images/Pics/Routes/routes17")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Erie, Albany, 20, new Image("./src/Images/Pics/Routes/routes12")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Harrisburg, NewYork, 11, new Image("./src/Images/Pics/Routes/routes5")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Ontario, Pittsburg, 10, new Image("./src/Images/Pics/Routes/routes25")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Rochester, Elmira, 3, new Image("./src/Images/Pics/Routes/routes11")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Philadelphia, NewYork, 6, new Image("./src/Images/Pics/Routes/routes49")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Warren, York, 10, new Image("./src/Images/Pics/Routes/routes36")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Williamsport, Albany, 10, new Image("./src/Images/Pics/Routes/routes27")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Chambersburg, ScrantonWilkesBarre, 8, new Image("./src/Images/Pics/Routes/routes34")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Coudersport, Binghamton, 7, new Image("./src/Images/Pics/Routes/routes35")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, NewYork, AtlanticCity, 6, new Image("./src/Images/Pics/Routes/routes46")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, OilCity, ScrantonWilkesBarre, 14, new Image("./src/Images/Pics/Routes/routes48")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Baltimore, Albany, 16, new Image("./src/Images/Pics/Routes/routes8")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Youngstown, Morgantown, 7, new Image("./src/Images/Pics/Routes/routes38")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Pittsburg, Buffalo, 10, new Image("./src/Images/Pics/Routes/routes41")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Gettysburg, Reading, 3, new Image("./src/Images/Pics/Routes/routes37")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Buffalo, NewYork, 18, new Image("./src/Images/Pics/Routes/routes23")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Buffalo, Johnstown, 10, new Image("./src/Images/Pics/Routes/routes22")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Buffalo, Philadelphia, 19, new Image("./src/Images/Pics/Routes/routes24")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Buffalo, Baltimore, 16, new Image("./src/Images/Pics/Routes/routes30")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Erie, Altoona, 8, new Image("./src/Images/Pics/Routes/routes14")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Johnstown, Elmira, 10, new Image("./src/Images/Pics/Routes/routes39")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Wheeling, Albany, 22, new Image("./src/Images/Pics/Routes/routes32")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Youngstown, ScrantonWilkesBarre, 17, new Image("./src/Images/Pics/Routes/routes33")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Youngstown, Rochester, 14, new Image("./src/Images/Pics/Routes/routes43")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Wheeling, Erie, 9, new Image("./src/Images/Pics/Routes/routes3")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Dubois, Stroudsburg, 12, new Image("./src/Images/Pics/Routes/routes20")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Harrisburg, Baltimore, 3, new Image("./src/Images/Pics/Routes/routes42")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Cumberland, Harrisburg, 4, new Image("./src/Images/Pics/Routes/routes26")));

            Collections.shuffle(destDeck);

            // the tracks for all of the cities will be hard coded here
        }
    }

    public void TakeTurn()
    {

    }



    /**
     * Returns the nmber of players that will be playing this game of Ticket to Ride 
     * @return int of the number of players playing the game
     */
    public int getNumPlayers()
    {
        int numPlayers = 0;

        return numPlayers;

    }

    public Player nextPlayer(Player currentPlayer)
    {
        if(numberOfPlayers == 2)
        {
            if(currentPlayer.equals(p1))
            {
                return p2;
            }
            if(currentPlayer.equals(p2))
            {
                return p1;
            }
        }
        if(numberOfPlayers == 3)
        {
            if(currentPlayer.equals(p1))
            {
                return p2;
            }
            if(currentPlayer.equals(p2))
            {
                return p3;
            }
            if(currentPlayer.equals(p3))
            {
                return p1;
            }
        }
        if(numberOfPlayers == 4)
        {
            if(currentPlayer.equals(p1))
            {
                return p2;
            }
            if(currentPlayer.equals(p2))
            {
                return p3;
            }
            if(currentPlayer.equals(p3))
            {
                return p4;
            }
            if(currentPlayer.equals(p4))
            {
                return p1;
            }
        }
        if(numberOfPlayers == 5)
        {
            if(currentPlayer.equals(p1))
            {
                return p2;
            }
            if(currentPlayer.equals(p2))
            {
                return p3;
            }
            if(currentPlayer.equals(p3))
            {
                return p4;
            }
            if(currentPlayer.equals(p4))
            {
                return p5;
            }
            if(currentPlayer.equals(p5))
            {
                return p1;
            }
        }
        return currentPlayer;
    }

    public boolean gameStarted()
    {
        return gameStarted;

    }

    public boolean gameEnded()
    {
        return gameEnded;

    }

    public ArrayList<DestinationTickets> popDest(int numCards) {
        ArrayList<DestinationTickets> newCards = new ArrayList<DestinationTickets>();


        for (int i = 0; i < numCards; i++) {
            newCards.add(destDeck.remove(0));
        }
        return newCards;
    }

    public ArrayList<TrainTickets> popTrain(int numCards) {
        ArrayList<TrainTickets> newCards = new ArrayList<TrainTickets>();
        for (int i = 0; i < numCards; i++) {
            newCards.add(trainDeck.remove(0));
        }
        return newCards;
    }

public void setUpPlayers() {

        for(int i=0; i<numberOfPlayers; i++){
            players.get(i).addDest(popDest(5));
        }

}

public Player getPlayer(int num){
        return players.get(num);
}




    }

