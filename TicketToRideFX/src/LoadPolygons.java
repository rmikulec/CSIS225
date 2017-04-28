import javafx.scene.shape.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.AnnotatedArrayType;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Ryan on 4/27/2017.
 */
public class LoadPolygons {
    protected ArrayList<Polygon> polygons;
    protected ArrayList<double[]> xCoor;
    protected ArrayList<double[]> yCoor;


    public LoadPolygons() {
        polygons = new ArrayList<Polygon>();
        xCoor = new ArrayList<double[]>();
        yCoor = new ArrayList<double[]>();

        double[] tempX;
        double[] tempY;

        String testLine = "";
        ArrayList<Double> a1 = new ArrayList<Double>();
        ArrayList<Double> a2 = new ArrayList<Double>();
        ArrayList<Double> fin = new ArrayList<Double>();

        Scanner inFile = null;
        String input = "./src/data/trackCoordinates.txt";
        int count = 0;

        try {
            inFile = new Scanner(new File(input));
            while (inFile.hasNextLine()) {
                testLine = inFile.nextLine();
                if (count == 2) {
                    tempX = new double[a1.size()];
                    tempY = new double[a1.size()];
                    for (int i = 0; i < a1.size(); i++) {
                        fin.add(a1.get(i));
                        tempX[i] = a1.get(i).doubleValue();
                        fin.add(a2.get(i));
                        tempY[i] = a2.get(i).doubleValue();
                    }

                    xCoor.add(tempX);
                    yCoor.add(tempY);
                    double[] finT = new double[fin.size()];
                    for (int i = 0; i < fin.size(); i++) {
                        finT[i] = fin.get(i).doubleValue();
                    }
                    polygons.add(new Polygon(finT));
                    count = 0;
                } else {
                    if (count == 0) {
                        String[] a1S = testLine.split(",");
                        for (int i = 0; i < a1S.length; i++) {
                            a1.add(Double.parseDouble(a1S[i]));
                        }
                    } else if (count == 1) {
                        String[] a2S = testLine.split(",");
                        for (int i = 0; i < a2S.length; i++) {
                            a2.add(Double.parseDouble(a2S[i]));
                        }

                    }
                    count++;
                }

            }


        } catch (FileNotFoundException e) {
            System.out.println("Board File not Found");

        }
    }
        public ArrayList<Polygon> getPolygons(){
            return polygons;
        }

        public ArrayList<double[]> getX(){
            return xCoor;
        }

    public ArrayList<double[]> getY(){
        return yCoor;
    }

}
