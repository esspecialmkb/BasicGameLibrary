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
    ArrayList<String> messages = new ArrayList<>();
    
    /*
    Hub topic ids
    1 - Mouse interaction
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
