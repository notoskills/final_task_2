package edu.kit.informatik.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.kit.informatik.car.Car;
import edu.kit.informatik.crossing.Crossing;
import edu.kit.informatik.street.Street;
import edu.kit.kastel.trafficsimulation.io.SimulationFileLoader;

/**
 * Class, that contains all program commands' implementations.
 * 
 * @author ubvaa
 * @version 1.3
 */
public final class Commands {

    /**
     * Private Commands constructor
     */
    private Commands() { }
    
    /**
     * Method, that handles user's input line and pass handled line 
     * to handleCommand(ArrayList<String> command)-Method
     * 
     * @param line - input line
     */
    public static void handleLine(String line) {
        if (line.length() == 0 || line.substring(line.length() - 1).equals(Main.BLANK_SYMBOL)) {
            ReadWrite.writeError(Main.INVALID_COMMAND);
            return;
        }
        List<String> command = new ArrayList<>(Arrays.asList(line.split(Main.BLANK_SYMBOL)));
        handleCommand(command);
    }
    
    /**
     * Method, that handles command and executes it, if it is correct
     * 
     * @param command - command, that is handled
     */
    private static void handleCommand(List<String> command) {
        String commandName = command.get(0);
        command.remove(0);
        
        switch (commandName) {
            case Main.COMMAND_LOAD:
                load(command);
                break;
            case Main.COMMAND_SIMULATE:
                if (Session.isLoadedCorrectly()) {
                    simulate(command);
                } else {
                    ReadWrite.writeError(Main.INVALID_LOAD);
                }
                break;
            case Main.COMMAND_POSITION:
                if (Session.isLoadedCorrectly()) {
                    position(command);
                } else {
                    ReadWrite.writeError(Main.INVALID_LOAD);
                }
                break;
            case Main.COMMAND_QUIT:
                quit();
                break;
            default:
                ReadWrite.writeError(Main.INVALID_COMMAND);
                break;
        }
    }
    
    /**
     * Method, that implements "load"-command. 
     * 
     * Firstly, it loads data to inspect from the files.
     * Secondly, it checks, if loaded data is correct.
     * If it is, then inspected (temporary) objects will be copied.
     * Then cars will be positioned on the streets and session will be saved, 
     * that the session is loaded correctly.
     * 
     * @param arguments - arguments of the command
     */
    private static void load(List<String> arguments) {
        if (arguments.size() != 1) {
            ReadWrite.writeError(Main.INVALID_COMMAND);
            return;
        }
        String path = arguments.get(0);
        
        try {
            SimulationFileLoader fileLoader = new SimulationFileLoader(path);
            List<String> cars = fileLoader.loadCars();
            List<String> streets = fileLoader.loadStreets();
            List<String> crossings = fileLoader.loadCrossings();
    
            if (Crossing.checkInput(crossings) && Street.checkInput(streets) && Car.checkInput(cars)) {
                if (Crossing.checkStreets() && Street.checkCars()) {
                    copyInspectedObjects();
                    Street.positionCars();
                    Session.loadIsCorrect();
                    ReadWrite.writeLine(Main.READY);
                }
            }
        } catch (IOException e) {
            ReadWrite.writeError(Main.INVALID_COMMAND);
        }
    }

    /**
     * Method, that implements "simulate"-command.
     * 
     * Firstly, it checks, if command arguments are correct. If everything is correct, 
     * then it will repeat objects' updates as many times, as specified.
     * 
     * @param arguments - simulations' amount
     */
    private static void simulate(List<String> arguments) {
        if (arguments.size() != 1) {
            ReadWrite.writeError(Main.INVALID_COMMAND);
            return;
        }
        
        int simulationsAmount = 0;
        
        try {
            simulationsAmount = Integer.valueOf(arguments.get(0));
        } catch (NumberFormatException e) {
            ReadWrite.writeError(Main.INVALID_ARGS + Main.COMMAND_SIMULATE);
            return;
        }
        
        if (simulationsAmount < 0) {
            ReadWrite.writeError(Main.INVALID_ARGS + Main.COMMAND_SIMULATE);
            return;
        }
        
        for (int i = 1; i <= simulationsAmount; i++) {
            updateObjects();
        }
        ReadWrite.writeLine(Main.READY);
    }
    
    /**
     * Method, that implements "position"-command
     * 
     * Firstly, it checks, if command's argument is correct and if car with that ID exists. 
     * If it does, it outputs street, position and speed of the car. Else - outputs error.
     * 
     * @param arguments - ID of the car,
     */
    private static void position(List<String> arguments) {
        if (arguments.size() != 1) {
            ReadWrite.writeError(Main.INVALID_COMMAND);
            return;
        }
        
        int carID = 0;
        
        try {
            carID = Integer.valueOf(arguments.get(0));
        } catch (NumberFormatException e) {
            ReadWrite.writeError(Main.INVALID_ARGS + Main.COMMAND_POSITION);
            return;
        }
        
        Car car = Car.getCar(carID);
        
        if (car != null) {
            ReadWrite.writeLine(Main.CAR + carID + Main.ON_STREET + car.getStreetID() + Main.WITH_SPEED 
                                + car.getCurrentSpeed() + Main.AND_POSITION + car.getPosition());
        } else {
            ReadWrite.writeError(Main.NO_CAR_WITH_ID + carID);
        }
    }
    
    /**
     * Method, that implements "quit"-command.
     * 
     * It will be called, if user's input will be "quit"
     * The program will be terminated.
     */
    private static void quit() {
        Session.endSession();
    }
    
    /**
     * Helping method, that calls copying of inspected copies for each objects' class. 
     */
    private static void copyInspectedObjects() {
        Crossing.copyInspectedCrossings();
        Street.copyInspectedStreets();
        Car.copyInspectedCars();
    }
    
    /**
     * Helping method, that calls updating for each objects' class. 
     */
    private static void updateObjects() {
        Street.updateStreets();
        Crossing.updateCrossings();
        Car.updateCars();
    }
}
