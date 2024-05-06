package edu.kit.informatik.main;

import java.io.IOException;

/**
 * Class, that implements current session of the simulation.
 * @author ubvaa
 * @version 1.2
 */
public class Session {
    /**
     * Attribute, that shows, if session is running or no
     */
    private static boolean isRunning = true;
    /**
     * Attribute, that shows, if a correct path has been already at least once loaded
     */
    private static boolean isLoadedCorrect = false;
    
    /**
     * Method, that reads user's input, until "quit" isn't entered
     * @param arguments - command line arguments, that will be ignored
     */
    public void begin(String[] arguments) {
        while (isRunning) {
            listen();
        }
    }
    
    /**
     * Method, that reads input of user and pass each input to Commands.handleLine(input)-Method
     */
    private void listen() {
        try {
            String input = ReadWrite.readLine();
            Commands.handleLine(input);
        } catch (IOException e) {
            ReadWrite.writeError(Main.INVALID_COMMAND);
        }
    }
    
    /**
     * Method, that ends session after the "quit"-command
     */
    public static void endSession() {
        isRunning = false;
    }
    
    /**
     * Method, that sets isLoadedCorrect to true, when first correct path is entered
     */
    public static void loadIsCorrect() {
        isLoadedCorrect = true;
    }
    
    /**
     * Method, that returns, if correct path has been ever loaded
     * @return true - if yes, else - false
     */
    public static boolean isLoadedCorrectly() {
        return isLoadedCorrect;
    }
}
