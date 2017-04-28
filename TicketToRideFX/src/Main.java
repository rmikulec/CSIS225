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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Main extends Application {

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

        Image test = master.getPlayer(0).dest.get(0).pic;

        gc.drawImage(test, 700, 700);

        LoadPolygons LP = new LoadPolygons();

        ArrayList<Polygon> polys = LP.getPolygons();






        Game.getChildren().addAll(mainMenuBG, canvas, polygons);
        primaryStage.show();
    }


    public static void main(String[] args) {
        Application.launch(args);
    }
}



