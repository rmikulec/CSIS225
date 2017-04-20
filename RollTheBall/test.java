import java.io.File;
/**
 * Write a description of class test here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class test
{
    protected Level[] levels;
    public static void main(String args[]){
        test T = new test();
        
        File current = new File("./levels");
        String[] surFiles = current.list();
        int n = surFiles.length;
        T.levels = new Level[n];
        
        for(int i=0; i<n; i++){
            T.levels[i] = new Level("./levels/"+surFiles[i]);
        }
        
        T.levels[2].displayGrid();
        
    }
}
