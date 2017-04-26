import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.io.*;
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
public class TrainMaster extends JFrame implements MouseListener, ActionListener
{
    private JPanel menu;
    private JPanel rules;
    private JPanel numPlayers;
    private JPanel gameMenu;
    private JPanel game;
    private JButton b1;
    private JButton b2;
    private JButton b3;
    private JButton b4;
    private JButton b5;
    private JButton twoPlayer;
    private JButton threePlayer;
    private JButton fourPlayer;
    private JButton fivePlayer;
    private JTextField item2;
    private JTextField item3;
    private JTextField item4;
    private JLabel board;
    private JPasswordField passwordField;
    private boolean gameStarted;
    private boolean gameEnded;
    private GraphicsDevice vc;
    private int screenWidth;
    private int screenHeight;
    private Stack<TrainTickets> trainDeck;
    private Stack<TrainTickets> trainDiscard;
    private ArrayList<DestinationTickets> destDeck;
    public BufferedImage gameBoard;

    /**
     * The constructor for the Train Master class will set up all of the GUI stuff 
     * that needs to be handled in the game during the beginning of the game
     */
    public TrainMaster()
    {

        //////////////Handles the FullScreen enivronment//////////////////////////
        super();
        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
        vc= e.getDefaultScreenDevice();

        JButton b = new JButton("exit");
        b.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent arg0) { 
                    dispose();
                    System.exit(0);

                }
            }
        );
        this.setLayout(new FlowLayout());
        this.add(b);
        //setFullScreen(this);
        ///////////////////////////////////////////////////////////////////////////

        //////////////////////Obtain Resolution of Screen//////////////////////////
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        screenWidth = gd.getDisplayMode().getWidth();
        screenHeight = gd.getDisplayMode().getHeight();
        ///////////////////////////////////////////////////////////////////////////

        // set the layout of the frame
        setLayout(new FlowLayout() );

        // create a new JPanel 
        // the main menu panel 
        menu = new JPanel();
        rules = new JPanel();
        gameMenu = new JPanel();
        game = new JPanel();
        game.setBackground(Color.gray);

        // add the text field 
        item2 = new JTextField();
        item2.setBounds(200, 5, 280, 50);

        // label for the main menu
        JLabel label = new JLabel(new ImageIcon("Images/rsz_mainmenu.jpg"), JLabel.CENTER);
        JLabel instructions = new JLabel(new ImageIcon("Images/instructions-2.jpg") );
        ImageIcon picture = new ImageIcon("Images/ticket6.jpg");
        Image resizedImage = getScaledImage(picture.getImage(), 620, 841); 
        picture.setImage(resizedImage);
        JLabel play = new JLabel(picture);
        //board = new JLabel(new ImageIcon("Images/board1.jpg") );

        label.setLayout(new FlowLayout() );
        instructions.setLayout(new FlowLayout() );
        play.setLayout(new FlowLayout() );
        //board.setLayout(new FlowLayout() );

        // create the button for the instructions
        b1 = new JButton("How to Play");

        // create the button for playing the game
        b2 = new JButton("Play Game");

        // create a button for returning to the main 
        b3 = new JButton("Main Menu");

        b4 = new JButton("Back to Main Menu");

        b5 = new JButton("Submit Players");

        //created buttons for the numbers of players 
        twoPlayer = new JButton("2 players");

        threePlayer = new JButton("3 players");

        fourPlayer = new JButton("4 players");

        fivePlayer = new JButton("5 players");

        // add the button to the label
        label.add(b2);

        // add the button to the label 
        label.add(b1);

        // add the label to the panel
        menu.add(label);

        // add the buttons and the text field 
        play.add(b3);
        play.add(b4); 
        //play.add(b5);

        //added the buttons for the players
        play.add(twoPlayer);
        play.add(threePlayer);
        play.add(fourPlayer);
        play.add(fivePlayer);

        //         play.add(item2);
        gameMenu.add(play);
        //game.add(board);
        instructions.add(b3);
        rules.add(instructions);

        //game.add(board);
        instructions.add(b3);
        rules.add(instructions);
        // for hovering 
        b1.setToolTipText("How to Play");
        b2.setToolTipText("Play Game");
        b3.setToolTipText("Main Menu");

        add(menu);
        add(rules);
        add(gameMenu);

        add(game);

        // the rules and the gameMenu panels should be set to false when the menu is loaded 
        rules.setVisible(false);
        gameMenu.setVisible(false);
        game.setVisible(false);

        // create the event handler object

        // add the buttons to the event handler 
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        item2.addActionListener(this);

        try { 
            gameBoard = ImageIO.read(getClass().getResourceAsStream("Images/board1.jpg")); }
        catch (IOException error) { 
            error.printStackTrace(); 
        }

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

        // create all of the stocks for the paths
        Stocks readingLines1 = new Stocks(CardTypes.READING, 1, "Reading Lines", 3);
        Stocks readingLines2 = new Stocks(CardTypes.READING, 2, "Reading Lines", 3);
        Stocks readingLines3 = new Stocks(CardTypes.READING, 3, "Reading Lines", 3);
        Stocks readingLines4 = new Stocks(CardTypes.READING, 4, "Reading Lines", 3);
        Stocks readingLines5 = new Stocks(CardTypes.READING, 5, "Reading Lines", 3);
        Stocks readingLines6 = new Stocks(CardTypes.READING, 6, "Reading Lines", 3);
        Stocks readingLines7 = new Stocks(CardTypes.READING, 7, "Reading Lines", 3);
        Stocks lehighValley1 = new Stocks(CardTypes.LEHIGH, 1, "Lehigh Valley", 3);
        Stocks lehighValley2 = new Stocks(CardTypes.LEHIGH, 2, "Lehigh Valley", 3);
        Stocks lehighValley3 = new Stocks(CardTypes.LEHIGH, 3, "Lehigh Valley", 3);
        Stocks lehighValley4 = new Stocks(CardTypes.LEHIGH, 4, "Lehigh Valley", 3);
        Stocks lehighValley5 = new Stocks(CardTypes.LEHIGH, 5, "Lehigh Valley", 3);
        Stocks lehighValley6 = new Stocks(CardTypes.LEHIGH, 6, "Lehigh Valley", 3);
        Stack<Stocks> lehighValley = new Stack<Stocks>();
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
        Stack<Stocks> pennsylvaniaRailroad = new Stack<Stocks>();
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
        Stack<Stocks> jerseyCentralLine = new Stack<Stocks>();
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
        Stack<Stocks> lackawannaErie = new Stack<Stocks>();
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
        Stack<Stocks> nyCentralSystem = new Stack<Stocks>();
        nyCentralSystem.push(nyCentralSystem5);
        nyCentralSystem.push(nyCentralSystem4);
        nyCentralSystem.push(nyCentralSystem3);
        nyCentralSystem.push(nyCentralSystem2);
        nyCentralSystem.push(nyCentralSystem1);
       
        Stocks westernMarylandRailway1 = new Stocks(CardTypes.WM, 1, "Western Maryland Railway", 2);
        Stocks westernMarylandRailway2 = new Stocks(CardTypes.WM, 2, "Western Maryland Railway", 2);
        Stocks westernMarylandRailway3 = new Stocks(CardTypes.WM, 3, "Western Maryland Railway", 2);
        Stocks westernMarylandRailway4 = new Stocks(CardTypes.WM, 4, "Western Maryland Railway", 2);
        Stack<Stocks> westernMarylandRailway = new Stack<Stocks>();
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
        Stack<Stocks> baltimoreandOhioRailroad = new Stack<Stocks>();
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
                trainDeck.push(new TrainTickets(CardTypes.TRAIN, "black", false, new ImageIcon(".\\Images\\Pics\\TrainColors\\TrainColors007")));
                trainDeck.push(new TrainTickets(CardTypes.TRAIN, "blue", false, new ImageIcon(".\\Images\\Pics\\TrainColors\\TrainColors003")));
                trainDeck.push(new TrainTickets(CardTypes.TRAIN, "pink", false, new ImageIcon(".\\Images\\Pics\\TrainColors\\TrainColors002")));
                trainDeck.push(new TrainTickets(CardTypes.TRAIN, "red", false, new ImageIcon(".\\Images\\Pics\\TrainColors\\TrainColors009")));
                trainDeck.push(new TrainTickets(CardTypes.TRAIN, "yellow", false, new ImageIcon(".\\Images\\Pics\\TrainColors\\TrainColors004")));
                trainDeck.push(new TrainTickets(CardTypes.TRAIN, "orange", false, new ImageIcon(".\\Images\\Pics\\TrainColors\\TrainColors006")));
                trainDeck.push(new TrainTickets(CardTypes.TRAIN, "green", false, new ImageIcon(".\\Images\\Pics\\TrainColors\\TrainColors008")));
                trainDeck.push(new TrainTickets(CardTypes.TRAIN, "white", false, new ImageIcon(".\\Images\\Pics\\TrainColors\\TrainColors005")));
                if(i == 0)//locomotives
                {
                    for(int j = 0; j < 20; j++)
                    {
                        trainDeck.push(new TrainTickets(CardTypes.TRAIN, "", true, new ImageIcon(".\\Images\\Pics\\TrainColors\\TrainColors001")));
                    }
                }
            }
            Collections.shuffle(trainDeck);
            trainDiscard = new Stack<TrainTickets>();

            destDeck = new ArrayList<DestinationTickets>();
            destDeck.add(new DestinationTickets(CardTypes.DEST, Syracuse, Allentown, 8, new ImageIcon(".\\Images\\Pics\\Routes\\routes13")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Baltimore, Philadelphia, 4, new ImageIcon(".\\Images\\Pics\\Routes\\routes29")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Philadelphia, AtlanticCity, 2, new ImageIcon(".\\Images\\Pics\\Routes\\routes18")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Towanda, Lancaster, 9, new ImageIcon(".\\Images\\Pics\\Routes\\routes1")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Wheeling, Allentown, 15, new ImageIcon(".\\Images\\Pics\\Routes\\routes4")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Baltimore, NewYork, 10, new ImageIcon(".\\Images\\Pics\\Routes\\routes28")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Harrisburg, Philadelphia, 6, new ImageIcon(".\\Images\\Pics\\Routes\\routes9")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Ontario, Syracuse, 10, new ImageIcon(".\\Images\\Pics\\Routes\\routes16")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, ScrantonWilkesBarre, Allentown, 3, new ImageIcon(".\\Images\\Pics\\Routes\\routes10")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Morgantown, Chambersburg, 7, new ImageIcon(".\\Images\\Pics\\Routes\\routes15")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Pittsburg, Baltimore, 13, new ImageIcon(".\\Images\\Pics\\Routes\\routes50")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Rochester, Reading, 13, new ImageIcon(".\\Images\\Pics\\Routes\\routes40")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Pittsburg, Philadelphia, 15, new ImageIcon(".\\Images\\Pics\\Routes\\routes31")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Buffalo, Harrisburg, 13, new ImageIcon(".\\Images\\Pics\\Routes\\routes21")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Lewiston, Syracuse, 9, new ImageIcon(".\\Images\\Pics\\Routes\\routes7")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Morgantown, Williamsport, 13, new ImageIcon(".\\Images\\Pics\\Routes\\routes2")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, OilCity, Johnstown, 6, new ImageIcon(".\\Images\\Pics\\Routes\\routes47")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Pittsburg, Harrisburg, 9, new ImageIcon(".\\Images\\Pics\\Routes\\routes44")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Pittsburg, NewYork, 20, new ImageIcon(".\\Images\\Pics\\Routes\\routes45")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Altoona, Binghamton, 9, new ImageIcon(".\\Images\\Pics\\Routes\\routes6")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Dubois, Cumberland, 6, new ImageIcon(".\\Images\\Pics\\Routes\\routes19")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Ontario, Warren, 5, new ImageIcon(".\\Images\\Pics\\Routes\\routes17")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Erie, Albany, 20, new ImageIcon(".\\Images\\Pics\\Routes\\routes12")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Harrisburg, NewYork, 11, new ImageIcon(".\\Images\\Pics\\Routes\\routes5")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Ontario, Pittsburg, 10, new ImageIcon(".\\Images\\Pics\\Routes\\routes25")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Rochester, Elmira, 3, new ImageIcon(".\\Images\\Pics\\Routes\\routes11")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Philadelphia, NewYork, 6, new ImageIcon(".\\Images\\Pics\\Routes\\routes49")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Warren, York, 10, new ImageIcon(".\\Images\\Pics\\Routes\\routes36")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Williamsport, Albany, 10, new ImageIcon(".\\Images\\Pics\\Routes\\routes27")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Chambersburg, ScrantonWilkesBarre, 8, new ImageIcon(".\\Images\\Pics\\Routes\\routes34")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Coudersport, Binghamton, 7, new ImageIcon(".\\Images\\Pics\\Routes\\routes35")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, NewYork, AtlanticCity, 6, new ImageIcon(".\\Images\\Pics\\Routes\\routes46")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, OilCity, ScrantonWilkesBarre, 14, new ImageIcon(".\\Images\\Pics\\Routes\\routes48")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Baltimore, Albany, 16, new ImageIcon(".\\Images\\Pics\\Routes\\routes8")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Youngstown, Morgantown, 7, new ImageIcon(".\\Images\\Pics\\Routes\\routes38")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Pittsburg, Buffalo, 10, new ImageIcon(".\\Images\\Pics\\Routes\\routes41")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Gettysburg, Reading, 3, new ImageIcon(".\\Images\\Pics\\Routes\\routes37")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Buffalo, NewYork, 18, new ImageIcon(".\\Images\\Pics\\Routes\\routes23")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Buffalo, Johnstown, 10, new ImageIcon(".\\Images\\Pics\\Routes\\routes22")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Buffalo, Philadelphia, 19, new ImageIcon(".\\Images\\Pics\\Routes\\routes24")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Buffalo, Baltimore, 16, new ImageIcon(".\\Images\\Pics\\Routes\\routes30")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Erie, Altoona, 8, new ImageIcon(".\\Images\\Pics\\Routes\\routes14")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Johnstown, Elmira, 10, new ImageIcon(".\\Images\\Pics\\Routes\\routes39")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Wheeling, Albany, 22, new ImageIcon(".\\Images\\Pics\\Routes\\routes32")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Youngstown, ScrantonWilkesBarre, 17, new ImageIcon(".\\Images\\Pics\\Routes\\routes33")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Youngstown, Rochester, 14, new ImageIcon(".\\Images\\Pics\\Routes\\routes43")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Wheeling, Erie, 9, new ImageIcon(".\\Images\\Pics\\Routes\\routes3")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Dubois, Stroudsburg, 12, new ImageIcon(".\\Images\\Pics\\Routes\\routes20")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Harrisburg, Baltimore, 3, new ImageIcon(".\\Images\\Pics\\Routes\\routes42")));
            destDeck.add(new DestinationTickets(CardTypes.DEST, Cumberland, Harrisburg, 4, new ImageIcon(".\\Images\\Pics\\Routes\\routes26")));

            Collections.shuffle(destDeck);

            // the tracks for all of the cities will be hard coded here
        }
    }

    //     public void setFullScreen(JFrame f){
    // 
    //         f.setUndecorated(true);
    //         f.setResizable(false);
    //         vc.setFullScreenWindow(f);
    // 
    //     }

    /**
     * Returns the nmber of players that will be playing this game of Ticket to Ride 
     * @return int of the number of players playing the game
     */
    public int getNumPlayers()
    {
        int numPlayers = 0;

        return numPlayers;

    }

    public boolean gameStarted()
    {
        return gameStarted;

    }

    public boolean gameEnded()
    {
        return gameEnded;

    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println(x + "," + y);

    }

    public void mouseEntered(MouseEvent e) {
        System.out.println("The mouse entered the frame.");

    }

    public void mouseExited(MouseEvent e) {
        System.out.println("The mouse exited the frame.");

    }

    public void mousePressed(MouseEvent e) {
        System.out.println("The left mouse button was pressed.");

    }

    public void mouseReleased(MouseEvent e) {
        System.out.println("The left mouse button was released.");

    }

    public Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

    /**
     * built in class of the ActionListener clas 
     */
    public void actionPerformed(ActionEvent event)
    {
        String string = "";

        // if they clicked enter on text field number one 
        if(event.getSource() == item2)
        {
            string = String.format("field 1: %s", event.getActionCommand() );

        }
        else if(event.getSource() == item3)
        {
            string = String.format("field 2: %s", event.getActionCommand() );

        }
        else if(event.getSource() == item4)
        {
            string = String.format("field 3: %s", event.getActionCommand() );

        }
        else if(event.getSource() == passwordField)
        {
            string = String.format("password field is : %s", event.getActionCommand() );

        }
        //if they click the instructions button
        else if(event.getSource() == b1) 
        {
            //set which panels should be visible and which ones should not be visible
            menu.setVisible(false);
            gameMenu.setVisible(false);
            game.setVisible(false);
            rules.setVisible(true);

            repaint();
        }
        // if they click the play game button
        else if(event.getSource() == b2) 
        {
            //set which panels should be visible and which ones should not be visible
            menu.setVisible(false);
            rules.setVisible(false);
            game.setVisible(false);
            gameMenu.setVisible(true);
        }
        // if they go back to the main menu 
        else if(event.getSource() == b3) 
        {
            //set which panels should be visible and which ones should not be visible
            rules.setVisible(false);
            gameMenu.setVisible(false);
            game.setVisible(false);
            menu.setVisible(true);
        }
        else if(event.getSource() == b4) 
        {
            //set which panels should be visible and which ones should not be visible
            rules.setVisible(false);
            gameMenu.setVisible(false);
            game.setVisible(false);
            menu.setVisible(true);
        }
        // if the click the submit button then the game has started 
        else if(event.getSource() == b5) 
        {
            //set which panels should be visible and which ones should not be visible
            //rules.setVisible(false);
            //gameMenu.setVisible(false);
            //menu.setVisible(false);

            remove(rules);
            remove(gameMenu);
            remove(menu);

            game.setVisible(true);

            // the width of the image is 1107 and the height is 738
            setSize(1350, 900);
            getContentPane().setBackground(Color.LIGHT_GRAY);
        }
        else if(event.getSource() == twoPlayer)
        {
            rules.setVisible(false);
            gameMenu.setVisible(false);
            menu.setVisible(false);
            game.setVisible(true);
            setSize(1600, 900);
            getContentPane().setBackground(Color.LIGHT_GRAY);

            Player player1 = new Player();
            Player player2 = new Player();
            //numPlayer = 2;
        }
        else if(event.getSource() == threePlayer)
        {
            rules.setVisible(false);
            gameMenu.setVisible(false);
            menu.setVisible(false);
            game.setVisible(true);
            setSize(1600, 900);
            getContentPane().setBackground(Color.LIGHT_GRAY);
            Player player1 = new Player();
            Player player2 = new Player();
            Player player3 = new Player();
            //numPlayer = 3;
        }
        else if(event.getSource() == fourPlayer)
        {
            rules.setVisible(false);
            gameMenu.setVisible(false);
            menu.setVisible(false);
            game.setVisible(true);
            setSize(1600, 900);
            getContentPane().setBackground(Color.LIGHT_GRAY);
            Player player1 = new Player();
            Player player2 = new Player();
            Player player3 = new Player();
            Player player4 = new Player();
            //numPlayer = 4;
        }
        else if(event.getSource() == fivePlayer)
        {
            rules.setVisible(false);
            gameMenu.setVisible(false);
            menu.setVisible(false);
            game.setVisible(true);
            setSize(1600, 900);
            getContentPane().setBackground(Color.LIGHT_GRAY);
            Player player1 = new Player();
            Player player2 = new Player();
            Player player3 = new Player();
            Player player4 = new Player();
            Player player5 = new Player();
            //numPlayer = 5;
        }
        // test conditional for getting the coorindates of the board
        else if(event.getSource() == board)
        {
            System.out.println(getX() );
            System.out.println(getY() );

        }
    }

    /**
     * Paint method
     */

    public void paint(Graphics g) {

        if(game.isVisible())
        {
            g.drawImage(gameBoard,0,0,null);

            g.setColor(Color.BLUE);
            int [] xCoordinates1 = new int [] {1036, 1048, 1048, 1036};
            int [] yCoordinates1 = new int [] {116, 116, 343, 343};
            Polygon p1 = new Polygon(xCoordinates1,yCoordinates1,xCoordinates1.length);
            g.fillPolygon(p1);

        }
    }
}
