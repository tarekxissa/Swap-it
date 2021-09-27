import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;






public class PuzzleGame extends Application {
    Point2D[][] cords;
    Button[][] buttons;
    String imageholder;
    String[][] imageinitial = new String[4][4];
    String[][] imagecomplete = new String[4][4];
    Image[][] images = new Image[4][4];
    Image[][] images_C = new Image[4][4];
    int count = 0;
    Image b = new Image("BLANK.png");
    int t;
    Timeline updateTimer;


    public void swap(int r, int c, Image[][] c2) {
        Image temp = images[r][c];


        if (r < 3 && images[r + 1][c] == b) {
            imageinitial[r+1][c] = imageinitial[r][c];
            images[r][c] = b;
            buttons[r][c].setGraphic(new ImageView(images[r][c]));
            imageinitial[r][c] = "BLANK.png";
            images[r + 1][c] = temp;

            buttons[r + 1][c].setGraphic(new ImageView(temp));
        } else if (r > 0 && images[r - 1][c] == b) {
            imageinitial[r-1][c] = imageinitial[r][c];
            images[r][c] = b;
            buttons[r][c].setGraphic(new ImageView(images[r][c]));
            imageinitial[r][c] = "BLANK.png";
            images[r - 1][c] = temp;
            buttons[r - 1][c].setGraphic(new ImageView(temp));
        } else if (c > 0 && images[r][c - 1] == b) {
            imageinitial[r][c-1] = imageinitial[r][c];

            images[r][c] = b;
            buttons[r][c].setGraphic(new ImageView(images[r][c]));
            imageinitial[r][c] = "BLANK.png";
            images[r][c - 1] = temp;
            buttons[r][c - 1].setGraphic(new ImageView(temp));
        } else if (c < 3 && images[r][c + 1] == b) {
            imageinitial[r][c+1] = imageinitial[r][c];
            images[r][c] = b;
            buttons[r][c].setGraphic(new ImageView(images[r][c]));
            imageinitial[r][c] = "BLANK.png";
            images[r][c + 1] = temp;
            buttons[r][c + 1].setGraphic(new ImageView(temp));

        }







    }
    public void check() {
        int counter = 0;
        for (int z = 0; z < 4; z++) {
            for (int l = 0; l < 4; l++) {
                ;




                if (imagecomplete[z][l].equals(imageinitial[z][l])) {
                    counter++;


                     if(counter == 16) {
                         updateTimer.stop();
                         for(int k = 0; k < 4; k++){
                             for( int p = 0; p< 4; p++){
                                 buttons[k][p].setDisable(true);
                             }
                         }

                     }

                }
            }
            }
        }






    public void shuffle(){
        for(int r = 0; r < 5000; r++){
            int c = (int)(Math.random()*4);
            int a = (int)(Math.random()* 4);
            swap(c,a, images_C);
        }
    }




