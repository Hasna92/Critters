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

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.*;



/*
 * See the PDF for descriptions of the methods and fields in this
 * class.
 * You may add fields, methods or inner classes to Critter ONLY
 * if you make your additions private; no new public, protected or
 * default-package code or data can be added to Critter.
 */

public abstract class Critter {

    /* START --- NEW FOR PROJECT 5 */
    public enum CritterShape {
        CIRCLE,
        SQUARE,
        TRIANGLE,
        DIAMOND,
        STAR,
        BOWTIE
    }

    /* the default color is white, which I hope makes critters invisible by default
     * If you change the background color of your View component, then update the default
     * color to be the same as your background
     *
     * critters must override at least one of the following three methods, it is not
     * proper for critters to remain invisible in the view
     *
     * If a critter only overrides the outline color, then it will look like a non-filled
     * shape, at least, that's the intent. You can edit these default methods however you
     * need to, but please preserve that intent as you implement them.
     */
    public javafx.scene.paint.Color viewColor() {
        return Color.BLACK;
    }

    public javafx.scene.paint.Color viewOutlineColor() {
        return viewColor();
    }

    public javafx.scene.paint.Color viewFillColor() {
        return viewColor();
    }

    public abstract CritterShape viewShape();

    protected final String look(int direction, boolean steps) {
            this.energy -= Params.LOOK_ENERGY_COST;
            int x = this.x_coord;
            int y = this.y_coord;
            int numSteps;
            if(steps == false) {
                numSteps = 1;
            }else numSteps = 2;

            switch(numSteps) {
                case 0:
                    switch(direction) {
                        //E
                        case 0: x +=1;
                            break;
                        //NE
                        case 1: x +=1;
                            y -=1;
                            break;
                        //N
                        case 2: y -=1;
                            break;
                        //NW
                        case 3: x -=1;
                            y -=1;
                            break;
                        //W
                        case 4: x -=1;
                            break;
                        //SW
                        case 5: x -=1;
                            y +=1;
                            break;
                        //S
                        case 6: y +=1;
                            break;
                        //SE
                        case 7: x +=1;
                            y +=1;
                            break;
                        default:
                            break;
                    }
                    break;
                case 1:
                    switch(direction) {
                        //E
                        case 0: x +=2;
                            break;
                        //NE
                        case 1: x +=2;
                            y -=2;
                            break;
                        //N
                        case 2: y -=2;
                            break;
                        //NW
                        case 3: x -=2;
                            y -=2;
                            break;
                        //W
                        case 4: x -=2;
                            break;
                        //SW
                        case 5: x -=2;
                            y +=2;
                            break;
                        //S
                        case 6: y +=2;
                            break;
                        //SE
                        case 7: x +=2;
                            y +=2;
                            break;
                        default:
                            break;
                    }
                    break;
            }
            for(Critter temp: population) {
                if(x == temp.x_coord && y == temp.y_coord) {
                    return temp.toString();
                }
            }
            return null;
    }

    public static String runStats(List<Critter> critters) {
        return "There are " + critters.size() + " critters of this type.";
    }

    //Yay it works
    public static void displayWorld(Object pane) {

        //Set grid so that we can work on it.
        GridPane toChange = (GridPane)pane;

        //setPadding
        toChange.setVgap(1);
        toChange.setHgap(1);

        //Loop through entire grid of size in Params.
        //Check population for Critters to add add them in correct place.
        //Otherwise make place full of blank Rectangles.
       int sizeOfSquares = 30;

       //hold elements to add into Grid
       List<Shape> fillGrid =  new ArrayList<>();

       //Loop and add as appropriate
       for (int i =0; i < Params.WORLD_HEIGHT;i++){
           for (int j =0; j<Params.WORLD_WIDTH;j++){
               boolean found = false;
               for (Critter critter : population){
                   if(critter.x_coord==j && critter.y_coord==i){
                       //clear first
                       Rectangle rectangle = new Rectangle(sizeOfSquares,sizeOfSquares);
                       rectangle.setStroke(Color.valueOf("#737B8F"));
                       GridPane.setConstraints(rectangle,j,i);
                       fillGrid.add(rectangle);
                       //add new critter
                       Shape critterShape = returnRealShape(critter.viewShape());
                       critterShape.setFill(critter.viewFillColor());
                       critterShape.setStroke(critter.viewOutlineColor());
                       GridPane.setConstraints(critterShape,j,i);
                       GridPane.setHalignment(critterShape, HPos.CENTER);
                       GridPane.setValignment(critterShape, VPos.CENTER);
                       fillGrid.add(critterShape);
                       found=true;
                       break;
                   }
               }
               if(found)
                   continue;
               Rectangle rectangle = new Rectangle(sizeOfSquares,sizeOfSquares);
               rectangle.setStroke(Color.valueOf("#737B8F"));
               GridPane.setConstraints(rectangle,j,i);
               fillGrid.add(rectangle);
           }
       }

       //Add all the correct shapes to the Grid
        toChange.getChildren().addAll(fillGrid);

       //Show the World
        worldWindow.showWorld();

    }


