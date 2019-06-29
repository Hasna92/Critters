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

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class worldWindow {

    protected static Stage worldStage;
    protected static GridPane worldGrid;

    private static final int worldHeight = 650;
    private static final int worldWidth = 600;

    public static void createWorld(){

        //create stage for world
        worldStage = new Stage();
        worldStage.setTitle("Critter Cam!");
        worldStage.setResizable(true);
        worldStage.setMaxHeight(worldHeight);
        worldStage.setMaxWidth(worldWidth);

        //Create GridPane to hold all the critters and stuff
        worldGrid = new GridPane();
        //Put grid into a scrollpane
        ScrollPane worldScroll = new ScrollPane(worldGrid);
        worldScroll.setFitToWidth(true);
        //Send gridPane to displayWorld so that Critter can manipulate it.
        Critter.displayWorld(worldGrid);

        Scene scene = new Scene(worldScroll);
        scene.getStylesheets().add("ControllerStyle.css");
        worldStage.setScene(scene);


    }

    public static void showWorld(){
        worldStage.show();;
    }

}
