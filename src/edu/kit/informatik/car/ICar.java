package edu.kit.informatik.car;

import edu.kit.informatik.crossing.Crossing;

/**
 * Interface for class Car of the program
 * 
 * @author ubvaa
 * @version 1.2
 */
public interface ICar {

    /**
     * Setter for traveled distance
     * 
     * @param distance - new traveled distance
     */
    void setTraveledDistance(int distance);
    
    /**
     * Getter for traveled distance
     * 
     * @return - traveled distance of the car
     */
    int getTraveledDistance();
    
    /**
     * Getter for current street ID of the car
     * 
     * @return - ID of the current street
     */
    int getStreetID();
    
    /**
     * Method, that proves, if car has already moved during the simulation/tick
     * 
     * @return - true, if car has already moved; else - false
     */
    boolean isAlreadyMoved();
    
    /**
     * Getter for current position of the car
     * 
     * @return - current position of the car
     */
    int getPosition();
    
    /**
     * Setter for current position of the car
     * 
     * @param position - new position of the car
     */
    void setPosition(int position);
    
    /**
     * Setter for current speed of the car
     * 
     * @param speed - new speed of the car
     */
    void setCurrentSpeed(int speed);
    
    /**
     * Getter for current speed of the car
     * 
     * @return - current speed of the car
     */
    int getCurrentSpeed();
    
    /**
     * Getter for desired speed of the car
     * 
     * @return - desired speed of the car
     */
    int getDesiredSpeed();
    
    /**
     * Getter for desired direction of the car
     * 
     * @return - desired direction of the car
     */
    int getDesiredDirection();
    
    /**
     * Getter for acceleration of the car
     * 
     * @return - acceleration of the car
     */
    int getAcceleration();
    
    /**
     * Method, that updates speed of the car at the beginning of each simulation/tick
     * 
     * @param speedLimit - maximal allowed speed on the current street
     */
    void updateSpeed(int speedLimit);
    
    /**
     * Method, that implements overtaking of the car
     * 
     * @param distance - distance, that the car can travel
     */
    void overtake(int distance);
    
    /**
     * Method, that implements turning of the car at crossing
     * 
     * @param crossing - crossing, where the turn takes place
     * @param distance - distance, that the car can travel
     */
    void turn(Crossing crossing, int distance);
}
