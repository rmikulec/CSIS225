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
    protected ArrayList<Polygon> polygons = new ArrayList<Polygon>();
    protected int numberOfPlayers;

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
        twoPlayer.addActionListener(this);
        threePlayer.addActionListener(this);
        fourPlayer.addActionListener(this);
        fivePlayer.addActionListener(this);
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

        Track t1= new Track(Ontario, Erie, "gray", 3, 2, new ArrayList<String>());
        t1.stockOptions.add("Pennsylvania Railroad");
        t1.stockOptions.add("Lackawanna Erie");
        t1.stockOptions.add("Pennsylvania Railroad");

        Track t2= new Track(Ontario, Erie, "gray", 3, 2, new ArrayList<String>());
        t2.stockOptions.add("Pennsylvania Railroad");
        t2.stockOptions.add("Lackawanna Erie");
        t2.stockOptions.add("Pennsylvania Railroad");

        Track t3= new Track(Ontario, Buffalo, "gray", 1, 1, new ArrayList<String>());
        t3.stockOptions.add("Pennsylvania Railroad");
        t3.stockOptions.add("Lackawanna Erie");
        t3.stockOptions.add("Pennsylvania Railroad");

        Track t4= new Track(Ontario, Buffalo, "gray", 1, 1, new ArrayList<String>());
        t4.stockOptions.add("Pennsylvania Railroad");
        t4.stockOptions.add("Lackawanna Erie");
        t4.stockOptions.add("Pennsylvania Railroad");

        Track t5= new Track(Buffalo, Coudersport, "gray", 4, 0, new ArrayList<String>());
        t5.stockOptions.add("Pennsylvania Railroad");
        t5.stockOptions.add("Lackawanna Erie");

        Track t6= new Track(Warren, Coudersport, "gray", 4, 0, new ArrayList<String>());
        t6.stockOptions.add("Pennsylvania Railroad");
        t6.stockOptions.add("Lackawanna Erie");
        t6.stockOptions.add("Pennsylvania Railroad");
        t6.stockOptions.add("Buffalo, Rochester and Pittsburgh Railway");
        t6.stockOptions.add("Baltimore and Ohio Railroad");

        Track t7= new Track(Dubois, Altoona, "gray", 2, 0, new ArrayList<String>());
        t7.stockOptions.add("Pennsylvania Railroad");

        Track t8= new Track(Lewiston, Harrisburg, "gray", 2, 0, new ArrayList<String>());
        t8.stockOptions.add("Pennsylvania Railroad");

        Track t9= new Track(Harrisburg, Lancaster, "gray", 2, 0, new ArrayList<String>());
        t9.stockOptions.add("Pennsylvania Railroad");

        Track t10= new Track(Harrisburg, Lancaster, "gray", 2, 0, new ArrayList<String>());
        t10.stockOptions.add("Pennsylvania Railroad");

        Track t11= new Track(Gettysburg, York, "gray", 1, 0, new ArrayList<String>());
        t11.stockOptions.add("Pennsylvania Railroad");
        t11.stockOptions.add("Western Maryland Railway");

        Track t12= new Track(Philadelphia, AtlanticCity, "gray", 2, 0, new ArrayList<String>());
        t12.stockOptions.add("Pennsylvania Railroad");
        t12.stockOptions.add("Reading Lines");
        t12.stockOptions.add("Jersey Central Line");

        Track t13= new Track(Philadelphia, AtlanticCity, "gray", 2, 0, new ArrayList<String>());
        t13.stockOptions.add("Pennsylvania Railroad");
        t13.stockOptions.add("Reading Lines");
        t13.stockOptions.add("Jersey Central Line");

        Track t14= new Track(Philadelphia, NewYork, "gray", 6, 0, new ArrayList<String>());
        t14.stockOptions.add("Pennsylvania Railroad");
        t14.stockOptions.add("Baltimore and Ohio Railroad");
        t14.stockOptions.add("Reading Lines");
        t14.stockOptions.add("Jersey Central Line");

        Track t15= new Track(Philadelphia, NewYork, "gray", 6, 0, new ArrayList<String>());
        t15.stockOptions.add("Pennsylvania Railroad");
        t15.stockOptions.add("Baltimore and Ohio Railroad");
        t15.stockOptions.add("Reading Lines");
        t15.stockOptions.add("Jersey Central Line");

        Track t16= new Track(Johnstown, Cumberland, "gray", 3, 0, new ArrayList<String>());
        t16.stockOptions.add("Pennsylvania Railroad");
        t16.stockOptions.add("Western Maryland Railway");
        t16.stockOptions.add("Baltimore and Ohio Railroad");

        Track t17= new Track(Harrisburg, ScrantonWilkesBarre, "gray", 6, 0, new ArrayList<String>());
        t17.stockOptions.add("Pennsylvania Railroad");
        t17.stockOptions.add("Reading Lines");

        Track t18= new Track(Towanda, ScrantonWilkesBarre, "gray", 3, 0, new ArrayList<String>());
        t18.stockOptions.add("Pennsylvania Railroad");
        t18.stockOptions.add("Lackawanna Erie");
        t18.stockOptions.add("Lehigh Valley");

        Track t19= new Track(Erie, Buffalo, "White", 5, 0, new ArrayList<String>());
        t19.stockOptions.add("Lackawanna Erie");
        t19.stockOptions.add("New York Central System");

        Track t20= new Track(Elmira, Binghamton, "White", 3, 0, new ArrayList<String>());
        t20.stockOptions.add("Lackawanna Erie");

        Track t21= new Track(Syracuse, Albany, "White", 6, 0, new ArrayList<String>());
        t21.stockOptions.add("New York Central System");

        Track t22= new Track(Youngstown, OilCity, "White", 3, 0, new ArrayList<String>());
        t22.stockOptions.add("Lackawanna Erie");

        Track t23= new Track(Wheeling, Pittsburg, "White", 2, 0, new ArrayList<String>());
        t23.stockOptions.add("Pennsylvania Railroad");

        Track t24= new Track(Dubois, Williamsport, "White", 6, 0, new ArrayList<String>());

        Track t25= new Track(York, Baltimore, "White", 2, 0, new ArrayList<String>());
        t25.stockOptions.add("Western Maryland Railway");
        t25.stockOptions.add("Pennsylvania Railroad");

        Track t26= new Track(ScrantonWilkesBarre, Allentown, "White", 3, 0, new ArrayList<String>());
        t26.stockOptions.add("Pennsylvania Railroad");
        t26.stockOptions.add("Lehigh Valley");
        t26.stockOptions.add("Reading Lines");
        t26.stockOptions.add("Jersey Central Line");

        Track t27= new Track(Syracuse, Albany, "red", 6, 0, new ArrayList<String>());
        t27.stockOptions.add("New York Central System");

        Track t28= new Track(ScrantonWilkesBarre, NewYork, "red", 5, 0, new ArrayList<String>());
        t28.stockOptions.add("Jersey Central Line");
        t28.stockOptions.add("Lackawanna Erie");
        t28.stockOptions.add("Lehigh Valley");

        Track t29= new Track(Allentown, Philadelphia, "red", 3, 0, new ArrayList<String>());
        t29.stockOptions.add("Reading Lines");
        t29.stockOptions.add("Pennsylvania Railroad");

        Track t30= new Track(Gettysburg, Baltimore, "red", 3, 0, new ArrayList<String>());
        t30.stockOptions.add("Pennsylvania Railroad");
        t30.stockOptions.add("Western Maryland Railway");

        Track t31= new Track(Altoona, Harrisburg, "red", 5, 0, new ArrayList<String>());
        t31.stockOptions.add("Pennsylvania Railroad");

        Track t32= new Track(Morgantown, Cumberland, "red", 5, 0, new ArrayList<String>());
        t32.stockOptions.add("Western Maryland Railway");
        t32.stockOptions.add("Baltimore and Ohio Railroad");

        Track t33= new Track(OilCity, Pittsburg, "red", 4, 0, new ArrayList<String>());
        t33.stockOptions.add("Baltimore and Ohio Railroad");
        t33.stockOptions.add("Pennsylvania Railroad");

        Track t34= new Track(Towanda, Binghamton, "red", 2, 0, new ArrayList<String>());
        t34.stockOptions.add("Lackawanna Erie");

        Track t35= new Track(Rochester, Syracuse, "blue", 4, 0, new ArrayList<String>());
        t35.stockOptions.add("New York Central System");
        t35.stockOptions.add("Lehigh Valley");

        Track t36= new Track(Albany, NewYork, "blue", 6, 0, new ArrayList<String>());
        t36.stockOptions.add("New York Central System");
        t36.stockOptions.add("Pennsylvania Railroad");
        t36.stockOptions.add("Baltimore and Ohio Railroad");

        Track t37= new Track(ScrantonWilkesBarre, Allentown, "blue", 3, 0, new ArrayList<String>());
        t37.stockOptions.add("Pennsylvania Railroad");
        t37.stockOptions.add("Lehigh Valley");
        t37.stockOptions.add("Reading Lines");
        t37.stockOptions.add("Jersey Central Line");

        Track t38= new Track(Cumberland, Baltimore, "blue", 7, 0, new ArrayList<String>());
        t38.stockOptions.add("Western Maryland Railway");
        t38.stockOptions.add("Baltimore and Ohio Railroad");

        Track t39= new Track(Wheeling, Morgantown, "blue", 3, 0, new ArrayList<String>());
        t39.stockOptions.add("Baltimore and Ohio Railroad");

        Track t40= new Track(Chambersburg, Harrisburg, "blue", 2, 0, new ArrayList<String>());
        t40.stockOptions.add("Reading Lines");
        t40.stockOptions.add("Pennsylvania Railroad");
        t40.stockOptions.add("Western Maryland Railway");

        Track t41= new Track(Youngstown, Pittsburg, "blue", 4, 0, new ArrayList<String>());
        t41.stockOptions.add("New York Central System");
        t41.stockOptions.add("Pennsylvania Railroad");
        t41.stockOptions.add("Baltimore and Ohio Railroad");

        Track t42= new Track(Erie, Warren, "blue", 3, 0, new ArrayList<String>());
        t42.stockOptions.add("Pennsylvania Railroad");
        t42.stockOptions.add("Lackawanna Erie");
        t42.stockOptions.add("Pennsylvania Railroad");

        Track t43= new Track(Johnstown, Altoona, "blue", 1, 0, new ArrayList<String>());
        t43.stockOptions.add("Pennsylvania Railroad");

        Track t44= new Track(Buffalo, Rochester, "Black", 5, 0, new ArrayList<String>());
        t44.stockOptions.add("New York Central System");
        t44.stockOptions.add("Pennsylvania Railroad");
        t44.stockOptions.add("Baltimore and Ohio Railroad");
        t44.stockOptions.add("Lehigh Valley");
        t44.stockOptions.add("Buffalo, Rochester and Pittsburgh Railway");

        Track t45= new Track(Elmira, Syracuse, "Black", 4, 0, new ArrayList<String>());
        t45.stockOptions.add("Lehigh Valley");
        t45.stockOptions.add("New York Central System");

        Track t46= new Track(Warren, Dubois, "Black", 3, 0, new ArrayList<String>());
        t46.stockOptions.add("Lackawanna Erie");
        t46.stockOptions.add("Buffalo, Rochester and Pittsburgh Railway");

        Track t47= new Track(Erie, OilCity, "Black", 3, 0, new ArrayList<String>());
        t47.stockOptions.add("New York Central System");
        t47.stockOptions.add("Pennsylvania Railroad");
        t47.stockOptions.add("Lackawanna Erie");

        Track t48= new Track(Pittsburg, Johnstown, "Black", 4, 0, new ArrayList<String>());
        t48.stockOptions.add("Pennsylvania Railroad");
        t48.stockOptions.add("Baltimore and Ohio Railroad");

        Track t49= new Track(Chambersburg, Gettysburg, "Black", 1, 0, new ArrayList<String>());

        Track t50= new Track(Harrisburg, York, "Black", 1, 0, new ArrayList<String>());
        t50.stockOptions.add("Pennsylvania Railroad");
        t50.stockOptions.add("Western Maryland Railway");

        Track t51= new Track(Allentown, Philadelphia, "Black", 3, 0, new ArrayList<String>());
        t51.stockOptions.add("Pennsylvania Railroad");
        t51.stockOptions.add("Reading Lines");

        Track t52= new Track(NewYork, AtlanticCity, "Black", 6, 0, new ArrayList<String>());
        t52.stockOptions.add("Jersey Central Line");

        Track t53= new Track(Binghamton, ScrantonWilkesBarre, "Black", 3, 0, new ArrayList<String>());
        t53.stockOptions.add("Lackawanna Erie");

        Track t54= new Track(Williamsport, Towanda, "Black", 2, 0, new ArrayList<String>());
        t54.stockOptions.add("Lackawanna Erie");
        t54.stockOptions.add("Reading Lines");

        Track t55= new Track(Buffalo, Warren, "Green", 4, 0, new ArrayList<String>());
        t55.stockOptions.add("Lackawanna Erie");
        t55.stockOptions.add("Pennsylvania Railroad");
        t55.stockOptions.add("Baltimore and Ohio Railroad");
        t55.stockOptions.add("Buffalo, Rochester and Pittsburgh Railway");

        Track t56= new Track(Erie, Youngstown, "Green", 4, 0, new ArrayList<String>());
        t56.stockOptions.add("Lackawanna Erie");
        t56.stockOptions.add("New York Central System");

        Track t57= new Track(Wheeling, Pittsburg, "Green", 2, 0, new ArrayList<String>());
        t57.stockOptions.add("Pennsylvania Railroad");

        Track t58= new Track(Cumberland, Chambersburg, "Green", 2, 0, new ArrayList<String>());
        t58.stockOptions.add("Western Maryland Railway");
        t58.stockOptions.add("Pennsylvania Railroad");

        Track t59= new Track(Lancaster, Philadelphia, "Green", 4, 0, new ArrayList<String>());
        t59.stockOptions.add("Pennsylvania Railroad");

        Track t60= new Track(Reading, Allentown, "Green", 2, 0, new ArrayList<String>());
        t60.stockOptions.add("Reading Lines");

        Track t61= new Track(Coudersport, Williamsport, "Green", 4, 0, new ArrayList<String>());
        t61.stockOptions.add("Pennsylvania Railroad");

        Track t62= new Track(Altoona, Lewiston, "Green", 2, 0, new ArrayList<String>());

        Track t63= new Track(Albany, NewYork, "Green", 6, 0, new ArrayList<String>());
        t63.stockOptions.add("Pennsylvania Railroad");
        t63.stockOptions.add("New York Central System");
        t63.stockOptions.add("Baltimore and Ohio Railroad");

        Track t64= new Track(Binghamton, ScrantonWilkesBarre, "Green", 3, 0, new ArrayList<String>());
        t64.stockOptions.add("Lackawanna Erie");

        Track t65= new Track(Rochester, Elmira, "Green", 3, 0, new ArrayList<String>());
        t64.stockOptions.add("Lackawanna Erie");
        t64.stockOptions.add("Pennsylvania Railroad");
        t64.stockOptions.add("Baltimore and Ohio Railroad");
        t64.stockOptions.add("Lehigh Valley");
        t64.stockOptions.add("Buffalo, Rochester and Pittsburgh Railway");

        Track t66= new Track(Buffalo, Rochester, "Yellow", 4, 0, new ArrayList<String>());
        t66.stockOptions.add("New York Central System");
        t66.stockOptions.add("Pennsylvania Railroad");
        t66.stockOptions.add("Baltimore and Ohio Railroad");
        t66.stockOptions.add("Lehigh Valley");
        t66.stockOptions.add("Buffalo, Rochester and Pittsburgh Railway");

        Track t67= new Track(Erie, Youngstown, "Yellow", 4, 0, new ArrayList<String>());
        t67.stockOptions.add("Lackawanna Erie");
        t67.stockOptions.add("New York Central System");

        Track t68= new Track(Harrisburg, Gettysburg, "Yellow", 2, 0, new ArrayList<String>());
        t68.stockOptions.add("Pennsylvania Railroad");
        t68.stockOptions.add("Reading Lines");

        Track t69= new Track(Baltimore, Philadelphia, "Yellow", 4, 0, new ArrayList<String>());
        t69.stockOptions.add("Pennsylvania Railroad");
        t69.stockOptions.add("Baltimore and Ohio Railroad");

        Track t70= new Track(ScrantonWilkesBarre, Stroudsburg, "Yellow", 2, 0, new ArrayList<String>());
        t70.stockOptions.add("Reading Lines");
        t70.stockOptions.add("Lehigh Valley");
        t70.stockOptions.add("Jersey Central Line");

        Track t71= new Track(Lewiston, Williamsport, "Yellow", 3, 0, new ArrayList<String>());
        t71.stockOptions.add("Pennsylvania Railroad");
        t71.stockOptions.add("Reading Lines");

        Track t72= new Track(Elmira, Towanda, "Yellow", 2, 0, new ArrayList<String>());
        t72.stockOptions.add("Pennsylvania Railroad");
        t72.stockOptions.add("Lehigh Valley");
        t72.stockOptions.add("Lackawanna Erie");

        Track t73= new Track(Altoona, Johnstown, "Yellow", 1, 0, new ArrayList<String>());
        t73.stockOptions.add("Pennsylvania Railroad");

        Track t74= new Track(Pittsburg, Morgantown, "Yellow", 3, 0, new ArrayList<String>());

        Track t75= new Track(Reading, Lancaster, "Yellow", 1, 0, new ArrayList<String>());
        t75.stockOptions.add("Reading Lines");

        Track t76= new Track(Syracuse, Binghamton, "Yellow", 2, 0, new ArrayList<String>());
        t76.stockOptions.add("Lackawanna Erie");

        Track t77= new Track(Erie, Buffalo, "Orange", 5, 0, new ArrayList<String>());
        t77.stockOptions.add("Lackawanna Erie");
        t77.stockOptions.add("New York Central System");

        Track t78= new Track(OilCity, Warren, "Orange", 2, 0, new ArrayList<String>());
        t78.stockOptions.add("Lackawanna Erie");
        t78.stockOptions.add("New York Central System");
        t78.stockOptions.add("Baltimore and Ohio Railroad");

        Track t79= new Track(Coudersport, Elmira, "Orange", 4, 0, new ArrayList<String>());
        t79.stockOptions.add("Lackawanna Erie");
        t79.stockOptions.add("New York Central System");
        t79.stockOptions.add("Baltimore and Ohio Railroad");
        t79.stockOptions.add("Lackawanna Erie");
        t79.stockOptions.add("Buffalo, Rochester and Pittsburgh Railway");

        Track t80= new Track(Syracuse, Binghamton, "Orange", 2, 0, new ArrayList<String>());
        t80.stockOptions.add("Lackawanna Erie");

        Track t81= new Track(Williamsport, ScrantonWilkesBarre, "Orange", 5, 0, new ArrayList<String>());

        Track t82= new Track(Altoona, Harrisburg, "Orange", 5, 0, new ArrayList<String>());
        t82.stockOptions.add("Pennsylvania Railroad");

        Track t83= new Track(Lancaster, Philadelphia, "Orange", 4, 0, new ArrayList<String>());
        t83.stockOptions.add("Pennsylvania Railroad");

        Track t84= new Track(Youngstown, Pittsburg, "Orange", 4, 0, new ArrayList<String>());
        t84.stockOptions.add("Pennsylvania Railroad");
        t84.stockOptions.add("Baltimore and Ohio Railroad");
        t84.stockOptions.add("New York Central System");

        Track t85= new Track(Allentown, Stroudsburg, "Orange", 2, 0, new ArrayList<String>());
        t85.stockOptions.add("Reading Lines");
        t85.stockOptions.add("Lehigh Valley");
        t85.stockOptions.add("Jersey Central Line");

        Track t86= new Track(Albany, Binghamton, "Pink", 6, 0, new ArrayList<String>());

        Track t87= new Track(Reading, Harrisburg, "Pink", 2, 0, new ArrayList<String>());
        t87.stockOptions.add("Reading Lines");

        Track t88= new Track(Pittsburg, Altoona, "Pink", 4, 0, new ArrayList<String>());
        t88.stockOptions.add("Pennsylvania Railroad");
        t88.stockOptions.add("Baltimore and Ohio Railroad");

        Track t89= new Track(Youngstown, Wheeling, "Pink", 5, 0, new ArrayList<String>());
        t89.stockOptions.add("Pennsylvania Railroad");
        t89.stockOptions.add("Baltimore and Ohio Railroad");
        t89.stockOptions.add("New York Central System");

        Track t90= new Track(OilCity, Dubois, "Pink", 3, 0, new ArrayList<String>());

        Track t91= new Track(York, Lancaster, "Pink", 1, 0, new ArrayList<String>());
        t91.stockOptions.add("Pennsylvania Railroad");

        Track t92= new Track(Baltimore, Philadelphia, "Pink", 4, 0, new ArrayList<String>());
        t92.stockOptions.add("Pennsylvania Railroad");
        t92.stockOptions.add("Baltimore and Ohio Railroad");

        Track t93= new Track(Rochester, Syracuse, "Pink", 4, 0, new ArrayList<String>());
        t93.stockOptions.add("New York Central System");
        t93.stockOptions.add("Lehigh Valley");

        Track t94= new Track(ScrantonWilkesBarre, NewYork, "Pink", 5, 0, new ArrayList<String>());
        t94.stockOptions.add("Jersey Central Line");
        t94.stockOptions.add("Lehigh Valley");
        t94.stockOptions.add("Lackawanna Erie");

        // create all of the stocks for the paths
        Stocks readingLines1 = new Stocks(CardTypes.READING, 1, "Reading Lines", 3);
        Stocks readingLines2 = new Stocks(CardTypes.READING, 2, "Reading Lines", 3);
        Stocks readingLines3 = new Stocks(CardTypes.READING, 3, "Reading Lines", 3);
        Stocks readingLines4 = new Stocks(CardTypes.READING, 4, "Reading Lines", 3);
        Stocks readingLines5 = new Stocks(CardTypes.READING, 5, "Reading Lines", 3);
        Stocks readingLines6 = new Stocks(CardTypes.READING, 6, "Reading Lines", 3);
        Stocks readingLines7 = new Stocks(CardTypes.READING, 7, "Reading Lines", 3);
        Stack<Stocks> readingLines = new Stack<Stocks>();
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

    public void TakeTurn()
    {

    }

    public void calculateScore()
    {

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
     * Paint method for changing stuff on the JFrame
     */

    public void paint(Graphics g) {
        if(game.isVisible())
        {
            g.drawImage(gameBoard,0,0,null);

            g.setColor(Color.magenta);
            // ferry route from Ontario to Erie 
            int [] xCoordinates1 = new int [] {157, 160, 106, 98, 95, 151};
            int [] yCoordinates1 = new int [] {163, 156, 68, 67, 76, 165};
            // create the new Polygon that will be the path name 
            Polygon p1 = new Polygon(xCoordinates1, yCoordinates1, xCoordinates1.length);
            g.fillPolygon(p1);
            polygons.add(p1);
            // ferry route from Ontario to Erie 
            int [] xCoordinates2 = new int [] {146, 148, 93, 87, 83, 139};
            int [] yCoordinates2 = new int [] {170, 165, 74, 75, 82, 172};
            // create the new Polygon that will be the path name 
            Polygon p2 = new Polygon(xCoordinates2, yCoordinates2, xCoordinates2.length);
            g.fillPolygon(p2);
            polygons.add(p2);
            // ferry route from Buffalo to Ontario 
            int [] xCoordinates3 = new int [] {331, 326, 299, 293, 298, 326};
            int [] yCoordinates3 = new int [] {53, 47, 47, 53, 59, 59};
            // create the new Polygon that will be the path name 
            Polygon p3 = new Polygon(xCoordinates3, yCoordinates3, xCoordinates3.length);
            g.fillPolygon(p3);
            polygons.add(p3);
            int [] xCoordinates4 = new int [] {331, 327, 297, 293, 297, 327};
            int [] yCoordinates4 = new int [] {67, 61, 60, 68, 74, 73};
            // create the new Polygon that will be the path name 
            Polygon p4 = new Polygon(xCoordinates4, yCoordinates4, xCoordinates4.length);
            g.fillPolygon(p4);
            polygons.add(p4);
            // change the color to gray
            g.setColor(Color.gray);
            // The gray path from Buffalo to Coudersport
            int [] xCoordinates5 = new int [] {384, 395, 468, 456};
            int [] yCoordinates5 = new int [] {93, 87, 220, 226};
            // create the new Polygon that will be the path name 
            Polygon p5 = new Polygon(xCoordinates5, yCoordinates5, xCoordinates5.length);
            g.fillPolygon(p5);
            polygons.add(p5);
            // the gray path for Warren to Coudersport
            int [] xCoordinates6 = new int [] {309, 344, 346, 381, 384, 420, 422, 458, 461, 424, 421, 385, 382, 345, 342, 306};
            int [] yCoordinates6 = new int [] {238, 246, 246, 247, 247, 246, 245, 239, 252, 258, 258, 260, 260, 258, 257, 251};
            // create the new Polygon that will be the path name 
            Polygon p6 = new Polygon(xCoordinates6, yCoordinates6, xCoordinates6.length);
            g.fillPolygon(p6);
            polygons.add(p6);
            // The gray path from Altoona to Dubois
            int [] xCoordinates7 = new int [] {367, 378, 410, 399};
            int [] yCoordinates7 = new int [] {406, 400, 468, 474};
            // create the new Polygon that will be the path name 
            Polygon p7 = new Polygon(xCoordinates7, yCoordinates7, xCoordinates7.length);
            g.fillPolygon(p7);
            polygons.add(p7);
            // The gray path from Lewiston to Harrisburg
            int [] xCoordinates8 = new int [] {562, 569, 634, 628};
            int [] yCoordinates8 = new int [] {491, 481, 518, 528};
            // create the new Polygon that will be the path name 
            Polygon p8 = new Polygon(xCoordinates8, yCoordinates8, xCoordinates8.length);
            polygons.add(p8);
            // The grey path from Harrisburg to Lancaster
            int [] xCoordinates9 = new int [] {660, 665, 732, 726};
            int [] yCoordinates9 = new int [] {546, 536, 569, 580};
            // create the new Polygon that will be the path name 
            Polygon p9 = new Polygon(xCoordinates9, yCoordinates9, xCoordinates9.length);
            g.fillPolygon(p9);
            polygons.add(p9);
            // The grey path from Harrisburg to Lancaster
            int [] xCoordinates10 = new int [] {654, 659, 725, 720};
            int [] yCoordinates10 = new int [] {558, 548, 581, 592};
            // create the new Polygon that will be the path name 
            Polygon p10 = new Polygon(xCoordinates10, yCoordinates10, xCoordinates10.length);
            g.fillPolygon(p10);
            polygons.add(p10);
            // The gray path from Gettysburg to York 
            int [] xCoordinates11 = new int [] {651, 656, 623, 618};
            int [] yCoordinates11 = new int [] {607, 619, 633, 620};
            // create the new Polygon that will be the path name 
            Polygon p11 = new Polygon(xCoordinates11, yCoordinates11, xCoordinates11.length);
            g.fillPolygon(p11);
            polygons.add(p11);
            // The gray path from Atlantic City to Philadelphia 
            int [] xCoordinates12 = new int [] {953, 1027, 1026, 952};
            int [] yCoordinates12 = new int [] {607, 615, 626, 618};
            // create the new Polygon that will be the path name 
            Polygon p12 = new Polygon(xCoordinates12, yCoordinates12, xCoordinates12.length);
            g.fillPolygon(p12);
            polygons.add(p12);
            // The gray path from Atlantic City to Philadelphia 
            int [] xCoordinates13 = new int [] {951, 1025, 1025, 950};
            int [] yCoordinates13 = new int [] {621, 629, 640, 633};
            // create the new Polygon that will be the path name 
            Polygon p13 = new Polygon(xCoordinates13, yCoordinates13, xCoordinates13.length);
            g.fillPolygon(p13);
            polygons.add(p13);
            // The gray path from New York to Philadelphia 
            int [] xCoordinates14 = new int [] {1009, 1019, 942, 931};
            int [] yCoordinates14 = new int [] {374, 379, 591, 588};
            // create the new Polygon that will be the path name 
            Polygon p14 = new Polygon(xCoordinates14, yCoordinates14, xCoordinates14.length);
            g.fillPolygon(p14);
            polygons.add(p14);
            // The gray path from New York to Philadelphia 
            int [] xCoordinates15 = new int [] {1021, 1033, 955, 943};
            int [] yCoordinates15 = new int [] {379, 384, 596, 592};
            // create the new Polygon that will be the path name 
            Polygon p15 = new Polygon(xCoordinates15, yCoordinates15, xCoordinates15.length);
            g.fillPolygon(p15);
            polygons.add(p15);
            // The gray path from Johnstown to Cumberland
            int [] xCoordinates16 = new int [] {353, 364, 416, 405};
            int [] yCoordinates16 = new int [] {557, 551, 651, 658};
            // create the new Polygon that will be the path name 
            Polygon p16 = new Polygon(xCoordinates16, yCoordinates16, xCoordinates16.length);
            g.fillPolygon(p16);
            polygons.add(p16);
            // The grey path from Harrisburg to Scranton/Wilkes Barre  
            int [] xCoordinates17 = new int [] {807, 816, 662, 653};
            int [] yCoordinates17 = new int [] {349, 358, 524, 515};
            // create the new Polygon that will be the path name 
            Polygon p17 = new Polygon(xCoordinates17, yCoordinates17, xCoordinates17.length);
            g.fillPolygon(p17);
            polygons.add(p17);
            // The grey path from Towanda to Scranton/Wilkes Barre  
            int [] xCoordinates18 = new int [] {719, 726, 822, 815};
            int [] yCoordinates18 = new int [] {270, 259, 318, 328};
            // create the new Polygon that will be the path name 
            Polygon p18 = new Polygon(xCoordinates18, yCoordinates18, xCoordinates18.length);
            g.fillPolygon(p18);
            polygons.add(p18);
            //change the color to white
            g.setColor(Color.white);
            // the white path from Erie to Buffalo 
            int [] xCoordinates19 = new int [] {184, 218, 220, 253, 256, 288, 291, 321, 323, 351, 359, 331, 328, 298, 295, 263, 259, 226, 223, 189};
            int [] yCoordinates19 = new int [] {169, 156, 156, 140, 140, 121, 120, 100, 99, 76, 85, 108, 110, 130, 132, 150, 151, 166, 167, 180};
            // create the new Polygon that will be the path name 
            Polygon p19 = new Polygon(xCoordinates19, yCoordinates19, xCoordinates19.length);
            g.fillPolygon(p19);
            polygons.add(p19);
            // The white path from Elmira to Binghamton 
            int [] xCoordinates20 = new int [] {673, 786, 786, 673};
            int [] yCoordinates20 = new int [] {176, 176, 189, 189};
            // create the new Polygon that will be the path name 
            Polygon p20 = new Polygon(xCoordinates20, yCoordinates20, xCoordinates20.length);
            g.fillPolygon(p20);
            polygons.add(p20);
            // The white path from Albany to Syracuse
            int [] xCoordinates21 = new int [] {803, 1029, 1028, 803};
            int [] yCoordinates21 = new int [] {47, 70, 82, 59}; 
            // create the new Polygon that will be the path name 
            Polygon p21 = new Polygon(xCoordinates21, yCoordinates21, xCoordinates21.length);
            g.fillPolygon(p21);
            polygons.add(p21);
            // The white path from Youngstown to Oil City 
            int [] xCoordinates22 = new int [] {187, 190, 81, 78};
            int [] yCoordinates22 = new int [] {325, 338, 366, 354};
            // create the new Polygon that will be the path name 
            Polygon p22 = new Polygon(xCoordinates22, yCoordinates22, xCoordinates22.length);
            g.fillPolygon(p22);
            polygons.add(p22);
            // The white path from Pittsburg to Wheeling 
            int [] xCoordinates23 = new int [] {146, 155, 96, 88};
            int [] yCoordinates23 = new int [] {527, 537, 583, 573};
            // create the new Polygon that will be the path name 
            Polygon p23 = new Polygon(xCoordinates23, yCoordinates23, xCoordinates23.length);
            g.fillPolygon(p23);
            polygons.add(p23);
            // The white path from Dubois to Williamsport
            int [] xCoordinates24 = new int [] {377, 376, 602, 602};
            int [] yCoordinates24 = new int [] {390, 378, 355, 367};
            // create the new Polygon that will be the path name 
            Polygon p24 = new Polygon(xCoordinates24, yCoordinates24, xCoordinates24.length);
            g.fillPolygon(p24);          
            polygons.add(p24);
            // The white path from York to Baltimore 
            int [] xCoordinates25 = new int [] {675, 684, 738, 729};
            int [] yCoordinates25 = new int [] {623, 614, 666, 675};
            // create the new Polygon that will be the path name 
            Polygon p25 = new Polygon(xCoordinates25, yCoordinates25, xCoordinates25.length);
            g.fillPolygon(p25);
            polygons.add(p25);
            // the white path for Scranton/Wilkes Barre to Allentown
            int [] xCoordinates26 = new int [] {821, 819, 820, 823, 824, 834, 846, 835, 835, 831, 831, 833};
            int [] yCoordinates26 = new int [] {350, 386, 390, 426, 430, 465, 462, 427, 425, 389, 387, 350};
            // create the new Polygon that will be the path name 
            Polygon p26 = new Polygon(xCoordinates26, yCoordinates26, xCoordinates26.length);
            g.fillPolygon(p26);
            polygons.add(p26);
            // the white route for New York to Atlantic City 
            int [] xCoordinates27 = new int [] {1049, 1062, 1062, 1049};
            int [] yCoordinates27 = new int [] {384, 384, 610, 611}; 
            // create the new Polygon that will be the path name 
            Polygon p27 = new Polygon(xCoordinates27, yCoordinates27, xCoordinates27.length);
            g.fillPolygon(p27);
            polygons.add(p27);
            // change the color to red
            g.setColor(Color.red);
            // The red path from Albany to Syracuse
            int [] xCoordinates28 = new int [] {802, 1028, 1027, 802};
            int [] yCoordinates28 = new int [] {61, 84, 95, 73}; 
            // create the new Polygon that will be the path name 
            Polygon p28 = new Polygon(xCoordinates28, yCoordinates28, xCoordinates28.length);
            g.fillPolygon(p28);
            polygons.add(p28);
            // The red path from Scranton/Wilkes Barre to New York  
            int [] xCoordinates29 = new int [] {850, 886, 889, 926, 930, 965, 970, 1005, 1007, 1041, 1037, 1004, 1002, 966, 963, 928, 925, 889, 885, 850};
            int [] yCoordinates29 = new int [] {316, 316, 316, 319, 320, 327, 328, 336, 337, 350, 361, 348, 347, 338, 337, 331, 331, 328, 328, 327};
            // create the new Polygon that will be the path name 
            Polygon p29 = new Polygon(xCoordinates29, yCoordinates29, xCoordinates29.length);
            g.fillPolygon(p29);
            polygons.add(p29);
            // The red path from Philidelphia to Allentown 
            int [] xCoordinates30 = new int [] {851, 862, 913, 902};
            int [] yCoordinates30 = new int [] {499, 493, 593, 599};
            // create the new Polygon that will be the path name 
            Polygon p30 = new Polygon(xCoordinates30, yCoordinates30, xCoordinates30.length);
            g.fillPolygon(p30);
            polygons.add(p30);
            // The red path from Gettysburg to Baltimore 
            int [] xCoordinates31 = new int [] {612, 616, 722, 718};
            int [] yCoordinates31 = new int [] {646, 636, 674, 685};
            // create the new Polygon that will be the path name 
            Polygon p31 = new Polygon(xCoordinates31, yCoordinates31, xCoordinates31.length);
            g.fillPolygon(p31);
            polygons.add(p31);
            // the red path for Altoona to Harrisburg 
            int [] xCoordinates32 = new int [] {437, 470, 472, 506, 508, 543, 545, 581, 583, 619, 619, 582, 579, 543, 541, 506, 502, 468, 465, 433};
            int [] yCoordinates32 = new int [] {486, 502, 503, 514, 515, 524, 524, 530, 530, 532, 544, 542, 541, 535, 534, 525, 525, 512, 512, 498};
            // create the new Polygon that will be the path name 
            Polygon p32 = new Polygon(xCoordinates32, yCoordinates32, xCoordinates32.length);
            g.fillPolygon(p32);
            polygons.add(p32);
            // The red path from Cumberland to Morgantown 
            int [] xCoordinates33 = new int [] {201, 201, 390, 390};
            int [] yCoordinates33 = new int [] {694, 682, 682, 694};
            // create the new Polygon that will be the path name 
            Polygon p33 = new Polygon(xCoordinates33, yCoordinates33, xCoordinates33.length);
            g.fillPolygon(p33);
            polygons.add(p33);
            // The red path from Pittsburg to Oil City 
            int [] xCoordinates34 = new int [] {197, 210, 184, 171};
            int [] yCoordinates34 = new int [] {341, 343, 492, 490};
            // create the new Polygon that will be the path name 
            Polygon p34 = new Polygon(xCoordinates34, yCoordinates34, xCoordinates34.length);
            g.fillPolygon(p34);
            polygons.add(p34);
            // The red path from Towanda to Binghamton 
            int [] xCoordinates35 = new int [] {784, 791, 731, 723};
            int [] yCoordinates35 = new int [] {194, 204, 247, 237};
            polygons.add(p34);
            // create the new Polygon that will be the path name 
            Polygon p35 = new Polygon(xCoordinates35, yCoordinates35, xCoordinates35.length);
            g.fillPolygon(p35);
            polygons.add(p35);
            // change the color to pink
            g.setColor(Color.blue);
            // The blue path from Syracuse to Rochester
            int [] xCoordinates36 = new int [] {614, 765, 765, 614};
            int [] yCoordinates36 = new int [] {63, 63, 75, 75}; 
            // create the new Polygon that will be the path name 
            Polygon p36 = new Polygon(xCoordinates36, yCoordinates36, xCoordinates36.length);
            g.fillPolygon(p36);
            polygons.add(p36);
            // the blue route for Albany to New York 
            int [] xCoordinates37 = new int [] {1036, 1048, 1048, 1036};
            int [] yCoordinates37 = new int [] {116, 116, 343, 343};
            // create the new Polygon that will be the path name 
            Polygon p37 = new Polygon(xCoordinates37,yCoordinates37,xCoordinates37.length);
            g.fillPolygon(p37);
            polygons.add(p37);
            // the blue path for Scranton/Wilkes Barre to Allentown
            int [] xCoordinates38 = new int [] {835, 833, 833, 836, 837, 848, 860, 849, 849, 845, 846, 847};
            int [] yCoordinates38 = new int [] {348, 384, 388, 424, 429, 463, 459, 426, 423, 388, 385, 349};
            // create the new Polygon that will be the path name 
            Polygon p38 = new Polygon(xCoordinates38, yCoordinates38, xCoordinates38.length);
            g.fillPolygon(p38);
            polygons.add(p38);
            // The blue path from Baltimore to Cumberland
            int [] xCoordinates39 = new int [] {438, 438, 704, 704};
            int [] yCoordinates39 = new int [] {694, 681, 682, 694};
            // create the new Polygon that will be the path name 
            Polygon p39 = new Polygon(xCoordinates39, yCoordinates39, xCoordinates39.length);
            g.fillPolygon(p39);
            polygons.add(p39);
            // The blue path from Morgantown to Wheeling 
            int [] xCoordinates40 = new int [] {72, 88, 92, 119, 123, 157, 162, 128, 127, 101, 99, 83};
            int [] yCoordinates40 = new int [] {607, 640, 643, 668, 671, 685, 675, 660, 659, 635, 633, 601};
            // create the new Polygon that will be the path name 
            Polygon p40 = new Polygon(xCoordinates40,yCoordinates40, xCoordinates40.length);
            g.fillPolygon(p40);
            polygons.add(p40);
            // The blue path from Chambersburg to Harrisburg
            int [] xCoordinates41 = new int [] {541, 535, 602, 609};
            int [] yCoordinates41 = new int [] {604, 594, 561, 573};
            // create the new Polygon that will be the path name 
            Polygon p41 = new Polygon(xCoordinates41, yCoordinates41, xCoordinates41.length);
            g.fillPolygon(p41);
            polygons.add(p41);
            // The blue path from Pittsburg to Youngstown 
            int [] xCoordinates42 = new int [] {79, 94, 95, 114, 115, 138, 139, 166, 157, 131, 129, 106, 104, 85, 84, 68 };
            int [] yCoordinates42 = new int [] {369, 402, 404, 434, 436, 464, 465, 491, 499, 474, 472, 444, 441, 411, 407, 375};
            // create the new Polygon that will be the path name 
            Polygon p42 = new Polygon(xCoordinates42, yCoordinates42, xCoordinates42.length);
            g.fillPolygon(p42);
            polygons.add(p42);
            // the blue path from Erie to Warren 
            int [] xCoordinates43 = new int [] {174, 179, 283, 278};
            int [] yCoordinates43 = new int [] {194, 182, 225, 236};
            // create the new Polygon that will be the path name 
            Polygon p43 = new Polygon(xCoordinates43, yCoordinates43, xCoordinates43.length);
            g.fillPolygon(p43);
            polygons.add(p43);
            // The blue path from Altoona to Johnstown
            int [] xCoordinates44 = new int [] {395, 402, 371, 365};
            int [] yCoordinates44 = new int [] {504, 514, 533, 522};
            // create the new Polygon that will be the path name 
            Polygon p44 = new Polygon(xCoordinates44, yCoordinates44, xCoordinates44.length);
            g.fillPolygon(p44);
            polygons.add(p44);
            // change the color to black 
            g.setColor(Color.black);
            // The black path from Rochester to Buffalo  
            int [] xCoordinates45 = new int [] {387, 576, 576, 387};
            int [] yCoordinates45 = new int [] {63, 63, 75, 75};
            // create the new Polygon that will be the path name 
            Polygon p45 = new Polygon(xCoordinates45, yCoordinates45, xCoordinates45.length);
            g.fillPolygon(p45);
            polygons.add(p45);
            // The black path from Syracuse to Elmira
            int [] xCoordinates46 = new int [] {768, 776, 658, 651};
            int [] yCoordinates46 = new int [] {77, 87, 179, 170};
            // create the new Polygon that will be the path name 
            Polygon p46 = new Polygon(xCoordinates46, yCoordinates46, xCoordinates46.length);
            g.fillPolygon(p46);
            polygons.add(p46);
            // The black path from Warren to Dubois
            int [] xCoordinates47 = new int [] {300, 311, 358, 348};
            int [] yCoordinates47 = new int [] {264, 258, 360, 365};
            // create the new Polygon that will be the path name 
            Polygon p47 = new Polygon(xCoordinates47, yCoordinates47, xCoordinates47.length);
            g.fillPolygon(p47);
            polygons.add(p47);
            // the black path from Oil City to Erie 
            int [] xCoordinates48 = new int [] {160, 171, 209, 197};
            int [] yCoordinates48 = new int [] {201, 197, 303, 308};
            // create the new Polygon that will be the path name 
            Polygon p48 = new Polygon(xCoordinates48, yCoordinates48, xCoordinates48.length);
            g.fillPolygon(p48);
            polygons.add(p48);
            // The black path from Pittsburg to Johnstown 
            int [] xCoordinates49 = new int [] {182, 216, 218, 254, 256, 292, 330, 331, 295, 292, 256, 252, 217, 213, 178};
            int [] yCoordinates49 = new int [] {517, 527, 528, 533, 534, 535, 532, 543, 546, 546, 546, 545, 539, 538, 528};
            // create the new Polygon that will be the path name 
            Polygon p49 = new Polygon(xCoordinates49, yCoordinates49, xCoordinates49.length);
            g.fillPolygon(p49);
            polygons.add(p49);
            // The black path from Chambersburg to Gettysburg
            int [] xCoordinates50 = new int [] {541, 545, 579, 575};
            int [] yCoordinates50 = new int [] {624, 613, 625, 636};
            // create the new Polygon that will be the path name 
            Polygon p50 = new Polygon(xCoordinates50, yCoordinates50, xCoordinates50.length);
            g.fillPolygon(p50);
            polygons.add(p50);
            // The black path from York to Harrisburg 
            int [] xCoordinates51 = new int [] {639, 649, 666, 656};
            int [] yCoordinates51 = new int [] {567, 562, 594, 599};
            // create the new Polygon that will be the path name 
            Polygon p51 = new Polygon(xCoordinates51, yCoordinates51, xCoordinates51.length);
            g.fillPolygon(p51);
            polygons.add(p51);
            // The black path from Philidelphia to Allentown 
            int [] xCoordinates52 = new int [] {863, 874, 925, 915};
            int [] yCoordinates52 = new int [] {492, 487, 586, 592};
            // create the new Polygon that will be the path name 
            Polygon p52 = new Polygon(xCoordinates52, yCoordinates52, xCoordinates52.length);
            g.fillPolygon(p52);
            polygons.add(p52);
            // the black route for New York too Atlantic City 
            int [] xCoordinates53 = new int [] {1036, 1049, 1049, 1036};
            int [] yCoordinates53 = new int [] {384, 384, 610, 611}; 
            // create the new Polygon that will be the path name 
            Polygon p53 = new Polygon(xCoordinates53, yCoordinates53, xCoordinates53.length);
            g.fillPolygon(p53);
            polygons.add(p53);
            // The black path from Binghamton to Scranton/Wilkes Barre 
            int [] xCoordinates54 = new int [] {802, 814, 833, 822};
            int [] yCoordinates54 = new int [] {200, 198, 309, 310};
            // create the new Polygon that will be the path name 
            Polygon p54 = new Polygon(xCoordinates54, yCoordinates54, xCoordinates54.length);
            g.fillPolygon(p54);
            polygons.add(p54);
            // The black path from Williamsport to Towanda 
            int [] xCoordinates55 = new int [] {686, 694, 644, 634};
            int [] yCoordinates55 = new int [] {274, 282, 336, 328};
            // create the new Polygon that will be the path name 
            Polygon p55 = new Polygon(xCoordinates55, yCoordinates55, xCoordinates55.length);
            g.fillPolygon(p55);
            polygons.add(p55);
            // change the color to gray
            g.setColor(Color.green);
            // The green path from Buffalo to Warren
            int [] xCoordinates56 = new int [] {367, 378, 310, 299};
            int [] yCoordinates56 = new int [] {85, 91, 226, 219};
            // create the new Polygon that will be the path name 
            Polygon p56 = new Polygon(xCoordinates56, yCoordinates56, xCoordinates56.length);
            g.fillPolygon(p56);
            polygons.add(p56);
            // the green path from Youngstown to Erie 
            int [] xCoordinates57 = new int [] {143, 154, 86, 75};
            int [] yCoordinates57 = new int [] {208, 214, 348, 342};
            // create the new Polygon that will be the path name 
            Polygon p57 = new Polygon(xCoordinates57, yCoordinates57, xCoordinates57.length);
            g.fillPolygon(p57);
            polygons.add(p57);
            // The green path from Pittsburg to Wheeling 
            int [] xCoordinates58 = new int [] {138, 145, 88, 80};
            int [] yCoordinates58 = new int [] {517, 526, 571, 562};
            // create the new Polygon that will be the path name 
            Polygon p58 = new Polygon(xCoordinates58, yCoordinates58, xCoordinates58.length);
            g.fillPolygon(p58);
            polygons.add(p58);
            // The green path from Chambersburg to Cumberland
            int [] xCoordinates59 = new int [] {496, 504, 444, 436};
            int [] yCoordinates59 = new int [] {615, 625, 670, 660};
            // create the new Polygon that will be the path name 
            Polygon p59 = new Polygon(xCoordinates59, yCoordinates59, xCoordinates59.length);
            g.fillPolygon(p59);
            polygons.add(p59);
            // The green path from Lancaster to Philedelphia 
            int [] xCoordinates60 = new int [] {753, 787, 789, 824, 826, 862, 865, 901, 901, 865, 862, 826, 823, 787, 784, 749};
            int [] yCoordinates60 = new int [] {583, 594, 595, 602, 602, 604, 604, 603, 615, 615, 616, 614, 613, 605, 605, 594};
            // create the new Polygon that will be the path name 
            Polygon p60 = new Polygon(xCoordinates60, yCoordinates60, xCoordinates60.length);
            g.fillPolygon(p60);
            polygons.add(p60);
            // The green path from Allentown to Reading
            int [] xCoordinates61 = new int [] {772, 767, 837, 842};
            int [] yCoordinates61 = new int [] {518, 506, 479, 491};
            // create the new Polygon that will be the path name 
            Polygon p61 = new Polygon(xCoordinates61, yCoordinates61, xCoordinates61.length);
            g.fillPolygon(p61);
            polygons.add(p61);
            // The green path from Coudersport to Williamsport
            int [] xCoordinates62 = new int [] {481, 489, 610, 603};
            int [] yCoordinates62 = new int [] {260, 251, 340, 349};
            // create the new Polygon that will be the path name 
            Polygon p62 = new Polygon(xCoordinates62, yCoordinates62, xCoordinates62.length);
            g.fillPolygon(p62);
            polygons.add(p62);
            // The green path from Altoona to Lewiston
            int [] xCoordinates63 = new int [] {439, 439, 514, 514};
            int [] yCoordinates63 = new int [] {482, 470, 470, 482};
            // create the new Polygon that will be the path name 
            Polygon p63 = new Polygon(xCoordinates63, yCoordinates63, xCoordinates63.length);
            g.fillPolygon(p63);
            polygons.add(p63);
            // the green route for Albany to New York
            int [] xCoordinates64 = new int [] {1049, 1062, 1062, 1049};
            int [] yCoordinates64 = new int [] {116, 116, 343, 343, }; 
            // create the new Polygon that will be the path name 
            Polygon p64 = new Polygon(xCoordinates64, yCoordinates64, xCoordinates64.length );
            g.fillPolygon(p64);
            polygons.add(p64);
            // The green path from Binghamton to Scranton/Wilkes Barre 
            int [] xCoordinates65 = new int [] {815, 827, 847, 835};
            int [] yCoordinates65 = new int [] {198, 195, 306, 308};
            // create the new Polygon that will be the path name 
            Polygon p65 = new Polygon(xCoordinates65, yCoordinates65, xCoordinates65.length);
            g.fillPolygon(p65);
            polygons.add(p65);
            // the green path for Rochester to Elmira 
            int [] xCoordinates66 = new int [] {587, 580, 581, 595, 600, 630, 637, 607, 605, 592, 592, 600};
            int [] yCoordinates66 = new int [] {72, 108, 117, 150, 157, 177, 168, 148, 145, 113, 110, 75};
            // create the new Polygon that will be the path name 
            Polygon p66 = new Polygon(xCoordinates66, yCoordinates66, xCoordinates66.length);
            g.fillPolygon(p66);
            polygons.add(p66);
            // change the color to yellow
            g.setColor(Color.yellow);
            // The yellow path from Rochester to Buffalo  
            int [] xCoordinates67 = new int [] {387, 576, 576, 387};
            int [] yCoordinates67 = new int [] {49, 49, 61, 61};
            // create the new Polygon that will be the path name 
            Polygon p67 = new Polygon(xCoordinates67, yCoordinates67, xCoordinates67.length);
            g.fillPolygon(p67);
            polygons.add(p67);
            // the yellow path from Youngstown to Erie 
            int [] xCoordinates68 = new int [] {131, 142, 74, 63};
            int [] yCoordinates68 = new int [] {201, 207, 342, 336};
            // create the new Polygon that will be the path name 
            Polygon p68 = new Polygon(xCoordinates68, yCoordinates68, xCoordinates68.length);
            g.fillPolygon(p68);
            polygons.add(p68);
            // The yellow path from Gettysburg to Harrisburg 
            int [] xCoordinates69 = new int [] {626, 637, 605, 593};
            int [] yCoordinates69 = new int [] {554, 559, 627, 620};
            // create the new Polygon that will be the path name 
            Polygon p69 = new Polygon(xCoordinates69, yCoordinates69, xCoordinates69.length);
            g.fillPolygon(p69);
            polygons.add(p69);
            // The yellow path from Philadelphia to Baltimore
            int [] xCoordinates70 = new int [] {903, 871, 869, 835, 833, 797, 795, 759, 759, 797, 800, 836, 839, 873, 877, 910 };
            int [] yCoordinates70 = new int [] {629, 646, 646, 658, 658, 666, 666, 668, 680, 678, 676, 670, 668, 657, 656, 640};
            // create the new Polygon that will be the path name 
            Polygon p70 = new Polygon(xCoordinates70, yCoordinates70, xCoordinates70.length);
            g.fillPolygon(p70);
            polygons.add(p70);
            // The yellow path from Scranton/Wilkes Barre to Stroudsburg
            int [] xCoordinates71 = new int [] {854, 862, 920, 912};
            int [] yCoordinates71 = new int [] {355, 345, 393, 402};
            // create the new Polygon that will be the path name 
            Polygon p71 = new Polygon(xCoordinates71, yCoordinates71, xCoordinates71.length);
            g.fillPolygon(p71);
            polygons.add(p71);
            // The yellow path from Williamsport to Lewiston
            int [] xCoordinates72 = new int [] {609, 620, 553, 543};
            int [] yCoordinates72 = new int [] {372, 379, 470, 463};
            // create the new Polygon that will be the path name 
            Polygon p72 = new Polygon(xCoordinates72, yCoordinates72, xCoordinates72.length);
            g.fillPolygon(p72);
            polygons.add(p72);
            // The yellow path from Elmira to Towanda
            int [] xCoordinates73 = new int [] {642, 652, 659, 691, 697, 664, 663, 653};
            int [] yCoordinates73 = new int [] {199, 235, 243, 259, 249, 232, 231, 196};
            // create the new Polygon that will be the path name 
            Polygon p73 = new Polygon(xCoordinates73, yCoordinates73, xCoordinates73.length);
            g.fillPolygon(p73);
            polygons.add(p73);
            // The yellow path from Altoona to Johnstown
            int [] xCoordinates74 = new int [] {388, 395, 364, 357};
            int [] yCoordinates74 = new int [] {492, 503, 521, 511};
            // create the new Polygon that will be the path name 
            Polygon p74 = new Polygon(xCoordinates74, yCoordinates74, xCoordinates74.length);
            g.fillPolygon(p74);
            polygons.add(p74);
            // The yellow path from Pittsburg to Morgantown 
            int [] xCoordinates75 = new int [] {157, 169, 180, 166};
            int [] yCoordinates75 = new int [] {545, 545, 657, 658};
            // create the new Polygon that will be the path name 
            Polygon p75 = new Polygon(xCoordinates75, yCoordinates75, xCoordinates75.length);
            g.fillPolygon(p75);
            polygons.add(p75);
            // The yellow path Reading to Lancaster
            int [] xCoordinates76 = new int [] {747, 759, 750, 737};
            int [] yCoordinates76 = new int [] {536, 539, 575, 571};
            // create the new Polygon that will be the path name 
            Polygon p76 = new Polygon(xCoordinates76, yCoordinates76, xCoordinates76.length);
            g.fillPolygon(p76);
            polygons.add(p76);
            // The yellow path from Syracuse to Binghamton
            int [] xCoordinates77 = new int [] {782, 794, 807, 795};
            int [] yCoordinates77 = new int [] {90, 88, 161, 164}; 
            // create the new Polygon that will be the path name 
            Polygon p77 = new Polygon(xCoordinates77, yCoordinates77, xCoordinates77.length);
            g.fillPolygon(p77);
            polygons.add(p77);
            //change the color to orange
            g.setColor(Color.orange);
            // the orange path from Erie to Buffalo 
            int [] xCoordinates78 = new int [] {179, 212, 215, 247, 250, 281, 283, 312, 315, 343, 350, 323, 320, 290, 287, 256, 253, 220, 217, 183};
            int [] yCoordinates78 = new int [] {155, 144, 143, 128, 127, 110, 108, 89, 87, 65, 74, 96, 98, 118, 120, 137, 138, 154, 155, 166};
            // create the new Polygon that will be the path name 
            Polygon p78 = new Polygon(xCoordinates78, yCoordinates78, xCoordinates78.length);
            g.fillPolygon(p78);
            polygons.add(p78);
            // the orange path from Oil City to Warren 
            int [] xCoordinates79 = new int [] {277, 286, 234, 225};
            int [] yCoordinates79 = new int [] {250, 258, 312, 303};
            // create the new Polygon that will be the path name 
            Polygon p79 = new Polygon(xCoordinates79, yCoordinates79, xCoordinates79.length);
            g.fillPolygon(p79);
            polygons.add(p79);
            // The orange path from Coudersport to Elmira
            int [] xCoordinates80 = new int [] {491, 486, 628, 632};
            int [] yCoordinates80 = new int [] {245, 234, 183, 194};
            // create the new Polygon that will be the path name 
            Polygon p80 = new Polygon(xCoordinates80, yCoordinates80, xCoordinates80.length);
            g.fillPolygon(p80);
            polygons.add(p80);
            // The orange path from Syracuse to Binghamton
            int [] xCoordinates81 = new int [] {795, 808, 821, 808};
            int [] yCoordinates81 = new int [] {88, 86, 159, 161};
            // create the new Polygon that will be the path name 
            Polygon p81 = new Polygon(xCoordinates81, yCoordinates81, xCoordinates81.length);
            g.fillPolygon(p81);
            polygons.add(p81);
            // The orange path from Williamsport to Scranton/Wilkes Barre  
            int [] xCoordinates82 = new int [] {632, 820, 821, 633};
            int [] yCoordinates82 = new int [] {350, 331, 343, 363};
            // create the new Polygon that will be the path name 
            Polygon p82 = new Polygon(xCoordinates82, yCoordinates82, xCoordinates82.length);
            g.fillPolygon(p82);
            polygons.add(p82);
            // the orange path for Altoona to Harrisburg 
            int [] xCoordinates83 = new int [] {433, 465, 468, 502, 505, 540, 543, 578, 580, 617, 617, 580, 577, 541, 537, 502, 497, 464, 460, 427};
            int [] yCoordinates83 = new int [] {500, 515, 515, 527, 528, 537, 538, 544, 544, 546, 557, 555, 555, 549, 548, 539, 538, 526, 525, 511};
            // create the new Polygon that will be the path name 
            Polygon p83 = new Polygon(xCoordinates83, yCoordinates83, xCoordinates83.length);
            g.fillPolygon(p83);
            polygons.add(p83);
            // The orange path from Lancaster to Philedelphia 
            int [] xCoordinates84 = new int [] {751, 785, 787, 822, 825, 861, 863, 900, 900, 863, 861, 825, 821, 786, 782, 747}; 
            int [] yCoordinates84 = new int [] {597, 608, 609, 615, 617, 618, 618, 617, 629, 630, 630, 627, 627, 619, 619, 608};
            // create the new Polygon that will be the path name 
            Polygon p84 = new Polygon(xCoordinates84, yCoordinates84, xCoordinates84.length);
            g.fillPolygon(p84);
            polygons.add(p84);
            // The orange path from Pittsburg to Youngstown 
            int [] xCoordinates85 = new int [] {67, 83, 84, 103, 104, 127, 128, 154, 146, 120, 118, 95, 93, 74, 72, 57};
            int [] yCoordinates85 = new int [] {378, 410, 413, 443, 445, 473, 474, 499, 508, 483, 480, 451, 449, 418, 415, 383};
            // create the new Polygon that will be the path name 
            Polygon p85 = new Polygon(xCoordinates85, yCoordinates85, xCoordinates85.length);
            g.fillPolygon(p85);
            polygons.add(p85);
            // The orange path from Stroudsburg to Allentown 
            int [] xCoordinates86 = new int [] {918, 928, 874, 865};
            int [] yCoordinates86 = new int [] {415, 424, 476, 466};
            // create the new Polygon that will be the path name 
            Polygon p86 = new Polygon(xCoordinates86, yCoordinates86, xCoordinates86.length);
            g.fillPolygon(p86);
            polygons.add(p86);
            // change the color to pink
            g.setColor(Color.pink);
            // The pink path from Albany to Binghamton
            int [] xCoordinates87 = new int [] {823, 1035, 1040, 827};
            int [] yCoordinates87 = new int [] {175, 97, 108, 186}; 
            // create the new Polygon that will be the path name 
            Polygon p87 = new Polygon(xCoordinates87, yCoordinates87, xCoordinates87.length);
            g.fillPolygon(p87);
            polygons.add(p87);
            // The pink path from Harrisburg to Reading
            int [] xCoordinates88 = new int [] {667, 666, 741, 741};
            int [] yCoordinates88 = new int [] {534, 522, 516, 527};
            // create the new Polygon that will be the path name 
            Polygon p88 = new Polygon(xCoordinates88, yCoordinates88, xCoordinates88.length);
            g.fillPolygon(p88);
            polygons.add(p88);
            // The pink path from Pittsburg to Johnstown 
            int [] xCoordinates89 = new int [] {183, 217, 219, 254, 257, 293, 296, 332, 332, 296, 293, 256, 253, 218, 215, 179}; 
            int [] yCoordinates89 = new int [] {503, 514, 514, 520, 520, 520, 521, 518, 530, 532, 532, 531, 531, 524, 524, 515};
            // create the new Polygon that will be the path name 
            Polygon p89 = new Polygon(xCoordinates89, yCoordinates89, xCoordinates89.length);
            g.fillPolygon(p89);
            polygons.add(p89);
            // The pink path from Wheeling  to Youngstown 
            int [] xCoordinates90 = new int [] {55, 53, 53, 53, 53, 55, 56, 63, 63, 73, 60, 51, 50, 44, 43, 40, 40, 40, 40, 43};
            int [] yCoordinates90 = new int [] {381, 416, 419, 455, 458, 494, 498, 533, 536, 570, 573, 538, 535, 500, 495, 459, 455, 419, 416, 379};
            // create the new Polygon that will be the path name 
            Polygon p90 = new Polygon(xCoordinates90, yCoordinates90, xCoordinates90.length);
            g.fillPolygon(p90);
            polygons.add(p90);
            // The pink path from Dubois to Oil City
            int [] xCoordinates91 = new int [] {229, 235, 339, 335};
            int [] yCoordinates91 = new int [] {343, 332, 374, 385};
            // create the new Polygon that will be the path name 
            Polygon p91 = new Polygon(xCoordinates91, yCoordinates91, xCoordinates91.length);
            g.fillPolygon(p91);
            polygons.add(p91);
            // The pink path from York to Lancaster
            int [] xCoordinates92 = new int [] {690, 688, 724, 726};
            int [] yCoordinates92 = new int [] {613, 601, 595, 607};
            // create the new Polygon that will be the path name 
            Polygon p92 = new Polygon(xCoordinates92, yCoordinates92, xCoordinates92.length);
            g.fillPolygon(p92);
            polygons.add(p92);
            //The pink path from Rochester to Syracuse
            int [] xCoordinates93 = new int [] {764, 800, 804, 839, 843, 877, 881, 913, 907, 875, 873, 839, 836, 801, 799, 763};
            int [] yCoordinates93 = new int [] {694, 691, 690, 683, 682, 671, 669, 654, 643, 659, 660, 671, 672, 679, 680, 682};
            // create the new Polygon that will be the path name 
            Polygon p93 = new Polygon(xCoordinates93, yCoordinates93, xCoordinates93.length);
            g.fillPolygon(p93);
            polygons.add(p93);
            // The pink path from Syracuse to Rochester
            int [] xCoordinates94 = new int [] {614, 765, 765, 614};
            int [] yCoordinates94 = new int [] {49, 49, 60, 61}; 
            // create the new Polygon that will be the path name 
            Polygon p94 = new Polygon(xCoordinates94, yCoordinates94, xCoordinates94.length);
            g.fillPolygon(p94);
            polygons.add(p94);
            // The pink path from Scranton/Wilkes Barre to New York
            int [] xCoordinates95 = new int [] {850, 884, 888, 924, 927, 963, 966,1001,1004, 1038, 1034, 1000, 998, 963, 961, 926, 887, 885, 885, 850};
            int [] yCoordinates95 = new int [] {330, 330, 330, 334, 334, 341, 340, 350, 351, 363, 374, 362, 362, 352, 351, 345, 345, 341, 341, 341};
            // create the new Polygon that will be the path name 
            Polygon p95 = new Polygon(xCoordinates95, yCoordinates95, xCoordinates95.length);
            g.fillPolygon(p95);
            polygons.add(p95);
        }
    }
}
