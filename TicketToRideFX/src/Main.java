import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Main extends Application{

    protected int screenWidth;
    protected int screenHeight;
    protected int numPlayers;
    protected boolean isStart;
    protected boolean zeroMove;

    protected TrainMaster master;


    @Override
    public void start(Stage primaryStage) throws Exception{
        isStart=true;
        System.out.println(Paths.get(".").toAbsolutePath().normalize().toString());
        Group root = new Group();
        Group Instr = new Group();
        Group setUp = new Group();

        Scene MainMenuScene = new Scene(root);
        Scene setUpScene = new Scene(setUp);
        Scene inatScene = new Scene(Instr);


        ////////////////////Obtain Screen Resolution///////////////////////////////
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        screenWidth = (int) primaryScreenBounds.getWidth();
        screenHeight = (int) primaryScreenBounds.getHeight();
        ///////////////////////////////////////////////////////////////////////////

        final Canvas canvas = new Canvas(screenWidth,screenHeight);
        final Canvas instCanvas = new Canvas(screenWidth,screenHeight);
        final Canvas setUpCanvas = new Canvas(screenWidth,screenHeight);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext gcInst = instCanvas.getGraphicsContext2D();
        GraphicsContext gcSetUp = setUpCanvas.getGraphicsContext2D();

        Image mainMenu =  new Image(getClass().getResourceAsStream("/images/MainMenu.jpg"),
                screenWidth/2, screenHeight, true, true);
        ImageView mainMenuBG = new ImageView(new Image(getClass().getResourceAsStream("/images/MMBackground.jpg"),
                screenWidth/2, screenHeight, true, true));
        ImageView mainMenuBG2 = new ImageView(new Image(getClass().getResourceAsStream("/images/MMBackground.jpg"),
                screenWidth/2, screenHeight, true, true));
        Image Instructions = new Image(getClass().getResourceAsStream("/images/Instructions.jpg"),
                screenWidth/2, screenHeight, true, true);




        ///////////////Centers and Re-sizes Images for Main Menu///////////////////
        gc.drawImage(mainMenu, ((screenWidth/2)-(mainMenu.getWidth()/2)),0);
        mainMenuBG.setPreserveRatio(true);
        mainMenuBG.setFitWidth(screenWidth+60);
        mainMenuBG.setFitHeight(screenHeight+60);
        mainMenuBG2.setPreserveRatio(true);
        mainMenuBG2.setFitWidth(screenWidth+60);
        mainMenuBG2.setFitHeight(screenHeight+60);
        ///////////////////////////////////////////////////////////////////////////

        ///////////////How to Menu//////////////////////////////////////////////////
        gcInst.drawImage(Instructions, ((screenWidth/2)-(mainMenu.getWidth()/2)),0);
        ///////////////////////////////////////////////////////////////////////////


        ///////////////Setup Up Menu////////////////////////////////////////////////
        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 72 );
        gcSetUp.setFont(theFont);
        gcSetUp.setFill( Color.IVORY );
        gcSetUp.setStroke( Color.GREEN);
        gcSetUp.fillText( "Select how many Players", 100 ,100);
        gcSetUp.strokeText( "Select how many Players", 100,100);

        gcSetUp.setLineWidth(2);
        theFont = Font.font( "Times New Roman", FontWeight.BOLD, 48 );
        gcSetUp.setFont( theFont );

        RoundRectangle2D[] playerSelectionButtons= new RoundRectangle2D[4];

        String text ;
        for(int i=1; i<5; i++) {
            gcSetUp.setFill(Color.MAROON);
            gcSetUp.setStroke(Color.LIGHTGREEN);
            gcSetUp.fillRoundRect((screenWidth * i / 6), screenHeight / 4, 250, 100, 20, 20);
            gcSetUp.strokeRoundRect((screenWidth * i / 6), screenHeight / 4, 250, 100, 20, 20);
            gcSetUp.setFill( Color.IVORY );
            gcSetUp.setStroke( Color.GREEN);
            if(i==1) text = Integer.toString(i+1) + " Player";
            else{
                text = Integer.toString(i+1) + " Players";
            }
            gcSetUp.fillText( text, (screenWidth * i / 6)+45, screenHeight / 4+60);
            gcSetUp.strokeText( text, (screenWidth * i / 6)+45, screenHeight / 4+60);
            playerSelectionButtons[i-1] = new RoundRectangle2D.Float();
            playerSelectionButtons[i-1].setRoundRect((screenWidth * i / 6), screenHeight / 4, 250, 100, 20, 20);
        }
        ////////////////////////////////////////////////////////////////////////////

        ////////////////////Buttons////////////////////////////////////////////////
        //Sets up play button
        gc.setFill(Color.MAROON);
        gc.setStroke(Color.LIGHTGREEN);
        gc.fillRoundRect((screenWidth*3/4),screenHeight/4,250,100,20,20);
        gc.strokeRoundRect((screenWidth*3/4),screenHeight/4,250,100,20,20);
        gc.setFill( Color.IVORY );
        gc.setStroke( Color.GREEN);
        gc.setLineWidth(2);
        theFont = Font.font( "Times New Roman", FontWeight.BOLD, 48 );
        gc.setFont( theFont );
        gc.fillText( "Play!", (screenWidth*3/4)+60 ,screenHeight/4+60);
        gc.strokeText( "Play!", (screenWidth*3/4)+60 ,screenHeight/4 + 60);
        RoundRectangle2D playButton = new RoundRectangle2D.Float();
        playButton.setRoundRect((screenWidth*3/4),screenHeight/4,250,100,20,20);
        //Sets up Instruction Button
        gc.setFill(Color.MAROON);
        gc.setStroke(Color.LIGHTGREEN);
        gc.fillRoundRect((screenWidth*3/4),screenHeight/4+120,250,100,20,20);
        gc.strokeRoundRect((screenWidth*3/4),screenHeight/4+120,250,100,20,20);
        gc.setFill( Color.IVORY );
        gc.setStroke( Color.GREEN);
        theFont = Font.font( "Times New Roman", FontWeight.BOLD, 36 );
        gc.setFont( theFont );
        gc.fillText( "How to Play", (screenWidth*3/4)+25 ,screenHeight/4+180);
        gc.strokeText( "How to Play", (screenWidth*3/4)+25 ,screenHeight/4 + 180);
        RoundRectangle2D instButton = new RoundRectangle2D.Float();
        instButton.setRoundRect((screenWidth*3/4),screenHeight/4+120,250,100,20,20);
        //////////////////////////////////////////////////////////////////////////////

        MainMenuScene.setOnMouseClicked(
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if(playButton.contains(event.getX(),event.getY()))
                        {
                            gc.setStroke(Color.MAROON);
                            gc.setFill(Color.LIGHTGREEN);
                            gc.fillRoundRect((screenWidth*3/4),screenHeight/4,250,100,20,20);
                            gc.strokeRoundRect((screenWidth*3/4),screenHeight/4,250,100,20,20);
                            gc.setFill( Color.BLACK );
                            gc.setStroke( Color.MAROON);
                            gc.fillText( "Play!", (screenWidth*3/4)+60 ,screenHeight/4+60);
                            gc.strokeText( "Play!", (screenWidth*3/4)+60 ,screenHeight/4 + 60);


                            setUp.getChildren().addAll(mainMenuBG2, setUpCanvas);
                            primaryStage.setScene(setUpScene);
                            primaryStage.setFullScreen(true);
                        }

                        else if(instButton.contains(event.getX(),event.getY()))
                        {
                            gc.setStroke(Color.MAROON);
                            gc.setFill(Color.LIGHTGREEN);
                            gc.fillRoundRect((screenWidth*3/4),screenHeight/4+120,250,100,20,20);
                            gc.strokeRoundRect((screenWidth*3/4),screenHeight/4+120,250,100,20,20);
                            gc.setFill( Color.BLACK );
                            gc.setStroke( Color.MAROON);
                            gc.fillText( "How to Play", (screenWidth*3/4)+25 ,screenHeight/4+180);
                            gc.strokeText( "How to Play", (screenWidth*3/4)+25 ,screenHeight/4+180);


                            Instr.getChildren().addAll(mainMenuBG2, instCanvas);
                            primaryStage.setScene(inatScene);
                            primaryStage.setFullScreen(true);


                        }
                    }
                });

        setUpScene.setOnMouseClicked(
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        for(int i=1; i<5; i++){
                            if(playerSelectionButtons[i-1].contains(event.getX(), event.getY())) {
                                numPlayers = i+1;

                                gcSetUp.setStroke(Color.MAROON);
                                gcSetUp.setFill(Color.LIGHTGREEN);
                                gcSetUp.fillRoundRect((screenWidth * i / 6), screenHeight / 4, 250, 100, 20, 20);
                                gcSetUp.strokeRoundRect((screenWidth * i / 6), screenHeight / 4, 250, 100, 20, 20);

                                playGame(primaryStage);
                            }
                            }
                        }
                    }
                );

                root.getChildren().addAll(mainMenuBG, canvas);



        primaryStage.setScene(MainMenuScene);
        primaryStage.setTitle("Ticket to Ride");
        primaryStage.setFullScreen(true);
        primaryStage.show();



    }




    public void playGame(Stage primaryStage){

        master = new TrainMaster(numPlayers);
        master.setup(numPlayers);

        Group Game = new Group();
        Group polygons = new Group();
        Scene gameScene = new Scene(Game);
        primaryStage.setScene(gameScene);
        primaryStage.setFullScreen(true);
        final Canvas canvas = new Canvas(screenWidth,screenHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        BorderPane gameplay = new BorderPane();

        ImageView mainMenuBG = new ImageView(new Image(getClass().getResourceAsStream("/images/MMBackground.jpg"),
                screenWidth/2, screenHeight, true, true));
        mainMenuBG.setPreserveRatio(true);
        mainMenuBG.setFitWidth(screenWidth+60);
        mainMenuBG.setFitHeight(screenHeight+60);


        Image board = new Image(getClass().getResourceAsStream("./images/board1.jpg"));
        gc.drawImage(board,0,0);


       if(isStart){
            master.setUpPlayers();
       }


       //Draws the train tickets
        Image TT1 = new Image(getClass().getResourceAsStream("./Images/Pics/TrainColors/TrainColors 001.jpg"));
        Image TT2 = new Image(getClass().getResourceAsStream("./Images/Pics/TrainColors/TrainColors 002.jpg"));
        Image TT3 = new Image(getClass().getResourceAsStream("./Images/Pics/TrainColors/TrainColors 003.jpg"));
        Image TT4 = new Image(getClass().getResourceAsStream("./Images/Pics/TrainColors/TrainColors 004.jpg"));
        Image TT5 = new Image(getClass().getResourceAsStream("./Images/Pics/TrainColors/TrainColors 005.jpg"));
        Image TT6 = new Image(getClass().getResourceAsStream("./Images/Pics/TrainColors/TrainColors 006.jpg"));
        Image TT7 = new Image(getClass().getResourceAsStream("./Images/Pics/TrainColors/TrainColors 007.jpg"));
        Image TT8 = new Image(getClass().getResourceAsStream("./Images/Pics/TrainColors/TrainColors 008.jpg"));
        Image TT9 = new Image(getClass().getResourceAsStream("./Images/Pics/TrainColors/TrainColors 009.jpg"));

        double TTposX = screenWidth/10.0-screenWidth/12.0;
        double TTposY = screenHeight*(3/4.0);

        gc.drawImage(TT1, TTposX,TTposY , TT1.getWidth()*(1/4.0), TT1.getHeight()*(1/4.0));
        gc.drawImage(TT2,  TTposX*2 , TTposY, TT2.getWidth()*(1/4.0), TT2.getHeight()*(1/4.0));
        gc.drawImage(TT3,  TTposX*3 , TTposY, TT3.getWidth()*(1/4.0), TT3.getHeight()*(1/4.0));
        gc.drawImage(TT4,  TTposX*4 , TTposY, TT4.getWidth()*(1/4.0), TT4.getHeight()*(1/4.0));
        gc.drawImage(TT5,  TTposX*5 , TTposY, TT5.getWidth()*(1/4.0), TT5.getHeight()*(1/4.0));
        gc.drawImage(TT6,  TTposX*6 , TTposY, TT6.getWidth()*(1/4.0), TT6.getHeight()*(1/4.0));
        gc.drawImage(TT7,  TTposX*7 , TTposY, TT7.getWidth()*(1/4.0), TT7.getHeight()*(1/4.0));
        gc.drawImage(TT8,  TTposX*8 , TTposY, TT8.getWidth()*(1/4.0), TT8.getHeight()*(1/4.0));
        gc.drawImage(TT9,  TTposX*9 , TTposY, TT9.getWidth()*(1/4.0), TT9.getHeight()*(1/4.0));

        Image DT1 = new Image(getClass().getResourceAsStream("./Images/Pics/Stocks/Stocks 002.jpg"));
        Image DT2 = new Image(getClass().getResourceAsStream("./Images/Pics/Stocks/Stocks 003.jpg"));
        Image DT3 = new Image(getClass().getResourceAsStream("./Images/Pics/Stocks/Stocks 004.jpg"));
        Image DT4 = new Image(getClass().getResourceAsStream("./Images/Pics/Stocks/Stocks 005.jpg"));
        Image DT5 = new Image(getClass().getResourceAsStream("./Images/Pics/Stocks/Stocks 006.jpg"));
        Image DT6 = new Image(getClass().getResourceAsStream("./Images/Pics/Stocks/Stocks 007.jpg"));
        Image DT7 = new Image(getClass().getResourceAsStream("./Images/Pics/Stocks/Stocks 008.jpg"));
        Image DT8 = new Image(getClass().getResourceAsStream("./Images/Pics/Stocks/Stocks 009.jpg"));
        Image DT9 = new Image(getClass().getResourceAsStream("./Images/Pics/Stocks/Stocks 010.jpg"));

        double DTposX = screenWidth*(6/11.0)+100;
        double DTposX2 = DTposX+70;
        double DTposY = screenHeight/2.0-25;
        double DTposY2 = DTposY+100;

        gc.drawImage(DT1, DTposX,DTposY,DT1.getWidth()/7.0,DT1.getHeight()/7.0);
        gc.drawImage(DT2, DTposX+150,DTposY,DT1.getWidth()/7.0,DT1.getHeight()/7.0);
        gc.drawImage(DT3, DTposX+300,DTposY,DT1.getWidth()/7.0,DT1.getHeight()/7.0);
        gc.drawImage(DT4, DTposX+450,DTposY,DT1.getWidth()/7.0,DT1.getHeight()/7.0);
        gc.drawImage(DT5, DTposX+600,DTposY,DT1.getWidth()/7.0,DT1.getHeight()/7.0);
        gc.drawImage(DT6, DTposX2,DTposY2,DT1.getWidth()/7.0,DT1.getHeight()/7.0);
        gc.drawImage(DT7, DTposX2+150,DTposY2,DT1.getWidth()/7.0,DT1.getHeight()/7.0);
        gc.drawImage(DT8, DTposX2+300,DTposY2,DT1.getWidth()/7.0,DT1.getHeight()/7.0);
        gc.drawImage(DT9, DTposX2+450,DTposY2,DT1.getWidth()/7.0,DT1.getHeight()/7.0);



        RoundRectangle2D ttHover = new RoundRectangle2D.Float();
        ttHover.setRoundRect(TTposX, TTposY,450, 300,20,20);

        LoadPolygons LP = new LoadPolygons();

        ArrayList<Polygon> polys = LP.getPolygons();



        double DCposX = DTposX;
        double DCposY = screenHeight/5.0+50;

        Image DC1 = master.displayCards.get(0).pic;
        Image DC2 = master.displayCards.get(1).pic;
        Image DC3 = master.displayCards.get(2).pic;
        Image DC4 = master.displayCards.get(3).pic;
        Image DC5 = master.displayCards.get(4).pic;

        gc.drawImage(DC1, DCposX, DCposY, DC1.getWidth()/6.0, DC1.getHeight()/6.0);
        gc.drawImage(DC2, DCposX+150, DCposY, DC1.getWidth()/6.0, DC1.getHeight()/6.0);
        gc.drawImage(DC3, DCposX+300, DCposY, DC1.getWidth()/6.0, DC1.getHeight()/6.0);
        gc.drawImage(DC4, DCposX+450, DCposY, DC1.getWidth()/6.0, DC1.getHeight()/6.0);
        gc.drawImage(DC5, DCposX+600, DCposY, DC1.getWidth()/6.0, DC1.getHeight()/6.0);


        gc.setStroke(Color.BLACK);
        gc.strokeRect(DCposX, screenHeight/20.0,100,100);



        gameScene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ;
                if(ttHover.contains(event.getX(), event.getY())) {

                    gc.clearRect(TTposX,TTposY,500,500);
                    gc.drawImage(TT1, screenWidth / 10.0 - screenWidth / 12.0, TTposY, TT1.getWidth() * (1 / 4.0), TT1.getHeight() * (1 / 4.0));
                    gc.drawImage(TT2, screenWidth * (2 / 10.0) - screenWidth / 12.0, TTposY, TT2.getWidth() * (1 / 4.0), TT2.getHeight() * (1 / 4.0));
                    gc.drawImage(TT3, screenWidth * (3 / 10.0) - screenWidth / 12.0, TTposY, TT3.getWidth() * (1 / 4.0), TT3.getHeight() * (1 / 4.0));
                    gc.drawImage(TT4, screenWidth * (4 / 10.0) - screenWidth / 12.0, TTposY, TT4.getWidth() * (1 / 4.0), TT4.getHeight() * (1 / 4.0));
                    gc.drawImage(TT5, screenWidth * (5 / 10.0) - screenWidth / 12.0, TTposY, TT5.getWidth() * (1 / 4.0), TT5.getHeight() * (1 / 4.0));
                    gc.drawImage(TT6, screenWidth * (6 / 10.0) - screenWidth / 12.0, TTposY, TT6.getWidth() * (1 / 4.0), TT6.getHeight() * (1 / 4.0));
                    gc.drawImage(TT7, screenWidth * (7 / 10.0) - screenWidth / 12.0, TTposY, TT7.getWidth() * (1 / 4.0), TT7.getHeight() * (1 / 4.0));
                    gc.drawImage(TT8, screenWidth * (8 / 10.0) - screenWidth / 12.0, TTposY, TT8.getWidth() * (1 / 4.0), TT8.getHeight() * (1 / 4.0));
                    gc.drawImage(TT9, screenWidth * (9 / 10.0) - screenWidth / 12.0, TTposY, TT9.getWidth() * (1 / 4.0), TT9.getHeight() * (1 / 4.0));

                }
                else{
                    gc.clearRect(TTposX,TTposY,screenWidth,500);
                    gc.drawImage(TT1, TTposX,TTposY , TT1.getWidth()*(1/4.0), TT1.getHeight()*(1/4.0));
                    gc.drawImage(TT2,  TTposX*2 , TTposY, TT2.getWidth()*(1/4.0), TT2.getHeight()*(1/4.0));
                    gc.drawImage(TT3,  TTposX*3 , TTposY, TT3.getWidth()*(1/4.0), TT3.getHeight()*(1/4.0));
                    gc.drawImage(TT4,  TTposX*4 , TTposY, TT4.getWidth()*(1/4.0), TT4.getHeight()*(1/4.0));
                    gc.drawImage(TT5,  TTposX*5 , TTposY, TT5.getWidth()*(1/4.0), TT5.getHeight()*(1/4.0));
                    gc.drawImage(TT6,  TTposX*6 , TTposY, TT6.getWidth()*(1/4.0), TT6.getHeight()*(1/4.0));
                    gc.drawImage(TT7,  TTposX*7 , TTposY, TT7.getWidth()*(1/4.0), TT7.getHeight()*(1/4.0));
                    gc.drawImage(TT8,  TTposX*8 , TTposY, TT8.getWidth()*(1/4.0), TT8.getHeight()*(1/4.0));
                    gc.drawImage(TT9,  TTposX*9 , TTposY, TT9.getWidth()*(1/4.0), TT9.getHeight()*(1/4.0));
                }
            }
        });




        Game.getChildren().addAll(mainMenuBG, canvas, polygons);
        primaryStage.show();
    }


    public static void main(String[] args) {
        Application.launch(args);
    }
}



