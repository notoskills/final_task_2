package edu.kit.informatik.street;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.kit.informatik.car.Car;
import edu.kit.informatik.car.CarsComparator;
import edu.kit.informatik.crossing.Crossing;
import edu.kit.informatik.main.Main;
import edu.kit.informatik.main.ReadWrite;

/**
 * Class, that implements streets in the program
 * 
 * @author ubvaa
 * @version 1.2
 */
public class Street implements IStreet {
    /**
     * Enum-class for 2 types of the streets: with single lane and with fast lane 
     * 
     * @author ubvaa
     * @version 1.0
     */
    private enum StreetType {
        SINGLELANE, FASTLANE
    }
    
    /**
     * List of all streets of the latest correct load
     */
    private static List<Street> streets;
    /**
     * List of inspected streets
     */
    private static List<Street> inspectedStreets;
    /**
     * Minimal distance between two cars on the street
     */
    private static final int MIN_DISTANCE = 10;
    /**
     * Minimal allowed length of the street
     */
    private static final int MIN_LENGTH = 5;
    /**
     * Maximal allowed length of the street
     */
    private static final int MAX_LENGTH = 10000;
    /**
     * Minimal allowed speed limit of the street
     */
    private static final int MIN_SPEED_LIMIT = 5;
    /**
     * Maximal allowed speed limit of the street
     */
    private static final int MAX_SPEED_LIMIT = 40;
    /**
     * ID of the street
     */
    private int id;
    /**
     * Length of the street
     */
    private int length;
    /**
     * Speed limit of the street
     */
    private int speedLimit;
    /**
     * Type of the street
     */
    private StreetType type;
    /**
     * ID of the crossing, to which the street is "directed" 
     */
    private int endingCrossingID;
    /**
     * List of all cars of the street
     */
    private List<Car> cars;
    
    /**
     * Constructor for a new Street
     * 
     * @param id - ID of the street
     * @param endingCrossingID - ID of the crossing, to which the street is "directed" 
     * @param length - length of the street
     * @param type - type of the street
     * @param speedLimit - speed limit of the street
     */
    public Street(int id, int endingCrossingID, int length, int type, int speedLimit) {
        this.id = id;
        this.endingCrossingID = endingCrossingID;
        this.length = length;
        if (type == 1) {
            this.type = StreetType.SINGLELANE;
        } else {
            this.type = StreetType.FASTLANE;
        }
        this.speedLimit = speedLimit;
        this.cars = new ArrayList<>();
    }
    
    /**
     * Getter for the street (by ID)
     * 
     * @param id - ID of the street
     * @return - Street with this ID, if it exists; else - null
     */
    public static Street getStreet(int id) {
        for (Street street : streets) {
            if (street.id == id) {
                return street;
            }
        }
        return null;
    }
    
    /**
     * Getter for the inspected street (by ID)
     * 
     * @param id - ID of the inspected street
     * @return - Street with this ID, if it exists; else - null
     */
    public static Street getInspectedStreet(int id) {
        for (Street street : inspectedStreets) {
            if (street.id == id) {
                return street;
            }
        }
        return null;
    }
    
    /**
     * Getter for minimal distance between two cars on the street
     * @return - minimal distance between two cars on the street
     */
    public static int getMinDistance() {
        return MIN_DISTANCE;
    }
    
    @Override
    public int getID() {
        return this.id;
    }
    
    /**
     * Method for calculating of cars' max amount on the street
     * 
     * @return - cars' max amount on the street
     */
    private int getCarsLimit() {
        return this.length / MIN_DISTANCE + 1;
    }
    
    @Override
    public List<Car> getCars() {
        return this.cars;
    }
    
    @Override
    public void addCar(Car car) {
        this.cars.add(car);
    }
    
    @Override
    public void removeCar(Car car) {
        this.cars.remove(car);
    }
    
    @Override
    public int getLength() {
        return this.length;
    }
    
