package edu.kit.informatik.main;

/**
 * This is Main class, the entry point of the program.
 * Here are initialized all in- and output constants of program.
 * @author ubvaa
 * @version 1.3
 */
public final class Main {
    /**
     * Command quit
     */
    public static final String COMMAND_QUIT = "quit";
    /**
     * Command load
     */
    public static final String COMMAND_LOAD = "load";
    /**
     * Command simulate
     */
    public static final String COMMAND_SIMULATE = "simulate";
    /**
     * Command position
     */
    public static final String COMMAND_POSITION = "position";
    /**
     * "READY"-output
     */
    public static final String READY = "READY";
    /**
     * Blank symbol, that separates command name and command arguments
     */
    public static final String BLANK_SYMBOL = " ";
    /**
     * Comma symbol, that separates cars' arguments
     */
    public static final String COMMA = ",";
    /**
     * Colon symbol, that separates crossings' arguments
     */
    public static final String COLON = ":";
    /**
     * Tick abbreviation, that is used in crossings' file
     */
    public static final String TICK = "t";
    /**
     * Arrow, that is used in streets' file
     */
    public static final String ARROW = "-->";
    /**
     * Meter abbreviation
     */
    public static final String METER = "m";
    /**
     * Abbreviation for a street art
     */
    public static final String ART = "x";
    /**
     * Abbreviation for speed limit
     */
    public static final String MAX = "max";
    /**
     * Invalid command error text
     */
    public static final String INVALID_COMMAND = "invalid command";
    /**
     * Invalid ID error text
     */
    public static final String INVALID_ID = "ID must be >= 0";
    /**
     * Repeated ID error text
     */
    public static final String REPEATED_ID = "all IDs must be unique";
    /**
     * "Crossing"-String for error-outputs
     */
    public static final String CROSSING = "Crossing ";
    /**
     * Invalid green time error text
     */
    public static final String INVALID_GREEN_TIME = " must have green light time either between 3t and 10t or 0t";
    /**
     * Invalid crossing initialization error text
     */
    public static final String INVALID_CROSSING = "all crossings must be following format: "
                                                + "[ID]:[GreenLightTime]t, where all parameters are integers";
    /**
     * Invalid incoming street error text
     */
    public static final String INVALID_INCOMING_AMOUNT = " must have between 1 and 4 incoming streets";
    /**
     * Invalid outgoing street error text
     */
    public static final String INVALID_OUTGOING_AMOUNT = " must have between 1 and 4 outgoing streets";
    /**
     * "Street"-String for error-outputs
     */
    public static final String STREET = "Street ";
    /**
     * Invalid length of the street error text
     */
    public static final String INVALID_LENGTH = " must have length between 10m and 10000m";
    /**
     * Invalid speed limit of the street error text
     */
    public static final String INVALID_SPEED_LIMIT = " must have speed limit between 5m/t and 40m/t";
    /**
     * Invalid starting and ending points of the street error text
     */
    public static final String INVALID_STARTING_ENDING = " must have two differents as starting and ending points";
    /**
     * Invalid street type error text
     */
    public static final String INVALID_TYPE = " must have type either single lane, or with fast lane";
    /**
     * Invalid street format error text
     */
    public static final String INVALID_STREET = "all streets must be following format: "
            + "[StartingPoint]-->[EndingPoint]:[Length]m,[Type]x,[SpeedLimit]max, where all parameters are integers";
    /**
     * Too many cars on the street error text
     */
    public static final String TOO_MANY_CARS = " have too many cars";
    /**
     * Invalid car format error text
     */
    public static final String INVALID_CAR = " all cars must be following format: "
            + "[ID],[Street],[DesiredSpeed],[Acceleration], , where all parameters are integers";
    /**
     * "Car"-String for error-outputs
     */
    public static final String CAR = "Car ";
    /**
     * Invalid car's speed error text
     */
    public static final String INVALID_SPEED = " have desired speed between 20m/t and 40m/t";
    /**
     * Invalid car's acceleration error text
     */
    public static final String INVALID_ACCELERATION = " have acceleration between 1m/t^2 and 10m/t^2";
    /**
     * Street network is yet to be loaded error text
     */
    public static final String INVALID_LOAD = "street network is yet to be loaded";
    /**
     * Invalid command's arguments error text
     */
    public static final String INVALID_ARGS = "invalid arguments of command ";
    /**
     * Invalid car's ID error text
     */
    public static final String NO_CAR_WITH_ID = "There is no car with the identifier ";
    /**
     * Invalid street's ID for the car
     */
    public static final String INVALID_STREET_FOR = "invalid street for ";
    /**
     * Invalid crossing(s) for the street
     */
    public static final String INVALID_CROSSING_FOR = "invalid crossing(s) for street ";
    /**
     * "on street"-String for position output
     */
    public static final String ON_STREET = " on street ";
    /**
     * "with speed"-String for position output
     */
    public static final String WITH_SPEED = " with speed ";
    /**
     * "and position"-String for position output
     */
    public static final String AND_POSITION = " and position ";
    
    /**
     * Private Main constructor
     */
    private Main() { }
    
    /**
     * Entry point of the program
     * @param args - command line arguments
     */
    public static void main(String[] args) {
        Session session = new Session();
        session.begin(args);
    }
}
