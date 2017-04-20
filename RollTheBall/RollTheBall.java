import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.lang.Math;
import java.util.*;

/**
 * RollTheBall simulates the app Roll the Ball with similar functionality
 * 
 * @author Chris, Ronald, Matt, Ryan, Luis
 * @version 1.0
 */
public class RollTheBall extends JApplet implements MouseListener
{
    protected int[] currentTile;
    protected int[] swapTile;
    protected  int WIDTH;
    protected  int HEIGHT;
    protected int TILE_SIZE;
    protected int ROWS = 4;//number of rows in the board
    protected int COLS = 4;//number of cols in the board
    protected Tile[][] currentLevel;
    protected int currentLevelInt;
    protected int starCount;
    protected boolean currentLevel_beat = false;
    protected int x1;//the x coordinate for the first click
    protected int y1;//the y coordinate for the first click
    protected int x2;//the x coordinate for the second click
    protected int y2;//the y coordinate for the second click
    protected boolean firstClick;
    protected Level[] levels;
    protected boolean path;
    protected ArrayList<Tile> pathList = new ArrayList<Tile>();

    /**
     * Called by the browser or applet viewer to inform this JApplet that it
     * has been loaded into the system. It is always called before the first 
     * time that the start method is called.
     */
    public void init()
    {
        firstClick = true;
        WIDTH = getWidth();
        HEIGHT = getHeight();

        File current = new File("./levels");
        String[] surFiles = current.list();
        int n = surFiles.length;
        levels = new Level[n];

        for(int i=0; i<n; i++){
            levels[i] = new Level("./levels/"+surFiles[i]);
        }

        currentLevelInt = 0;
        currentLevel = levels[currentLevelInt].getGrid();
        int[] temp = levels[currentLevelInt].getSize();
        ROWS = temp[0];
        COLS = temp[1];
        TILE_SIZE = HEIGHT/(ROWS);
        showStatus("CLICK THE PIECE TO MOVE");
        addMouseListener( this );
    }

    public void currentLevelswap(int row1, int col1, int row2, int col2)
    {
        Tile temp = currentLevel[row1][col1];
        if((Math.abs(row1-row2)==1||Math.abs(row1-row2)==0)&&(Math.abs(col1-col2)==1||Math.abs(col1-col2)==0)
        &&(Math.abs(row1-row2)!=1||Math.abs(col1-col2)!=1))
        {
            if(currentLevel[row1][col1].canMove()&&currentLevel[row2][col2].canMove()){
                if(currentLevel[row1][col1].getShape().equals(TileShape.N)
                ||currentLevel[row2][col2].getShape().equals(TileShape.N))
                {
                    currentLevel[row1][col1] = currentLevel[row2][col2];
                    currentLevel[row2][col2] = temp;
                }
            }
            else
            {
                showStatus("CANNOT MOVE PIECE");
            }
        }

        else
        {
            showStatus("INVALID MOVE");
        }
    }

    public void mouseClicked(MouseEvent e)
    {
        if(firstClick)
        {
            showStatus("CLICK THE EMPTY SPOT");
            x1 = e.getX();
            y1 = e.getY();
            currentTile = getTile(x1,y1);
            firstClick = false;
            e.consume();
        }
        else
        {
            showStatus("CLICK THE PIECE TO MOVE");
            x2 = e.getX();
            y2 = e.getY();

            swapTile = getTile(x2,y2);

            currentLevelswap(currentTile[1],currentTile[0],swapTile[1],swapTile[0]);
            firstClick=true;
            path = pathFound();
            repaint();
            if(path){
                String Message = "You beat the level with " +starCount+" stars! Would you like to continue?";
                int reply = JOptionPane.showConfirmDialog(null, Message, "",JOptionPane.YES_NO_OPTION );
                if (reply == JOptionPane.YES_OPTION)
                {
                    currentLevelInt++;
                    currentLevel = levels[currentLevelInt].getGrid();
                    int[] temp = levels[currentLevelInt].getSize();
                    ROWS = temp[0];
                    COLS = temp[1];
                    TILE_SIZE = HEIGHT/(ROWS);
                }
                repaint();
            }
            e.consume();
        }
    }

