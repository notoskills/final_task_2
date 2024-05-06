package edu.kit.informatik.crossing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.kit.informatik.car.Car;
import edu.kit.informatik.main.Main;
import edu.kit.informatik.main.ReadWrite;
import edu.kit.informatik.street.Street;

/**
 * Class, that implements all crossing of the program.
 * 
 * @author ubvaa
 * @version 1.2
 */
public class Crossing implements ICrossing {
    /**
     * Crossing's type attribute
     * @author ubvaa
     * @version 1.0
     */
    private enum CrossingType {
        ROUNDABOUT, CROSSROAD
    }
    
    /**
     * Minimal green time of the crossroad (in ticks)
     */
    private static final int MIN_GREEN_TIME = 3;
    /**
     * Maximal green time of the crossroad (in ticks)
     */
    private static final int MAX_GREEN_TIME = 10;
    /**
     * Minimal amount of incoming/outgoing streets
     */
    private static final int MIN_STREETS = 1;
    /**
     * Maximal amount of incoming/outgoing streets
     */
    private static final int MAX_STREETS = 4;
    /**
     * List of all crossings of the latest correct load
     */
    private static List<Crossing> crossings;
    /**
     * List of inspected crossings
     */
    private static List<Crossing> inspectedCrossings;
    /**
     * ID of the crossing
     */
    private int id;
    /**
     * Type of the crossing
     */
    private CrossingType type;
    /**
     * Green light time of the crossing
     */
    private int greenTime;
    /**
     * Current time of the crossing
     */
    private int currentTime;
    /**
     * ID of the incoming street with green light
     */
    private int greenID;
    /**
     * List of all incoming streets of the crossing
     */
    private List<Street> incomingStreets;
    /**
     * List of all outgoing streets of the crossing
     */
    private List<Street> outgoingStreets;
    
    /**
     * Crossing constructor
     * 
     * @param id - ID of the crossing
     * @param greenTime - greenTime of the crossing
     */
    public Crossing(int id, int greenTime) {
        this.id = id;
        this.greenTime = greenTime;
        if (greenTime == 0) {
            this.type = CrossingType.ROUNDABOUT;
        } else {
            this.type = CrossingType.CROSSROAD;
            this.greenID = 0;
            this.currentTime = greenTime;
        }
        this.incomingStreets = new ArrayList<>();
        this.outgoingStreets = new ArrayList<>();
    }
    
    /**
     * Getter for inspected crossing (by ID)
     * 
     * @param id - ID of the crossing
     * @return - inspected crossing with this ID, if it exists; else - null
     */
    public static Crossing getInspectedCrossing(int id) {
        for (Crossing crossing : inspectedCrossings) {
            if (crossing.id == id) {
                return crossing;
            }
        }
        return null;
    }
    
    /**
     * Getter for simulation crossing (by ID)
     * 
     * @param id - ID of the crossing
     * @return - crossing with this ID, if it exists; else - null
     */
    public static Crossing getCrossing(int id) {
        for (Crossing crossing : crossings) {
            if (crossing.id == id) {
                return crossing;
            }
        }
        return null;
    }
    
    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public List<Street> getOutgoingStreets() {
        return this.outgoingStreets;
    }
    
    @Override
    public void addIncomingStreet(Street street) {
        this.incomingStreets.add(street);
    }
    
    @Override
    public void addOutgoingStreet(Street street) {
        this.outgoingStreets.add(street);
    }
    
    /**
     * Method, that checks the input crossings' file for correctness.
     * 
     * @param crossingsList - list of the crossings from the file
     * @return - true - if everything is correct; else - false
     */
    public static boolean checkInput(List<String> crossingsList) { 
        inspectedCrossings = new ArrayList<>();
        Set<Integer> idSet = new HashSet<>();
        boolean isCorrect = true;
        for (String line : crossingsList) {
            try {
                int id = Integer.valueOf(line.substring(0, line.indexOf(Main.COLON)));
                int greenTime = Integer.valueOf(line.substring(line.indexOf(Main.COLON) + 1, line.indexOf(Main.TICK)));
                if (id < 0) {
                    ReadWrite.writeError(Main.INVALID_ID);
                    isCorrect = false;
                    break;
                } else if (greenTime > MAX_GREEN_TIME || (greenTime < MIN_GREEN_TIME && greenTime != 0)) {
                    ReadWrite.writeError(Main.CROSSING + String.valueOf(id) + Main.INVALID_GREEN_TIME);
                    isCorrect = false;
                    break;
                } else if (!idSet.add(id)) {
                    ReadWrite.writeError(Main.REPEATED_ID);
                    isCorrect = false;
                    break;
                }
                
                inspectedCrossings.add(new Crossing(id, greenTime));
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                ReadWrite.writeError(Main.INVALID_CROSSING);
                isCorrect = false;
                break;
            }
        }
        
        return isCorrect;
    }
    
    /**
     * Method, that checks, if all crossings have the correct amount of incoming and outgoing streets
     * 
     * @return - true - if amount is correct; else - false
     */
    public static boolean checkStreets() {
        boolean isCorrect = true;
        Collections.sort(inspectedCrossings, new CrossingsComparator());
        for (Crossing crossing : inspectedCrossings) {
            int incomingAmount = crossing.incomingStreets.size();
            int outgoingAmount = crossing.outgoingStreets.size();
            if (incomingAmount > MAX_STREETS || incomingAmount < MIN_STREETS) {
                ReadWrite.writeError(Main.CROSSING + crossing.id + Main.INVALID_INCOMING_AMOUNT);
                isCorrect = false;
                break;
            } else if (outgoingAmount > MAX_STREETS || outgoingAmount < MIN_STREETS) {
                ReadWrite.writeError(Main.CROSSING + crossing.id + Main.INVALID_OUTGOING_AMOUNT);
                isCorrect = false;
                break;
            }
        }
        
        return isCorrect;
    }
    
    /**
     * Method, that copies inspected (temporary) crossings to the session's crossings' list
     */
    public static void copyInspectedCrossings() {
        crossings = new ArrayList<>(inspectedCrossings.size());
        for (Crossing crossing : inspectedCrossings) {
            crossings.add(crossing);
        }
    }
    
    @Override
    public boolean turnAllowed(Car car) {
        int desiredDirection = car.getDesiredDirection();
        int currentStreetID = this.incomingStreets.indexOf(Street.getStreet(car.getStreetID()));
        if (this.outgoingStreets.size() < desiredDirection + 1) {
            desiredDirection = 0;
        }
        
        Street desiredStreet = this.outgoingStreets.get(desiredDirection);
        
        if (this.type.equals(CrossingType.CROSSROAD) && this.greenID != currentStreetID) {
            return false;
        } else if (desiredStreet.isFull()) {
            return false;
        } else if (!desiredStreet.isFree() && desiredStreet.getLastCarPosition() < Street.getMinDistance()) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Method, that updates crossings.
     * It decreases their current time and if current time equals 0,
     * then it switches greenID to the next incoming street.
     */
    public static void updateCrossings() {
        for (Crossing crossing : crossings) {
            if (crossing.type.equals(CrossingType.ROUNDABOUT)) {
                continue;
            }
            
            crossing.currentTime--;
            
            if (crossing.currentTime == 0) {
                crossing.greenID++;
                if (crossing.incomingStreets.size() < crossing.greenID + 1) {
                    crossing.greenID = 0;
                }
                crossing.currentTime = crossing.greenTime;
            }
        }
    }

}
