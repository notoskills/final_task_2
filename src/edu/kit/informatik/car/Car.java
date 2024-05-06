package edu.kit.informatik.car;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.kit.informatik.crossing.Crossing;
import edu.kit.informatik.main.Main;
import edu.kit.informatik.main.ReadWrite;
import edu.kit.informatik.street.Street;

/**
 * Class, that implements all cars in the program.
 * 
 * @author ubvaa
 * @version 1.2
 */
public class Car implements ICar {
    /**
     * List of all cars of the program
     */
    private static List<Car> cars;
    /**
     * List of inspected cars
     */
    private static List<Car> inspectedCars;
    /**
     * Minimal allowed desired speed
     */
    private static final int MIN_DESIRED_SPEED = 20;
    /**
     * Maximal allowed desired speed
     */
    private static final int MAX_DESIRED_SPEED = 40;
    /**
     * Minimal allowed acceleration
     */
    private static final int MIN_ACCELERATION = 1;
    /**
     * Maximal allowed acceleration
     */
    private static final int MAX_ACCELERATION = 10;
    /**
     * Maximal amount for car declaration arguments
     */
    private static final int CAR_INITIALIZATION_ARGS_AMOUNT = 4;
    /**
     * Maximal possible desired direction
     */
    private static final int MAX_DESIRED_DIRECTION = 3;
    /**
     * ID of the car
     */
    private int id;
    /**
     * Desired speed of the car
     */
    private int desiredSpeed;
    /**
     * Acceleration of the car
     */
    private int acceleration;
    /**
     * Desired direction of the car
     */
    private int desiredDirection;
    /**
     * Current street ID
     */
    private int streetID;
    /**
     * Current speed
     */
    private int currentSpeed;
    /**
     * Current position of the car
     */
    private int position;
    /**
     * Attribute, that indicates, if the car is already moved during the tick
     */
    private boolean alreadyMoved;
    /**
     * Traveled distance of the car
     */
    private int traveledDistance;

    /**
     * Constructor of a new car
     * 
     * @param id - ID of the car
     * @param streetID - ID of the street, where is the car
     * @param desiredSpeed - desired direction of the car
     * @param acceleration - acceleration of the car
     */
    public Car(int id, int streetID, int desiredSpeed, int acceleration) {
        this.id = id;
        this.streetID = streetID;
        this.desiredSpeed = desiredSpeed;
        this.acceleration = acceleration;
        this.desiredDirection = 0;
        this.currentSpeed = 0;
        this.alreadyMoved = false;
    }
    
    /**
     * Getter for the car (by ID)
     * 
     * @param id - ID of the car to be found
     * @return - Car with this ID, if it exists; else - null
     */
    public static Car getCar(int id) {
        for (Car car : cars) {
            if (car.id == id) {
                return car;
            }
        }
        return null;
    }
    
    @Override
    public void setTraveledDistance(int distance) {
        this.traveledDistance = distance;
    }
    
    @Override
    public int getTraveledDistance() {
        return this.traveledDistance;
    }
    
    @Override
    public int getStreetID() {
        return this.streetID;
    }

    @Override
    public boolean isAlreadyMoved() {
        return this.alreadyMoved;
    }
    
    @Override
    public int getPosition() {
        return this.position;
    }
    
    @Override
    public void setPosition(int position) {
        this.position = position;
    }
    
    @Override
    public void setCurrentSpeed(int speed) {
        this.currentSpeed = speed;
    }
    
    @Override
    public int getCurrentSpeed() {
        return this.currentSpeed;
    }
    
    @Override
    public int getDesiredSpeed() {
        return this.desiredSpeed;
    }
    
    @Override
    public int getDesiredDirection() {
        return this.desiredDirection;
    }
    
    @Override
    public int getAcceleration() {
        return this.acceleration;
    }
    
