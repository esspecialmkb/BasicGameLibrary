/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

/**
 *  Considering using this class to create ui objects
 * @author michael
 */
public class UIHub extends UIElement{
    
    
    // This is a prototype Publish-Subscibe Server/Hub - tailored to the GUI
    public void post(int topic, int id, String message){
        // Clients use this to post messages to hub
    }

    public int ping(int topic){
        // Clients use this to ping the hub

        // Return the number of matching messages
        return 0;
    }

    public String get(int topic, boolean removeAfterRead){
        // Get message from hub
        return "Message!";
    }
}
