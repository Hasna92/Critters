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

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Stats {

    private static Stage statsStage;

    public static void createStats(){

        //Create Stats window
        statsStage = new Stage();
        statsStage.setHeight(300);
        statsStage.setWidth(500);
        statsStage.setAlwaysOnTop(true);
        statsStage.setTitle("Critter Stats");

        //Create layout and make it
        GridPane statsGrid = new GridPane();
        createStatsGrid(statsGrid);

        //Set scene
        Scene statScene = new Scene(statsGrid,500,300);
        statScene.getStylesheets().add("ControllerStyle.css");
        statsStage.setScene(statScene);

    }

    //generate checkboxes and labels for stats
    private static void createStatsGrid(GridPane statsGrid) {

        //Grid stuff
        statsGrid.setPadding(new Insets(10,10,10,10));
        statsGrid.setHgap(40);

        //Grid will have CheckBoxes and will display Runstats for all checked critters
        //Make List of all valid Critters in package and add them to drop down menu
        List<String> critterNames =  new ArrayList<>();
        try {
            String packageName = Stats.class.getPackage().toString().split(" ")[1];
            Class[] classes = FindClasses.getClasses(packageName);
            List<Class> validClasses = new ArrayList<>();
            for (int i = 0; i < classes.length; i++) {
                validClasses.add(classes[i]);
            }
            FindClasses.getValidCritters(validClasses,critterNames,0);
        }catch(ClassNotFoundException | IOException e){
            critterNames.add("Fail");
        }

        //Create Labels to show stats
        List<Label> labels = new ArrayList<>();
        for (int i = 0; i <critterNames.size() ; i++) {
            Label stats = new Label();
            GridPane.setConstraints(stats,1,i);
            labels.add(stats);
        }
        //Add empty labels to grid
        statsGrid.getChildren().addAll(labels);

        //Now critter names will have all valid critter names in it
        //Create checkboxes for each
        for (int i = 0; i < critterNames.size(); i++) {
            //Create a check box
            CheckBox cb = new CheckBox(critterNames.get(i));
            GridPane.setConstraints(cb,0,i);
            statsGrid.getChildren().add(cb);
        }

        //Individually Set each checkboxes actions
        for(Node n : statsGrid.getChildren()){
            try{
                if(n instanceof CheckBox){
                    ((CheckBox)n).setOnAction(e -> {
                        if(((CheckBox) n).isSelected()){
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        runAndShowStats((CheckBox)n,statsGrid);
                                    }
                                });
                        }else{
                            ((Label)statsGrid.getChildren().get(GridPane.getRowIndex(n))).setText("");
                        }
                    });
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void closeStats(){
        statsStage.close();
    }
    public static void showStats() {statsStage.show();}
    public static void runAndShowStats(CheckBox n, GridPane statsGrid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (n.isSelected()) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            String stats ="";
                            String myPackage = Stats.class.getPackage().toString().split(" ")[1];
                            String className = n.getText();
                            try {
                                //Wild card class type
                                Class<?> crit = null;
                                //Check for Crit Class
                                crit = 	Class.forName(myPackage + "." + className);
                                List<Critter> critterOfSpecClass = Critter.getInstances(className);
                                try {
                                    //Need to get this method to run specific to class type in class name.
                                    //This is because Goblin and clover have their own versions.
                                    Method m = crit.getMethod("runStats",List.class);
                                    stats = (String)m.invoke(crit, critterOfSpecClass);
                                }
                                catch(Exception e) {
                                }
                            }
                            catch(Exception e) {
                            }
                            ((Label)statsGrid.getChildren().get(GridPane.getRowIndex(n))).setText(stats);
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
