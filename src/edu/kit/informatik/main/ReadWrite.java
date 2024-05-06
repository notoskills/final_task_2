package edu.kit.informatik.main;

import java.io.IOException;
import java.util.Scanner;

/**
 * Class, that implements all the in- and outputs in the program.
 * 
 * @author ubvaa
 * @version 1.1
 */
public final class ReadWrite {

    /**
     * New scanner
     */
    private static Scanner scanner = new Scanner(System.in);
    
    /**
     * Private InOutput constructor
     */
    private ReadWrite() { }
    
    /**
     * Method, that outputs @object in the console
     * @param object
     */
    public static void writeLine(Object object) {
        System.out.println(object);
    }
    
    /**
     * Method, that reads players' input in the console
     * @return players' input as a String
     * @throws IOException
     */
    public static String readLine() throws IOException {
        return scanner.nextLine();
    }
    
    /**
     * Method, that outputs errors in the console
     * @param errorText text of the error
     */
    public static void writeError(String errorText) {
        writeLine("Error: " + errorText + ".");
    }
        
}
