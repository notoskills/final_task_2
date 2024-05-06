package edu.kit.informatik.street;

import java.util.List;

import edu.kit.informatik.car.Car;

/**
 * Interface for the class Street.
 * 
 * @author ubvaa
 * @version 1.2
 */
public interface IStreet {
    /**
     * Getter for the street's ID
     * 
     * @return - street's ID
     */
    int getID();
    
    /**
     * Getter for the street's cars list
     * 
     * @return - street's cars list
     */
    List<Car> getCars();
    
    /**
     * Method, that adds a new car to the street
     * 
     * @param car - car to be added
     */
    void addCar(Car car);
    
    /**
     * Method, that removes a car from the street after it turned
     * 
     * @param car - car to be removed
     */
    void removeCar(Car car);
    
    /**
     * Getter for the street's length
     * 
     * @return - street's length
     */
    int getLength();
    
    /**
     * Method, that proves, if the street is free
     * 
     * @return - true, if the street is free; else - false
     */
    boolean isFree();
    
    /**
     * Method, that proves, if the street if full
     * 
     * @return - true, if the street is full; else - false
     */
    boolean isFull();
    
    /**
     * Method, that gets position of the last car (closest to the beginning) on the street
     * 
     * @return - position of the car, if the street isn't free; 
     *           else - street's length
     */
    int getLastCarPosition();
    
    /**
     * Method, that gets position of the front car on the street
     * 
     * @param observedCarPosition - position of the observed car
     * @return - position of the front car, if it exists;
     *           else - street's length + minimal distance between two cars
     */
    int getFrontCarPosition(int observedCarPosition);
}