    /* END --- NEW FOR PROJECT 5
			rest is unchanged from Project 4 */


    //Syed - replacing code beyond this point with code from Project 4 Critters.
    //Fields:

    //energy of critter
    private int energy = 0;
    //position of critter
    private int x_coord;
    private int y_coord;
    //if critter is fighting or just moving in time step
    public boolean inFight;
    //holds critter population
    private static List<Critter> population = new ArrayList<Critter>();
    //holds critters children
    private static List<Critter> babies = new ArrayList<Critter>();

    /* Gets the package name.  This assumes that Critter and its
     * subclasses are all in the same package. */
    private static String myPackage;
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    private static Random rand = new Random();

    //Methods:
    /**
     * Generates random integer between 0-max.
     * @param max: - Int that specifies the maximum integer that can be randomly generated.
     * @return: returns the randomly generated integer.
     */
    public static int getRandomInt(int max) {
        return rand.nextInt(max);
    }

    /**
     * Sets seed.
     * @param new_seed: long that is used to setSeed.
     */
    public static void setSeed(long new_seed) {
        rand = new Random(new_seed);
    }

    /**
     * Sets energy
     * @param energy: - int value that specifies the amount of energy to be set to Critter.
     */
    protected void setEnergy(int energy) {
        this.energy = energy;
    }

    /**
     * create and initialize a Critter subclass.
     * critter_class_name must be the qualified name of a concrete
     * subclass of Critter, if not, an InvalidCritterException must be
     * thrown.
     *
     * @param critter_class_name: - string that tells which type of critter is to be created.
     * @throws InvalidCritterException - Will throw an InvalidCritterException if string does not match
     * 									  Any Critter type.
     */
    public static void createCritter(String critter_class_name) throws InvalidCritterException {
        try {

            //Create critter
            Critter crit = (Critter)Class.forName(myPackage + "." + critter_class_name).newInstance();

            //Add critter to population list
            Critter.population.add(crit);

            //set energy
            crit.energy = Params.START_ENERGY;

            //set location
            crit.x_coord = getRandomInt(Params.WORLD_WIDTH);
            crit.y_coord = getRandomInt(Params.WORLD_HEIGHT);

            //set fight status
            crit.inFight = false;

        }
        catch(ClassNotFoundException | IllegalAccessException | InstantiationException e ) {
            throw new InvalidCritterException(critter_class_name);
        }
    }

