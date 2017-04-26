import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.lang.Math;
import java.util.*;
/**
 * RollTheBall simulates the app Roll the Ball with similar functionality
 * Path from start to end turns green upon completion.
 * Currently contains only 2 levels, but more can easily be added.
 * Levels come from input txt file, pop ups would have to be modified
 * slightly to accomodate more levels.
 * 
 * @author Chris, Ronald, Matt, Ryan, Luis
 * @version 1.0
 */
public class RollTheBall extends JApplet implements MouseListener
{
    //Instance Variables
    protected int[] currentTile;//the current tile in a 2d array
    protected int[] swapTile;//the tile to be swapped in 2d array
    protected  int WIDTH;//width
    protected  int HEIGHT;//heitht
    protected int TILE_SIZE;//the size of the tile for graphics
    protected int ROWS = 4;//number of rows in the board
    protected int COLS = 4;//number of cols in the board
    protected Tile[][] currentLevel;//the current level array
    protected int currentLevelInt;//the number of the current level
    protected int starCount;//the number of stars in the path
    protected boolean currentLevel_beat = false;//level beaten
    protected int x1;//the x coordinate for the first click
    protected int y1;//the y coordinate for the first click
    protected int x2;//the x coordinate for the second click
    protected int y2;//the y coordinate for the second click
    protected boolean firstClick;//indicates first click
    protected Level[] levels;//array of level txt files
    protected boolean path;//true if path exists
    //arraylist of path tiles
    protected ArrayList<Tile> pathList = new ArrayList<Tile>();

    /**
     * Called by the browser or applet viewer to inform this JApplet that it
     * has been loaded into the system. It is always called before the first 
     * time that the start method is called.
     */
    public void init()
    {
        firstClick = true;//sets first click to true
        WIDTH = getWidth();//gets width of applet window
        HEIGHT = getHeight();//gets height of applet window

        //fills level array with input from txt files
        File current = new File("./levels"); 
        String[] surFiles = current.list();
        int n = surFiles.length;
        levels = new Level[n];

        for(int i=0; i<n; i++){
            levels[i] = new Level("./levels/"+surFiles[i]);
        }

        currentLevelInt = 0;//level starts at 0
        currentLevel = levels[currentLevelInt].getGrid();
        int[] temp = levels[currentLevelInt].getSize();
        //functionality for larger levels can be implemented
        //with the first two ints of input file format
        ROWS = temp[0];
        COLS = temp[1];
        //formats size of tiles based on grid dimensions
        TILE_SIZE = HEIGHT/(ROWS);
        showStatus("CLICK THE PIECE TO MOVE");//shows status
        addMouseListener( this );//implements mouse listener
    }

    /**
     * Method to swap two tiles in a 2d array of Tile objects
     * Conforms to rules of Roll the Ball, tiles may only be swapped
     * if one is a "N" or nothing tile and the other is not. Tiles which
     * are not supposed to move are never swapped.
     * 
     * @param row1 the first row coordinate in the 2d array
     * @param col1 the first column coordinate in the 2d array
     * @param row2 the second row coordinate in the 2d array
     * @param col2 the second column coordinate in the 2d array
     */
    public void currentLevelswap(int row1, int col1, int row2, int col2)
    {
        Tile temp = currentLevel[row1][col1];//temp tile object
        //applies swap provided rules are not violated
        //handles cases where two tiles are not able to swap
        if((Math.abs(row1-row2)==1||Math.abs(row1-row2)==0)&&
        (Math.abs(col1-col2)==1||Math.abs(col1-col2)==0)
        &&(Math.abs(row1-row2)!=1||Math.abs(col1-col2)!=1))
        {
            if(currentLevel[row1][col1].canMove()//swap
            &&currentLevel[row2][col2].canMove())
            {
                if(currentLevel[row1][col1].getShape().equals(TileShape.N)
                ||currentLevel[row2][col2].getShape().equals(TileShape.N))
                {
                    currentLevel[row1][col1] = currentLevel[row2][col2];
                    currentLevel[row2][col2] = temp;
                }
            }
            else//shows status indicating the tile cannot be moved
            {
                showStatus("CANNOT MOVE PIECE");
            }
        }

        else//shows a status indicating an invalid move was made
        {
            showStatus("INVALID MOVE");
        }
    }

