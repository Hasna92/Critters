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

import javafx.scene.paint.Color;

/**
 * Ugly_Critter
 *
 * This Critter is lazy and ugly. It will not move in its timeStep and its chances of reproduction are very
 * low. It has a 33% of finding a mate to reproduce with, and can only do so with 3* the energy required to
 * reproduce, further limiting its options.
 *
 * When it comes to fighting, this critter is all about its gut feeling. It is dumb in this however
 * because it even runs away from food at times.
 *
 */
public class Critter3 extends Critter {

    //change fill
    @Override
    public javafx.scene.paint.Color viewColor() {
        return javafx.scene.paint.Color.valueOf("2F8F5E");
    }

    //Implement viewColor to change outline or fill
    @Override
    public javafx.scene.paint.Color viewOutlineColor() {
        return Color.FIREBRICK;
    }


    //Implement viewShape to setShape
    @Override
    public CritterShape viewShape() {
        return CritterShape.STAR;
    }

    @Override
    public void doTimeStep() {
        int direction = Critter.getRandomInt(8);
        if(Critter.getRandomInt(3) == 0) {
            if( this.getEnergy() > (Params.MIN_REPRODUCE_ENERGY)*3) {
                Critter3 baby = new Critter3();
                reproduce(baby, direction);
            }
        }
    }

    @Override
    public boolean fight(String opponent) {
        int fightOrFlight = Critter.getRandomInt(2);
        if(fightOrFlight == 0) {
            run(getRandomInt(8));
            return false;
        }
        if(fightOrFlight == 1) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "3";
    }
}
