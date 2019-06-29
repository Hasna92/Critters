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
 * Wise_Critter
 *
 * This is the most Wise critter.It will walk in a random direction per time step,
 * and reproduce only when it has a healthy amount of energy. It reproduces 33% of the time
 * and only when it has at least twice the required energy to do so.
 *
 *
 * On top of that, this Critter will always eat food when it finds it
 * and will fight only when it has a good amount of energy.
 */
public class Critter1 extends Critter {

    //override viewcolor to change this wise crits color to something awesome.
    @Override
    public javafx.scene.paint.Color viewColor() {
        return javafx.scene.paint.Color.valueOf("#7EBCEA");
    }

    //Override the shape method to Set wise critter as diamond.
    @Override
    public CritterShape viewShape() {
        return CritterShape.BOWTIE;
    }

    @Override
        public void doTimeStep() {
            //Move in random direction
            int direction = Critter.getRandomInt(8);
            look(direction,false);
            walk(direction);
            //Reproduction check
            if(Critter.getRandomInt(3) < 1) {
                if( this.getEnergy() > (Params.MIN_REPRODUCE_ENERGY)*2) {
                    Critter1 baby = new Critter1();
                    reproduce(baby, direction);
                }
            }
        }

        @Override
        public boolean fight(String opponent) {
            //Fight if clover
            if(opponent.equals("@")){
                return true;
            }
            if (getEnergy() >= 100) return true;
            else return false;
        }

        public String toString() {
            return "1";
        }
}


