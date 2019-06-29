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

/**
 * Frisky_Critter
 *
 * This critter loves to pro-create. It will spend every time step doing so as long as it has energy.
 * Apart from that, this critter will always run from a fight, and if its food, the critter will
 * still run away 75% of the time. This Critter
 * does not have a long lifespan, but does reproduce very often.
 *
 */
public class Critter2 extends Critter {

    //override outline viewColor to change fill color
    @Override
    public javafx.scene.paint.Color viewColor() {
        return javafx.scene.paint.Color.valueOf("#FF5400");
    }

    //Override outlinecolor
    @Override
    public javafx.scene.paint.Color viewOutlineColor() {
        return javafx.scene.paint.Color.valueOf("#BE1D00");
    }

    //ovverride viewShape to set shape
    @Override
    public CritterShape viewShape() {
        return CritterShape.TRIANGLE;
    }

    @Override
    public void doTimeStep() {
        int direction = Critter.getRandomInt(8);
        //Reproduction check
        if( this.getEnergy() > (Params.MIN_REPRODUCE_ENERGY)) {
            Critter2 baby = new Critter2();
            reproduce(baby, direction);
        }
    }

    @Override
    public boolean fight(String opponent) {
        //Eat if Clover
        if(opponent.equals("@")){
            if(Critter.getRandomInt(100)>74)
                return true;
        }
        //only run to new spot if spot unoccupied
        int direction =getRandomInt(8);
        if(look(direction,true)!=null){
            run(direction);
        }
        return false;
    }

    @Override
    public String toString() {
        return "2";
    }
}