    /**
     * Method, that checks the input cars' file for correctness.
     * 
     * @param carsList - list of the cars from the file
     * @return - true - if everything is correct; else - false
     */
    public static boolean checkInput(List<String> carsList) {
        inspectedCars = new ArrayList<>();
        Set<Integer> idSet = new HashSet<>();
        boolean isCorrect = true;
        for (String line : carsList) {
            try {
                if (line.charAt(0) == Main.COMMA.charAt(0) || line.charAt(line.length() - 1) == Main.COMMA.charAt(0)) {
                    ReadWrite.writeError(Main.INVALID_CAR);
                    isCorrect = false;
                    break;
                }
                String[] carParams = line.split(String.valueOf(Main.COMMA));
                if (carParams.length != CAR_INITIALIZATION_ARGS_AMOUNT) {
                    ReadWrite.writeError(Main.INVALID_CAR);
                    isCorrect = false;
                    break;
                }
                int id = Integer.valueOf(carParams[0]);
                int streetID = Integer.valueOf(carParams[1]);
                int desiredSpeed = Integer.valueOf(carParams[2]);
                int acceleration = Integer.valueOf(carParams[3]);
                if (id < 0) {
                    ReadWrite.writeError(Main.INVALID_ID);
                    isCorrect = false;
                    break;
                } else if (desiredSpeed < MIN_DESIRED_SPEED || desiredSpeed > MAX_DESIRED_SPEED) {
                    ReadWrite.writeError(Main.CAR + id + Main.INVALID_SPEED);
                    isCorrect = false;
                    break;
                } else if (acceleration < MIN_ACCELERATION || acceleration > MAX_ACCELERATION) {
                    ReadWrite.writeError(Main.CAR + id + Main.INVALID_ACCELERATION);
                    isCorrect = false;
                    break;
                } else if (Street.getInspectedStreet(streetID) == null) {
                    ReadWrite.writeError(Main.INVALID_STREET_FOR + Main.CAR + id);
                    isCorrect = false;
                    break;
                } else if (!idSet.add(id)) {
                    ReadWrite.writeError(Main.REPEATED_ID);
                    isCorrect = false;
                    break;
                }
                
                Car car = new Car(id, streetID, desiredSpeed, acceleration);
                Street street = Street.getInspectedStreet(streetID);
                
                street.addCar(car);
                inspectedCars.add(car);
                
            } catch (NumberFormatException e) {
                ReadWrite.writeError(Main.INVALID_CAR);
                isCorrect = false;
                break;
            }
        }
        
        return isCorrect;
    }
    
    /**
     * Method, that copies inspected (temporary) cars to the session's cars' list
     */
    public static void copyInspectedCars() {
        cars = new ArrayList<>(inspectedCars.size());
        for (Car car : inspectedCars) {
            cars.add(car);
        }
    }
    
    @Override
    public void updateSpeed(int speedLimit) {
        List<Integer> speedsList = new ArrayList<>();
        speedsList.add(this.desiredSpeed);
        speedsList.add(this.currentSpeed + this.acceleration);
        speedsList.add(speedLimit);
        int newSpeed = Collections.min(speedsList);
        this.currentSpeed = newSpeed;
    }
    
    @Override
    public void overtake(int distance) {
        Street street = Street.getStreet(this.streetID);
        int postOvertakePosition = this.position + 2 * Street.getMinDistance();
        int distanceLeft = distance - 2 * Street.getMinDistance();
        int endingPosition;
        
        int nextCarPosition = street.getFrontCarPosition(postOvertakePosition); 
        
        if (postOvertakePosition + distanceLeft >= nextCarPosition - Street.getMinDistance()) {
            endingPosition = nextCarPosition - Street.getMinDistance();
        } else {
            endingPosition = postOvertakePosition + distanceLeft;
        }
        
        this.position = endingPosition;
    }
    
    @Override
    public void turn(Crossing crossing, int distance) {
        int oldStreetLength = Street.getStreet(this.streetID).getLength();
        this.traveledDistance = oldStreetLength - this.position;
        int distanceLeft = distance - oldStreetLength;
        
        int desiredDirection = this.desiredDirection;
        if (crossing.getOutgoingStreets().size() < desiredDirection + 1) {
            desiredDirection = 0;
        }
        
        Street desiredStreet = crossing.getOutgoingStreets().get(desiredDirection);
        
        int newPosition = 0;
        
        if (desiredStreet.isFree()) {
            int streetLength = desiredStreet.getLength();
            newPosition = distanceLeft > streetLength ? streetLength : distanceLeft;
        } else {
            int lastCarPosition = desiredStreet.getLastCarPosition();
            
            if (lastCarPosition - Street.getMinDistance() < distanceLeft) {
                newPosition = lastCarPosition - Street.getMinDistance();
            } else {
                newPosition = distanceLeft;
            }
        }
        
        desiredStreet.addCar(this);
        Street.getStreet(this.streetID).removeCar(this);

        this.traveledDistance += newPosition;
        this.position = newPosition;
        this.desiredDirection++;
        this.streetID = desiredStreet.getID();
        this.alreadyMoved = true;
    }
    
    /**
     * Method, that updates all cars after the simulation/tick
     */
    public static void updateCars() {
        for (Car car : cars) {
            car.alreadyMoved = false;
            if (car.desiredDirection > MAX_DESIRED_DIRECTION) {
                car.desiredDirection = 0;
            }
        }
    }
}
