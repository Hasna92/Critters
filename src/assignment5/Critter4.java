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
 * HotHeaded_Critter
 *
 * Fighter, always fights when another critter is encountered of any type. In its timeStep
 * This critter will walk into any random direction, and will always reproduce if it has enough energy.
 */
public class Critter4 extends Critter {

    //Over Ride Color
    @Override
    public javafx.scene.paint.Color viewColor() {
        return javafx.scene.paint.Color.valueOf("#FF1493");
    }

    //Over ride outline
    @Override
    public javafx.scene.paint.Color viewOutlineColor() {
        return javafx.scene.paint.Color.valueOf("#00CED1");
    }

    //Over ride view shape to set Shape
    @Override
    public CritterShape viewShape() {
        return CritterShape.DIAMOND;
    }

    @Override
    public void doTimeStep() {
        int direction = Critter.getRandomInt(8);
        walk(direction);
        if( this.getEnergy() > (Params.MIN_REPRODUCE_ENERGY)) {
            Critter4 baby = new Critter4();
            reproduce(baby, direction);
        }
    }

    @Override
    public boolean fight(String opponent) {
        if(opponent.equals("@")){
            return true;
        }
        return true;
    }

    @Override
    public String toString() {
        return "4";
    }
}