    /**
     * Method to handle what happens when the mouse is clicked on screen
     * 
     * @param e the MouseEvent object
     */
    public void mouseClicked(MouseEvent e)
    {
        if(firstClick)//what happens on the first click
        {
            showStatus("CLICK THE EMPTY SPOT");//directions
            x1 = e.getX();//gets coordinates for swap
            y1 = e.getY();
            currentTile = getTile(x1,y1);//the first tile object from array
            firstClick = false;//set firstClick to false
            e.consume();
        }
        else//what happens on the second click
        {
            showStatus("CLICK THE PIECE TO MOVE");//directions
            x2 = e.getX();//gets coordinates for swap
            y2 = e.getY();

            swapTile = getTile(x2,y2);//applies swap of tiles

            currentLevelswap(currentTile[1],currentTile[0],swapTile[1],swapTile[0]);
            firstClick=true;//sets first click back to true
            path = pathFound();//determines if a path exists from start to end
            repaint();//repaints
            if(path)//handles pop up messages for when the level is beaten
            {
                String Message1 = "You beat the level with " + 
                    starCount + " stars! Would you like to continue?";
                String Message2 = "You beat the level with " +
                    starCount + "stars! Please Exit game.";
                int reply = 0;
                if(currentLevelInt == 0 || currentLevelInt == 1)
                {
                    reply = JOptionPane.showConfirmDialog
                    (null, Message1, "",JOptionPane.YES_NO_OPTION );
                }
                if(currentLevelInt == 2)
                {
                    JOptionPane.showMessageDialog(null, Message2);
                }

                if (reply == JOptionPane.YES_OPTION)
                {
                    currentLevelInt++;
                    currentLevel = levels[currentLevelInt].getGrid();
                    int[] temp = levels[currentLevelInt].getSize();
                    ROWS = temp[0];
                    COLS = temp[1];
                    TILE_SIZE = HEIGHT/(ROWS);
                }
                repaint();//paints new board for next level if there is one
            }
            e.consume();
        }
    }

    /**
     * Method to get the tile at a given set of coordinates
     * 
     * @param a the first coordinate 
     * @param b the second coordinate
     * @return an int array with the coordinates of the tile
     * on the board
     */
    public int[] getTile(int a, int b)
    {
        int[] t = new int[2];
        int[] sizes = new int[ROWS];
        for(int i=1; i<=ROWS; i++){
            sizes[i-1] = TILE_SIZE*i;       
        }

        for(int i=1; i<ROWS; i++){
            if(a>0 && a<TILE_SIZE)t[0]=0;           
            else if(a>TILE_SIZE*(ROWS-1) && a<TILE_SIZE*ROWS)t[0]=ROWS-1;           
            else if(a>sizes[i-1] && a<sizes[i])t[0]=i;

            if(b>0 && b<TILE_SIZE)t[1]=0;
            else if(b>TILE_SIZE*(ROWS-1) && b<TILE_SIZE*ROWS)t[1]=ROWS-1;
            else if(b>sizes[i-1] && b<sizes[i])t[1]=i;
        }
        //showStatus("X: "+t[0]+" Y: "+t[1]);
        return t;
    }

