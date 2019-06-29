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

public class InvalidCritterException extends Exception {

    String offending_class;

    public InvalidCritterException(String critter_class_name) {
        offending_class = critter_class_name;
    }

    public String toString() {
        return "Invalid Critter Class: " + offending_class;
    }
}
