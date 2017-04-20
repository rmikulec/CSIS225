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
/**
 * This Class will handle most of the GUI for Ticket to Ride
 * 
 * @author Matthew MacFadyen 
 * @version April 18th, 2017
 */
public class TrainMaster extends JFrame implements MouseListener  
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
        setFullScreen(this);
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
        board = new JLabel(new ImageIcon("Images/board1.jpg") );

        label.setLayout(new FlowLayout() );
        instructions.setLayout(new FlowLayout() );
        play.setLayout(new FlowLayout() );
        board.setLayout(new FlowLayout() );

        // create the button for the instructions
        b1 = new JButton("How to Play");

        // create the button for playing the game
        b2 = new JButton("Play Game");

        // create a button for returning to the main 
        b3 = new JButton("Main Menu");

        b4 = new JButton("Back to Main Menu");

        b5 = new JButton("Submit Players");

        // add the button to the label
        label.add(b2);

        // add the button to the label 
        label.add(b1);

        // add the label to the panel
        menu.add(label);

        // add the buttons and the text field 
        play.add(b3);
        play.add(b4); 
        play.add(b5);

        play.add(item2);
        gameMenu.add(play);
        game.add(board);
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
        thehandler handler = new thehandler();

        // add the buttons to the event handler 
        b1.addActionListener(handler);
        b2.addActionListener(handler);
        b3.addActionListener(handler);
        b4.addActionListener(handler);
        b5.addActionListener(handler);
        item2.addActionListener(handler);

    }

    public void setFullScreen(JFrame f){

        f.setUndecorated(true);
        f.setResizable(false);
        vc.setFullScreenWindow(f);

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
     * this class will handle all the event listeners on Ticket to Ride. Like if a button 
     * is cliked on the main menu it will make panels go away and come back 
     */
    private class thehandler implements ActionListener 
    {
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
                rules.setVisible(false);
                gameMenu.setVisible(false);
                menu.setVisible(false);
                game.setVisible(true);
                // the width of the image is 1107 and the height is 738
                setSize(1600, 900);
                getContentPane().setBackground(Color.LIGHT_GRAY);
            }
            // test conditional for getting the coorindates of the board
            else if(event.getSource() == board)
            {
                System.out.println(getX() );
                System.out.println(getY() );

            }
        }
    }
}