    /**
     * Method to determine if a path exists between the start and end tiles
     */
    public boolean pathFound()
    {
        int r = 0;//row of start tile
        int c = 0;//col of start tile
        int r2 = 0;//row of end tile
        int c2 = 0;//col of end tile
        int dir = 0; //1-going down, 2-going up, 3-going left, 4-going right
        starCount = 0;//counts stars
        pathList.clear();//clears the arraylist for path tiles before finding path
        for(int i = 0; i < ROWS; i++)
        {
            for(int j = 0; j < COLS; j++)
            {
                if(currentLevel[i][j].isStart)//find start
                {
                    r = i; 
                    c = j;
                }
                if(currentLevel[i][j].isEnd)//find end
                {
                    r2 = i; 
                    c2 = j;
                }
            }
        }

        pathList.add(currentLevel[r][c]);//adds start tile to pathList
        pathList.add(currentLevel[r2][c2]);//adds end tile to pathList

        //cases for start tiles
        if(currentLevel[r][c].getShape().equals(TileShape.US)){
            dir = 2;
            if(r==0)
            {
                return false;
            }
            r = r-1;
        }
        else if(currentLevel[r][c].getShape().equals(TileShape.DS)){
            dir = 1;
            if(r==ROWS-1)
            {
                return false;
            }
            r = r+1;
        }
        else if(currentLevel[r][c].getShape().equals(TileShape.LS)){
            dir = 3;
            if(c==0)
            {
                return false;
            }
            c = c-1;
        }
        else if(currentLevel[r][c].getShape().equals(TileShape.RS)){
            dir = 4;
            if(c==COLS-1)
            {
                return false;
            }
            c = c+1;
        }

        //loops until the path to the end tile is found
        while(!currentLevel[r][c].isEnd)
        {
            if(currentLevel[r][c].isStar())//increment star count if star
            {
                starCount++;  
            }

            if(currentLevel[r][c].getShape().equals(TileShape.N))
            {
                return false;
            }
            //return false if there is a blank or nothing tile on path
            if(currentLevel[r][c].getShape().equals(TileShape.B))
            {
                return false;
            }

            pathList.add(currentLevel[r][c]);//adds current tile to pathList

            //cases for each type of tile shape
            if(currentLevel[r][c].getShape().equals(TileShape.LU))//left-up
            {
                if(dir==4)
                {
                    if(r==0)
                    {
                        return false;
                    }
                    r = r-1;
                    dir = 2;
                }
                else if(dir==1){
                    if(c==0)
                    {
                        return false;
                    }
                    c = c-1;
                    dir = 3;
                }
            }
            else if(currentLevel[r][c].getShape().equals(TileShape.RU))//right-up
            {
                if(dir==3)
                {
                    if(r==0)
                    {
                        return false;
                    }
                    r = r-1;
                    dir = 2;
                }
                else if(dir==1)
                {
                    if(c==COLS-1)
                    {
                        return false;
                    }
                    c = c+1;
                    dir = 4;
                }
            }
            else if(currentLevel[r][c].getShape().equals(TileShape.LD))//left-down
            {
                if(dir==2)
                {
                    if(c==0)
                    {
                        return false;
                    }
                    c = c-1;
                    dir = 3;
                }
                else if(dir==4)
                {
                    if(r==ROWS-1)
                    {
                        return false;
                    }
                    r = r+1;
                    dir = 1;
                }
            }
            else if(currentLevel[r][c].getShape().equals(TileShape.RD))//right-down
            {
                if(dir==2)
                {
                    if(c==COLS-1)
                    {
                        return false;
                    }
                    c = c+1;
                    dir = 4;
                }
                else if(dir==3){
                    if(r==ROWS-1)return false;
                    r = r+1;
                    dir = 1;
                }
            }

            else if(currentLevel[r][c].getShape().equals(TileShape.H))//horizontal
            {
                if(dir==1||dir==2)
                {
                    return false;
                }
                else if(dir==3)
                {
                    if(c==0)
                    {
                        return false;
                    }
                    c = c-1;
                }
                else if(dir==4)
                {
                    if(c==COLS-1)
                    {
                        return false;
                    }
                    c = c+1;
                }
            }

            else if(currentLevel[r][c].getShape().equals(TileShape.V))//vertical
            {
                if(dir==3||dir==4)
                {
                    return false;
                }
                else if(dir==1)
                {
                    if(r==ROWS-1)
                    {
                        return false;
                    }
                    r = r+1;
                }
                else if(dir==2){
                    if(r==0)
                    {
                        return false;
                    }
                    r = r-1;
                }
            }
        }
        return true;//if there is a path between start and end
    }

    /**
     * MouseListener methods must be overridden
     * this method does nothing
     * 
     * @param e the MouseEvent
     */
    public void mouseEntered(MouseEvent e)
    {
        return;
    }

    /**
     * MouseListener methods must be overridden
     * this method does nothing
     * 
     * @param e the MouseEvent
     */
    public void mouseExited(MouseEvent e)
    {
        return;
    }

    /**
     * MouseListener methods must be overridden
     * this method does nothing
     * 
     * @param e the MouseEvent
     */
    public void mousePressed(MouseEvent e)
    {
        return;
    }

    /**
     * MouseListener methods must be overridden
     * this method does nothing
     * 
     * @param e the MouseEvent
     */
    public void mouseReleased(MouseEvent e)
    {
        return;
    }

