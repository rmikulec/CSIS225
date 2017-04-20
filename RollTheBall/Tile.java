
/**
 * Tile is a class dedicated to tile objects for the Roll the Ball applet
 * 
 * @author Chris, Ronald, Matt, Ryan, Luis
 * @version 1.0
 */
public class Tile
{
    //Instance Variables
    protected boolean isStart;
    protected boolean isEnd;
    protected boolean Move;
    protected TileShape shape;
    protected int x;
    protected int y;
    protected boolean hasStar;
    protected boolean visited;

    /**
     * Constructor that takes no arguments and sets all
     * variables to false with the blank tile shape
     */
    public Tile()
    {
        isStart = false;
        isEnd = false;
        Move = false;
        hasStar = false;
        shape = TileShape.B;
        visited =false;
    }

    /**
     * Constructor which takes input parameters to initialize instance variables
     * 
     * @param start an indicator whether or not this tile is a start tile
     * @param end an indicator whether or not this tile is a end tile
     * @param right whether or not the tile can move right
     * @param left whether or not the tile can move left
     * @param up whether or not the tile can move up
     * @param down whether or not the tile can move down
     * @param star whether or not the tile contains a star
     * @param shapeIn the shape for this tile object to be
     * 
     */
    public Tile(boolean start, boolean end, boolean move, boolean star, TileShape shapeIn)
    {
        isStart = start;
        isEnd = end;
        Move = move;
        hasStar = star;
        shape = shapeIn;
    }

     
    public TileShape getShape()
    {
        return shape;
    }

    public boolean isTrack(){
        if(shape.equals(TileShape.LU)||shape.equals(TileShape.RU)||shape.equals(TileShape.LD)||
        shape.equals(TileShape.RD)||shape.equals(TileShape.H)||shape.equals(TileShape.V))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean canMove(){
        return Move;
    }
    
    public boolean isStar(){
        return hasStar;
    }
}