    /**
     * Method, that checks the input streets' file for correctness.
     * 
     * @param streetsList - list of the streets from the file
     * @return - true - if everything is correct; else - false
     */
    public static boolean checkInput(List<String> streetsList) {
        inspectedStreets = new ArrayList<>();
        boolean isCorrect = true;
        int id = 0;
        for (String line : streetsList) {
            try {
                int startCrossingID = Integer.valueOf(line.substring(0, line.indexOf(Main.ARROW)));
                int endingCrossingID = Integer.valueOf(line.substring(line.indexOf(Main.ARROW) + 3, 
                                                                      line.indexOf(Main.COLON)));
                int length = Integer.valueOf(line.substring(line.indexOf(Main.COLON) + 1, 
                                                            line.indexOf(Main.METER)));
                int type = Integer.valueOf(line.substring(line.indexOf(Main.COMMA) + 1, 
                                                          line.indexOf(Main.ART)));
                int speedLimit = Integer.valueOf(line.substring(line.lastIndexOf(Main.COMMA) + 1, 
                                                                line.indexOf(Main.MAX)));
                if (length < MIN_LENGTH || length > MAX_LENGTH) {
                    ReadWrite.writeError(Main.STREET + id + Main.INVALID_LENGTH);
                    isCorrect = false;
                    break;
                } else if (speedLimit < MIN_SPEED_LIMIT || speedLimit > MAX_SPEED_LIMIT) {
                    ReadWrite.writeError(Main.STREET + id + Main.INVALID_SPEED_LIMIT);
                    isCorrect = false;
                    break;
                } else if (startCrossingID == endingCrossingID) {
                    ReadWrite.writeError(Main.STREET + id + Main.INVALID_STARTING_ENDING);
                    isCorrect = false;
                    break;
                } else if (type != 1 && type != 2) {
                    ReadWrite.writeError(Main.STREET + id + Main.INVALID_TYPE);
                    isCorrect = false;
                    break;
                } else if (Crossing.getInspectedCrossing(startCrossingID) == null 
                        || Crossing.getInspectedCrossing(endingCrossingID) == null) {
                    ReadWrite.writeError(Main.INVALID_CROSSING_FOR + id);
                    isCorrect = false;
                    break;
                }
                
                Street street = new Street(id, endingCrossingID, length, type, speedLimit);
                
                Crossing startCrossing = Crossing.getInspectedCrossing(startCrossingID);
                Crossing endCrossing = Crossing.getInspectedCrossing(endingCrossingID);
                
                startCrossing.addOutgoingStreet(street);
                endCrossing.addIncomingStreet(street);
                
                inspectedStreets.add(street);
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                ReadWrite.writeError(Main.INVALID_STREET);
                isCorrect = false;
                break;
            }
            id++;
        }
        
        return isCorrect;
    }
    
    /**
     * Method, that checks, if amount of cars on the street isn't bigger, 
     * than maximal allowed amount of cars on this street
     * 
     * @return true - if everything is correct; else - false
     */
    public static boolean checkCars() {
        boolean isCorrect = true;
        for (Street street : inspectedStreets) {
            int carsAmount = street.cars.size();
            if (carsAmount > street.getCarsLimit()) {
                ReadWrite.writeError(Main.STREET + street.id + Main.TOO_MANY_CARS);
                isCorrect = false;
                break;
            }
        }
        
        return isCorrect;
    }
    
    /**
     * Method, that copies inspected (temporary) streets to the session's streets' list
     */
    public static void copyInspectedStreets() {
        streets = new ArrayList<>(inspectedStreets.size());
        for (Street street : inspectedStreets) {
            streets.add(street);
        }
    }
    
    /**
     * Method, that positions all cars at the begin of the simulation
     */
    public static void positionCars() {
        for (Street street : streets) {
            int position = street.length;
            for (Car car : street.cars) {
                car.setPosition(position);
                position -= MIN_DISTANCE;
            }
        }
    }
    
    @Override
    public boolean isFree() {
        return this.cars.size() == 0;
    }
    
    @Override
    public boolean isFull() {
        return this.cars.size() == this.getCarsLimit();
    }
        