    /**
     * Gets a list of critters of a specific type.
     *
     * @param critter_class_name: - What kind of Critter is to be listed.
     *        						Unqualified class name.
     * @return List of Critters: - Returns a list of critters of same type.
     * @throws InvalidCritterException - Will throw an InvalidCritterException if string does not match
     * 									  Any Critter type.
     */
    public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
        //Wild card class type
        Class<?> crit = null;
        //Check for Crit Class
        try {
            crit = 	Class.forName(myPackage + "." + critter_class_name);
        }
        //Throw Exception
        catch(ClassNotFoundException e) {
            throw new InvalidCritterException(critter_class_name);
        }
        //Get list
        List<Critter> critList = new ArrayList<Critter>();
        for(int i = 0 ; i < population.size() ; i ++) {
            Critter temp = population.get(i);
            if(crit.isInstance(temp)) {
                critList.add(temp);
            }
        }
        return critList;
    }

    /**
     * Clear the world of all critters, dead and alive. Removes from critter List data structure.
     */
    public static void clearWorld() {
        //population and babies hold all existing critters
        population.clear();
        babies.clear();
    }

    /**
     * As specified, method invokes doTimeStep for every living Critter.
     * Than handles any encounters. Makes clovers for the critter world. Adds any newborn Critters to
     * population and than gets rid of all dead critters.
     *
     */
    public static void worldTimeStep() {
        //Invoke doTimeStep for every living Critter
        for( Critter temp : population) {
            if( temp.energy >0) {
                temp.doTimeStep();
                //Update energy of all Crits
                temp.energy -= Params.REST_ENERGY_COST;
            }
        }
        //take care of encounters after worldTimeStep is invoked
        doEncounters();
        cloverProduction();
        birthToBabies();
        buryTheDead();
    }

    /**
     * Takes care of encounters in the Critter World. If two or more Critters are in the same
     * spot, this method will be invoked to settle encounters.
     */
    public static void doEncounters() {
        for( int i = 0 ; i < population.size() ; i ++) {
            Critter A = population.get(i);

            //Check if A can fight (not dead)
            if(!(A.getEnergy() <= 0)) {
                for( int j = i +1 ; j < population.size() ; j ++) {
                    Critter B = population.get(j);

                    //Check if B can fight (not dead)
                    if(!(B.getEnergy() <= 0)) {

                        //Both critters can fight
                        //Check critter locations
                        if(A.x_coord == B.x_coord && A.y_coord == B.y_coord) {

                            //Check if both Critters WISH to fight
                            A.inFight = true;
                            B.inFight = true;
                            boolean Afight = false;
                            boolean Bfight = false;
                            Afight = A.fight(B.toString());
                            Bfight = B.fight(A.toString());
                            A.inFight = false;
                            B.inFight = false;
                            //Check if A and B are both still alive and in same location
                            if(A.getEnergy() > 0 && B.getEnergy() > 0 && A.x_coord == B.x_coord && A.y_coord == B.y_coord) {
                                //Attack values (from roll dice)
                                int Adamage = 0;
                                int Bdamage = 0;

                                //4 cases
                                //1: A wants to fight, but B doesn't
                                if(Afight && !Bfight) {
                                    Adamage = Critter.getRandomInt(A.energy);
                                    Bdamage = 0;
                                    //Redundant if statement (A always beats B) but will use regardless
                                    if( Adamage >= Bdamage) {
                                        //Award half of losers energy to A
                                        A.energy += B.energy/2;
                                        //Update B energy to 0 (dead)
                                        B.energy = 0;
                                        //immediately remove dead critter in fight
                                        population.remove(j);
                                    }
                                }

                                //2: A doesn't want to fight, but B does
                                else if(!Afight && Bfight) {
                                    Adamage = 0;
                                    Bdamage = Critter.getRandomInt(B.energy);
                                    //Redundant if statement (B always beats A) but will use regardless
                                    if( Bdamage >= Adamage) {
                                        //Award half of losers energy to B
                                        B.energy += A.energy/2;
                                        //UpdateA energy to 0 (dead)
                                        A.energy = 0;
                                        //immediately remove dead critter
                                        population.remove(i);
                                    }
                                }

                                //3: Both A & B want to fight
                                else if( Afight && Bfight) {
                                    Adamage = Critter.getRandomInt(A.energy);
                                    Bdamage = Critter.getRandomInt(B.energy);
                                    if( Adamage > Bdamage) {
                                        //Award half of losers energy to A
                                        A.energy += B.energy/2;
                                        //Update B energy to 0 (dead)
                                        B.energy = 0;
                                        //immediately remove dead critter
                                        population.remove(j);

                                    }
                                    else if(Bdamage > Adamage) {
                                        //Award half of losers energy to B
                                        B.energy += A.energy/2;
                                        //UpdateA energy to 0 (dead)
                                        A.energy = 0;
                                        //immediately remove dead critter
                                        population.remove(i);
                                    }
                                    //Let critter with more energy live
                                    else if(Adamage == Bdamage) {
                                        if(A.energy >= B.energy) {
                                            //Award half of losers energy to A
                                            A.energy += B.energy/2;
                                            //Update B energy to 0 (dead)
                                            B.energy = 0;
                                            //immediately remove dead critter
                                            population.remove(j);
                                        }else {
                                            //Award half of losers energy to B
                                            B.energy += A.energy/2;
                                            //UpdateA energy to 0 (dead)
                                            A.energy = 0;
                                            //immediately remove dead critter
                                            population.remove(i);
                                        }
                                    }
                                }

                                //4: Both dont want to fight
                                else if (!Afight && !Bfight) {
                                    if( A.energy > B.energy) {
                                        A.energy += B.energy/2;
                                        B.energy = 0;
                                        //immediately remove dead critter
                                        population.remove(j);
                                    }else {
                                        B.energy += A.energy/2;
                                        A.energy = 0;
                                        //immediately remove dead critter
                                        population.remove(i);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Refreshes and adds clover critters onto the world.
     */
    public static void cloverProduction() {
        for(int i = 0 ; i < Params.REFRESH_CLOVER_COUNT ; i++) {
            try {
                createCritter("Clover");
            }
            catch(InvalidCritterException e) {
            }
        }
    }

    /**
     * Adds babies to population. Than clears any newborn babies
     * from babies list in critter class.
     */
    public static void birthToBabies() {
        if(!babies.isEmpty()) {
            Critter temp = babies.get(0);
            //add babies to pop
            for(int i = 0 ; i < babies.size() ; i ++) {
                temp = babies.get(i);
                population.add(temp);
            }
            //clear babies
            babies.clear();
        }
    }

    /**
     * This gets rid of any dead critters from list holding all Critters.
     */
    public static void buryTheDead() {
        Iterator<Critter> iterator = population.iterator();
        while(iterator.hasNext()) {
            if(iterator.next().energy <= 0) {
                iterator.remove();
            }
        }

    }


    /**
     * Abstract function to implement how a Critter interacts per timeStep.
     */
    public abstract void doTimeStep();
    /**
     * Abstract method to be implemented that tells whether a Critter will fight or not given an encounter
     * with another Critter.
     *
     * @param oponent - Critter that this critter has encountered.
     * @return - boolean value to show if it will fight(true) or not(false).
     */
    public abstract boolean fight(String oponent);
    /* a one-character long string that visually depicts your critter
     * in the ASCII interface */
    public String toString() {
        return "";
    }

    /**
     * Returns the energy of a critter.
     * @return energy of a critter as int.
     */
    protected int getEnergy() {
        return energy;
    }

    /**
     * Will move the critter one space in the direction specified. The possible directions
     * are ints 0-7 representing all cardinal directions plus diagonals.
     *
     * @param direction - int from 0-7 representing all cardinal directions plus diagonals. (8 directions).
     */
    protected final void walk(int direction) {
        //Get current coordinates and fight status;
        boolean inFight = this.inFight;
        boolean safePlace = false;
        int x = this.x_coord;
        int y = this.y_coord;
        int its = 0;
        //if fighting then must make sure critter moves into free space
        if(inFight) {
            while(!safePlace) {
                its++;
                switch(direction) {
                    //E
                    case 0: x +=1;
                        break;
                    //NE
                    case 1: x +=1;
                        y -=1;
                        break;
                    //N
                    case 2: y -=1;
                        break;
                    //NW
                    case 3: x -=1;
                        y -=1;
                        break;
                    //W
                    case 4: x -=1;
                        break;
                    //SW
                    case 5: x -=1;
                        y +=1;
                        break;
                    //S
                    case 6: y +=1;
                        break;
                    //SE
                    case 7: x +=1;
                        y +=1;
                        break;
                    default:
                        break;
                }
                //Super Smash Bros Bridge map wrap
                if(x >= Params.WORLD_WIDTH) x -= Params.WORLD_WIDTH;
                if(x < 0) x += Params.WORLD_WIDTH;
                if(y >= Params.WORLD_HEIGHT) y -= Params.WORLD_HEIGHT;
                if(y < 0) y += Params.WORLD_HEIGHT;
                //Check for other critters in that location
                for(Critter temp: population) {
                    if(x == temp.x_coord && y == temp.y_coord) {
                        x = this.x_coord;
                        y = this.y_coord;
                    }
                }
                //If x and y are back to original coordinates then look in next direction
                //else update coordinates of Critter
                if(x == this.x_coord && y == this.y_coord) {
                    direction += 1;
                    if(direction == 8) {
                        direction = 0;
                    }
                }else {
                    safePlace = true;
                    this.x_coord = x;
                    this.y_coord = y;
                }
                if(its == 8) {
                    return;
                }
            }
        }else {
            switch(direction) {
                //E
                case 0: x +=1;
                    break;
                //NE
                case 1: x +=1;
                    y -=1;
                    break;
                //N
                case 2: y -=1;
                    break;
                //NW
                case 3: x -=1;
                    y -=1;
                    break;
                //W
                case 4: x -=1;
                    break;
                //SW
                case 5: x -=1;
                    y +=1;
                    break;
                //S
                case 6: y +=1;
                    break;
                //SE
                case 7: x +=1;
                    y +=1;
                    break;
                default:
                    break;
            }
            this.x_coord = x;
            this.y_coord = y;
        }
        //Super Smash Bros Bridge map wrap
        if(this.x_coord >= Params.WORLD_WIDTH) this.x_coord -= Params.WORLD_WIDTH;
        if(this.x_coord < 0) this.x_coord += Params.WORLD_WIDTH;
        if(this.y_coord >= Params.WORLD_HEIGHT) this.y_coord -= Params.WORLD_HEIGHT;
        if(this.y_coord < 0) this.y_coord += Params.WORLD_HEIGHT;
        //Update Energy
        this.energy -= Params.WALK_ENERGY_COST;
    }

    /**
     * Will move the critter two spaces in the direction specified. The possible directions
     * are ints 0-7 representing all cardinal directions plus diagonals.
     * @param direction -  - int from 0-7 representing all cardinal directions plus diagonals. (8 directions).
     */
    protected final void run(int direction) {
        //Get current coordinates and fight status;
        boolean inFight = this.inFight;
        boolean safePlace = false;
        int x = this.x_coord;
        int y = this.y_coord;
        int its = 0;
        //if fighting then must make sure critter moves into free space
        if(inFight) {
            while(!safePlace) {
                its++;
                switch(direction) {
                    //E
                    case 0: x +=2;
                        break;
                    //NE
                    case 1: x +=2;
                        y -=2;
                        break;
                    //N
                    case 2: y -=2;
                        break;
                    //NW
                    case 3: x -=2;
                        y -=2;
                        break;
                    //W
                    case 4: x -=2;
                        break;
                    //SW
                    case 5: x -=2;
                        y +=2;
                        break;
                    //S
                    case 6: y +=2;
                        break;
                    //SE
                    case 7: x +=2;
                        y +=2;
                        break;
                    default:
                        break;
                }
                //Super Smash Bros Bridge map wrap
                if(x >= Params.WORLD_WIDTH) x -= Params.WORLD_WIDTH;
                if(x < 0) x += Params.WORLD_WIDTH;
                if(y >= Params.WORLD_HEIGHT) y -= Params.WORLD_HEIGHT;
                if(y < 0) y += Params.WORLD_HEIGHT;
                //Check for other critters in that location
                for(Critter temp: population) {
                    if(x == temp.x_coord && y == temp.y_coord) {
                        x = this.x_coord;
                        y = this.y_coord;
                    }
                }
                //If x and y are back to original coordinates then look in next direction
                //else update coordinates of Critter
                if(x == this.x_coord && y == this.y_coord) {
                    direction += 1;
                    if(direction == 8) {
                        direction = 0;
                    }
                }else {
                    safePlace = true;
                    this.x_coord = x;
                    this.y_coord = y;
                }
                //looked in all directions
                //just let it die
                if(its == 8) {
                    return;
                }
            }
        }else {
            switch(direction) {
                //E
                case 0: x +=2;
                    break;
                //NE
                case 1: x +=2;
                    y -=2;
                    break;
                //N
                case 2: y -=2;
                    break;
                //NW
                case 3: x -=2;
                    y -=2;
                    break;
                //W
                case 4: x -=2;
                    break;
                //SW
                case 5: x -=2;
                    y +=2;
                    break;
                //S
                case 6: y +=2;
                    break;
                //SE
                case 7: x +=2;
                    y +=2;
                    break;
                default:
                    break;
            }
            this.x_coord = x;
            this.y_coord = y;
        }
        //Super Smash Bros Bridge map wrap
        if(this.x_coord >= Params.WORLD_WIDTH) this.x_coord -= Params.WORLD_WIDTH;
        if(this.x_coord < 0) this.x_coord += Params.WORLD_WIDTH;
        if(this.y_coord >= Params.WORLD_HEIGHT) this.y_coord -= Params.WORLD_HEIGHT;
        if(this.y_coord < 0) this.y_coord += Params.WORLD_HEIGHT;
        this.energy -= Params.RUN_ENERGY_COST;
    }

    /**
     * This method will check if a critter can reproduce and then make its baby appropriately.
     * If successful baby will be stored to be introduced to the world in the next timeStep.
     *
     * @param offspring - Critter type of calling Critter created without specification or place in world.
     * @param direction - ints 0-7 indicating where to put new Critter.
     */
    protected final void reproduce(Critter offspring, int direction) {
        //First check if there is enough energy for reproduction
        if( this.energy < Params.MIN_REPRODUCE_ENERGY) return;

        //Give offspring half of parents energy
        offspring.energy = this.energy/2;

        //Update parents energy levels, rounding up
        int roundUp = this.energy%2;
        this.energy = this.energy/2 + roundUp;

        //Set coordinates
        offspring.x_coord = this.x_coord;
        offspring.y_coord = this.y_coord;

        //Walk child
        offspring.walk(direction);

        //Add to list of babies, born as Critter in next time step
        babies.add(offspring);
    }

    /**
     * The TestCritter class allows some critters to "cheat". If you
     * want to create tests of your Critter model, you can create
     * subclasses of this class and then use the setter functions
     * contained here.
     * <p>
     * NOTE: you must make sure that the setter functions work with
     * your implementation of Critter. That means, if you're recording
     * the positions of your critters using some sort of external grid
     * or some other data structure in addition to the x_coord and
     * y_coord functions, then you MUST update these setter functions
     * so that they correctly update your grid/data structure.
     */
    static abstract class TestCritter extends Critter {

        protected void setEnergy(int new_energy_value) {
            super.energy = new_energy_value;
        }

        protected void setX_coord(int new_x_coord) {
            super.x_coord = new_x_coord;
        }

        protected void setY_coord(int new_y_coord) {
            super.y_coord = new_y_coord;
        }

        protected int getX_coord() {
            return super.x_coord;
        }

        protected int getY_coord() {
            return super.y_coord;
        }

        /**
         * This method getPopulation has to be modified by you if you
         * are not using the population ArrayList that has been
         * provided in the starter code.  In any case, it has to be
         * implemented for grading tests to work.
         *
         * @return population - Returns a list of all living Critters in world.
         */
        protected static List<Critter> getPopulation() {
            return population;
        }

        /**
         * This method getBabies has to be modified by you if you are
         * not using the babies ArrayList that has been provided in
         * the starter code.  In any case, it has to be implemented
         * for grading tests to work.  Babies should be added to the
         * general population at either the beginning OR the end of
         * every timestep.
         *
         * @return babies - Returns a list of all not yet born babies to be added to World.
         */
        protected static List<Critter> getBabies() {
            return babies;
        }
    }


    /**
     * This method converts our CritterShapes into javafx shapes
     * @param cs
     * @return Shape
     */
    private static Shape returnRealShape(CritterShape cs){
        //check if critter shape is square
        if(cs.equals(CritterShape.SQUARE)){
            return new Rectangle(20,20);
        }
        else if(cs.equals(CritterShape.CIRCLE)){
            return new Circle(10);
        }
        else if(cs.equals(CritterShape.BOWTIE)){
            Polygon diamond = new Polygon();
            diamond.getPoints().addAll( new Double[]{
                    0.0,0.0,
                    0.0,20.0,
                    -10.0,10.0,
                    10.0,10.0
            });
            return diamond;
        }
        else if (cs.equals(CritterShape.TRIANGLE)){
            Polygon triangle = new Polygon();
            triangle.getPoints().addAll( new Double[]{
                    -10.0,0.0,
                    10.0,0.0,
                    0.0,20.0,
            });
            return triangle;
        }
        else if (cs.equals(CritterShape.DIAMOND)){
            Polygon diamond = new Polygon();
            diamond.getPoints().addAll( new Double[]{
                    0.0,-10.0,
                    5.0,0.0,
                    0.0,10.0,
                    -5.0,0.0
            });
            return diamond;
        }
        else{
            Polygon star = new Polygon();
            star.getPoints().addAll( new Double[]{
                    -10.0,-10.0,
                    0.0,-5.0,
                    10.0,-10.0,
                    5.0,0.0,
                    10.0,15.0,
                    0.0,5.0,
                    -10.0,15.0,
                    -5.0,0.0
            });
            return star;
        }

    }
}