    public void start(Stage primaryStage) {
        GridPane aPane = new GridPane();
        aPane.setHgap(1);
        aPane.setVgap(1);
        aPane.setPadding(new Insets(10,10,10,10));
        //Add time label
        Label time1 = new Label("Time");
        aPane.add(time1, 4,2);
        aPane.setValignment(time1,VPos.TOP);
        aPane.setMargin(time1,new Insets(0,0,0,9));
        time1.setStyle("-fx-translate-y: 15; \n");
        //Add timer
        TextField time2 = new TextField("00:00");
        aPane.add(time2, 4 ,2);
        aPane.setValignment(time2,VPos.TOP);
        time2.setStyle("-fx-translate-x: 80;-fx-translate-y: 10;" );
        //time2.setStyle("-fx-translate-y: 15");
        time2.setMaxSize(100,10);


        time2.setEditable(false);


        //Timer
        updateTimer = new Timeline(new KeyFrame(Duration.millis(1000),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        t++;
                        time2.setText(String.format("%d:%02d",t/60,t%60));
                    }
                }));
        updateTimer.setCycleCount(Timeline.INDEFINITE);



       // Adding preview button
        Label d = new Label();
        d.setPrefSize(187,187);
        Image anImage2 = new Image(getClass().getResourceAsStream("Lego_Thumbnail.png"));
        d.setGraphic(new ImageView(anImage2));
        aPane.add(d,4, 0);
        aPane.setMargin(d,new Insets(0,0,0,9));

        //Adding listview
        ListView<String>puzzleList = new ListView<String>();
        String[] puzzles = {"Pets " ,
                "Scenery", "Lego", "Numbers"};
        puzzleList.setItems(FXCollections.observableArrayList(puzzles));
        puzzleList.setPrefWidth(187);
        puzzleList.setMaxHeight(140);
        aPane.add(puzzleList,4,1);
        aPane.setValignment(puzzleList, VPos.TOP);
        aPane.setMargin(puzzleList,new Insets(0,0,0,9));
        puzzleList.getSelectionModel().select(2);
        imageholder = puzzleList.getSelectionModel().getSelectedItem();






        //Adding start/stop button
        Button start = new Button("Start");

        aPane.setValignment(start,VPos.BOTTOM);
        aPane.setMargin(start,new Insets(0,0,0,10));
        start.minHeight(20);
        start.setPadding(new Insets(10));
        start.setStyle("-fx-background-color: darkgreen; -fx-text-fill: white;");

        puzzleList.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                count= 1;

                if(puzzleList.getSelectionModel().getSelectedIndex()== 0) {
                    imageholder = puzzles[0].trim();
                    Image anImage2 = new Image(getClass().getResourceAsStream("Pets_Thumbnail.png"));
                    d.setGraphic(new ImageView(anImage2));

                }
                else if(puzzleList.getSelectionModel().getSelectedIndex()==1){
                    imageholder = puzzles[1];
                    Image anImage2 = new Image(getClass().getResourceAsStream("Scenery_Thumbnail.png"));
                    d.setGraphic(new ImageView(anImage2));

                }
                else if(puzzleList.getSelectionModel().getSelectedIndex()==2) {
                    imageholder = puzzles[2];
                    Image anImage2 = new Image(getClass().getResourceAsStream("Lego_Thumbnail.png"));
                    d.setGraphic(new ImageView(anImage2));
                }
                else if(puzzleList.getSelectionModel().getSelectedIndex()==3) {
                    imageholder = puzzles[3];
                    Image anImage2 = new Image(getClass().getResourceAsStream("Numbers_Thumbnail.png"));
                    d.setGraphic(new ImageView(anImage2));
                }


            }
        });


        start.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {


            if (start.getText().equals("Start")) {
                updateTimer.play();



                start.setText("Stop");
                start.setStyle("-fx-background-color: darkred; -fx-text-fill: white;");
                d.setDisable(true);
                puzzleList.setDisable(true);
                //if (count >0) {
                int aRandomRow = (int) (Math.random() * 4);
                int aRandomColumn = (int) (Math.random() * 4);

                for (int row = 0; row < 4; row++) {
                    for (int col = 0; col < 4; col++) {
                        buttons[row][col].setDisable(false);
                        final int r = row;
                        final int c = col;
                        String s = (imageholder + "_" + row + col + ".png");
                        images[row][col] = new Image(s);
                        images_C[row][col] = new Image(s);
                        imageinitial[row][col] = s;
                        imagecomplete[row][col] = s;
                        buttons[row][col].setGraphic(new ImageView(images[row][col]));

                        images[aRandomRow][aRandomColumn] = b;
                        imageinitial[aRandomRow][aRandomColumn] = "BLANK.png";
                        imagecomplete[aRandomRow][aRandomColumn] = "BLANK.png";
                        buttons[aRandomRow][aRandomColumn].setGraphic(new ImageView(images[aRandomRow][aRandomColumn]));
                        buttons[row][col].setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                swap(r, c, images_C);
                                check();

                                if( buttons[r][c].isDisabled() == true) {
                                    buttons[aRandomRow][aRandomColumn].setGraphic(new ImageView(imageholder +"_"+ aRandomRow + aRandomColumn+".png"));
                                    d.setDisable(false);
                                    puzzleList.setDisable(false);
                                    updateTimer.stop();
                                    start.setText("Start");
                                    start.setStyle("-fx-background-color: darkgreen; -fx-text-fill: white;");
                                }

                            }
                        });
                    }
                }

                shuffle();




            }
            else if(start.getText().equals("Stop")){
                updateTimer.stop();
                for(int i = 0; i < 4; i++){
                    for(int j = 0;  j < 4; j++){
                        d.setDisable(false);
                        puzzleList.setDisable(false);

                        int c = puzzleList.getSelectionModel().getSelectedIndex();
                        d.setGraphic(new ImageView((imageholder+"_Thumbnail.png")));
                        images[i][j] = b;
                        imageinitial[i][j] = "BLANK.png";
                        buttons[i][j].setGraphic(new ImageView(images[i][j]));
                        start.setText("Start");
                        start.setStyle("-fx-background-color: darkgreen; -fx-text-fill: white;");

                    }
                }
            }

        }
    }
    );

        aPane.add(start,4,1);

        start.setPrefWidth(187);



        //




        //Adding the puzzle buttons
        buttons = new Button[4][4];
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                buttons[row][col] = new Button();
                buttons[row][col].setPrefSize(187,187);
                Image anImage = new Image(getClass().getResourceAsStream("BLANK.png"));
                buttons[row][col].setGraphic(new ImageView(anImage));
                buttons[row][col].setPadding(new Insets(0, 0, 0, 0));




                aPane.add(buttons[row][col],col,row);










            }

        }


        primaryStage.setTitle("Slider Puzzle Game");

         primaryStage.setScene(new Scene(aPane));
         primaryStage.show();
         }
        public static void main(String[] args) {
        launch(args);



        }


}