    public int[] getTile(int a, int b){
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

    public boolean pathFound()
    {
        int r = 0;
        int c = 0;
        int r2 = 0;
        int c2 = 0;
        int dir = 0; //1-going down, 2-going up, 3-going left, 4-going right
        starCount = 0;
        pathList.clear();
        for(int i = 0; i < ROWS; i++)
        {
            for(int j = 0; j < COLS; j++)
            {
                if(currentLevel[i][j].isStart)
                {
                    r = i; 
                    c = j;
                }
                if(currentLevel[i][j].isEnd)
                {
                    r2 = i; 
                    c2 = j;
                }
            }
        }

        pathList.add(currentLevel[r][c]);
        pathList.add(currentLevel[r2][c2]);

        if(currentLevel[r][c].getShape().equals(TileShape.US)){
            dir = 2;
            if(r==0)return false;
            r = r-1;
        }
        else if(currentLevel[r][c].getShape().equals(TileShape.DS)){
            dir = 1;
            if(r==ROWS-1)return false;
            r = r+1;
        }
        else if(currentLevel[r][c].getShape().equals(TileShape.LS)){
            dir = 3;
            if(c==0)return false;
            c = c-1;
        }
        else if(currentLevel[r][c].getShape().equals(TileShape.RS)){
            dir = 4;
            if(c==COLS-1)return false;
            c = c+1;
        }

        while(!currentLevel[r][c].isEnd)
        {
            if(currentLevel[r][c].isStar())starCount++;

            if(currentLevel[r][c].getShape().equals(TileShape.N))return false;

            if(currentLevel[r][c].getShape().equals(TileShape.B))return false;
            pathList.add(currentLevel[r][c]);
            if(currentLevel[r][c].getShape().equals(TileShape.LU)){
                if(dir==4){
                    if(r==0)return false;
                    r = r-1;
                    dir = 2;
                }
                else if(dir==1){
                    if(c==0)return false;
                    c = c-1;
                    dir = 3;
                }
            }

            else if(currentLevel[r][c].getShape().equals(TileShape.RU)){
                if(dir==3){
                    if(r==0)return false;
                    r = r-1;
                    dir = 2;
                }
                else if(dir==1){
                    if(c==COLS-1)return false;
                    c = c+1;
                    dir = 4;
                }
            }

            else if(currentLevel[r][c].getShape().equals(TileShape.LD)){
                if(dir==2){
                    if(c==0)return false;
                    c = c-1;
                    dir = 3;
                }
                else if(dir==4){
                    if(r==ROWS-1)return false;
                    r = r+1;
                    dir = 1;
                }
            }

            else if(currentLevel[r][c].getShape().equals(TileShape.RD)){
                if(dir==2){
                    if(c==COLS-1)return false;
                    c = c+1;
                    dir = 4;
                }
                else if(dir==3){
                    if(r==ROWS-1)return false;
                    r = r+1;
                    dir = 1;
                }
            }

            else if(currentLevel[r][c].getShape().equals(TileShape.H)){
                if(dir==1||dir==2)return false;
                else if(dir==3){
                    if(c==0)return false;
                    c = c-1;
                }
                else if(dir==4){
                    if(c==COLS-1)return false;
                    c = c+1;
                }
            }

            else if(currentLevel[r][c].getShape().equals(TileShape.V)){
                if(dir==3||dir==4)return false;
                else if(dir==1){
                    if(r==ROWS-1)return false;
                    r = r+1;
                }
                else if(dir==2){
                    if(r==0)return false;
                    r = r-1;
                }
            }

        }
        return true;
    }

    public void mouseEntered(MouseEvent e)
    {
        return;
    }

    public void mouseExited(MouseEvent e)
    {
        return;
    }

    public void mousePressed(MouseEvent e)
    {
        return;
    }

    public void mouseReleased(MouseEvent e)
    {
        return;
    }

    /**
     * Called by the browser or applet viewer to inform this JApplet that it 
     * should start its execution. It is called after the init method and 
     * each time the JApplet is revisited in a Web page. 
     */
    public void start()
    {
        // provide any code requred to run each time 
        // web page is visited
    }

    /** 
     * Called by the browser or applet viewer to inform this JApplet that
     * it should stop its execution. It is called when the Web page that
     * contains this JApplet has been replaced by another page, and also
     * just before the JApplet is to be destroyed. 
     */
    public void stop()
    {
        // provide any code that needs to be run when page
        // is replaced by another page or before JApplet is destroyed 
    }

    /**
     * Paint method for applet.
     * 
     * @param  g   the Graphics object for this applet
     */
    public void paint(Graphics g)
    {
        //Color brown = new Color(139,69,19);
        g.setColor(Color.lightGray);
        g.fillRect(0,0, WIDTH,HEIGHT);

        for(int i = 0; i < ROWS; i++)
        {
            for(int j = 0; j < COLS; j++)
            {
                if(currentLevel[i][j].getShape().equals(TileShape.N))
                {
                    continue;
                }                    
                if(currentLevel[i][j].isStart)
                {

                    g.setColor(Color.BLUE);
                    g.fillRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE );
                    g.setColor(Color.BLACK);

                    g.fillRect((j*TILE_SIZE)+TILE_SIZE/4, (i*TILE_SIZE)+TILE_SIZE/4, TILE_SIZE/2,(TILE_SIZE*(3/4)));
                    g.drawRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    if(path && pathList.contains(currentLevel[i][j]))
                    {
                        g.setColor(Color.GREEN);
                    }
                    if(currentLevel[i][j].getShape().equals(TileShape.LS)){
                        g.fillRect(j*TILE_SIZE, (i*TILE_SIZE) + TILE_SIZE/4, TILE_SIZE*3/4, TILE_SIZE/2);
                    }
                    else if(currentLevel[i][j].getShape().equals(TileShape.RS)){
                        g.fillRect(j*TILE_SIZE+(TILE_SIZE/4), (i*TILE_SIZE) + TILE_SIZE/4, TILE_SIZE*3/4, TILE_SIZE/2);
                    }
                    else if(currentLevel[i][j].getShape().equals(TileShape.US)){
                        g.fillRect((j*TILE_SIZE)+TILE_SIZE/4, i*TILE_SIZE, TILE_SIZE/2, TILE_SIZE*3/4);
                    }
                    else if(currentLevel[i][j].getShape().equals(TileShape.DS)){
                        g.fillRect((j*TILE_SIZE)+TILE_SIZE/4, i*TILE_SIZE+(TILE_SIZE/4), TILE_SIZE/2, TILE_SIZE*3/4);
                    }
                }
                if(currentLevel[i][j].isEnd)
                {
                    g.setColor(Color.RED);
                    g.fillRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    g.setColor(Color.BLACK);

                    g.fillRect(j*TILE_SIZE, (i*TILE_SIZE)+TILE_SIZE/4, (TILE_SIZE*(3/4)), TILE_SIZE/2);
                    g.drawRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);

                    if(path && pathList.contains(currentLevel[i][j]))
                    {
                        g.setColor(Color.GREEN);
                    }

                    if(currentLevel[i][j].getShape().equals(TileShape.LE)){
                        g.fillRect(j*TILE_SIZE, (i*TILE_SIZE) + TILE_SIZE/4, TILE_SIZE*3/4, TILE_SIZE/2);
                    }
                    else if(currentLevel[i][j].getShape().equals(TileShape.RE)){
                        g.fillRect(j*TILE_SIZE+(TILE_SIZE/4), (i*TILE_SIZE) + TILE_SIZE/4, TILE_SIZE*3/4, TILE_SIZE/2);
                    }
                    else if(currentLevel[i][j].getShape().equals(TileShape.UE)){
                        g.fillRect((j*TILE_SIZE)+TILE_SIZE/4, i*TILE_SIZE, TILE_SIZE/2, TILE_SIZE*3/4);
                    }
                    else if(currentLevel[i][j].getShape().equals(TileShape.DE)){
                        g.fillRect((j*TILE_SIZE)+TILE_SIZE/4, i*TILE_SIZE+(TILE_SIZE/4), TILE_SIZE/2, TILE_SIZE*3/4);
                    }
                }
                if(currentLevel[i][j].getShape().equals(TileShape.B))
                {
                    g.setColor(Color.darkGray);
                    g.fillRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    g.setColor(Color.BLACK);
                    if(path && pathList.contains(currentLevel[i][j]))
                    {
                        g.setColor(Color.GREEN);
                    }
                    g.drawRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    if(currentLevel[i][j].hasStar)
                    {
                        g.setColor(Color.YELLOW);
                        char[] temp = {'*'};
                        g.drawChars(temp,0,1,(j*TILE_SIZE)+TILE_SIZE/2,(i*TILE_SIZE)+TILE_SIZE/2);
                    }
                }
                if(currentLevel[i][j].getShape().equals(TileShape.V))
                {
                    g.setColor(Color.darkGray);
                    g.fillRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    g.setColor(Color.BLACK);
                    if(path && pathList.contains(currentLevel[i][j]))
                    {
                        g.setColor(Color.GREEN);
                    }
                    g.fillRect((j*TILE_SIZE)+TILE_SIZE/4, i*TILE_SIZE, TILE_SIZE/2, TILE_SIZE);
                    g.drawRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    if(currentLevel[i][j].hasStar)
                    {
                        g.setColor(Color.YELLOW);
                        char[] temp = {'*'};
                        g.drawChars(temp,0,1,(j*TILE_SIZE)+TILE_SIZE/2,(i*TILE_SIZE)+TILE_SIZE/2);
                    }
                }
                if(currentLevel[i][j].getShape().equals(TileShape.H))
                {
                    g.setColor(Color.darkGray);
                    g.fillRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    g.setColor(Color.BLACK);
                    if(path && pathList.contains(currentLevel[i][j]))
                    {
                        g.setColor(Color.GREEN);
                    }
                    g.fillRect(j*TILE_SIZE, (i*TILE_SIZE) + TILE_SIZE/4, TILE_SIZE, TILE_SIZE/2);
                    g.drawRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    if(currentLevel[i][j].hasStar)
                    {
                        g.setColor(Color.YELLOW);
                        char[] temp = {'*'};
                        g.drawChars(temp,0,1,(j*TILE_SIZE)+TILE_SIZE/2,(i*TILE_SIZE)+TILE_SIZE/2);
                    }
                }
                if(currentLevel[i][j].getShape().equals(TileShape.LU))
                {
                    g.setColor(Color.darkGray);
                    g.fillRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    g.setColor(Color.BLACK);
                    if(path && pathList.contains(currentLevel[i][j]))
                    {
                        g.setColor(Color.GREEN);
                    }
                    g.fillRect((j*TILE_SIZE)+TILE_SIZE/4, i*TILE_SIZE, TILE_SIZE/2, TILE_SIZE/2);
                    g.fillRect((j*TILE_SIZE), i*TILE_SIZE + TILE_SIZE/4, (3*TILE_SIZE/4),TILE_SIZE/2);
                    g.drawRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    if(currentLevel[i][j].hasStar)
                    {
                        g.setColor(Color.YELLOW);
                        char[] temp = {'*'};
                        g.drawChars(temp,0,1,(j*TILE_SIZE)+TILE_SIZE/2,(i*TILE_SIZE)+TILE_SIZE/2);
                    }
                }
                if(currentLevel[i][j].getShape().equals(TileShape.RU))
                {
                    g.setColor(Color.darkGray);
                    g.fillRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    g.setColor(Color.BLACK);
                    if(path && pathList.contains(currentLevel[i][j]))
                    {
                        g.setColor(Color.GREEN);
                    }
                    g.fillRect((j*TILE_SIZE)+TILE_SIZE/4, i*TILE_SIZE, TILE_SIZE/2, TILE_SIZE/2);
                    g.fillRect((j*TILE_SIZE)+TILE_SIZE/4, i*TILE_SIZE + TILE_SIZE/4, (3*TILE_SIZE/4),TILE_SIZE/2);
                    g.drawRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    if(currentLevel[i][j].hasStar)
                    {
                        g.setColor(Color.YELLOW);
                        char[] temp = {'*'};
                        g.drawChars(temp,0,1,(j*TILE_SIZE)+TILE_SIZE/2,(i*TILE_SIZE)+TILE_SIZE/2);
                    }
                }
                if(currentLevel[i][j].getShape().equals(TileShape.LD))
                {
                    g.setColor(Color.darkGray);
                    g.fillRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    g.setColor(Color.BLACK);
                    if(path && pathList.contains(currentLevel[i][j]))
                    {
                        g.setColor(Color.GREEN);
                    }
                    g.fillRect((j*TILE_SIZE)+TILE_SIZE/4, (i*TILE_SIZE)+TILE_SIZE/2, TILE_SIZE/2, TILE_SIZE/2);
                    g.fillRect((j*TILE_SIZE), i*TILE_SIZE + TILE_SIZE/4, (3*TILE_SIZE/4),TILE_SIZE/2);
                    g.drawRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    if(currentLevel[i][j].hasStar)
                    {
                        g.setColor(Color.YELLOW);
                        char[] temp = {'*'};
                        g.drawChars(temp,0,1,(j*TILE_SIZE)+TILE_SIZE/2,(i*TILE_SIZE)+TILE_SIZE/2);
                    }
                }
                if(currentLevel[i][j].getShape().equals(TileShape.RD))
                {
                    g.setColor(Color.darkGray);
                    g.fillRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    g.setColor(Color.BLACK);
                    if(path && pathList.contains(currentLevel[i][j]))
                    {
                        g.setColor(Color.GREEN);
                    }
                    g.fillRect((j*TILE_SIZE)+TILE_SIZE/4, (i*TILE_SIZE)+TILE_SIZE/2, TILE_SIZE/2, TILE_SIZE/2);
                    g.fillRect((j*TILE_SIZE)+TILE_SIZE/4, i*TILE_SIZE + TILE_SIZE/4, (3*TILE_SIZE/4),TILE_SIZE/2);
                    g.drawRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    if(currentLevel[i][j].hasStar)
                    {
                        g.setColor(Color.YELLOW);
                        char[] temp = {'*'};
                        g.drawChars(temp,0,1,(j*TILE_SIZE)+TILE_SIZE/2,(i*TILE_SIZE)+TILE_SIZE/2);
                    }
                }
            }
        }

    }

    /**
     * Called by the browser or applet viewer to inform this JApplet that it
     * is being reclaimed and that it should destroy any resources that it
     * has allocated. The stop method will always be called before destroy. 
     */
    public void destroy()
    {
        // provide code to be run when JApplet is about to be destroyed.
    }

    /**
     * Returns information about this applet. 
     * An applet should override this method to return a String containing 
     * information about the author, version, and copyright of the JApplet.
     *
     * @return a String representation of information about this JApplet
     */
    public String getAppletInfo()
    {
        // provide information about the applet
        return "Title:   \nAuthor:   \nA simple applet example description. ";
    }

    /**
     * Returns parameter information about this JApplet. 
     * Returns information about the parameters than are understood by this JApplet.
     * An applet should override this method to return an array of Strings 
     * describing these parameters. 
     * Each element of the array should be a set of three Strings containing 
     * the name, the type, and a description.
     *
     * @return a String[] representation of parameter information about this JApplet
     */
    public String[][] getParameterInfo()
    {
        // provide parameter information about the applet
        String paramInfo[][] = {
                {"firstParameter",    "1-10",    "description of first parameter"},
                {"status", "boolean", "description of second parameter"},
                {"images",   "url",     "description of third parameter"}
            };
        return paramInfo;
    }
}