    @Override
    public int getLastCarPosition() {
        int position = this.length;
        for (Car car : this.cars) {
            int carPosition = car.getPosition();
            if (carPosition < position) {
                position = carPosition;
            }
        }
        return position;
    }
    
    /**
     * Method, that proves, if the observed car is the first (closest to the end) on the street
     * 
     * @param observedCar - car to be observed
     * @return - true, if the car is the first; else - false
     */
    private boolean isFirst(Car observedCar) {
        for (Car car : this.cars) {
            if (observedCar.getPosition() < car.getPosition()) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int getFrontCarPosition(int observedCarPosition) {
        int frontCarPosition = this.length + MIN_DISTANCE;
        for (Car car : this.cars) {
            int carPosition = car.getPosition();
            if (carPosition > observedCarPosition && carPosition < frontCarPosition) {
                frontCarPosition = carPosition;
            }
        }
        
        return frontCarPosition;
    }
    
    /**
     * Method, that updates streets after every tick
     */
    public static void updateStreets() {
        for (Street street : streets) {
            List<Car> originalCars = new ArrayList<>(street.cars);
            Collections.sort(originalCars, new CarsComparator());
            for (Car car : originalCars) {
                if (car.isAlreadyMoved()) {
                    continue;
                }
                car.updateSpeed(street.speedLimit);
                
                int frontCarPosition = street.getFrontCarPosition(car.getPosition());
                int speed = car.getCurrentSpeed();
                int startingPosition = car.getPosition();
                int endingPosition;
                int streetLength = street.length;
                boolean isFirst = street.isFirst(car);
                
                //Car is already close to the front car
                if (!isFirst && frontCarPosition - MIN_DISTANCE == startingPosition) {
                    endingPosition = startingPosition;
                    car.setTraveledDistance(endingPosition - startingPosition);
                }
                //Car doesn't overtake/turn and just rides to the possible distance
                else if (!isFirst && startingPosition + speed <= frontCarPosition - MIN_DISTANCE 
                        || isFirst && startingPosition + speed <= streetLength) {
                    endingPosition = startingPosition + speed;
                    car.setTraveledDistance(speed);
                    car.setPosition(endingPosition);
                }
                //Car can overtake the front car, if it's allowed. Else car stops at the minimal distance
                else if (!isFirst && startingPosition + speed > frontCarPosition - MIN_DISTANCE) {
                    endingPosition = frontCarPosition - MIN_DISTANCE;
                    car.setPosition(endingPosition);
                    int distanceLeft = startingPosition + speed - endingPosition;
                    
                    if (street.overtakeAllowed(car, distanceLeft)) {
                        car.overtake(distanceLeft);
                    }
                    
                    car.setTraveledDistance(car.getPosition() - startingPosition);
                }
                //Car is first on the street and can turn, if it's allowed. Else car stops at the end of the street.
                else if (isFirst && startingPosition + speed > streetLength) {
                    Crossing crossing = Crossing.getCrossing(street.endingCrossingID);
                    
                    if (crossing.turnAllowed(car)) {
                        int distance = startingPosition + speed;
                        car.turn(crossing, distance);
                    } else {
                        car.setPosition(streetLength);
                        car.setTraveledDistance(streetLength - startingPosition);
                    }
                }
                
                if (car.getTraveledDistance() == 0) {
                    car.setCurrentSpeed(0);
                }
            }
        }
    }
    
    /**
     * Method, that proves, if the car is allowed to overtake the front car.
     * 
     * @param car - observed car, that is proved
     * @param distanceLeft - distance, that is left during the tick
     * @return - true, if the car is allowed to overtake; else - false
     */
    private boolean overtakeAllowed(Car car, int distanceLeft) {
        if (this.type.equals(StreetType.SINGLELANE)) {
            return false;
        } else if (distanceLeft < 2 * MIN_DISTANCE) {
            return false;
        }
        
        int frontCarPosition = this.getFrontCarPosition(car.getPosition());
        int nextCarPosition = this.getFrontCarPosition(frontCarPosition);
        
        if (nextCarPosition >= frontCarPosition + 2 * MIN_DISTANCE) {
            return true;
        }

        return false;
    }
    
    
}
