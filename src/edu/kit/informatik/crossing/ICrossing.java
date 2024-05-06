package edu.kit.informatik.crossing;

import java.util.List;

import edu.kit.informatik.car.Car;
import edu.kit.informatik.street.Street;

/**
 * Interface for class Crossing
 * 
 * @author ubvaa
 * @version 1.1
 */
public interface ICrossing {
    /**
     * Getter for crossing's ID
     * 
     * @return - ID of the crossing
     */
    int getID();
    
    /**
     * Getter for outgoing streets
     * 
     * @return - List of outgoing streets
     */
    List<Street> getOutgoingStreets();
    
    /**
     * Method, that adds a new street to the list of the incoming streets
     * 
     * @param street - street to be added
     */
    void addIncomingStreet(Street street);
    
    /**
     * Method, that adds a new street to the list of the outgoing streets
     * 
     * @param street - street to be added
     */
    void addOutgoingStreet(Street street);
    
    /**
     * Method, that proves, if car is allowed to turn on the current crossing
     * 
     * @param car - observed car
     * @return true - if allowed; else - false
     */
    boolean turnAllowed(Car car);
}
