package edu.kit.informatik.car;

import java.util.Comparator;

/**
 * Comparator, that is used for sorting cars by position, starting from the end of the street.
 * @author ubvaa
 * @version 1.2
 */
public class CarsComparator implements Comparator<Car> {
    @Override
    public int compare(Car c1, Car c2) {
        return c2.getPosition() - c1.getPosition();
    }
}