/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.util.Scanner;

/**
 *
 * @author TigerSage
 */
public class StringNumberParseTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] arg)
    {
        // Declaring an variable which
        // holds the input number entered
        int intNumber;
        float floatNumber;
 
        // Creating a Scanner class object to
        // take input from keyboard
        // System.in -> Keyboard
        Scanner sc = new Scanner(System.in);
 
        // Condition check
        // If condition holds true, Continue loop until
        // valid integer is entered by user
        while (true) {
 
            // Display message
            System.out.println("Enter any valid Integer: ");
 
            // Try block to check if any exception occurs
            try {
 
                // Parsing user input to integer
                // using the parseInt() method
                //intNumber = Integer.parseInt(sc.next());
                floatNumber = Float.parseFloat(sc.next());
                // Number can be valid or invalid
 
                // If number is valid, print and display
                // the message and number
                System.out.println("You entered: "
                                   + floatNumber);
 
                // Get off from this loop
                break;
            }
 
            // Catch block to handle NumberFormatException
            catch (NumberFormatException e) {
 
                // Print the message if exception occurred
                System.out.println(
                    "NumberFormatException occurred");
            }
        }
    }
    
}
