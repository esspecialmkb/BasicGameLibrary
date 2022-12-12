/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.util.ArrayList;

/**
 *  Considering using this class to create ui objects
 * @author michael
 */
public class UIHub extends UIElement{
    public static final String textReset = "\u001B[0m";
    public static final String textBlack = "\u001B[30m";
    public static final String textRed = "\u001B[31m";
    public static final String textGreen = "\u001B[32m";
    public static final String textYellow = "\u001B[33m";
    public static final String textBlue = "\u001B[34m";
    public static final String textPurple = "\u001B[35m";
    public static final String textCyan = "\u001B[36m";
    public static final String textWhite = "\u001B[37m";
    
    public static final String textBackBlack = "\u001B[40m";
    public static final String textBackRed = "\u001B[41m";
    public static final String textBackGreen = "\u001B[42m";
    public static final String textBackYellow = "\u001B[43m";
    public static final String textBackBlue = "\u001B[44m";
    public static final String textBackPurple = "\u001B[45m";
    public static final String textBackCyan = "\u001B[46m";
    public static final String textBackWhite = "\u001B[47m";
    
    ArrayList<String> messages = new ArrayList<>();
    
    ArrayList<UIElement> subscribers;
    /*
    Hub topic ids
    0 - UI interaction (Hover)
    1 - Mouse interaction (Click, mouse move)
    */
    
    // This is a prototype Publish-Subscibe Server/Hub - tailored to the GUI
    public void post(int topic, int id, String message){
        // Clients use this to post messages to hub
        System.out.println("UIHUB::POST - Topic: "+topic+", id: "+id+", Message: "+message);
        messages.add(topic+"::"+id+"::"+message);
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
    
    @Override
    public void onUpdate(){
        // Monitor messages
        int mCount = 0;
        int mProc = 0;
        while(messages.size() > 0){
            String get = messages.get(0);
            String[] split = get.split("::");
            // 0: topic, 1: id, 2:message
            
            // PROCESS MESSAGES WE CARE ABOUT
            System.out.println(split[2]);
            // Discard message when done
            messages.remove(0);
            mCount++;
        }
        if(mProc>0){
            System.out.println(mProc + " Messages Processed");
        }
        if(mCount>0){
            System.out.println(mCount + " Messages Discarded");
        }
    }
}
