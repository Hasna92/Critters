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

import com.sun.javafx.tk.Toolkit;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Dimension2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;


import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage){

		
	System.out.println((char)('A'+1));
		// primaryStage is created by Java VMtry
		//Set primary stage as control stage
		Stage controlStage = primaryStage;
		createController(controlStage);
		controlStage.show();

		//Will call createWorldStage
		worldWindow.createWorld();

		//Will create Animationwindow
		AnimationWindow.CreateAnimationScreen();

		//Create Stats Screen
		Stats.createStats();


	}


	//Creates the entire stage and scene objects - upper level
	private void createController(Stage controlStage){
		//Set title and some specifications
		controlStage.setTitle("JavaFX Critters Controller");
		controlStage.setHeight(600);
		controlStage.setWidth(500);
		controlStage.setResizable(true);
		controlStage.setAlwaysOnTop(true);

		//Create layout of control Stage. set some specifications
		VBox controllerLayout = new VBox();
		controllerLayout.setSpacing(20);

		//Create Menubar for controller
		MenuBar controllerMenu = new MenuBar();
		createControllerMenu(controllerMenu);
		//add to layout
		controllerLayout.getChildren().add(controllerMenu);

		//add welcome message
		//Create Header Label
		Label welcomeMessage =  new Label("		Welcome to Critters!");
		//style from css doc
		welcomeMessage.setId("welcomeMessage");
		//add it
		controllerLayout.getChildren().add(welcomeMessage);

		//Create GridPane for rest of control window
		GridPane controlGrid = new GridPane();
		//create the rest of the window.
		createControlGrid(controlGrid);
		//add to layout
		controllerLayout.getChildren().add(controlGrid);

		//Create Quit Button
		Button quit = new Button("Quit Program");
		quit.setId("round-red");
		//Set action for quit button (exit program).
		quit.setOnAction(e -> {
			System.exit(0);
		});

		//add to center of layout
		GridPane end = new GridPane();
		end.setHgap(100);
		end.setVgap(15);
		GridPane.setConstraints(quit,3,2);
		end.getChildren().add(quit);
		controllerLayout.getChildren().add(end);

		//Create controller scene
		Scene controllerScene =  new Scene(controllerLayout, 500,600);
		controllerScene.getStylesheets().add("ControllerStyle.css");

		//add to the scene to controller stage and show
		controlStage.setScene(controllerScene);

		//Make it so that closing the controller window will close the program.
		controlStage.setOnCloseRequest(e -> {
			System.exit(0);
		});

	}

	//Method makes and populates full menubar for the controller menu
	private void createControllerMenu(MenuBar controllerMenu){
		//Set specifications for Menubar
		controllerMenu.autosize();

		//Add menus

		//Make View menu
		Menu view = new Menu("_View");
		//Create animation view option
		MenuItem animationScreen = new MenuItem("Configure and View Animation...");
		//set action for animationScreen
		animationScreen.setOnAction(e -> {
			AnimationWindow.Display();
		});
		//create stats view
		MenuItem runStats = new MenuItem("Stats");
		//set action for stats
		runStats.setOnAction(e->{
			Stats.showStats();
		});
		view.getItems().addAll(animationScreen,runStats);

		controllerMenu.getMenus().addAll(view);
	}

	//Method makes and populates the control beyond the menu bar
	private void createControlGrid(GridPane controlGrid){

		//set grid specifications
		controlGrid.setVgap(10);
		controlGrid.setHgap(50);

		//create seed input
		createSeedInput(controlGrid);

		//create critter input
		createCritterInput(controlGrid);

		//create TimeStep input
		createTimeStepInput(controlGrid);


	}

	//create all critter input stuff
	private void createCritterInput(GridPane controlGrid) {
		//Add label for set addCritter
		Label critterLabel =  new Label("   Choose a critter: ");
		GridPane.setConstraints(critterLabel,0,3);

		//add drop down list of Valid Critters.
		ChoiceBox<String> validCritters = new ChoiceBox<>();
		GridPane.setConstraints(validCritters,1,3);

		//Make List of all valid Critters in package and add them to drop down menu
		try {
			String packageName = Main.class.getPackage().toString().split(" ")[1];
			Class[] classes = FindClasses.getClasses(packageName);
			List<Class> validClasses = new ArrayList<>();
			for (int i = 0; i < classes.length; i++) {
				validClasses.add(classes[i]);
			}
			List<String> critterNames =  new ArrayList<>();
			FindClasses.getValidCritters(validClasses,critterNames,0);
			validCritters.getItems().addAll(critterNames);
			validCritters.autosize();
		}catch(ClassNotFoundException | IOException e){
			List<String> critterNames = new ArrayList<>();
			critterNames.add("Fail");
			validCritters.getItems().addAll(critterNames);
		}

		//Add label for number of critters to add
		Label critterLabel2 =  new Label("   Number of Critters: ");
		GridPane.setConstraints(critterLabel2,0,4);

		//add textfield for number of Critters
		TextField critterInput = new TextField();
		critterInput.setPromptText("Enter an int.");
		critterInput.setMinWidth(100);
		GridPane.setConstraints(critterInput,1,4);

		//add button to make Critters
		Button makeCrit = new Button("Make Critters");
		makeCrit.setId("rich-blue");
		GridPane.setConstraints(makeCrit, 1,5);

		//Add feedback Text Spot
		Label feedback = new Label();
		GridPane.setConstraints(feedback,1,6);

		//Employ button action for make Critter.
		makeCrit.setOnAction(e -> {
			if(isValidInt(critterInput.getText())){
				int numCrits = Integer.parseInt(critterInput.getText());
				if(numCrits>=0){
					try {
						for (int i =0; i<numCrits;i++) {
							Critter.createCritter(validCritters.getSelectionModel().getSelectedItem());
						}
					}
					catch (InvalidCritterException ex) {
							System.out.println("No critter added");
							ex.printStackTrace();
					}
					feedback.setText(numCrits+ " " + validCritters.getSelectionModel().getSelectedItem() + "'s created");
					feedback.setId("valid-message");
					Critter.displayWorld(worldWindow.worldGrid);
				}
				else{
					feedback.setText("Not a valid input");
					feedback.setId("error-message");
				}
			}
			else{
				feedback.setText("Not a valid input");
				feedback.setId("error-message");
			}
		});

		//Add all the Critter Fields to Controller
		controlGrid.getChildren().addAll(critterLabel, validCritters, critterLabel2, critterInput,feedback,makeCrit);

	}

	//create all seed input stuff - including button and action for button.
	private void createSeedInput(GridPane controlGrid){
		//Add label for set seed
		Label seedLabel =  new Label("   Set Seed: ");
		GridPane.setConstraints(seedLabel,0,0);

		//add textfield for set seed
		TextField seedInput = new TextField();
		seedInput.setPromptText("Enter an int.");
		seedInput.setMinWidth(100);
		GridPane.setConstraints(seedInput,1,0);

		//Add feedback text
		Label valid = new Label("");
		GridPane.setConstraints(valid,1,2);

		//add button for Set Seed
		Button setSeed = new Button("Set Seed");
		setSeed.setId("rich-blue");
		GridPane.setConstraints(setSeed, 1,1);

		//Set action for Seed Button
		setSeed.setOnAction(e -> {
			if(isValidInt(seedInput.getText())){
				int seed = Integer.parseInt(seedInput.getText());
				if(seed>=0){
					Critter.setSeed(seed);
					valid.setText("Seed set to "+seed);
					valid.setId("valid-message");
				}
				else{
					valid.setText("Not a valid input");
					valid.setId("error-message");
				}
			}
			else{
				valid.setText("Not a valid input");
				valid.setId("error-message");
			}
		});

		//Add everything to Grid (window)
		controlGrid.getChildren().addAll(seedLabel,seedInput,setSeed,valid);
	}

	//create all timeStep input stuff
	private void createTimeStepInput(GridPane controlGrid) {

		//Make a label for inputing number of time steps.
		Label timeStep = new Label("   Number of Time Steps(int): ");
		GridPane.setConstraints(timeStep,0,7);

		//Make a textfield for user submission
		TextField timeEntered =  new TextField();
		timeEntered.setPromptText("Enter an int.");
		GridPane.setConstraints(timeEntered,1,7);

		//Make a button to read user inputted number of time steps
		Button timeEnter =  new Button("Do Time Steps");
		timeEnter.setId("rich-blue");
		GridPane.setConstraints(timeEnter,1,8);

		//Text to show successful
		Label verify = new Label();
		GridPane.setConstraints(verify,1,9);

		//Add button functionality for doTimeSteps
		timeEnter.setOnAction(e -> {

			if(isValidInt(timeEntered.getText())){
				int time = Integer.parseInt(timeEntered.getText());
				if(time>=0){
					for (int i =0; i<time;i++){
						Critter.worldTimeStep();
					}
					Critter.displayWorld(worldWindow.worldGrid);
					verify.setText(time + " time steps completed.");
					verify.setId("valid-message");
				}
				else{
					verify.setText("Not a valid input");
					verify.setId("error-message");
				}
			}
			else{
				verify.setText("Not a valid input");
				verify.setId("error-message");
			}
		});

		//Add all to grid
		controlGrid.getChildren().addAll(timeEnter,timeStep,timeEntered,verify);
	}

	//Checks if inputted int was valid.
	private boolean isValidInt(String input){
		try{
			Integer.parseInt(input);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}

}