    /**
     * Paint method for applet.
     * 
     * @param  g   the Graphics object for this applet
     */
    public void paint(Graphics g)
    {
        g.setColor(Color.lightGray);//set background color
        g.fillRect(0,0, WIDTH,HEIGHT);//draws background

        for(int i = 0; i < ROWS; i++)//loop through 2d array
        {
            for(int j = 0; j < COLS; j++)
            {
                //cases for each type of shape
                if(currentLevel[i][j].getShape().equals(TileShape.N))
                {
                    continue;//nothing
                }     

                if(currentLevel[i][j].isStart)//start
                {
                    //start tiles are blue
                    g.setColor(Color.BLUE);
                    g.fillRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE );
                    g.setColor(Color.BLACK);
                    //draws background
                    g.fillRect((j*TILE_SIZE)+TILE_SIZE/4,
                        (i*TILE_SIZE)+TILE_SIZE/4, TILE_SIZE/2,(TILE_SIZE*(3/4)));

                    g.drawRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    //draws path shape with appropriate color
                    if(path && pathList.contains(currentLevel[i][j]))
                    {
                        g.setColor(Color.GREEN);//when path found
                    }
                    if(currentLevel[i][j].getShape().equals(TileShape.LS))
                    {
                        g.fillRect(j*TILE_SIZE, (i*TILE_SIZE)
                            + TILE_SIZE/4, TILE_SIZE*3/4, TILE_SIZE/2);
                    }
                    else if(currentLevel[i][j].getShape().equals(TileShape.RS))
                    {
                        g.fillRect(j*TILE_SIZE+(TILE_SIZE/4), (i*TILE_SIZE)
                            + TILE_SIZE/4, TILE_SIZE*3/4, TILE_SIZE/2);
                    }
                    else if(currentLevel[i][j].getShape().equals(TileShape.US))
                    {
                        g.fillRect((j*TILE_SIZE)+TILE_SIZE/4, 
                            i*TILE_SIZE, TILE_SIZE/2, TILE_SIZE*3/4);
                    }
                    else if(currentLevel[i][j].getShape().equals(TileShape.DS)){
                        g.fillRect((j*TILE_SIZE)+TILE_SIZE/4, 
                            i*TILE_SIZE+(TILE_SIZE/4), TILE_SIZE/2, TILE_SIZE*3/4);
                    }
                }
                if(currentLevel[i][j].isEnd)
                {
                    //end tiles are red
                    g.setColor(Color.RED);
                    g.fillRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    g.setColor(Color.BLACK);
                    //draws background
                    g.fillRect(j*TILE_SIZE, (i*TILE_SIZE)+TILE_SIZE/4,
                        (TILE_SIZE*(3/4)), TILE_SIZE/2);

                    g.drawRect(j*TILE_SIZE, i*TILE_SIZE,
                        TILE_SIZE, TILE_SIZE);
                    //draws path shape with appropriate color
                    if(path && pathList.contains(currentLevel[i][j]))
                    {
                        g.setColor(Color.GREEN);//when path found
                    }
                    if(currentLevel[i][j].getShape().equals(TileShape.LE)){
                        g.fillRect(j*TILE_SIZE, (i*TILE_SIZE) 
                            + TILE_SIZE/4, TILE_SIZE*3/4, TILE_SIZE/2);
                    }
                    else if(currentLevel[i][j].getShape().equals(TileShape.RE)){
                        g.fillRect(j*TILE_SIZE+(TILE_SIZE/4), 
                            (i*TILE_SIZE) + TILE_SIZE/4, TILE_SIZE*3/4, TILE_SIZE/2);
                    }
                    else if(currentLevel[i][j].getShape().equals(TileShape.UE)){
                        g.fillRect((j*TILE_SIZE)+TILE_SIZE/4,
                            i*TILE_SIZE, TILE_SIZE/2, TILE_SIZE*3/4);
                    }
                    else if(currentLevel[i][j].getShape().equals(TileShape.DE)){
                        g.fillRect((j*TILE_SIZE)+TILE_SIZE/4, 
                            i*TILE_SIZE+(TILE_SIZE/4), TILE_SIZE/2, TILE_SIZE*3/4);
                    }
                }
                if(currentLevel[i][j].getShape().equals(TileShape.B))//blank
                {
                    //regular tiles are dark gray
                    g.setColor(Color.darkGray);
                    if(!currentLevel[i][j].Move && !currentLevel[i][j].isStart 
                    && !currentLevel[i][j].isEnd)
                    {
                        g.setColor(Color.magenta);
                    }
                    g.fillRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    g.setColor(Color.BLACK);
                    //draws path shape with appropriate color
                    if(path && pathList.contains(currentLevel[i][j]))
                    {
                        g.setColor(Color.GREEN);//when path found
                    }
                    g.drawRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    if(currentLevel[i][j].hasStar)//draws star
                    {
                        g.setColor(Color.YELLOW);
                        char[] temp = {'*'};
                        g.drawChars(temp,0,1,(j*TILE_SIZE)+TILE_SIZE/2,
                            (i*TILE_SIZE)+TILE_SIZE/2);
                    }
                }
                if(currentLevel[i][j].getShape().equals(TileShape.V))//vertical
                {
                    g.setColor(Color.darkGray);
                    if(!currentLevel[i][j].Move && !currentLevel[i][j].isStart 
                    && !currentLevel[i][j].isEnd)
                    {
                        g.setColor(Color.magenta);
                    }
                    g.fillRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    g.setColor(Color.BLACK);
                    if(path && pathList.contains(currentLevel[i][j]))
                    {
                        g.setColor(Color.GREEN);//when path found
                    }
                    //draws path shape with appropriate color
                    g.fillRect((j*TILE_SIZE)+TILE_SIZE/4, i*TILE_SIZE, 
                        TILE_SIZE/2, TILE_SIZE);
                    g.drawRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    if(currentLevel[i][j].hasStar)//draws star
                    {
                        g.setColor(Color.YELLOW);
                        char[] temp = {'*'};
                        g.drawChars(temp,0,1,(j*TILE_SIZE)+TILE_SIZE/2,
                            (i*TILE_SIZE)+TILE_SIZE/2);
                    }
                }
                if(currentLevel[i][j].getShape().equals(TileShape.H))//horizontal
                {
                    g.setColor(Color.darkGray);
                    if(!currentLevel[i][j].Move && !currentLevel[i][j].isStart 
                    && !currentLevel[i][j].isEnd)
                    {
                        g.setColor(Color.magenta);
                    }
                    g.fillRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    g.setColor(Color.BLACK);
                    if(path && pathList.contains(currentLevel[i][j]))
                    {
                        g.setColor(Color.GREEN);//when path found
                    }
                    //draws path shape with appropriate color
                    g.fillRect(j*TILE_SIZE, (i*TILE_SIZE) +
                        TILE_SIZE/4, TILE_SIZE, TILE_SIZE/2);

                    g.drawRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    if(currentLevel[i][j].hasStar)//draws star
                    {
                        g.setColor(Color.YELLOW);
                        char[] temp = {'*'};
                        g.drawChars(temp,0,1,(j*TILE_SIZE)+TILE_SIZE/2,
                            (i*TILE_SIZE)+TILE_SIZE/2);
                    }
                }
                if(currentLevel[i][j].getShape().equals(TileShape.LU))//left-up
                {
                    g.setColor(Color.darkGray);
                    if(!currentLevel[i][j].Move && !currentLevel[i][j].isStart 
                    && !currentLevel[i][j].isEnd)
                    {
                        g.setColor(Color.magenta);
                    }
                    g.fillRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    g.setColor(Color.BLACK);
                    if(path && pathList.contains(currentLevel[i][j]))
                    {
                        g.setColor(Color.GREEN);//when path found
                    }
                    //draws path shape with appropriate color
                    g.fillRect((j*TILE_SIZE)+TILE_SIZE/4, 
                        i*TILE_SIZE, TILE_SIZE/2, TILE_SIZE/2);
                    g.fillRect((j*TILE_SIZE), i*TILE_SIZE + 
                        TILE_SIZE/4, (3*TILE_SIZE/4),TILE_SIZE/2);
                    g.drawRect(j*TILE_SIZE, i*TILE_SIZE,
                        TILE_SIZE, TILE_SIZE);
                    if(currentLevel[i][j].hasStar)//draws star
                    {
                        g.setColor(Color.YELLOW);
                        char[] temp = {'*'};
                        g.drawChars(temp,0,1,(j*TILE_SIZE)
                            +TILE_SIZE/2,(i*TILE_SIZE)+TILE_SIZE/2);
                    }
                }
                if(currentLevel[i][j].getShape().equals(TileShape.RU))//right-up
                {
                    g.setColor(Color.darkGray);
                    if(!currentLevel[i][j].Move && !currentLevel[i][j].isStart 
                    && !currentLevel[i][j].isEnd)
                    {
                        g.setColor(Color.magenta);
                    }
                    g.fillRect(j*TILE_SIZE, i*TILE_SIZE,
                        TILE_SIZE, TILE_SIZE);
                    g.setColor(Color.BLACK);
                    if(path && pathList.contains(currentLevel[i][j]))
                    {
                        g.setColor(Color.GREEN);//when path found
                    }
                    //draws path shape with appropriate color
                    g.fillRect((j*TILE_SIZE)+TILE_SIZE/4,
                        i*TILE_SIZE, TILE_SIZE/2, TILE_SIZE/2);
                    g.fillRect((j*TILE_SIZE)+TILE_SIZE/4, 
                        i*TILE_SIZE + TILE_SIZE/4, (3*TILE_SIZE/4),TILE_SIZE/2);
                    g.drawRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    if(currentLevel[i][j].hasStar)//draws star
                    {
                        g.setColor(Color.YELLOW);
                        char[] temp = {'*'};
                        g.drawChars(temp,0,1,(j*TILE_SIZE)+
                            TILE_SIZE/2,(i*TILE_SIZE)+TILE_SIZE/2);
                    }
                }
                if(currentLevel[i][j].getShape().equals(TileShape.LD))//left-down
                {
                    g.setColor(Color.darkGray);
                    if(!currentLevel[i][j].Move && !currentLevel[i][j].isStart 
                    && !currentLevel[i][j].isEnd)
                    {
                        g.setColor(Color.magenta);
                    }
                    g.fillRect(j*TILE_SIZE, i*TILE_SIZE,
                        TILE_SIZE, TILE_SIZE);
                    g.setColor(Color.BLACK);
                    if(path && pathList.contains(currentLevel[i][j]))
                    {
                        g.setColor(Color.GREEN);
                    }
                    //draws path shape with appropriate color
                    g.fillRect((j*TILE_SIZE)+TILE_SIZE/4, 
                        (i*TILE_SIZE)+TILE_SIZE/2, TILE_SIZE/2, TILE_SIZE/2);
                    g.fillRect((j*TILE_SIZE), i*TILE_SIZE +
                        TILE_SIZE/4, (3*TILE_SIZE/4),TILE_SIZE/2);
                    g.drawRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    if(currentLevel[i][j].hasStar)//draws star
                    {
                        g.setColor(Color.YELLOW);
                        char[] temp = {'*'};
                        g.drawChars(temp,0,1,(j*TILE_SIZE)+
                            TILE_SIZE/2,(i*TILE_SIZE)+TILE_SIZE/2);
                    }
                }
                if(currentLevel[i][j].getShape().equals(TileShape.RD))//right-down
                {
                    g.setColor(Color.darkGray);
                    if(!currentLevel[i][j].Move && !currentLevel[i][j].isStart 
                    && !currentLevel[i][j].isEnd)
                    {
                        g.setColor(Color.magenta);
                    }
                    g.fillRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    g.setColor(Color.BLACK);
                    if(path && pathList.contains(currentLevel[i][j]))
                    {
                        g.setColor(Color.GREEN);//when path found
                    }
                    //draws path shape with appropriate color
                    g.fillRect((j*TILE_SIZE)+TILE_SIZE/4, 
                        (i*TILE_SIZE)+TILE_SIZE/2, TILE_SIZE/2, TILE_SIZE/2);
                    g.fillRect((j*TILE_SIZE)+TILE_SIZE/4,
                        i*TILE_SIZE + TILE_SIZE/4, (3*TILE_SIZE/4),TILE_SIZE/2);
                    g.drawRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    if(currentLevel[i][j].hasStar)//draws star
                    {
                        g.setColor(Color.YELLOW);
                        char[] temp = {'*'};
                        g.drawChars(temp,0,1,(j*TILE_SIZE)+
                            TILE_SIZE/2,(i*TILE_SIZE)+TILE_SIZE/2);
                    }
                }
            }
        }
    }
}
