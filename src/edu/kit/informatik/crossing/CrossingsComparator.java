package edu.kit.informatik.crossing;

import java.util.Comparator;

/**
 * Comparator, that is used for sorting crossings in ascending order of the IDs.
 * @author ubvaa
 * @version 1.2
 */
public class CrossingsComparator implements Comparator<Crossing> {
    @Override
    public int compare(Crossing c1, Crossing c2) {
        return c1.getID() - c2.getID();
    }   
}