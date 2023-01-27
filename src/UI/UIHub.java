/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.util.ArrayList;
// This import allows usage of the StringCharConst class
import static Utility.StringCharConst.*;

/**
 *  Considering using this class to create ui objects
 * @author michael
 */
public class UIHub extends UIElement{
    
    ArrayList<String> messages = new ArrayList<>();
    
    ArrayList<UIElement> subscribers;
    
    // The UIHub needs to keep track of whatever element was last hovered over
    // When click events happen, we will use this info to determine what (on the UI) was clicked
    String currentUIHover;    
    
    /*
    Hub topic ids
    0 - UI interaction (Hover)
    1 - Mouse interaction (Click, mouse move)
    */
    
    // This is a prototype Publish-Subscibe Server/Hub - tailored to the GUI
    // Currently, the post method spits out a copy of the incoming message
    public void post(int topic, int id, String message){
        // Clients use this to post messages to hub
        System.out.println(textRed+"UIHUB::POST - Topic: "+textWhite+topic+textRed+", id: "+textWhite+id+", Message: "+textGreen+message+textReset);
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
            
            // Check the number of items resulting from our split
            // If we have more than 3 items then we know there is extra data...            
            if(split.length > 3){
                // What we do with this extra data depends on the message
            }
            // PROCESS MESSAGES WE CARE ABOUT
            System.out.println(textYellow+split[2]+textReset);
            
            // Check the topic of the message
            // 0 - UI Hub Assignments
            // 2 - Mouse hover events
            // 3 - Mouse input events
            switch(Integer.getInteger(split[0])){
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    // The UIHub will receive either...
                    // MOUSEOVER,
                    // HOVER, or
                    // NO_HOVER
                    // We need to assign the element hover tag for MouseOver and Hover events
                    // We need to clear that tag for No_Hover events
                    if(split[3].equals("HOVER")||split[3].equals("MOUSEOVER")){
                        currentUIHover = split[2];
                    }else if(split[3].equals("NO_HOVER")){
                        currentUIHover = "null";
                    }
                    
                    break;
                case 3:
                    // We will receive mouse clicks in two phases...
                    // Mouse (L,M or R) true
                    // Mouse (L,M or R) false
                    
                    break;
            }
            // Discard message when done
            messages.remove(0);
            mCount++;
        }
        if(mProc>0){
            //System.out.println(textGreen + "Found Comment tag..." + textReset);
            System.out.println(textGreen + mProc + " Messages Processed" + textReset);
        }
        if(mCount>0){
            System.out.println(textGreen + mCount + " Messages Discarded" + textReset);
        }
    }
}
