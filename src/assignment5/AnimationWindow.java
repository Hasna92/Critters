/* CRITTERS GUI <MyClass.java>
 * EE422C Project 5 submission by
 * Replace <...> with your actual data.
 * Syed Naqvi
 * smn756
 * 16225
 * Khalid Ahmad
 * kaa2625
 * 16190
 * Slip days used: <0>
 * Spring 2019
 */

/*
   Describe here known bugs or issues in this file. If your issue spans multiple
   files, or you are not sure about details, add comments to the README.txt file.
 */
package assignment5;

import javafx.animation.Animation;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AnimationWindow {

    private static Stage animation;
    private static boolean animationRunning;
    private static int numTimeSteps;
    private static int refreshRate;


    public static void Display(){
        //Cant leave until animation window closed
        animation.showAndWait();

    }

    public static void CreateAnimationScreen(){
        //Cant leave until animation window closed
        createAnimation();
    }
    //Create Animation Stage
    private static void createAnimation(){

        //Set stage
        animation =  new Stage();
        animation.setAlwaysOnTop(true);
        animation.setHeight(300);
        animation.setWidth(500);
        animation.setTitle("Let's Animate!");

        //set modality and ownership
        animation.initModality(Modality.APPLICATION_MODAL);


        //Create Layout for window
        VBox overallLayout = new VBox();
        createLayout(overallLayout);

        //show and wait
        Scene animationScene = new Scene(overallLayout,300,350);
        animationScene.getStylesheets().add("ControllerStyle.css");
        animation.setScene(animationScene);

    }

    //create layout and buttons features
    private static void createLayout(VBox overallLayout) {

        overallLayout.setAlignment(Pos.CENTER);
        //Add label to top of window
        Label animationLabel = new Label("Lets Animate!");
        animationLabel.setId("animate");
        overallLayout.getChildren().add(animationLabel);

        //Create GridPane
        GridPane animationLayout = new GridPane();
        animationLayout.setPadding(new Insets(20,20,20,20));
        animationLayout.setVgap(10);
        animationLayout.setHgap(10);
        //add to overall layout
        overallLayout.getChildren().add(animationLayout);


        //first row
        //Add label
        Label animationSpeed = new Label("Choose an animation speed: ");
        GridPane.setValignment(animationSpeed,VPos.CENTER);
        GridPane.setHalignment(animationSpeed, HPos.CENTER);
        GridPane.setConstraints(animationSpeed,0,0);
        animationLayout.getChildren().add(animationSpeed);

        //Add drop down for user to choose
        ChoiceBox<String> speed = new ChoiceBox();
        speed.getItems().addAll("slow", "medium", "fast");
        //set actions
        speed.setOnAction(e -> {
            if(speed.getSelectionModel().getSelectedItem()=="slow"){
                refreshRate = 10;
            }
            else if(speed.getSelectionModel().getSelectedItem()=="medium"){
                refreshRate = 5;
            }
            else{
                refreshRate = 1;
            }
        });
        //add to grid
        GridPane.setValignment(speed,VPos.CENTER);
        GridPane.setHalignment(speed, HPos.CENTER);
        GridPane.setConstraints(speed,1,0);
        animationLayout.getChildren().add(speed);

        //Row 2
        //Add label
        Label animationFrames = new Label("Number of time Steps per frame: ");
        GridPane.setValignment(animationFrames,VPos.CENTER);
        GridPane.setHalignment(animationFrames, HPos.CENTER);
        GridPane.setConstraints(animationFrames,0,1);
        animationLayout.getChildren().add(animationFrames);

        //Add drop down for user to choose
        ChoiceBox<Integer> frames = new ChoiceBox<>();
        frames.getItems().addAll(1,5,10,20,40,60,100);
        //set action
        frames.setOnAction(e -> {
            numTimeSteps = frames.getSelectionModel().getSelectedItem();
        });
        //add to grid
        GridPane.setValignment(frames,VPos.CENTER);
        GridPane.setHalignment(frames, HPos.CENTER);
        GridPane.setConstraints(frames,1,1);
        animationLayout.getChildren().add(frames);

        //third row
        //Add checkbox for sTats
        CheckBox showStats = new CheckBox("Show Stats");
        //set action
        showStats.setOnAction(e -> {
            if(showStats.isSelected()){
                Stats.showStats();
            }else{
                Stats.closeStats();
            }
        });
        //add to grid
        GridPane.setValignment(showStats,VPos.CENTER);
        GridPane.setHalignment(showStats, HPos.CENTER);
        GridPane.setConstraints(showStats,0,2);
        animationLayout.getChildren().add(showStats);

        //Add button to pause animation
        Button pause = new Button("Run Animation");
        pause.setId("rich-green");
        //set action
        pause.setOnAction(e -> {

            if (pause.getText() == "Run Animation") {
                animationRunning = true;
                pause.setText("Pause Animation");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        doAnimation();
                    }
                });
            } else {
                animationRunning = false;
                pause.setText("Run Animation");
            }

        });
        //add to grid
        GridPane.setValignment(pause,VPos.CENTER);
        GridPane.setHalignment(pause, HPos.CENTER);
        GridPane.setConstraints(pause,1,2);
        animationLayout.getChildren().add(pause);

        //Add button to close animation
        Button close = new Button("Stop and close");
        close.setId("round-red");
        //Set action
        close.setOnAction(e -> {
            animationRunning=false;
            pause.setText("Run Animation");
            animation.close();
        });
        //add to grid
        GridPane.setValignment(close,VPos.CENTER);
        GridPane.setHalignment(close, HPos.CENTER);
        GridPane.setConstraints(close,1,3);
        animationLayout.getChildren().add(close);
    }

    //start and run animation
    private static void doAnimation() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(animationRunning){
                    for (int i = 0; i < numTimeSteps; i++) {
                        Critter.worldTimeStep();
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Critter.displayWorld(worldWindow.worldGrid);
                        }
                    });
                    try {
                        Thread.sleep(refreshRate*500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
