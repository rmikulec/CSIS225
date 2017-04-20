import java.util.*;
import java.io.*;
/**
 * Write a description of class Level here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Level
{
    protected Tile[][] grid = new Tile[4][4];
    protected int r;
    protected int c;

    public Level(String input){
        Scanner fromFile = null;
        try
        {
            fromFile = new Scanner(new File(input));
            int row = -1;
            int col = 0;
            r = fromFile.nextInt();
            c = fromFile.nextInt();
            grid = new Tile[r][c];
            while (fromFile.hasNextLine() ) { //Proceed while there are lines left in the txt file

                String nextLine = fromFile.nextLine();
                StringTokenizer st = new StringTokenizer(nextLine);
                while (st.hasMoreTokens() ) {
                    String e = st.nextToken();

                    //Normal tiles
                    if(e.equals("LU"))grid[row][col] = new Tile(false, false, true, false,TileShape.LU);
                    else if(e.equals("RU"))grid[row][col] = new Tile(false, false, true, false,TileShape.RU);
                    else if(e.equals("LD"))grid[row][col] = new Tile(false, false, true, false,TileShape.LD);
                    else if(e.equals("RD"))grid[row][col] = new Tile(false, false, true, false,TileShape.RD);
                    else if(e.equals("H"))grid[row][col] = new Tile(false, false, true, false,TileShape.H);
                    else if(e.equals("V"))grid[row][col] = new Tile(false, false, true, false, TileShape.V);
                    //Immovable tiles
                    else if(e.equals("LUM"))grid[row][col] = new Tile(false, false, false, false,TileShape.LU);
                    else if(e.equals("RUM"))grid[row][col] = new Tile(false, false, false, false,TileShape.RU);
                    else if(e.equals("LDM"))grid[row][col] = new Tile(false, false, false, false,TileShape.LD);
                    else if(e.equals("RDM"))grid[row][col] = new Tile(false, false, false, false,TileShape.RD);
                    else if(e.equals("HM"))grid[row][col] = new Tile(false, false, false, false,TileShape.H);
                    else if(e.equals("VM"))grid[row][col] = new Tile(false, false, false, false,TileShape.V);
                    //tiles with stars
                    else if(e.equals("LUs"))grid[row][col] = new Tile(false, false, true, true,TileShape.LU);
                    else if(e.equals("RUs"))grid[row][col] = new Tile(false, false, true, true,TileShape.RU);
                    else if(e.equals("LDs"))grid[row][col] = new Tile(false, false, true, true,TileShape.LD);
                    else if(e.equals("RDs"))grid[row][col] = new Tile(false, false, true, true,TileShape.RD);
                    else if(e.equals("Hs"))grid[row][col] = new Tile(false, false, true, true,TileShape.H);
                    else if(e.equals("Vs"))grid[row][col] = new Tile(false, false, true, true,TileShape.V);
                    //special tiles
                    else if(e.equals("B"))grid[row][col] = new Tile(false, false, true, false,TileShape.B);
                    else if(e.equals("N"))grid[row][col] = new Tile(false, false, true, false,TileShape.N);
                    //Start tiles
                    else if(e.equals("RS"))grid[row][col] = new Tile(true, false, false, false,TileShape.RS);
                    else if(e.equals("LS"))grid[row][col] = new Tile(true, false, false, false,TileShape.LS);
                    else if(e.equals("DS"))grid[row][col] = new Tile(true, false, false, false,TileShape.DS);
                    else if(e.equals("US"))grid[row][col] = new Tile(true, false, false, false,TileShape.US);
                    //end tiles
                    else if(e.equals("RE"))grid[row][col] = new Tile(false, true, false, false,TileShape.RE);
                    else if(e.equals("LE"))grid[row][col] = new Tile(false, true, false, false,TileShape.LE);
                    else if(e.equals("DE"))grid[row][col] = new Tile(false, true, false, false,TileShape.DE);
                    else if(e.equals("UE"))grid[row][col] = new Tile(false, true, false, false,TileShape.UE);
                    col++;
                }
                row++;
                col=0;
            }
        }
        catch(FileNotFoundException e){
            System.out.println("File not Found");

        }

    }

    public Tile[][] getGrid(){
        return grid;
    }

    public void displayGrid(){
        for(int i=0; i<r; i++){
            for(int j=0; j<r; j++){
                System.out.print(grid[i][j].getShape() + " ");
            }
            System.out.println("");
        }
    }

    public int[] getSize(){
        int[] t = new int[2];
        t[0] = r;
        t[1] = c;
        return t;
    }
}